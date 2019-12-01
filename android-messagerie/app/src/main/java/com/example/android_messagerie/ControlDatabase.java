package com.example.android_messagerie;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ControlDatabase extends AppCompatActivity {
    DatabaseHelper database;
    boolean debug = true;
    TextView debugtext;
    String accountname;

    enum InputsReceived {
        CONNEXION_ACTIVITY,
        INSCRIPTION_ACTIVITY
    }

    InputsReceived inputsActivity;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.debug_layout);

        Intent intent = getIntent();

        if (intent.getStringExtra("sendDatas").equals("inputsFromConnexion")) {
            ArrayList<String> ids = intent.getStringArrayListExtra("data");
            inputsActivity = InputsReceived.CONNEXION_ACTIVITY;

            controlInputs(ids);
        }

        else if (intent.getStringExtra("sendDatas").equals("inputsFromInscription")) {
            ArrayList<String> ids = intent.getStringArrayListExtra("data");
            inputsActivity = InputsReceived.INSCRIPTION_ACTIVITY;

            controlInputs(ids);
        }

        else {
            Log.v("From CONTROLDATABASE","Erreur dans la récéption des données émise par l'utilisateur");
        }
    }

    public void controlInputs(ArrayList<String> ids) {
        database = new DatabaseHelper(this);
        database.createFriendTableIfNotExists();
        database.createMessageTableIfNotExists();

        debugtext = (TextView) findViewById(R.id.text_debug);

        if (inputsActivity == InputsReceived.CONNEXION_ACTIVITY ) {
            Log.v("controlInpus", "inputs de connexion");
            Cursor res = database.getRowFromUserTable(ids.get(0),ids.get(1));

            if (res.getCount() == 0) {
                debugtext.setText(new String("erreur dans les champs"));
                Log.v("From CONTROLDATABASE", "nothing in bdd");
                return;
            } else {
                while(res.moveToNext()) {
                    Log.v("From CONTROLDATABASE", res.getString(1));
                    accountname = res.getString(1);
                }
            }
        }

        if (inputsActivity == InputsReceived.INSCRIPTION_ACTIVITY) {
            Log.v("controlInpus", "inputs de inscription");
            Cursor res = database.getRowFromUserTable(ids.get(0));

            if (res.getCount() > 0) {
                debugtext.setText(new String("pseudonyme déjà utilisé"));
                return;
            } else {
                database.insertDataUserTable(ids.get(0), ids.get(1), ids.get(2));
                accountname = ids.get(0);
            }
        }

        if (debug) {
            for (String id : ids) {
                Log.v("FROM CONTROLDATABASE", id);
            }
        }

        //startActivity(new Intent(this, FriendActivity.class));
        startFriendActivity();
    }

    public void startFriendActivity(){
        Intent vintent = new Intent(this, FriendActivity.class);
        vintent.putExtra("accountname", accountname);

        startActivity(vintent);

        return;
    }

    @Override
    public void onBackPressed() {
        if (inputsActivity == InputsReceived.CONNEXION_ACTIVITY) {
            startActivity(new Intent(this, Connexion.class));
        }

        if (inputsActivity == InputsReceived.INSCRIPTION_ACTIVITY) {
            startActivity(new Intent(this, Inscription.class));
        }
    }
}

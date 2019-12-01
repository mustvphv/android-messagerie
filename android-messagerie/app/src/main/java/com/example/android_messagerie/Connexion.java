package com.example.android_messagerie;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import android.widget.Button;
import android.widget.EditText;

public class Connexion extends AppCompatActivity {
    Button connexionButton;
    EditText pseudo, password;
    List<String> ids = new ArrayList<String>();
    boolean debug = false;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connexion);

        connexionButton = (Button) findViewById(R.id.button_connexion);
        pseudo = (EditText) findViewById(R.id.pseudoc);
        password = (EditText) findViewById(R.id.passwordc);

        connexionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ids.add(pseudo.getText().toString());
                ids.add(password.getText().toString());

                if (debug) {
                    for (String id : ids) {
                        Log.v("FROM CONNEXION", id);
                    }
                }

                if (!(checkUserInputs(ids.get(0), ids.get(1)))) { Log.v("OULALA", ids.get(0) + " " + ids.get(1)); clearInputs(); return; }

                sendDatas();
            }
        });

        return;
    }

    public void clearInputs() {
        pseudo.getText().clear();
        password.getText().clear();
        ids.clear();

        return;
    }

    //envoie les données entrées par l'utilisateur à la class ControlDatabase
    public void sendDatas(){
        Intent vintent = new Intent(this, ControlDatabase.class);
        vintent.putStringArrayListExtra("data", (ArrayList<String>) ids);
        vintent.putExtra("sendDatas", "inputsFromConnexion");

        startActivity(vintent);

        return;
    }

    //vérifie si les inputs de l'utilisateur respecte les conventions
    public boolean checkUserInputs(String pseudo, String password) {
        if (pseudo.length() > 12 || password.length() > 12 || pseudo.length() < 4 || password.length() < 4) {
            return  false;
        }

        return true;
    }
}


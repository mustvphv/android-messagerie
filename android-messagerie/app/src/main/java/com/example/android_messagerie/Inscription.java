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

public class Inscription extends AppCompatActivity {
    Button inscriptionButton;
    EditText pseudo, email, password, confirmpassword;
    boolean debug = true;
    List<String> ids = new ArrayList<String>();

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inscription);

        inscriptionButton = (Button) findViewById(R.id.button_inscrire);
        pseudo = (EditText) findViewById(R.id.pseudo);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        confirmpassword = (EditText) findViewById(R.id.password2);

        inscriptionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ids.add(pseudo.getText().toString());
                ids.add(email.getText().toString());
                ids.add(password.getText().toString());
                ids.add(confirmpassword.getText().toString());

                if (debug) {
                    for (String id : ids) {
                        Log.v("FROM INSCRIPTION", id);
                    }
                }

                if (!(checkUserInputs(ids.get(0), ids.get(1), ids.get(2)))) { clearInputs(); return; }
                if (!(checkPassword(ids.get(2), ids.get(3)))) { clearInputs(); return; }

                sendDatas();
            }
        });

        return;
    }
    //envoie les données entrées par l'utilisateur à la class ControlDatabase
    public void sendDatas(){
        Intent vintent = new Intent(this, ControlDatabase.class);
        vintent.putStringArrayListExtra("data", (ArrayList<String>) ids);
        vintent.putExtra("sendDatas", "inputsFromInscription");

        startActivity(vintent);

        return;
    }

    public Boolean checkPassword(String pass, String cpass) {
        if (pass.equals(cpass)) {
            return true;
        }

        return false;
    }

    //vérifie si les inputs de l'utilisateur respecte les conventions
    public boolean checkUserInputs(String pseudo, String email, String password) {
        if (pseudo.length() > 12 || password.length() > 12 || pseudo.length() < 5 || password.length() < 5) {
            return  false;
        }

        return true;
    }

    public void clearInputs() {
        pseudo.getText().clear();
        email.getText().clear();
        password.getText().clear();
        confirmpassword.getText().clear();
        ids.clear();

        return;
    }
}


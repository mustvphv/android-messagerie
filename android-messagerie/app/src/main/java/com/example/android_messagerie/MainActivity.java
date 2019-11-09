package com.example.android_messagerie;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_layout);
    }

    public void inscription(View view){
        startActivity(new Intent(this, Inscription.class));
    }

    public void connexion(View view){
        startActivity(new Intent(this, Connexion.class));
    }
}
package com.example.android_messagerie;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    boolean debug = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
    }

    //démarre une nouvelle activité (inscription)
    public void inscription(View view){
        startActivity(new Intent(this, Inscription.class));
    }

    //démarre une nouvelle activité (connexion)
    public void connexion(View view){
        startActivity(new Intent(this, Connexion.class));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }
}
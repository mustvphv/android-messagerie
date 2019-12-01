package com.example.android_messagerie;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class NotificationFriendActivity extends AppCompatActivity {
    String providername, idrow, accountname;
    DatabaseHelper database;
    TextView friendrequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_layout);

        database = new DatabaseHelper(this);

        Intent intent = getIntent();
        accountname =  intent.getStringExtra("accoutname");
        providername =  intent.getStringExtra("providername");
        idrow =  intent.getStringExtra("id");

        friendrequest = findViewById(R.id.request);
        friendrequest.setText("Nouvelle demande d'ami de " + providername);

        return;
    }

    public void accepter(View view) {
        database.updateStatusFriendRequest(idrow, "accpt");
        Intent vintent = new Intent(this, FriendActivity.class);
        vintent.putExtra("accountname", accountname);

        startActivity(vintent);

        return;
    }

    public void refuser(View view) {
        database.updateStatusFriendRequest(idrow, "refuser");
        Intent vintent = new Intent(this, FriendActivity.class);
        vintent.putExtra("accountname", accountname);

        startActivity(vintent);

        return;
    }

    @Override
    public void onBackPressed() {
        Intent vintent = new Intent(this, FriendActivity.class);
        vintent.putExtra("accountname", accountname);

        startActivity(vintent);

        return;
    }
}


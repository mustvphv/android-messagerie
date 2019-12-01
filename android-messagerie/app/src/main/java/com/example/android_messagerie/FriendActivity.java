package com.example.android_messagerie;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FriendActivity extends AppCompatActivity {
    LinearLayout linearlayout, linearlayout2;
    DatabaseHelper database;
    boolean debug = true;
    String accountname, providername, idprovider, currentfriend;
    EditText friendname;
    TextView notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_layout);

        database = new DatabaseHelper(this);

        Intent intent = getIntent();
        accountname = intent.getStringExtra("accountname");

        if (debug) {
            Log.v("From FriendActivity", "accout name " + accountname);
        }

        linearlayout  = findViewById(R.id.linearlayout);
        linearlayout2 = findViewById(R.id.linearlayout2);
        friendname    = findViewById(R.id.pseudo);
        notification  = findViewById(R.id.notification);

        showFriendRequest();
        showFriendsList();

        return;
    }

    public void addFriend(View view) {
        database.insertDataFriendList(accountname, friendname.getText().toString(), "attente");
        if (debug) {
            Log.v("From FriendActivity", "addFriend");
        }

        friendname.getText().clear();

        return;
    }

    public void showFriendRequest() {
        Cursor res = database.hasFriendRequest(accountname);

        if (res.getCount() == 0) {
            notification.setText(new String("Tu n'as pas de demande d'ami"));
        } else {
            while (res.moveToNext()) {
                notification.setText(new String("Tu as une demande d'ami"));
                providername = res.getString(1);
                idprovider = res.getString(0);
                final Button seefriendrequest = new Button(this);
                seefriendrequest.setLayoutParams(new LinearLayout.LayoutParams(150, 70));
                seefriendrequest.setText("voir");

                linearlayout2.addView(seefriendrequest);
                seefriendrequest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switchOnNotificationActivity();
                    }
                });
            }
        }

        return;
    }

    public void showFriendsList() {
        List<String> friends = new ArrayList<String>();
        Cursor res = database.readFriendsProvider(accountname);

        while (res.moveToNext()) {
            friends.add(res.getString(2));
        }

        res = database.readFriendsRequest(accountname);

        while (res.moveToNext()) {
            friends.add(res.getString(1));
        }


        for (int i = 0; i < friends.size(); ++i) {
            final Button button = new Button(this);
            button.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            button.setId(i);
            button.setText(friends.get(i));

            linearlayout.addView(button);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentfriend = button.getText().toString();
                    switchOnMessageActivity();
                }
            });
        }

        return;
    }

    public void switchOnNotificationActivity() {
        Intent vintent = new Intent(this, NotificationFriendActivity.class);
        vintent.putExtra("providername", providername);
        vintent.putExtra("id", idprovider);
        vintent.putExtra("accoutname", accountname);

        startActivity(vintent);

        return;
    }

    public void switchOnMessageActivity() {
        Intent vintent = new Intent(this, MessageActivity.class);
        vintent.putExtra("accoutname", accountname);
        vintent.putExtra("currentfriend", currentfriend);

        startActivity(vintent);

        return;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));

        return;
    }
}

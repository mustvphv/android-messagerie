package com.example.android_messagerie;

import android.content.Intent;
import android.database.Cursor;
import android.icu.text.Edits;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MessageActivity extends AppCompatActivity {
    String accountname, currentfriend;
    TextView messageshowed;
    DatabaseHelper database;
    EditText messagecontent;
    boolean debug = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_layout);

        Intent intent = getIntent();
        accountname   =  intent.getStringExtra("accoutname");
        currentfriend =  intent.getStringExtra("currentfriend");

        database = new DatabaseHelper(this);

        messageshowed  = findViewById(R.id.message);
        messagecontent = findViewById(R.id.message_content);

        readMessage();

        return;
    }

    public void sendMessage(View view) {
        database.insertDataMessage(accountname, currentfriend, messagecontent.getText().toString());
        messagecontent.getText().clear();

        return;
    }

    public void readMessage() {
        Map<Integer, String> unsortedmessages = new HashMap<Integer, String>();
        //final List<String> messages = new ArrayList<String>();
        Cursor res = database.readMessageFromTable(accountname, currentfriend, 1);

        while (res.moveToNext()) {
            unsortedmessages.put(Integer.parseInt(res.getString(0)), res.getString(1) + ": " + res.getString(3));
        }

        res = database.readMessageFromTable(accountname, currentfriend, 0);

        while (res.moveToNext()) {
            unsortedmessages.put(Integer.parseInt(res.getString(0)), res.getString(1) + ": " + res.getString(3));
        }

        Map<Integer, String> sortedmessages = new TreeMap<Integer, String>(unsortedmessages);
        if (debug) {
            printHashMap(sortedmessages);
        }

        printMessageToTextView(sortedmessages);

        return;
    }

    public void printMessageToTextView(Map mp) {
        Iterator it = mp.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            messageshowed.setText(messageshowed.getText().toString() + pair.getValue().toString() + "\n");
            it.remove();
        }
    }

    public void printHashMap(Map mp) {
        Iterator it = mp.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            Log.v("Mon Hashmap : ", pair.getKey() + " " + pair.getValue());
            it.remove();
        }
    }

    @Override
    public void onBackPressed() {
        Intent vintent = new Intent(this, FriendActivity.class);
        vintent.putExtra("accountname", accountname);

        startActivity(vintent);

        return;
    }
}

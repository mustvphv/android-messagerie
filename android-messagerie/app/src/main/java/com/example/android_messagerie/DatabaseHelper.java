package com.example.android_messagerie;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.Arrays;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "messagerieuser.db";
    public static final ArrayList<String> USER_TABLE = new ArrayList<String>(Arrays.asList(
            "user_table",   /* nom   */
            "ID",           /* col_1 */
            "NAME",         /* col_2 */
            "EMAIL",        /* col_3 */
            "PASSWORD"      /* col_4 */
    ));

    public static final ArrayList<String> FRIEND_TABLE = new ArrayList<String>(Arrays.asList(
            "friend_table", /* nom   */
            "ID",           /* col_1 */
            "PROVIDER",     /* col_2 */
            "REQUEST",      /* col_3 */
            "STATUS"        /* col_4 */
    ));

    public static final ArrayList<String> MESSAGE_TABLE = new ArrayList<String>(Arrays.asList(
            "message_table", /* nom   */
            "ID",            /* col_1 */
            "SENDER",        /* col_2 */
            "RECEIVER",      /* col_3 */
            "MESSAGE"        /* col_4 */
    ));

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    ////////////USER_TABLE////////////

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + USER_TABLE.get(0) + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, EMAIL TEXT, PASSWORD TEXT)");

        return;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int NewVersion){
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE.get(0));
        onCreate(db);

        return;
    }

    /* enregistre les données dans la table user_table */
    public boolean insertDataUserTable(String name, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_TABLE.get(2), name);
        contentValues.put(USER_TABLE.get(3), email);
        contentValues.put(USER_TABLE.get(4), password);

        long res = db.insert(USER_TABLE.get(0), null, contentValues);

        if (res == -1) { return false; }
        else { return true; }
    }

    /* retourne toutes les données de la table user_table */
    public Cursor getAllDataFromUserTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + USER_TABLE.get(0), null);

        return res;
    }

    /* retourne la ligne de la table user_table où le pseudo & le mdp sont égaux aux paramètres */
    public Cursor getRowFromUserTable(String psd, String pass) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + USER_TABLE.get(0) + " WHERE " + USER_TABLE.get(2) + " = '" + psd + "'"
                + " AND " + USER_TABLE.get(4) + " = '" + pass + "'", null);

        return res;
    }

    /* retourne la ligne de la table user_table où le pseudo est egal au paramètre */
    public Cursor getRowFromUserTable(String psd) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + USER_TABLE.get(0) + " WHERE " + USER_TABLE.get(2) + " = '" + psd + "'", null);

        return res;
    }

    /*vide la table user_table*/
    public void deleteUserTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(USER_TABLE.get(0), null, null);
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + USER_TABLE.get(0) + "'");
    }

    ////////////FRIEND_TABLE////////////

    /* construit la table friend_table si elle n'existe pas */
    public void createFriendTableIfNotExists() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("CREATE TABLE IF NOT EXISTS " + FRIEND_TABLE.get(0) + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, PROVIDER TEXT, REQUEST TEXT, STATUS TEXT)");

        return;
    }

    /* enregistre les données dans la table friend_table */
    public boolean insertDataFriendList(String provider, String request, String status) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(FRIEND_TABLE.get(2), provider);
        contentValues.put(FRIEND_TABLE.get(3), request);
        contentValues.put(FRIEND_TABLE.get(4), status);

        long res = db.insert(FRIEND_TABLE.get(0), null, contentValues);

        if (res == -1) { return false; }
        else { return true; }
    }

    public Cursor readFriendsProvider(String psd) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + FRIEND_TABLE.get(0) + " WHERE " + FRIEND_TABLE.get(2)  + " = '" + psd + "'"
                + " AND " + FRIEND_TABLE.get(4) + " = '" + "accpt" + "'", null);

        return res;
    }

    public Cursor readFriendsRequest(String psd) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + FRIEND_TABLE.get(0) + " WHERE " + FRIEND_TABLE.get(3)  + " = '" + psd + "'"
                + " AND " + FRIEND_TABLE.get(4) + " = '" + "accpt" + "'", null);

        return res;
    }

    public Cursor hasFriendRequest(String psd) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + FRIEND_TABLE.get(0) + " WHERE " + FRIEND_TABLE.get(3) + " = '" + psd + "'"
                + " AND " + FRIEND_TABLE.get(4) + " = '" + "attente" + "'" ,null);

        return res;
    }

    public boolean updateStatusFriendRequest(String id, String status) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(FRIEND_TABLE.get(1), id);
        contentValues.put(FRIEND_TABLE.get(4), status);

        db.update(FRIEND_TABLE.get(0), contentValues, "ID = ?", new String[] { id });

        return true;
    }


    ////////////MESSAGE_TABLE////////////

    public void createMessageTableIfNotExists() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("CREATE TABLE IF NOT EXISTS " + MESSAGE_TABLE.get(0) + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, SENDER TEXT, RECEIVER TEXT, MESSAGE TEXT)");

        return;
    }

    public boolean insertDataMessage(String sender, String receiver, String message) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(MESSAGE_TABLE.get(2), sender);
        contentValues.put(MESSAGE_TABLE.get(3), receiver);
        contentValues.put(MESSAGE_TABLE.get(4), message);

        long res = db.insert(MESSAGE_TABLE.get(0), null, contentValues);

        if (res == -1) { return false; }
        else { return true; }
    }

    public Cursor readMessageFromTable(String sender, String receiver, int req) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res;
        if (req == 1 ) {
            res = db.rawQuery("select * from " + MESSAGE_TABLE.get(0) + " WHERE " + MESSAGE_TABLE.get(2) + " = '" + sender + "'"
                    + " AND " + MESSAGE_TABLE.get(3) + " = '" + receiver + "'", null);
        } else {
            res = db.rawQuery("select * from " + MESSAGE_TABLE.get(0) + " WHERE " + MESSAGE_TABLE.get(2) + " = '" + receiver + "'"
                    + " AND " + MESSAGE_TABLE.get(3) + " = '" + sender + "'", null);
        }

        return res;
    }
}

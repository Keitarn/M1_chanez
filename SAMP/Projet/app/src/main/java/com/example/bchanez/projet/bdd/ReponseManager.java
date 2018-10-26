package com.example.bchanez.projet.bdd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.bchanez.projet.model.Reponse;

public class ReponseManager {
    public static final String TABLE_NAME_REPONSE = "reponse";
    public static final String KEY_ID_REPONSE = "id";
    public static final String KEY_TEXT_REPONSE = "text";
    public static final String KEY_VRAI_REPONSE = "vrai";
    public static final String KEY_ID_QUESTION_REPONSE = "question_id";
    public static final
    String CREATE_TABLE_REPONSE = "CREATE TABLE " + TABLE_NAME_REPONSE + " (" +
            " " + KEY_ID_REPONSE + " INTEGER primary key autoincrement," +
            " " + KEY_TEXT_REPONSE + " TEXT," +
            " " + KEY_VRAI_REPONSE + " BOOLEAN," +
            " " + KEY_ID_QUESTION_REPONSE + " INTEGER," +
            " " + "FOREIGN KEY(" + KEY_ID_QUESTION_REPONSE + ") REFERENCES " +
            QuestionManager.TABLE_NAME_QUESTION + "(" + QuestionManager.KEY_ID_QUESTION + ")" +
            " ON DELETE CASCADE);";

    private MySQLite maBaseSQLite;
    private SQLiteDatabase db;
    private Context context;

    public ReponseManager(Context context) {
        this.context = context;
        maBaseSQLite = MySQLite.getInstance(context);
    }

    public void open() {
        db = maBaseSQLite.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    public long addReponse(Reponse reponse) {
        ContentValues values = new ContentValues();
        values.put(KEY_TEXT_REPONSE, reponse.getText());
        values.put(KEY_VRAI_REPONSE, reponse.getVrai());
        values.put(KEY_ID_QUESTION_REPONSE, reponse.getQuestion().getId());
        return db.insert(TABLE_NAME_REPONSE, null, values);
    }

    public int modReponse(Reponse reponse) {
        ContentValues values = new ContentValues();
        values.put(KEY_TEXT_REPONSE, reponse.getText());
        values.put(KEY_VRAI_REPONSE, reponse.getVrai());

        String where = KEY_ID_REPONSE + " = ?";
        String[] whereArgs = {reponse.getId() + ""};

        return db.update(TABLE_NAME_REPONSE, values, where, whereArgs);
    }

    public int supReponse(Reponse reponse) {
        String where = KEY_ID_REPONSE + " = ?";
        String[] whereArgs = {reponse.getId() + ""};

        return db.delete(TABLE_NAME_REPONSE, where, whereArgs);
    }

    public Reponse getReponse(int id) {
        Reponse r = null;


        Cursor c = db.rawQuery(
                "SELECT * FROM " + TABLE_NAME_REPONSE + " WHERE " +
                        KEY_ID_REPONSE + "=" + id, null);
        if (c.moveToFirst()) {
            QuestionManager qm = new QuestionManager(context);
            qm.open();
            try {
                r = new Reponse(
                        c.getInt(c.getColumnIndex(KEY_ID_REPONSE)),
                        c.getString(c.getColumnIndex(KEY_TEXT_REPONSE)),
                        c.getInt(c.getColumnIndex(KEY_VRAI_REPONSE)) > 0,
                        qm.getQuestion(c.getInt(c.getColumnIndex(KEY_ID_QUESTION_REPONSE)))
                );
            } catch (Exception e) {
                e.printStackTrace();
            }

            qm.close();
            c.close();
        }

        return r;
    }

    public Cursor getReponses() {
        return db.rawQuery("SELECT * FROM " + TABLE_NAME_REPONSE,
                null);
    }

    public Cursor getReponsesByQuestion(int id) {
        return db.rawQuery("SELECT * FROM " + TABLE_NAME_REPONSE + " WHERE " +
                        KEY_ID_QUESTION_REPONSE + "=" + id,
                null);
    }
}

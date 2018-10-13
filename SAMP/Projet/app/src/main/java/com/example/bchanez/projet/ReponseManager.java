package com.example.bchanez.projet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ReponseManager {
    public static final String TABLE_NAME_REPONSE = "reponse";
    public static final String KEY_ID_REPONSE = "id";
    public static final String KEY_TEXT_REPONSE = "text";
    public static final String KEY_VRAI_REPONSE = "vrai";
    public static final String KEY_ID_QUESTION_REPONSE = "question_id";
    public static final
    String CREATE_TABLE_REPONSE = "CREATE TABLE " + TABLE_NAME_REPONSE + " (" +
            " " + KEY_ID_REPONSE + " INTEGER primary key," +
            " " + KEY_TEXT_REPONSE + " TEXT," +
            " " + KEY_VRAI_REPONSE + " BOOL," +
            " " + KEY_ID_QUESTION_REPONSE + " INTEGER," +
            " " + "FOREIGN KEY(" + KEY_ID_QUESTION_REPONSE + ") REFERENCES " +
            QuestionManager.TABLE_NAME_QUESTION + "(" + QuestionManager.KEY_ID_QUESTION + ")" +
            ");";

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
        return db.insert(TABLE_NAME_REPONSE, null, values);
    }

    public int modReponse(Reponse reponse) {
        ContentValues values = new ContentValues();
        values.put(KEY_TEXT_REPONSE, reponse.getText());

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
        Reponse r = new Reponse(0, "", false, null);

        Cursor c = db.rawQuery(
                "SELECT * FROM " + TABLE_NAME_REPONSE + " WHERE " +
                        KEY_ID_REPONSE + "=" + id, null);
        if (c.moveToFirst()) {
            r.setId(c.getInt(c.getColumnIndex(KEY_ID_REPONSE)));
            r.setText(c.getString(c.getColumnIndex(KEY_TEXT_REPONSE)));
            r.setVrai(c.getInt(c.getColumnIndex(KEY_VRAI_REPONSE)) > 0);
            QuestionManager qm = new QuestionManager(context);
            r.setQuestion(qm.getQuestion(c.getInt(c.getColumnIndex(KEY_ID_QUESTION_REPONSE))));
            c.close();
        }

        return r;
    }

    public Cursor getReponses() {
        return db.rawQuery("SELECT * FROM " + TABLE_NAME_REPONSE,
                null);
    }
}

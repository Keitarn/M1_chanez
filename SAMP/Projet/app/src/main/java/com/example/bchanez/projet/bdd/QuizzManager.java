package com.example.bchanez.projet.bdd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.bchanez.projet.model.Quizz;

public class QuizzManager {
    public static final String TABLE_NAME_QUIZZ = "quizz";
    public static final String KEY_ID_QUIZZ = "id";
    public static final String KEY_NOM_QUIZZ = "nom";
    public static final
    String CREATE_TABLE_QUIZZ = "CREATE TABLE " + TABLE_NAME_QUIZZ + " (" +
            " " + KEY_ID_QUIZZ + " INTEGER primary key autoincrement," +
            " " + KEY_NOM_QUIZZ + " TEXT" +
            ");";

    private MySQLite maBaseSQLite;
    private SQLiteDatabase db;
    private Context context;

    public QuizzManager(Context context) {
        this.context = context;
        maBaseSQLite = MySQLite.getInstance(context);
    }

    public void open() {
        db = maBaseSQLite.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    public long addQuizz(Quizz quizz) {
        ContentValues values = new ContentValues();
        values.put(KEY_NOM_QUIZZ, quizz.getNom());
        return db.insert(TABLE_NAME_QUIZZ, null, values);
    }

    public int modQuizz(Quizz quizz) {
        ContentValues values = new ContentValues();
        values.put(KEY_NOM_QUIZZ, quizz.getNom());

        String where = KEY_ID_QUIZZ + " = ?";
        String[] whereArgs = {quizz.getId() + ""};

        return db.update(TABLE_NAME_QUIZZ, values, where, whereArgs);
    }

    public int supQuizz(Quizz quizz) {
        String where = KEY_ID_QUIZZ + " = ?";
        String[] whereArgs = {quizz.getId() + ""};

        return db.delete(TABLE_NAME_QUIZZ, where, whereArgs);
    }

    public Quizz getQuizz(int id) {
        Quizz q = new Quizz(0, "");

        Cursor c = db.rawQuery(
                "SELECT * FROM " + TABLE_NAME_QUIZZ + " WHERE " +
                        KEY_ID_QUIZZ + "=" + id, null);
        if (c.moveToFirst()) {
            q.setId(c.getInt(c.getColumnIndex(KEY_ID_QUIZZ)));
            q.setNom(c.getString(c.getColumnIndex(KEY_NOM_QUIZZ)));
            c.close();
        }

        return q;
    }

    public Cursor getQuizzs() {
        return db.rawQuery("SELECT * FROM " + TABLE_NAME_QUIZZ,
                null);
    }
}

package com.example.bchanez.projet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class QuestionManager {
    public static final String TABLE_NAME_QUESTION = "question";
    public static final String KEY_ID_QUESTION = "id";
    public static final String KEY_TEXT_QUESTION = "text";
    public static final String KEY_ID_QUIZZ_QUESTION = "quizz_id";
    public static final
    String CREATE_TABLE_QUESTION = "CREATE TABLE " + TABLE_NAME_QUESTION + " (" +
            " " + KEY_ID_QUESTION + " INTEGER primary key," +
            " " + KEY_TEXT_QUESTION + " TEXT," +
            " " + KEY_ID_QUIZZ_QUESTION + " INTEGER," +
            " " + "FOREIGN KEY(" + KEY_ID_QUIZZ_QUESTION + ") REFERENCES " +
            QuizzManager.TABLE_NAME_QUIZZ + "(" + QuizzManager.KEY_ID_QUIZZ + ")" +
            ");";

    private MySQLite maBaseSQLite;
    private SQLiteDatabase db;
    private Context context;

    public QuestionManager(Context context) {
        this.context = context;
        maBaseSQLite = MySQLite.getInstance(context);
    }

    public void open() {
        db = maBaseSQLite.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    public long addQuestion(Question question) {
        ContentValues values = new ContentValues();
        values.put(KEY_TEXT_QUESTION, question.getText());
        return db.insert(TABLE_NAME_QUESTION, null, values);
    }

    public int modQuestion(Question question) {
        ContentValues values = new ContentValues();
        values.put(KEY_TEXT_QUESTION, question.getText());

        String where = KEY_ID_QUESTION + " = ?";
        String[] whereArgs = {question.getId() + ""};

        return db.update(TABLE_NAME_QUESTION, values, where, whereArgs);
    }

    public int supQuestion(Question question) {
        String where = KEY_ID_QUESTION + " = ?";
        String[] whereArgs = {question.getId() + ""};

        return db.delete(TABLE_NAME_QUESTION, where, whereArgs);
    }

    public Question getQuestion(int id) {
        Question q = new Question(0, "", null);

        Cursor c = db.rawQuery(
                "SELECT * FROM " + TABLE_NAME_QUESTION + " WHERE " +
                        KEY_ID_QUESTION + "=" + id, null);
        if (c.moveToFirst()) {
            q.setId(c.getInt(c.getColumnIndex(KEY_ID_QUESTION)));
            q.setText(c.getString(c.getColumnIndex(KEY_TEXT_QUESTION)));
            QuizzManager qm = new QuizzManager(context);
            q.setQuizz(qm.getQuizz(c.getInt(c.getColumnIndex(KEY_ID_QUIZZ_QUESTION))));
            c.close();
        }

        return q;
    }

    public Cursor getQuestions() {
        return db.rawQuery("SELECT * FROM " + TABLE_NAME_QUESTION,
                null);
    }
}

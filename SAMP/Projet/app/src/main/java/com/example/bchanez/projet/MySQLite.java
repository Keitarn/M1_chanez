package com.example.bchanez.projet;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLite extends SQLiteOpenHelper {

    private static final
    String DATABASE_NAME = "db.sqlite";
    private static final int DATABASE_VERSION = 1;
    private static MySQLite sInstance;

    public static synchronized MySQLite getInstance(Context context) {
        Log.d("synchronized", "test1");
        if (sInstance == null) {
            Log.d("synchronized", "test2");
            sInstance = new MySQLite(context);
        }
        return sInstance;
    }

    private MySQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("MySQLite", "test1");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("onCreate", "test1");
        db.execSQL(QuizzManager.CREATE_TABLE_QUIZZ);
        db.execSQL(QuestionManager.CREATE_TABLE_QUESTION);
        db.execSQL(ReponseManager.CREATE_TABLE_REPONSE);

        Log.d("onCreate", "test2");


//        Log.d("quizz insert",
////        db.execSQL(
//                "INSERT INTO " + QuizzManager.TABLE_NAME_QUIZZ + " (" + QuizzManager.KEY_ID_QUIZZ +
//                        ", " + QuizzManager.KEY_NOM_QUIZZ + ") VALUES (" + 1 + ", 'defaut');"
//        );
//
//        Log.d("question insert",
////        db.execSQL(
//                "INSERT INTO " + QuestionManager.TABLE_NAME_QUESTION + " (" + QuestionManager.KEY_ID_QUESTION +
//                        ", " + QuestionManager.KEY_TEXT_QUESTION + ", "
//                        + QuestionManager.KEY_ID_QUIZZ_QUESTION +
//                        ") VALUES (" + 1 + ", 'Le diable de Tasmanie vit dans la jungle du Brésil.'" + ", " + 1 + "), " +
//                        "(" + 2 + ", 'La sauterelle saute l\'équivalent de 200fois sa taille.'" + ", " + 1 + "), " +
//                        "(" + 3 + ", 'Les pandas hibernent.'" + ", " + 1 + "), " +
//                        "(" + 4 + ", 'On trouve des dromadaires en liberté en Australie.'" + ", " + 1 + "), " +
//                        "(" + 5 + ", 'Le papillon monarque vole plus de 4000km.'" + ", " + 1 + "), " +
//                        "(" + 6 + ", 'Les gorilles mâles dorment dans les arbres.'" + ", " + 1 + ");"
//        );
//
//        Log.d("reponse insert",
////        db.execSQL(
//                "INSERT INTO " + ReponseManager.TABLE_NAME_REPONSE + " (" + ReponseManager.KEY_ID_REPONSE +
//                        ", " + ReponseManager.KEY_TEXT_REPONSE + ", "
//                        + ReponseManager.KEY_VRAI_REPONSE + ", " + ReponseManager.KEY_ID_QUESTION_REPONSE +
//                        ") VALUES (" + 1 + ", 'faux'" + ", " + true + ", " + 1 + "), " +
//                        "(" + 2 + ", 'vrai'" + ", " + false + ", " + 1 + "), " +
//                        "(" + 3 + ", 'faux'" + ", " + false + ", " + 2 + "), " +
//                        "(" + 4 + ", 'vrai'" + ", " + true + ", " + 2 + "), " +
//                        "(" + 5 + ", 'faux'" + ", " + true + ", " + 3 + "), " +
//                        "(" + 6 + ", 'vrai'" + ", " + false + ", " + 3 + "), " +
//                        "(" + 7 + ", 'faux'" + ", " + false + ", " + 4 + "), " +
//                        "(" + 8 + ", 'vrai'" + ", " + true + ", " + 4 + "), " +
//                        "(" + 9 + ", 'faux'" + ", " + false + ", " + 5 + "), " +
//                        "(" + 10 + ", 'vrai'" + ", " + true + ", " + 5 + "), " +
//                        "(" + 11 + ", 'faux'" + ", " + true + ", " + 6 + "), " +
//                        "(" + 12 + ", 'vrai'" + ", " + false + ", " + 6 + ");"
//        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("onUpgrade", "test");
    }
}

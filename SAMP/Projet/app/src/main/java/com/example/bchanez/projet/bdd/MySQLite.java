package com.example.bchanez.projet.bdd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLite extends SQLiteOpenHelper {

    private static final
    String DATABASE_NAME = "sqlite.db";
    private static final int DATABASE_VERSION = 1;
    private static MySQLite sInstance;

    public static synchronized MySQLite getInstance(Context context) {
        if (sInstance == null) {
            context.deleteDatabase(DATABASE_NAME);
            sInstance = new MySQLite(context);
        }
        return sInstance;
    }

    private MySQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(QuizzManager.CREATE_TABLE_QUIZZ);
        db.execSQL(QuestionManager.CREATE_TABLE_QUESTION);
        db.execSQL(ReponseManager.CREATE_TABLE_REPONSE);

        db.execSQL(
                "INSERT INTO " + QuizzManager.TABLE_NAME_QUIZZ + " (" + QuizzManager.KEY_NOM_QUIZZ + ") " +
                        "VALUES ('defaut');"
        );

        db.execSQL(
                "INSERT INTO " + QuestionManager.TABLE_NAME_QUESTION + " ("
                        + QuestionManager.KEY_TEXT_QUESTION + ", "
                        + QuestionManager.KEY_ID_QUIZZ_QUESTION +
                        ") VALUES ('Le diable de Tasmanie vit dans la jungle du Brésil.'" + ", " + 1 + "), " +
                        "('La sauterelle saute l équivalent de 200fois sa taille.'" + ", " + 1 + "), " +
                        "('Les pandas hibernent.'" + ", " + 1 + "), " +
                        "('On trouve des dromadaires en liberté en Australie.'" + ", " + 1 + "), " +
                        "('Le papillon monarque vole plus de 4000km.'" + ", " + 1 + "), " +
                        "('Les gorilles mâles dorment dans les arbres.'" + ", " + 1 + ");"
        );

        db.execSQL(
                "INSERT INTO " + ReponseManager.TABLE_NAME_REPONSE + " ("
                        + ReponseManager.KEY_TEXT_REPONSE + ", "
                        + ReponseManager.KEY_VRAI_REPONSE + ", " + ReponseManager.KEY_ID_QUESTION_REPONSE +
                        ") VALUES ('faux'" + ", " + 1 + ", " + 1 + "), " +
                        "('vrai'" + ", " + 0 + ", " + 1 + "), " +
                        "('faux'" + ", " + 0 + ", " + 2 + "), " +
                        "('vrai'" + ", " + 1 + ", " + 2 + "), " +
                        "('faux'" + ", " + 1 + ", " + 3 + "), " +
                        "('vrai'" + ", " + 0 + ", " + 3 + "), " +
                        "('faux'" + ", " + 0 + ", " + 4 + "), " +
                        "('vrai'" + ", " + 1 + ", " + 4 + "), " +
                        "('faux'" + ", " + 0 + ", " + 5 + "), " +
                        "('vrai'" + ", " + 1 + ", " + 5 + "), " +
                        "('faux'" + ", " + 1 + ", " + 6 + "), " +
                        "('vrai'" + ", " + 0 + ", " + 6 + ");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}

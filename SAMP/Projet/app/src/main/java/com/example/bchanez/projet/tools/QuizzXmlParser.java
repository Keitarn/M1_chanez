package com.example.bchanez.projet.tools;

import android.content.Context;
import android.util.Xml;

import com.example.bchanez.projet.bdd.QuestionManager;
import com.example.bchanez.projet.bdd.QuizzManager;
import com.example.bchanez.projet.bdd.ReponseManager;
import com.example.bchanez.projet.model.Question;
import com.example.bchanez.projet.model.Quizz;
import com.example.bchanez.projet.model.Reponse;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class QuizzXmlParser {
    private static final String ns = null;

    private QuizzManager quizzManager;
    private QuestionManager questionManager;
    private ReponseManager reponseManager;

    public void parse(InputStream in, Context context) throws XmlPullParserException, IOException {
        try {
            quizzManager = new QuizzManager(context);
            questionManager = new QuestionManager(context);
            reponseManager = new ReponseManager(context);
            XmlPullParser parser = Xml.newPullParser();
//            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            readDocument(parser);
        } finally {
            in.close();
        }
    }

    private void readDocument(XmlPullParser parser) throws XmlPullParserException, IOException {
        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType != XmlPullParser.START_TAG) {
                continue;
            }
            if (parser.getName().equals("Quizzs")) {
                readQuizzs(parser);
            }
            eventType = parser.next();
        }
    }

    private void readQuizzs(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "Quizzs");

        while (!(parser.next() == XmlPullParser.END_TAG && parser.getName().equals("Quizzs"))) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            switch (parser.getName()) {
                case "Quizz":
                    readQuizz(parser);
                    break;
                default:
                    skip(parser);
                    break;
            }
        }

        parser.require(XmlPullParser.END_TAG, ns, "Quizzs");
    }

    private void readQuizz(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "Quizz");

        Quizz quizz = new Quizz(-1, parser.getAttributeValue(ns, "type"));
        quizzManager.open();
        quizz.setId((int) quizzManager.addQuizz(quizz));
        quizzManager.close();

        while (!(parser.next() == XmlPullParser.END_TAG && parser.getName().equals("Quizz"))) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            switch (parser.getName()) {
                case "Question":
                    readQuestion(parser, quizz);
                    break;
                default:
                    skip(parser);
                    break;
            }
        }

        parser.require(XmlPullParser.END_TAG, ns, "Quizz");
    }

    private void readQuestion(XmlPullParser parser, Quizz quizz) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "Question");

        Question question = new Question(-1, readText(parser), quizz);
        questionManager.open();
        question.setId((int) questionManager.addQuestion(question));
        questionManager.close();

        List reponses = new ArrayList();
        int valeur = -1;

        while (!(parser.next() == XmlPullParser.END_TAG && parser.getName().equals("Question"))) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            switch (parser.getName()) {
                case "Propositions":
                    reponses = readPropositions(parser);
                    break;
                case "Reponse":
                    valeur = readReponse(parser);
                    break;
                default:
                    skip(parser);
                    break;
            }
        }

        parser.require(XmlPullParser.END_TAG, ns, "Question");

        ((Reponse) reponses.get(valeur - 1)).setVrai(true);

        for (Object reponse : reponses) {
            ((Reponse) reponse).setQuestion(question);
            reponseManager.open();
            reponseManager.addReponse((Reponse) reponse);
            reponseManager.close();
        }
    }


    private List readPropositions(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "Propositions");

        List reponses = new ArrayList();

        while (!(parser.next() == XmlPullParser.END_TAG && parser.getName().equals("Propositions"))) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            switch (parser.getName()) {
                case "Proposition":
                    reponses.add(readProposition(parser));
                    break;
                default:
                    skip(parser);
                    break;
            }
        }

        parser.require(XmlPullParser.END_TAG, ns, "Propositions");

        return reponses;
    }

    private Reponse readProposition(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "Proposition");
        Reponse reponse = new Reponse(-1, readText(parser), false, null);
        parser.nextTag();
        parser.require(XmlPullParser.END_TAG, ns, "Proposition");
        return reponse;
    }

    private int readReponse(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "Reponse");
        int valeur = Integer.parseInt(parser.getAttributeValue(ns, "valeur"));
        parser.nextTag();
        parser.require(XmlPullParser.END_TAG, ns, "Reponse");

        return valeur;
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
        }
        return result;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}
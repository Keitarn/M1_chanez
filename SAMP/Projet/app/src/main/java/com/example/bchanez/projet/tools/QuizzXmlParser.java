package com.example.bchanez.projet.tools;

import android.content.Context;
import android.util.Log;
import android.util.Xml;

import com.example.bchanez.projet.bdd.QuestionManager;
import com.example.bchanez.projet.bdd.QuizzManager;
import com.example.bchanez.projet.bdd.ReponseManager;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

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

//        Log.d("log-parser", parser.getName());

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

//        Log.d("log-parser", parser.getName() + " " + parser.getAttributeValue(ns, "type"));

        while (!(parser.next() == XmlPullParser.END_TAG && parser.getName().equals("Quizz"))) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            switch (parser.getName()) {
                case "Question":
                    readQuestion(parser);
                    break;
                default:
                    skip(parser);
                    break;
            }
        }

        parser.require(XmlPullParser.END_TAG, ns, "Quizz");
    }

    private void readQuestion(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "Question");

//        Log.d("log-parser", parser.getName() + " " + readText(parser));

        while (!(parser.next() == XmlPullParser.END_TAG && parser.getName().equals("Question"))) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            switch (parser.getName()) {
                case "Propositions":
                    readPropositions(parser);
                    break;
                case "Reponse":
                    readReponse(parser);
                    break;
                default:
                    skip(parser);
                    break;
            }
        }

        parser.require(XmlPullParser.END_TAG, ns, "Question");
    }


    private void readPropositions(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "Propositions");

        Log.d("log-parser", parser.getName());

        while (!(parser.next() == XmlPullParser.END_TAG && parser.getName().equals("Propositions"))) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            switch (parser.getName()) {
                case "Proposition":
                    readProposition(parser);
                    break;
                default:
                    skip(parser);
                    break;
            }
        }

        parser.require(XmlPullParser.END_TAG, ns, "Propositions");
    }

    private void readProposition(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "Proposition");
        Log.d("log-parser", parser.getName() + " " + readText(parser));
        parser.nextTag();
        parser.require(XmlPullParser.END_TAG, ns, "Proposition");
    }

    private void readReponse(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "Reponse");
        Log.d("log-parser", parser.getAttributeValue(ns, "valeur"));
        parser.nextTag();
        parser.require(XmlPullParser.END_TAG, ns, "Reponse");
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
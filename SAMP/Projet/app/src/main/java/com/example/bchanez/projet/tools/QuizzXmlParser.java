package com.example.bchanez.projet.tools;

import android.content.Context;
import android.util.Log;
import android.util.Xml;

import com.example.bchanez.projet.bdd.QuestionManager;
import com.example.bchanez.projet.bdd.QuizzManager;
import com.example.bchanez.projet.bdd.ReponseManager;
import com.example.bchanez.projet.model.Question;
import com.example.bchanez.projet.model.Quizz;

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
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            readQuizzs(parser);
        } finally {
            in.close();
        }
    }

    private void readQuizzs(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "Quizzs");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            switch (parser.getName()) {
                case "Quizz":


                    readQuizz(parser);
                default:
                    skip(parser);
                    break;
            }
        }
    }

    private void readQuizz(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "Quizz");

        Log.d("log", parser.getName());
        Log.d("log", parser.getAttributeValue(ns, "type"));

        Quizz quizz = new Quizz(-1, parser.getAttributeValue(ns, "type"));
        quizzManager.open();
        quizz.setId((int) quizzManager.addQuizz(quizz));
        quizzManager.close();

        while (parser.next() != XmlPullParser.END_TAG) {
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
    }

    private void readQuestion(XmlPullParser parser, Quizz quizz) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "Question");

        Log.d("log", parser.getName());
        Log.d("log", readText(parser));
        Log.d("log", parser.getName());

        Question question = new Question(-1, readText(parser), quizz);
        questionManager.open();
        question.setId((int) questionManager.addQuestion(question));
        questionManager.close();

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            Log.d("log", parser.getName());
            switch (parser.getName()) {
                case "Propositions":
                    Log.d("log", parser.getName());
//                    readPropositions(parser);

                    break;
                case "Reponse":
                    Log.d("log", parser.getName());
                    Log.d("log", parser.getAttributeValue(ns, "valeur"));
//                    readReponse(parser);
                    break;
                default:
                    skip(parser);
                    break;
            }
        }
    }

    //    private Entry readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
//        parser.require(XmlPullParser.START_TAG, ns, "Quizz");
//        String quizz = null;
//        String question = null;
//        String reponse = null;
//        int bonne_reponse = -1;
//
//        while (parser.next() != XmlPullParser.END_TAG) {
//            if (parser.getEventType() != XmlPullParser.START_TAG) {
//                continue;
//            }
//            String name = parser.getName();
//            Log.d("log", name);
//            if (name.equals("Quizz")) {
//                Log.d("log", "test");
//                quizz = readQuizz(parser);
//            } else {
//                skip(parser);
//            }
//        }
//        return new Entry("", "", "");
//    }
//
//    private String readQuizz(XmlPullParser parser) throws IOException, XmlPullParserException {
//        parser.require(XmlPullParser.START_TAG, ns, "Quizz");
//
//        Log.d("log", parser.getAttributeValue(ns, "type"));
//
//        String title = readText(parser);
//        parser.require(XmlPullParser.END_TAG, ns, "Quizz");
//        return title;
//    }
//
//    private String readLink(XmlPullParser parser) throws IOException, XmlPullParserException {
//        String link = "";
//        parser.require(XmlPullParser.START_TAG, ns, "link");
//        String tag = parser.getName();
//        String relType = parser.getAttributeValue(null, "rel");
//        if (tag.equals("link")) {
//            if (relType.equals("alternate")) {
//                link = parser.getAttributeValue(null, "href");
//                parser.nextTag();
//            }
//        }
//        parser.require(XmlPullParser.END_TAG, ns, "link");
//        return link;
//    }
//
//    private String readSummary(XmlPullParser parser) throws IOException, XmlPullParserException {
//        parser.require(XmlPullParser.START_TAG, ns, "summary");
//        String summary = readText(parser);
//        parser.require(XmlPullParser.END_TAG, ns, "summary");
//        return summary;
//    }
//
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
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
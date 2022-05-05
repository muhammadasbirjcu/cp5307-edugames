package com.asbir.cp5307.edugames.game;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class GameXMLParser {
    private static final String ns = null;
    private Difficulty difficulty;
    private AssetManager assetManager;

    public GameXMLParser(Difficulty difficulty, AssetManager assetManager){
        this.difficulty = difficulty;
        this.assetManager = assetManager;
    }

    public InputStream assetStream() throws XmlPullParserException, IOException{
        return this.assetManager.open(String.valueOf(difficulty) + ".xml");
    }

    public List<Question> read() throws XmlPullParserException, IOException{
        return parse(assetStream());
    }

    public List<Question> parse(InputStream in) throws XmlPullParserException, IOException {
        List<Question> questions = new ArrayList<>();
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            questions = readFeed(parser);
        } finally {
            in.close();
        }
        return questions;
    }

    private List<Question> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        int eventType = parser.getEventType();
        List<Question> questions = new ArrayList<>();
        Question pendingQuestion = new Question("");
        String lastTag = "";
        String lastText = "";
        while (eventType != XmlPullParser.END_DOCUMENT) {

            if(parser.getEventType() == XmlPullParser.START_TAG){
                lastTag = parser.getName();

            }else if(parser.getEventType() == XmlPullParser.TEXT) {
                lastText = parser.getText().trim();

                Log.i("xml", lastTag + " : " + lastText);

                if(lastText.isEmpty()){

                }else if(lastTag.equals("question")){
                    if(!pendingQuestion.getQuestion().isEmpty()){
                        questions.add(pendingQuestion);
                    }
                    pendingQuestion = new Question(lastText);

                }else if(lastTag.equals("answers")){
                    pendingQuestion.addAnswer(lastText);
                }else if(lastTag.equals("answer")){
                    pendingQuestion.setCorrectAnswer(lastText);
                }else if(lastTag.equals("image")){
                    try {
                        InputStream imageStream = assetManager.open("img/" + lastText);
                        pendingQuestion.setIllustration(BitmapFactory.decodeStream(imageStream));
                    } catch (IOException e) {
                        Log.e("xml", "bitmap parse " + lastText, e);
                        // handle exception
                    }
                }
            }
            eventType = parser.next();
        }
        questions.add(pendingQuestion);
        return questions;

    }
}

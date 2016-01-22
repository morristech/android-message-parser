package com.miguelgaeta.android_message_parser;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.miguelgaeta.message_parser.MessageParser;
import com.miguelgaeta.message_parser.MessageParserImplFastJson;

import java.io.IOException;
import java.io.StringReader;

public class AppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.app_activity);

        final TestModel testModel = new TestModel();

        try {

            final MessageParser reader = new MessageParserImplFastJson(new StringReader("{\"test\":\"Bob\", \"test_again\":13}"));

            reader.beginObject();

            while (reader.hasNext()) {

                switch (reader.nextName()) {

                    case "test":
                        testModel.test = reader.nextString();
                        break;
                    case "test_again":
                        testModel.test_again = reader.nextInt();
                        break;
                }
            }

            reader.endObject();
            reader.close();

            Log.e("MessageParser", "Parsed: " + testModel);

        } catch (IOException e) {

            Log.e("MessageParser", "Error", e);
        }
    }

    public static class TestModel {

        private String test;

        private int test_again;

        @Override
        public String toString() {

            return "test: " +  test + ", test_again: " + test_again;
        }
    }
}

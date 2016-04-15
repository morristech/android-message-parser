package com.miguelgaeta.android_message_parser;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.miguelgaeta.message_parser.MessageParser;
import com.miguelgaeta.message_parser.MessageParserImplGson;
import com.miguelgaeta.message_parser.MessageParserImplJackson;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class AppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.app_activity);

        final String json = "{\"test\":\"Bob\", \"test_again\":13, \"test_bool\":true, \"test_arr\":[1, 2, 3, 4]}";

        Log.e("MessageParser", json);

        try {

            test1(new TestModel(), json);
            test2(new TestModel(), json);

        } catch (IOException e) {

            Log.e("MessageParser", "Error", e);
        }
    }

    private void test1(final TestModel testModel, final String json) throws IOException {

        final MessageParser reader = new MessageParserImplGson(new StringReader(json));

        reader.beginObjectStructure();

        while (reader.hasNext()) {

            assign(reader, testModel);
        }

        reader.endObject();
        reader.close();

        Log.e("MessageParser", "Parsed: " + testModel);
    }

    private void test2(final TestModel testModel, final String json) throws IOException {

        final MessageParser reader = new MessageParserImplJackson(new StringReader(json));

        reader.nextObject(() -> AppActivity.this.assign(reader, testModel));

        reader.close();

        Log.e("MessageParser", "Parsed: " + testModel);
    }

    private void assign(final MessageParser reader, TestModel testModel) throws IOException {

        switch (reader.nextName()) {

            case "test":
                testModel.test = reader.nextString();
                break;
            case "test_again":
                testModel.test_again = reader.nextInt();
                break;
            case "test_bool":
                testModel.test_bool = reader.nextBoolean();
                break;
            case "test_arr":
                testModel.test_arr = reader.nextList(ArrayList::new, reader::nextInt);
                break;
        }
    }

    public static class TestModel {

        private String test;

        private int test_again;

        private List<Integer> test_arr;

        private boolean test_bool;

        @Override
        public String toString() {

            return "test: " +  test + ", test_again: " + test_again + ", test_bool: " + test_bool + ", test_arr: " + test_arr;
        }
    }
}

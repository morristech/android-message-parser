package com.miguelgaeta.message_parser;


import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

/**
 * Created by Miguel Gaeta on 1/19/16.
 */
@SuppressWarnings("UnusedDeclaration")
public class MessageParserImplGson implements MessageParser {

    private final JsonReader reader;

    public MessageParserImplGson(final Reader reader) {
        this.reader = new JsonReader(reader);
    }

    @Override
    public boolean beginObject() throws IOException {
        if (reader.peek() == JsonToken.NULL) {
            nextNull();

            return false;
        }

        reader.beginObject();

        return true;
    }

    @Override
    public void endObject() throws IOException {
        reader.endObject();
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }

    @Override
    public boolean hasNext() throws IOException {
        return reader.hasNext();
    }

    @Override
    public String nextName() throws IOException {
        return reader.nextName();
    }

    @Override
    public void skipValue() throws IOException {
        reader.skipValue();
    }

    @Override
    public void nextNull() throws IOException {
        reader.nextNull();
    }

    @Override
    public String nextString() throws IOException {
        return reader.nextString();
    }

    @Override
    public String nextString(String defaultValue) throws IOException {
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull();

            return defaultValue;
        }

        return reader.nextString();
    }

    @Override
    public String nextStringOrNull() throws IOException {
        return nextString(null);
    }

    @Override
    public boolean nextBoolean(boolean defaultValue) throws IOException {
        final Boolean value = nextBooleanOrNull();

        return value != null ? value : defaultValue;
    }

    @Override
    public boolean nextBoolean() throws IOException {
        return reader.nextBoolean();
    }

    @Override
    public Boolean nextBooleanOrNull() throws IOException {
        if (reader.peek() == JsonToken.NULL) {
            nextNull();
        }

        return nextBoolean();
    }

    @Override
    public double nextDouble() throws IOException {
        return reader.nextDouble();
    }

    @Override
    public long nextLong() throws IOException {
        return reader.nextLong();
    }

    @Override
    public long nextLong(long defaultValue) throws IOException {
        final Long value = nextLongOrNull();

        return value != null ? value : defaultValue;
    }

    @Override
    public Long nextLongOrNull() throws IOException {
        if (reader.peek() == JsonToken.NULL) {
            nextNull();

            return null;
        }

        return nextLong();
    }

    @Override
    public int nextInt() throws IOException {
        return reader.nextInt();
    }

    @Override
    public int nextInt(int defaultValue) throws IOException {
        if (reader.peek() == JsonToken.NULL) {
            nextNull();

            return defaultValue;
        }

        return nextInt();
    }

    @Override
    public Integer nextIntOrNull() throws IOException {
        if (reader.peek() == JsonToken.NULL) {
            nextNull();

            return null;
        }

        return nextInt();
    }

    @Override
    public <T> List<T> nextList(ListInitializer<T> initializer, ListItem<T> item) throws IOException {
        return nextList(initializer, item, false);
    }

    @Override
    public <T> List<T> nextList(ListInitializer<T> initializer, ListItem<T> item, boolean filterNull) throws IOException {
        final List<T> list = initializer.get();

        reader.beginArray();

        while (hasNext()) {

            final T i = item.get();

            if (!filterNull || i != null) {

                list.add(i);
            }
        }

        reader.endArray();

        return list;
    }

    @Override
    public boolean nextObject(ObjectFieldAssigner handler) throws IOException {
        if (beginObject()) {

            while (hasNext()) {

                handler.assign();
            }

            endObject();

            return true;

        } else {

            return false;
        }
    }
}

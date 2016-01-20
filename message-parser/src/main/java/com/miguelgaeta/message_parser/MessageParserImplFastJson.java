package com.miguelgaeta.message_parser;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONReader;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

/**
 * Created by Miguel Gaeta on 1/19/16.
 */
@SuppressWarnings("UnusedDeclaration")
public class MessageParserImplFastJson implements MessageParser {

    private final JSONReader reader;

    public MessageParserImplFastJson(final Reader reader) {
        this.reader = new JSONReader(reader);
    }

    @Override
    public boolean beginObject() throws IOException {
        return reader.startObject();
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
        return (String)reader.readObject();
    }

    @Override
    public void skipValue() throws IOException {
        reader.readObject();
    }

    @Override
    public void nextNull() throws IOException {
        if (reader.readObject() != null) {

            throw new IllegalStateException("Non-null value encountered.");
        }
    }

    @Override
    public String nextString() throws IOException {
        return reader.readString();
    }

    @Override
    public String nextString(String defaultValue) throws IOException {
        final String value = reader.readString();

        return value != null ? value : defaultValue;
    }

    @Override
    public String nextStringOrNull() throws IOException {
        return reader.readString();
    }

    @Override
    public boolean nextBoolean() throws IOException {
        final Boolean value = nextBooleanOrNull();

        if (value == null) {

            throw new IOException("Null boolean value found.");
        }

        return value;
    }

    @Override
    public boolean nextBoolean(boolean defaultValue) throws IOException {
        final Boolean value = nextBooleanOrNull();

        return value != null ? value : defaultValue;
    }


    @Override
    public Boolean nextBooleanOrNull() throws IOException {
        return (Boolean) reader.readObject();
    }

    @Override
    public double nextDouble() throws IOException {
        return (double) reader.readObject();
    }

    @Override
    public long nextLong() throws IOException {
        final Long value = nextLongOrNull();

        if (value == null) {

            throw new IOException("Null long value found.");
        }

        return value;
    }

    @Override
    public long nextLong(long defaultValue) throws IOException {
        final Long value = nextLongOrNull();

        return value != null ? value : defaultValue;
    }

    @Override
    public Long nextLongOrNull() throws IOException {
        try {

            return reader.readLong();

        } catch (JSONException e) {

            return null;
        }
    }

    @Override
    public int nextInt() throws IOException {
        final Integer value = nextIntOrNull();

        if (value == null) {

            throw new IOException("Null int value found.");
        }

        return value;
    }

    @Override
    public int nextInt(int defaultValue) throws IOException {
        final Integer value = nextIntOrNull();

        return value != null ? value : defaultValue;
    }

    @Override
    public Integer nextIntOrNull() throws IOException {
        try {

            return reader.readInteger();

        } catch (JSONException e) {

            return null;
        }
    }

    @Override
    public <T> List<T> nextList(ListInitializer<T> initializer, ListItem<T> item) throws IOException {
        return nextList(initializer, item, false);
    }

    @Override
    public <T> List<T> nextList(ListInitializer<T> initializer, ListItem<T> item, boolean filterNull) throws IOException {
        final List<T> list = initializer.get();

        reader.startArray();

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
    public boolean nextObject(ObjectFieldAssigner assigner) throws IOException {
        if (beginObject()) {

            while (hasNext()) {

                assigner.assign();
            }

            endObject();

            return true;

        } else {

            return false;
        }
    }
}

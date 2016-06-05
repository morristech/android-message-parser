package com.miguelgaeta.message_parser;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("UnusedDeclaration")
public class MessageParserImplJackson implements MessageParser {

    private final JsonParser reader;

    public MessageParserImplJackson(final Reader reader) throws IOException {
        final JsonFactory factory = new JsonFactory();

        this.reader = factory.createParser(reader);
        this.reader.nextToken();
    }

    @Override
    public boolean beginObjectStructure() throws IOException {

        if (reader.isExpectedStartObjectToken()) {
            reader.nextToken();

            return true;
        }

        reader.nextToken();

        return false;
    }

    @Override
    public void endObject() throws IOException {
        reader.nextToken();
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }

    @Override
    public boolean hasNext() throws IOException {
        return
            reader.getCurrentToken() != JsonToken.END_ARRAY &&
            reader.getCurrentToken() != JsonToken.END_OBJECT;
    }

    @Override
    public String nextName() throws IOException {
        final String name = reader.getCurrentName();

        reader.nextToken();

        return name;
    }

    @Override
    public void skipValue() throws IOException {
        reader.skipChildren();
        reader.nextToken();
    }

    @Override
    public void nextNull() throws IOException {
        reader.nextToken();
    }

    @Override
    public String nextString() throws IOException {
        final String value = reader.getValueAsString();

        reader.nextToken();

        return value;
    }

    @Override
    public String nextString(String defaultValue) throws IOException {
        final String value = reader.getValueAsString(defaultValue);

        reader.nextToken();

        return value;
    }

    @Override
    public String nextStringOrNull() throws IOException {
        final String value = reader.getValueAsString(null);

        reader.nextToken();

        return value;
    }

    @Override
    public boolean nextBoolean() throws IOException {
        final boolean value = reader.getValueAsBoolean();

        reader.nextToken();

        return value;
    }

    @Override
    public boolean nextBoolean(boolean defaultValue) throws IOException {
        final boolean value = reader.getValueAsBoolean(defaultValue);

        reader.nextToken();

        return value;
    }

    @Override
    public Boolean nextBooleanOrNull() throws IOException {
        final Boolean value = reader.getCurrentToken() == JsonToken.VALUE_NULL ? null : reader.getValueAsBoolean();

        reader.nextToken();

        return value;
    }

    @Override
    public double nextDouble() throws IOException {
        final double value = reader.getValueAsDouble();

        reader.nextToken();

        return value;
    }

    @Override
    public double nextDouble(double defaultValue) throws IOException {
        final double value = reader.getValueAsDouble(defaultValue);

        reader.nextToken();

        return value;
    }

    @Override
    public Double nextDoubleOrNull() throws IOException {
        final Double value = reader.getCurrentToken() == JsonToken.VALUE_NULL ? null : reader.getValueAsDouble();

        reader.nextToken();

        return value;
    }

    @Override
    public long nextLong() throws IOException {
        final long value = reader.getValueAsLong();

        reader.nextToken();

        return value;
    }

    @Override
    public long nextLong(long defaultValue) throws IOException {
        final long value = reader.getValueAsLong(defaultValue);

        reader.nextToken();

        return value;
    }

    @Override
    public Long nextLongOrNull() throws IOException {
        final Long value = reader.getCurrentToken() == JsonToken.VALUE_NULL ? null : reader.getValueAsLong();

        reader.nextToken();

        return value;
    }

    @Override
    public int nextInt() throws IOException {
        final int value = reader.getValueAsInt();

        reader.nextToken();

        return value;
    }

    @Override
    public int nextInt(int defaultValue) throws IOException {
        final int value = reader.getValueAsInt(defaultValue);

        reader.nextToken();

        return value;
    }

    @Override
    public Integer nextIntOrNull() throws IOException {
        final Integer value = reader.getCurrentToken() == JsonToken.VALUE_NULL ? null : reader.getValueAsInt();

        reader.nextToken();

        return value;
    }

    @Override
    public <T> List<T> nextList(ListItem<T> item) throws IOException {
        final List<T> list = new ArrayList<>();

        reader.nextToken();


        while (hasNext()) {
            final T i = item.get();

            if (i != null) {
                list.add(i);
            }
        }

        reader.nextToken();

        return list;
    }

    @Override
    public <T> List<T> nextList(ListItem<T> item, boolean filterNull) throws IOException {
        final List<T> list = new ArrayList<>();

        reader.nextToken();


        while (hasNext()) {
            final T i = item.get();

            if (!filterNull || i != null) {
                list.add(i);
            }
        }

        reader.nextToken();

        return list;
    }

    @Override
    public <T> List<T> nextList(ListItem<T> item, boolean filterNull, ListInitializer<T> initializer) throws IOException {
        final List<T> list = initializer.get();

        reader.nextToken();


        while (hasNext()) {
            final T i = item.get();

            if (!filterNull || i != null) {
                list.add(i);
            }
        }

        reader.nextToken();

        return list;
    }

    @Override
    public boolean nextObject(ObjectFieldAssigner handler) throws IOException {
        if (beginObjectStructure()) {

            while (hasNext()) {
                handler.assign();
            }

            endObject();

            return true;
        }

        return false;
    }

    @Override
    public <T> T readObject(Class<T> type) throws IOException {
        return reader.readValueAs(type);
    }

    @Override
    public <T> T getReader(Class<T> type) {

        //noinspection unchecked
        return (T) reader;
    }

    @Override
    public void beginArray() throws IOException {
        reader.nextToken();
    }

    @Override
    public void endArray() throws IOException {
        reader.nextToken();
    }
}

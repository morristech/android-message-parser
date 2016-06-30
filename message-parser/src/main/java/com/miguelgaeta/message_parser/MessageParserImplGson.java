package com.miguelgaeta.message_parser;


import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("UnusedDeclaration")
public class MessageParserImplGson extends JsonReader implements MessageParser {

    private final Gson gson = new Gson();

    public MessageParserImplGson(final Reader reader) {
        super(reader);
    }

    @Override
    public boolean beginObjectStructure() throws IOException {
        if (peek() == JsonToken.NULL) {
            nextNull();

            return false;
        }

        beginObject();

        return true;
    }

    @Override
    public String nextString(String defaultValue) throws IOException {
        if (peek() == JsonToken.NULL) {
            nextNull();

            return defaultValue;
        }

        return nextString();
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
    public Boolean nextBooleanOrNull() throws IOException {
        if (peek() == JsonToken.NULL) {
            nextNull();
        }

        return nextBoolean();
    }

    @Override
    public double nextDouble(double defaultValue) throws IOException {
        final Double value = nextDoubleOrNull();

        return value != null ? value : defaultValue;
    }

    @Override
    public Double nextDoubleOrNull() throws IOException {
        if (peek() == JsonToken.NULL) {
            nextNull();

            return null;
        }

        return nextDouble();
    }

    @Override
    public long nextLong(long defaultValue) throws IOException {
        final Long value = nextLongOrNull();

        return value != null ? value : defaultValue;
    }

    @Override
    public Long nextLongOrNull() throws IOException {
        if (peek() == JsonToken.NULL) {
            nextNull();

            return null;
        }

        return nextLong();
    }

    @Override
    public int nextInt(int defaultValue) throws IOException {
        if (peek() == JsonToken.NULL) {
            nextNull();

            return defaultValue;
        }

        return nextInt();
    }

    @Override
    public Integer nextIntOrNull() throws IOException {
        if (peek() == JsonToken.NULL) {
            nextNull();

            return null;
        }

        return nextInt();
    }

    @Override
    public <T> List<T> nextList(ListItem<T> item) throws IOException {
        final List<T> list = new ArrayList<>();

        beginArray();

        while (hasNext()) {
            final T i = item.get();

            if (i != null) {
                list.add(i);
            }
        }

        endArray();

        return list;
    }

    @Override
    public <T> List<T> nextList(ListItem<T> item, boolean filterNull) throws IOException {
        final List<T> list = new ArrayList<>();

        beginArray();

        while (hasNext()) {
            final T i = item.get();

            if (!filterNull || i != null) {
                list.add(i);
            }
        }

        endArray();

        return list;
    }

    @Override
    public <T> List<T> nextList(ListItem<T> item, boolean filterNull, ListInitializer<T> initializer) throws IOException {
        final List<T> list = initializer.get();

        beginArray();

        while (hasNext()) {
            final T i = item.get();

            if (!filterNull || i != null) {
                list.add(i);
            }
        }

        endArray();

        return list;
    }

    @Override
    public <K, V> Map<K, V> nextListAsMap(ListItem<V> item, MapKey<K, V> key) throws IOException {
        final Map<K, V> map = new HashMap<>();

        beginArray();

        while (hasNext()) {
            final V i = item.get();

            if (i != null) {
                map.put(key.get(i), i);
            }
        }

        endArray();

        return map;
    }

    @Override
    public <K, V> Map<K, V> nextListAsMap(ListItem<V> item, MapKey<K, V> key, boolean filterNull) throws IOException {
        final Map<K, V> map = new HashMap<>();

        beginArray();

        while (hasNext()) {
            final V i = item.get();

            if (!filterNull || i != null) {
                map.put(key.get(i), i);
            }
        }

        endArray();

        return map;
    }

    @Override
    public <K, V> Map<K, V> nextListAsMap(ListItem<V> item, MapKey<K, V> key, boolean filterNull, MapInitializer<K, V> initializer) throws IOException {
        final Map<K, V> map = initializer.get();

        beginArray();

        while (hasNext()) {
            final V i = item.get();

            if (!filterNull || i != null) {
                map.put(key.get(i), i);
            }
        }

        endArray();

        return map;
    }


    @Override
    public boolean nextObject(ObjectFieldAssigner handler) throws IOException {
        if (beginObjectStructure()) {

            while (hasNext()) {
                handler.assign();
            }

            endObject();

            return true;

        } else {
            return false;
        }
    }

    @Override
    public <T> T readObject(Class<T> type) {
        return gson.fromJson(this, type);
    }

    @Override
    public <T> T getReader(Class<T> type) {

        //noinspection unchecked
        return (T) this;
    }
}

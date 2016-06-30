package com.miguelgaeta.message_parser;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONReader;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("UnusedDeclaration")
public class MessageParserImplFastJson extends JSONReader implements MessageParser {

    public MessageParserImplFastJson(Reader reader) {
        super(reader);
    }

    @Override
    public boolean beginObjectStructure() throws IOException {
        return startObject();
    }

    @Override
    public String nextName() throws IOException {
        return (String)readObject();
    }

    @Override
    public void skipValue() throws IOException {
        readObject();
    }

    @Override
    public void nextNull() throws IOException {
        if (readObject() != null) {
            throw new IllegalStateException("Non-null value encountered.");
        }
    }

    @Override
    public String nextString() throws IOException {
        return readString();
    }

    @Override
    public String nextString(String defaultValue) throws IOException {
        final String value = readString();

        return value != null ? value : defaultValue;
    }

    @Override
    public String nextStringOrNull() throws IOException {
        return readString();
    }

    @Override
    public boolean nextBoolean() throws IOException {
        try {
            return nextBooleanOrNull();

        } catch (ClassCastException e) {
            throw new IOException("Null boolean value found.");
        }
    }

    @Override
    public boolean nextBoolean(boolean defaultValue) throws IOException {
        final Boolean value = nextBooleanOrNull();

        return value != null ? value : defaultValue;
    }

    @Override
    public Boolean nextBooleanOrNull() throws IOException {
        return (Boolean) readObject();
    }

    @Override
    public double nextDouble() throws IOException {
        try {
            return nextDoubleOrNull();

        } catch (ClassCastException e) {
            throw new IOException("Null double value found.");
        }
    }

    @Override
    public double nextDouble(double defaultValue) throws IOException {
        final Double value = nextDoubleOrNull();

        return value != null ? value : defaultValue;
    }

    @Override
    public Double nextDoubleOrNull() throws IOException {
        try {
            return (double)readObject();
        } catch (JSONException e) {
            return null;
        } catch (ClassCastException e) {
            return null;
        }
    }

    @Override
    public long nextLong() throws IOException {
        try {
            return nextLongOrNull();

        } catch (ClassCastException e) {

            throw new IOException("Null long value found.");
        }
    }

    @Override
    public long nextLong(long defaultValue) throws IOException {
        final Long value = nextLongOrNull();

        return value != null ? value : defaultValue;
    }

    @Override
    public Long nextLongOrNull() throws IOException {
        try {
            return readLong();

        } catch (JSONException e) {
            return null;
        }
    }

    @Override
    public int nextInt() throws IOException {
        try {
            return nextIntOrNull();

        } catch (ClassCastException e) {

            throw new IOException("Null int value found.");
        }
    }

    @Override
    public int nextInt(int defaultValue) throws IOException {
        final Integer value = nextIntOrNull();

        return value != null ? value : defaultValue;
    }

    @Override
    public Integer nextIntOrNull() throws IOException {
        try {
            return readInteger();

        } catch (JSONException e) {
            return null;
        }
    }

    @Override
    public <T> List<T> nextList(ListItem<T> item) throws IOException {
        final List<T> list = new ArrayList<>();

        startArray();

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

        startArray();

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

        startArray();

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

        startArray();

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

        startArray();

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

        startArray();

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
    public boolean nextObject(ObjectFieldAssigner assigner) throws IOException {
        if (beginObjectStructure()) {

            while (hasNext()) {
                assigner.assign();
            }

            endObject();

            return true;

        } else {
            return false;
        }
    }

    @Override
    public <T> T getReader(Class<T> type) {

        //noinspection unchecked
        return (T) this;
    }

    @Override
    public void beginArray() throws IOException {
        startArray();
    }
}

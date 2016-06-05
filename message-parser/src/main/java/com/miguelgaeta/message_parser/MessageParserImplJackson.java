package com.miguelgaeta.message_parser;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.json.ReaderBasedJsonParser;
import com.fasterxml.jackson.core.sym.CharsToNameCanonicalizer;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("UnusedDeclaration")
public class MessageParserImplJackson extends ReaderBasedJsonParser implements MessageParser {

    public MessageParserImplJackson(final Reader reader) throws IOException {
        this(new Factory().configure(reader));
    }

    private MessageParserImplJackson(final Factory factory) throws IOException {
        super(
            factory.context,
            factory.parserFeatures,
            factory.decoratedReader,
            factory.objectCodec,
            factory.charsToNameCanonicalizer);

        nextToken();
    }

    @Override
    public boolean beginObjectStructure() throws IOException {

        if (isExpectedStartObjectToken()) {
            nextToken();

            return true;
        }

        nextToken();

        return false;
    }

    @Override
    public void endObject() throws IOException {
        nextToken();
    }

    @Override
    public boolean hasNext() throws IOException {
        return
            getCurrentToken() != JsonToken.END_ARRAY &&
            getCurrentToken() != JsonToken.END_OBJECT;
    }

    @Override
    public String nextName() throws IOException {
        final String name = getCurrentName();

        nextToken();

        return name;
    }

    @Override
    public void skipValue() throws IOException {
        skipChildren();
        nextToken();
    }

    @Override
    public void nextNull() throws IOException {
        nextToken();
    }

    @Override
    public String nextString() throws IOException {
        final String value = getValueAsString();

        nextToken();

        return value;
    }

    @Override
    public String nextString(String defaultValue) throws IOException {
        final String value = getValueAsString(defaultValue);

        nextToken();

        return value;
    }

    @Override
    public String nextStringOrNull() throws IOException {
        final String value = getValueAsString(null);

        nextToken();

        return value;
    }

    @Override
    public boolean nextBoolean() throws IOException {
        final boolean value = getValueAsBoolean();

        nextToken();

        return value;
    }

    @Override
    public boolean nextBoolean(boolean defaultValue) throws IOException {
        final boolean value = getValueAsBoolean(defaultValue);

        nextToken();

        return value;
    }

    @Override
    public Boolean nextBooleanOrNull() throws IOException {
        final Boolean value = getCurrentToken() == JsonToken.VALUE_NULL ? null : getValueAsBoolean();

        nextToken();

        return value;
    }

    @Override
    public double nextDouble() throws IOException {
        final double value = getValueAsDouble();

        nextToken();

        return value;
    }

    @Override
    public double nextDouble(double defaultValue) throws IOException {
        final double value = getValueAsDouble(defaultValue);

        nextToken();

        return value;
    }

    @Override
    public Double nextDoubleOrNull() throws IOException {
        final Double value = getCurrentToken() == JsonToken.VALUE_NULL ? null : getValueAsDouble();

        nextToken();

        return value;
    }

    @Override
    public long nextLong() throws IOException {
        final long value = getValueAsLong();

        nextToken();

        return value;
    }

    @Override
    public long nextLong(long defaultValue) throws IOException {
        final long value = getValueAsLong(defaultValue);

        nextToken();

        return value;
    }

    @Override
    public Long nextLongOrNull() throws IOException {
        final Long value = getCurrentToken() == JsonToken.VALUE_NULL ? null : getValueAsLong();

        nextToken();

        return value;
    }

    @Override
    public int nextInt() throws IOException {
        final int value = getValueAsInt();

        nextToken();

        return value;
    }

    @Override
    public int nextInt(int defaultValue) throws IOException {
        final int value = getValueAsInt(defaultValue);

        nextToken();

        return value;
    }

    @Override
    public Integer nextIntOrNull() throws IOException {
        final Integer value = getCurrentToken() == JsonToken.VALUE_NULL ? null : getValueAsInt();

        nextToken();

        return value;
    }

    @Override
    public <T> List<T> nextList(ListItem<T> item) throws IOException {
        final List<T> list = new ArrayList<>();

        nextToken();


        while (hasNext()) {
            final T i = item.get();

            if (i != null) {
                list.add(i);
            }
        }

        nextToken();

        return list;
    }

    @Override
    public <T> List<T> nextList(ListItem<T> item, boolean filterNull) throws IOException {
        final List<T> list = new ArrayList<>();

        nextToken();


        while (hasNext()) {
            final T i = item.get();

            if (!filterNull || i != null) {
                list.add(i);
            }
        }

        nextToken();

        return list;
    }

    @Override
    public <T> List<T> nextList(ListItem<T> item, boolean filterNull, ListInitializer<T> initializer) throws IOException {
        final List<T> list = initializer.get();

        nextToken();


        while (hasNext()) {
            final T i = item.get();

            if (!filterNull || i != null) {
                list.add(i);
            }
        }

        nextToken();

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
        return readValueAs(type);
    }

    @Override
    public <T> T getReader(Class<T> type) {

        //noinspection unchecked
        return (T) this;
    }

    @Override
    public void beginArray() throws IOException {
        nextToken();
    }

    @Override
    public void endArray() throws IOException {
        nextToken();
    }

    @SuppressWarnings("SpellCheckingInspection")
    static class Factory extends JsonFactory {

        IOContext context;
        int parserFeatures;
        Reader decoratedReader;
        ObjectCodec objectCodec;
        CharsToNameCanonicalizer charsToNameCanonicalizer;

        public Factory configure(final Reader reader) throws IOException {
            context = _createContext(reader, false);
            parserFeatures = _parserFeatures;
            decoratedReader = _decorate(reader, context);
            objectCodec = _objectCodec;
            charsToNameCanonicalizer = _rootCharSymbols.makeChild(_factoryFeatures);

            return this;
        }
    }
}

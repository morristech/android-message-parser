package com.miguelgaeta.message_parser;

import java.io.IOException;
import java.util.List;

@SuppressWarnings("UnusedDeclaration")
public interface MessageParser {

    boolean beginObjectStructure() throws IOException;

    void endObject() throws IOException;

    void close() throws IOException;

    boolean hasNext() throws IOException;

    String nextName() throws IOException;

    void skipValue() throws IOException;

    void nextNull() throws IOException;

    String nextString() throws IOException;

    String nextString(String defaultValue) throws IOException;

    String nextStringOrNull() throws IOException;

    boolean nextBoolean() throws IOException;

    boolean nextBoolean(boolean defaultValue) throws IOException;

    Boolean nextBooleanOrNull() throws IOException;

    double nextDouble() throws IOException;

    double nextDouble(double defaultValue) throws IOException;

    Double nextDoubleOrNull() throws IOException;

    long nextLong() throws IOException;

    long nextLong(long defaultValue) throws IOException;

    Long nextLongOrNull() throws IOException;

    int nextInt() throws IOException;

    int nextInt(int defaultValue) throws IOException;

    Integer nextIntOrNull() throws IOException;

    <T> List<T> nextList(final ListItem<T> item) throws IOException;

    <T> List<T> nextList(final ListItem<T> item, final boolean filterNull) throws IOException;

    <T> List<T> nextList(final ListItem<T> item, final boolean filterNull, final ListInitializer<T> initializer) throws IOException;

    boolean nextObject(ObjectFieldAssigner handler) throws IOException;

    <T> T readObject(Class<T> type) throws IOException;

    <T> T getReader(Class<T> type);

    void beginArray() throws IOException;
    void endArray() throws IOException;

    interface ListInitializer<T> {

        /**
         * Provides an initialized list implementation.
         *
         * @return A concrete mutable implementation of List.
         */
        List<T> get();
    }

    interface ListItem<T> {

        T get() throws IOException;
    }

    interface ObjectFieldAssigner {

        void assign() throws IOException;
    }
}

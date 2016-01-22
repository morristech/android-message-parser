package com.miguelgaeta.message_parser;

import java.io.IOException;
import java.util.List;

/**
 * Created by Miguel Gaeta on 1/19/16.
 */
@SuppressWarnings("UnusedDeclaration")
public interface MessageParser {

    boolean beginObject() throws IOException;

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

    <T> List<T> nextList(ListInitializer<T> initializer, ListItem<T> item) throws IOException;

    <T> List<T> nextList(ListInitializer<T> initializer, ListItem<T> item, boolean filterNull) throws IOException;

    boolean nextObject(ObjectFieldAssigner handler) throws IOException;

    <T> T readObject(Class<T> type) throws IOException;

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

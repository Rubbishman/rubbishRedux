package com.rubbishman.rubbishRedux;

import com.rubbishman.rubbishRedux.middlewareEnhancer.MiddlewareEnhancer;
import com.rubbishman.rubbishRedux.misc.MyLoggingMiddleware;
import com.rubbishman.rubbishRedux.misc.MyReducer;
import com.rubbishman.rubbishRedux.misc.MyState;
import org.junit.Test;
import redux.api.Reducer;
import redux.api.Store;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class MiddlewareEnchancerTest {

    @Test
    public void testMiddlewareEnhancer() {
        StringBuilder stringBuilder = new StringBuilder();

        OutputStream myOutput = new OutputStream() {
            public void write(int b) throws IOException {
                stringBuilder.append((char)b);
            }
        };

        PrintStream printStream = new PrintStream(myOutput);

        Store.Creator<MyState> creator = new com.glung.redux.Store.Creator();
        MyReducer reducer = new MyReducer(printStream);

        MiddlewareEnhancer<MyState> enhancer = new MiddlewareEnhancer<>();
        enhancer.addMiddleware(new MyLoggingMiddleware(printStream, "moo1"));
        enhancer.addMiddleware(new MyLoggingMiddleware(printStream, "moo2"));
        enhancer.addMiddleware(new MyLoggingMiddleware(printStream, "moo3"));

        creator = enhancer.enhance(creator);

        Store<MyState> store = creator.create(reducer, new MyState());

        store.dispatch("First");
        store.dispatch("Second");
        store.dispatch("Third");

        assertEquals(
       "Initial state INIT" +
                "moo1 First" +
                "moo2 First" +
                "moo3 First" +
                "Adding First" +
                "moo1 Second" +
                "moo2 Second" +
                "moo3 Second" +
                "Adding Second" +
                "moo1 Third" +
                "moo2 Third" +
                "moo3 Third" +
                "Adding Third",
                stringBuilder.toString().replaceAll(System.lineSeparator(), ""));
    }
}

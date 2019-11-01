package com.rubbishman.rubbishRedux.multistageCreateObjectTest.action;

import com.rubbishman.rubbishRedux.createObjectCallback.interfaces.ICreateObjectCallback;

public class MultistageCreateObject<T> {
    public final T createObject;
    public transient final ICreateObjectCallback callback;

    public MultistageCreateObject(T createObject, ICreateObjectCallback callback) {
        this.createObject = createObject;
        this.callback = callback;
    }
}

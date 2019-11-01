package com.rubbishman.rubbishRedux.createObjectCallback.action;

import com.rubbishman.rubbishRedux.createObjectCallback.interfaces.ICreateObjectCallback;

public class CreateObject<T> {
    public final T createObject;
    public transient final ICreateObjectCallback callback;

    public CreateObject(T createObject, ICreateObjectCallback callback) {
        this.createObject = createObject;
        this.callback = callback;
    }
}

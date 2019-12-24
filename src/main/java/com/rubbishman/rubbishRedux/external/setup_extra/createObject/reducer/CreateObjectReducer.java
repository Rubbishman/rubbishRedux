package com.rubbishman.rubbishRedux.external.setup_extra.createObject.reducer;

import com.rubbishman.rubbishRedux.external.operational.action.createObject.CreateObject;
import com.rubbishman.rubbishRedux.internal.dynamicObjectStore.store.IdGenerator;
import com.rubbishman.rubbishRedux.external.operational.store.IdObject;
import com.rubbishman.rubbishRedux.external.operational.store.Identifier;
import com.rubbishman.rubbishRedux.external.operational.store.ObjectStore;
import redux.api.Reducer;

public class CreateObjectReducer implements Reducer<ObjectStore> {
    private Reducer<ObjectStore> wrappedReducer;
    private Runnable postDispatchRunnable;

    public void setWrappedReducer(Reducer<ObjectStore> wrapperReducer) {
        this.wrappedReducer = wrapperReducer;
    }

    public void postDispatch() {
        if(postDispatchRunnable != null) {
            postDispatchRunnable.run();
            postDispatchRunnable = null;
        }
    }

    public ObjectStore reduceCreateObject(ObjectStore state, CreateObject action) {
        IdGenerator idGenerator = new IdGenerator(state.idGenerator.idSequence);

        Identifier identifier = idGenerator.nextId(action.createObject.getClass());
        IdObject idObject = new IdObject(identifier, action.createObject);

        ObjectStore cloned = new ObjectStore(
                state.objectMap.assoc(identifier, idObject),
                new IdGenerator(idGenerator.idSequence)
        );
        if(action.callback != null) {
            this.postDispatchRunnable = () -> {
                action.callback.postCreateState(idObject);
            };
        }

        return cloned;
    }

    public ObjectStore reduce(ObjectStore state, Object action) {
        if(action instanceof CreateObject) {
            return reduceCreateObject(state, (CreateObject)action);
        } else if(wrappedReducer != null) {
            return wrappedReducer.reduce(state, action);
        }

        return state;
    }
}

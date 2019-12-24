package com.rubbishman.rubbishRedux.external.setup;

import com.rubbishman.rubbishRedux.external.operational.store.ObjectStore;
import com.rubbishman.rubbishRedux.external.operational.action.multistageAction.MultistageActionResolver;
import redux.api.Reducer;
import redux.api.enhancer.Middleware;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RubbishContainerOptions {
    protected List<Middleware<ObjectStore>> middlewareList = new ArrayList<>();
    protected Map<Class,MultistageActionResolver<ObjectStore>> multistageActionList = new HashMap<>();
    protected IRubbishReducer reducer;
    public RubbishContainerOptions addMiddleware(Middleware<ObjectStore> middleware) {
        middlewareList.add(middleware);

        return this;
    }

    public RubbishContainerOptions addMultistageAction(Class clazz, MultistageActionResolver multistageActionResolver) {
        multistageActionList.put(clazz, multistageActionResolver);

        return this;
    }

    public void setReducer(IRubbishReducer reducer) {
        this.reducer = reducer;
    }
}

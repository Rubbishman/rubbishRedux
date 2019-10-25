package com.rubbishman.rubbishRedux.multistageActionTest.action.MultistageAction;

import com.rubbishman.rubbishRedux.dynamicObjectStore.store.ObjectStore;
import com.rubbishman.rubbishRedux.multistageActionTest.action.IncrementCounter;
import com.rubbishman.rubbishRedux.multistageActionTest.action.IncrementCounterResolved;
import com.rubbishman.rubbishRedux.multistageActionTest.stage.StageConstants;
import com.rubbishman.rubbishRedux.multistageActionTest.state.Counter;
import com.rubbishman.rubbishRedux.multistageActions.action.MultistageAction;
import com.rubbishman.rubbishRedux.multistageActions.stage.Stage;

import java.util.Random;

public class IncrementCounterResolution implements MultistageAction<IncrementCounter> {
    private static final Stage myStage = StageConstants.INCREMENT_RESOLUTION;

    public final Random rand;

    public IncrementCounterResolution(long seed) {
        rand = new Random(seed);
    }

    public IncrementCounterResolution() {
        rand = new Random();
    }

    public Stage getStage() {
        return myStage;
    }

    public Object provideAction(IncrementCounter action, ObjectStore state, long nowTime) {
        Counter counter = (Counter)state.objectMap.get(action.targetCounterId).object;

        int increment = 0;
        for(int i = 0; i < counter.incrementDiceNum; i++) {
            increment += (rand.nextInt(counter.incrementDiceSize) + 1);
        }

        return new IncrementCounterResolved(action.targetCounterId, increment);
    }
}

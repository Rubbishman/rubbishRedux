package com.rubbishman.rubbishRedux.multistageActions.stage;

public class StageAction {
    public final Stage stage;
    public final Object action;

    public StageAction(Stage stage, Object action) {
        this.stage = stage;
        this.action = action;
    }
}

package com.android.workflow;

public class ActionCallbackToOriginWorkflow  extends SingleAction
{

    @Override
    protected Action.State doStep()
    {
        callbackToOriginWorkflow(null);

        return Action.State.completed;
    }
}

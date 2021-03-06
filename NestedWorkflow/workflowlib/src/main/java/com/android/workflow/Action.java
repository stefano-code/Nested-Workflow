package com.android.workflow;

import android.os.Bundle;

public abstract class Action
{
    protected enum State
    {
        idle, running, blocked, completed
    }

    public static WorkflowContext wfContext;

    public abstract State execute();

    public boolean succeeded(Void w)
    {
        return true;
    }

    protected void logExecution()
    {
        Journal.Log(wfContext.tag, "executing " + getMsg());
    }

    protected abstract State doStep();

    protected abstract State getState();

    protected abstract boolean completed();

    public String getMsg()
    {
        //SS Log.i( wfContext.tag, "msg " +  getClass().getSimpleName() );
        return getClass().getSimpleName();
    }

    public abstract String getBlockingActions();

    public void startNestedWorkflow(String packageName, String callbackAction, Bundle data )
    {
        WorkflowService.startNestedWorkflow(wfContext.ctx, packageName, callbackAction, data );
    }

    public void callbackToOriginWorkflow(Bundle data )
    {
        WorkflowService.callbackToOriginWorkflow(wfContext.ctx, data );
    }

    public boolean isNestedWorkflow()
    {
        return WorkflowService.isNested();
    }

    public void reset()
    {}
}

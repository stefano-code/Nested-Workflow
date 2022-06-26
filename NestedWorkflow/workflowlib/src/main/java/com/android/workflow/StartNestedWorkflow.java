package com.android.workflow;

import android.util.Log;

import com.android.workflow.SingleAction;

public class StartNestedWorkflow extends SingleAction
{
    public static String callbackAction = "nestedworkflow.completed";

    private String packageNestedWorkflow;

    public StartNestedWorkflow(String packageNestedWorkflow)
    {
        super();
        this.packageNestedWorkflow = packageNestedWorkflow;
    }

    @Override
    protected State doStep()
    {
        startNestedWorkflow(packageNestedWorkflow, callbackAction, null);

        return State.completed;
    }
}

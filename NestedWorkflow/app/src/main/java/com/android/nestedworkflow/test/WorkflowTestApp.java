package com.android.nestedworkflow.test;

import com.android.workflow.WorkflowApplication;
import com.android.workflow.WorkflowEngine;

public class WorkflowTestApp extends WorkflowApplication
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        WorkflowTest wf = new WorkflowTest(WorkflowEngine.getInstance(), getBaseContext());
        wf.execute();
    }
}

package com.android.workflow.app2;

import android.util.Log;

import com.android.workflow.WorkflowApplication;
import com.android.workflow.WorkflowEngine;

public class WorkflowApp2  extends WorkflowApplication
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        Log.e("TEST****","TEST********");
        Workflow2 wf = new Workflow2(WorkflowEngine.getInstance(), this);
        wf.execute();
    }
}

package com.android.workflow;

import android.content.Context;

public class WorkflowContext
{
    private String rawTag;
    public String tag;
    public String actionPathForDump;
    public final Context ctx;


    public WorkflowContext( String tag, Context ctx)
    {
        this.tag = setTag(tag);
        this.ctx = ctx;
        Action.wfContext = this;
    }

    private String setTag(String tag)
    {
        rawTag = tag;
        return "wf." + tag;
    }

    public void nestTag(String origin )
    {
        this.tag = setTag( origin + "." + rawTag );
    }
}

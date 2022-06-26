package com.android.workflow.test;


import com.android.workflow.AsyncAwaitAction;
import com.android.workflow.Predicate;

public class AsyncActionTest extends AsyncAwaitAction
{
    public AsyncActionTest(String actionName, Predicate<Void> f) {
        super(actionName, f);
    }
}

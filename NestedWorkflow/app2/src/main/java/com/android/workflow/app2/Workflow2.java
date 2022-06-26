package com.android.workflow.app2;

import static com.android.workflow.WorkflowEngine.Parallel;
import static com.android.workflow.WorkflowEngine.Sequence;

import android.content.Context;

import com.android.workflow.ActionCallbackToOriginWorkflow;
import com.android.workflow.CheckAction;
import com.android.workflow.WorkflowContext;
import com.android.workflow.WorkflowEngine;
import com.android.workflow.test.ActionTest;
import com.android.workflow.test.AsyncActionTest;
import com.android.workflow.test.RandomPredicate;

public class Workflow2
{
    WorkflowEngine engine;

    public Workflow2(WorkflowEngine wfe, Context context)
    {
        engine = wfe;
        WorkflowContext wfc = new WorkflowContext("nested_workflow", context);
        wfe.CreateWorkflow(wfc,Sequence("SequenceRoot").
                                start(new AsyncActionTest("Action10", new RandomPredicate())).
                                andThen(new CheckAction("Predicate2", new RandomPredicate()).
                                        thenElse(new ActionTest("Action11"),
                                                        new AsyncActionTest("Action12", new RandomPredicate()))).
                                andThen(new ActionTest("Action14")).
                                andThen(new ActionCallbackToOriginWorkflow())

        );
    }

    public void execute()
    {
        engine.execute();
    }
}

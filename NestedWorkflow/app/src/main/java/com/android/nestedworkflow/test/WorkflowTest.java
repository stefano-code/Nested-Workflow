package com.android.nestedworkflow.test;

import static com.android.workflow.WorkflowEngine.Parallel;
import static com.android.workflow.WorkflowEngine.Sequence;
import static com.android.workflow.WorkflowEngine.WaitBroadcast;

import android.content.Context;

import com.android.workflow.ActionCallbackToOriginWorkflow;
import com.android.workflow.CheckAction;
import com.android.workflow.StartNestedWorkflow;
import com.android.workflow.WorkflowContext;
import com.android.workflow.WorkflowEngine;
import com.android.workflow.test.ActionTest;
import com.android.workflow.test.AsyncActionTest;
import com.android.workflow.test.RandomPredicate;

public class WorkflowTest
{
    WorkflowEngine engine;

    public WorkflowTest(WorkflowEngine wfe, Context context)
    {
        engine = wfe;
        WorkflowContext wfc = new WorkflowContext("test_workflow", context);
        wfe.CreateWorkflow(wfc,Sequence("SequenceRoot").
                start((Parallel("Parallel0").
                                start(Sequence("Sequence1").
                                        start(new ActionTest("Action1")).
                                        andThen(new CheckAction("Predicate1", new RandomPredicate()).
                                                thenElse(new ActionTest("Action2"),
                                                         new AsyncActionTest("Action3", new RandomPredicate()))).
                                        andThen(new ActionTest("Action4"))).
                                andConcurrently(Sequence("Sequence2").
                                        start(new AsyncActionTest("Action5", new RandomPredicate())).
                                        andThen(new StartNestedWorkflow("com.android.workflow.app2"))).
                                andConcurrently(Sequence("Sequence3").
                                        start(WaitBroadcast(StartNestedWorkflow.callbackAction)).
                                        andThen(new ActionTest("Action8"))
                                    )
                                )
                        ).
                andThen(new ActionCallbackToOriginWorkflow())
        );
    }

    public void execute()
    {
        engine.execute();
    }
}

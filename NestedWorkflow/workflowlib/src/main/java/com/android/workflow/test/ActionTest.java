package com.android.workflow.test;


import com.android.workflow.Journal;
import com.android.workflow.SingleAction;

public class ActionTest extends SingleAction
{
    String name;
    Simulation simulation = new Simulation();

    public ActionTest(String n)
    {
        name = n;
    }

    @Override
    protected State doStep()
    {
        simulation.update();
        if(simulation.isTerminate())
            Journal.Log(wfContext.tag, name + " isTerminate() " + simulation.isTerminate());
        return (simulation.isTerminate()) ? State.completed : State.running;
        //SS return State.completed;
    }

    public String getMsg()
    {
        //SS Log.i( wfContext.tag, "msg " +  getClass().getSimpleName() );
        return getClass().getSimpleName() + " " + name;
    }
}

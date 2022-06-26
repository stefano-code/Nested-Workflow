package com.android.workflow;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import java.util.concurrent.atomic.AtomicBoolean;

class WaitBroadcastAction extends SingleAction
{
    private BroadcastReceiver rec;
    private String broadcast;
    private AtomicBoolean unlock;

    public WaitBroadcastAction(String broadcast)
    {
        this.broadcast = broadcast;
        unlock = new AtomicBoolean();
    }

    @Override
    protected State getState()
    {
        updateState();
        return state;
    }

    @Override
    public State execute()
    {
        updateState();
        if (state == State.blocked)
            return state;
        else
            return super.execute();
    }

    private void updateState()
    {
        if (state == State.idle)
        {
            state = State.blocked;

            rec = new BroadcastReceiver()
            {
                @Override
                public void onReceive(Context context, Intent intent)
                {
                    Journal.Log(wfContext.tag, "WaitBroadcastAction " + broadcast + " unlocked");
                    wfContext.ctx.unregisterReceiver(rec);
                    synchronized (wfContext)
                    {
                        unlock.set(true);
                        wfContext.notify();
                    }
                }
            };

            wfContext.ctx.registerReceiver(rec, new IntentFilter(broadcast));

            Journal.Log(wfContext.tag, "WaitBroadcastAction " + broadcast + " blocked");
        }

        if (state == State.blocked && unlock.get())
            state = State.running;
    }

    @Override
    protected State doStep()
    {
        state = State.completed;
        return state;
    }

    @Override
    public String getMsg()
    {
        return this.getClass().getSimpleName() + " " + broadcast;
    }

    @Override
    public String getBlockingActions()
    {
        if (state == State.blocked)
            return wfContext.actionPathForDump + getMsg();
        else
            return super.getBlockingActions();
    }

    @Override
    public void reset()
    {
        super.reset();
        unlock.set(false);
    }
}

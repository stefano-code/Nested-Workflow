package com.android.workflow;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;

import java.util.concurrent.atomic.AtomicBoolean;


/*

<service android:name="com.android.nestedworkflow.framework.WorkflowService">
  <intent-filter>
    <action android:name="##packagename##.NEST_WORKFLOW"/>
    <category android:name="android.intent.category.DEFAULT"/>
  </intent-filter>
</service>
 */


public class WorkflowService extends PersistentService
{
    static AtomicBoolean nested = new AtomicBoolean(false);
    static String callBackActionIntent = null;
    BroadcastReceiver dumpReceiver;
    protected Intent startIntent;

    public static void startNestedWorkflow(Context ctx, String packageName, String callbackAction, Bundle data)
    {
        Intent i = new Intent(packageName + ".NEST_WORKFLOW");
        i.setClassName(packageName, "com.android.workflow.WorkflowService");
        i.putExtra("ORIGIN", Action.wfContext.tag);
        if(callbackAction != null)
            i.putExtra("CALLBACK_ACTION", callbackAction);
        if(data != null)
            i.putExtra("DATA", data);
        Journal.Log("wf", "WorkflowService startNestedWorkflow " + i.toString());
        Platform.startService(ctx, i);
    }

    public static void callbackToOriginWorkflow(Context ctx, Bundle data)
    {
        if(callBackActionIntent != null)
        {
            Intent i = new Intent(callBackActionIntent);
            if(data != null)
                i.putExtra("DATA", data);
            Journal.Log("wf", "WorkflowService callbackToOriginWorkflow " + i.toString());
            ctx.sendBroadcast(i);
        }
    }

    public static boolean isNested()
    {
        return nested.get();
    }

    @Override
    public final int onStartCommand(Intent intent, int flags, int startId)
    {
        Journal.Log("wf", "WorkflowService onStartCommand");

        String action = intent.getAction();

        if (action == null)
        {
            Journal.Log("wf", "WorkflowService onStartCommand w/ null");
            return START_NOT_STICKY;
        }

        if (action.contentEquals(getPackageName() + ".NEST_WORKFLOW"))
        {
            String origin = intent.getStringExtra("ORIGIN");
            if(origin == null)
                origin = "###";
            if(intent.hasExtra("CALLBACK_ACTION"))
                callBackActionIntent = intent.getStringExtra("CALLBACK_ACTION");
            readData(intent.getBundleExtra("DATA"));

            Journal.Log("wf", "WorkflowService in " + getPackageName() + "started nested from " + origin + " callBackActionIntent " + callBackActionIntent);

            nested.set(true);
            if (Action.wfContext != null)
            {
                Action.wfContext.nestTag(origin);
            }
        }
        else
        {
            if( startIntent == null ) {
                startIntent = intent;
                WorkflowEngine.WaitStartIntent().setCompleted(intent);
            }
            // else processUnexpectedIntent(intent);
        }

        return START_NOT_STICKY;
    }

    protected void readData(Bundle b)
    {
    }

    @TargetApi(27)
    @Override
    public void onCreate()
    {
        super.onCreate();

        dumpReceiver = new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context context, Intent intent)
            {
                Journal.Log( "DUMP", "service: " + getClass().getName());
                WorkflowEngine.getInstance().dumpWorkflowState();
            }
        };
       registerReceiver(dumpReceiver, new IntentFilter("com.workflow.DUMP"));
    }


    protected long getRestartTime()
    {
        return 5000;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        unregisterReceiver(dumpReceiver);
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }
}

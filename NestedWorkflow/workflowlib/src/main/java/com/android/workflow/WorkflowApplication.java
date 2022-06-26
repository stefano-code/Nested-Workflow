package com.android.workflow;

import android.app.Application;
import android.content.Intent;


// under application
/*
        <receiver android:name="com.technogym.android.workflow.UsbAttachedReceiver" >
            <intent-filter>
                <action android:name="android.hardware.usb.action.USB_DEVICE_ATTACHED" />
            </intent-filter>
        </receiver>

 */
public abstract class WorkflowApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();

        Intent wfIntent = new Intent();
        wfIntent.setClassName( this.getPackageName(), "com.technogym.android.workflow.WorkflowService");
        Platform.startService(this, wfIntent );
    }
}

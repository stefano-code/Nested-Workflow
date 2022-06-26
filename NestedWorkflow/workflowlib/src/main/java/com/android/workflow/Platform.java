package com.android.workflow;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

public class Platform
{
    @TargetApi(26)
    public static void startService(Context context, Intent intent)
    {
        try
        {
            Log.d("CrossPlatformUtil", "startService intent " + intent.toString());

            int currentapiVersion = android.os.Build.VERSION.SDK_INT;
            if (currentapiVersion < Build.VERSION_CODES.O)
                context.startService(intent);
            else
            {
                context.startForegroundService(intent);
            }
        } catch (Exception e)
        {
            Log.e("CrossPlatformUtil", "startService exception ");
            e.printStackTrace();
        }
    }
}

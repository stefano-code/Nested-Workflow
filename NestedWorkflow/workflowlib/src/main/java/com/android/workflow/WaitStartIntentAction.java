package com.android.workflow;

import android.content.Intent;

public class WaitStartIntentAction extends AsynchAndWaitCallback {

    private Intent intent;

    WaitStartIntentAction() {
        super();
    }

    public boolean intentNull(Void w) {
        return intent == null;
    }

    @Override
    protected void triggerCallback() {
    }

    public void setCompleted(Intent intent) {
        this.intent = intent;
        this.signalCallbackCompleted();
    }
}
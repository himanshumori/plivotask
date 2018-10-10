package com.plivotest.callhandler;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.plivo.endpoint.Endpoint;
import com.plivo.endpoint.EventListener;
import com.plivo.endpoint.Incoming;
import com.plivo.endpoint.Outgoing;
import com.plivotest.callhandler.listeners.CallEventListener;
import com.plivotest.util.Utils;

import static com.plivotest.util.Utils.PLIVO_PASSWORD;
import static com.plivotest.util.Utils.PLIVO_USERNAME;

public class CallHandlerService extends Service implements EventListener {

    public Endpoint endpointObj;
    public Incoming incomingObj;
    public Outgoing outgoingObj;

    public static CallEventListener eventListener;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String action = intent.getAction();

        if (action.equals(Utils.LOAD_USER_SESSION)) {

            loadUser();

        } else if (action.equals(Utils.ACCEPT_ACTION)) {

            incomingObj.answer();

        } else if (action.equals(Utils.REJECT_ACTION)) {

            incomingObj.hangup();

        } else if (action.equals(Utils.END_ACTION)) {

            String callNumber = intent.getStringExtra(Utils.CALL_NUMBER);
            outgoingObj = endpointObj.createOutgoingCall();
            outgoingObj.hangup();

        } else if (action.equals(Utils.DIAL_ACTION)) {

            String  callNumber = intent.getStringExtra(Utils.CALL_NUMBER);
            outgoingObj.call(callNumber);
        }

        return START_STICKY;
    }

    private void loadUser() {

        endpointObj = Endpoint.newInstance(true, this);
        endpointObj.setRegTimeout(3600);//Number in seconds
        endpointObj.login(PLIVO_USERNAME, PLIVO_PASSWORD);
        outgoingObj = new Outgoing(endpointObj);
    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onDestroy() {
        // service finished
        super.onDestroy();
    }

    @Override
    public void onLogin() {
        eventListener.onShowMessage("Login Success");
    }

    @Override
    public void onLogout() {
        eventListener.onShowMessage("Logout");
    }

    @Override
    public void onLoginFailed() {
        eventListener.onShowMessage("LoginFailed");
    }

    @Override
    public void onIncomingDigitNotification(String s) {

    }

    @Override
    public void onIncomingCall(Incoming incoming) {

        CallNotificationBuilder builder = new CallNotificationBuilder();

        builder.build(this, incoming.getCallId());
    }

    @Override
    public void onIncomingCallHangup(Incoming incoming) {
        incomingObj = incoming;
    }

    @Override
    public void onIncomingCallRejected(Incoming incoming) {

    }

    @Override
    public void onOutgoingCall(Outgoing outgoing) {

    }

    @Override
    public void onOutgoingCallAnswered(Outgoing outgoing) {

    }

    @Override
    public void onOutgoingCallRejected(Outgoing outgoing) {
        eventListener.onCallFailed();
    }

    @Override
    public void onOutgoingCallHangup(Outgoing outgoing) {
        eventListener.onCallFailed();
    }

    @Override
    public void onOutgoingCallInvalid(Outgoing outgoing) {
        eventListener.onCallFailed();
    }
}

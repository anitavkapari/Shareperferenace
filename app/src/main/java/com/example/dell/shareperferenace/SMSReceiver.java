package com.example.dell.shareperferenace;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras() ;
        SmsMessage[] smsMessages=null;
        String messge="";
        if (bundle !=null){
            Object[] pdus = (Object[]) bundle.get("pdus");
            smsMessages = new SmsMessage[pdus.length];
            for (int i=0;i<smsMessages.length; i++){
                smsMessages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                messge += "\r\nMessage:";
                messge += smsMessages[i].getMessageBody().toString();
                messge += "\r\n";
                String sender = smsMessages[i].getDisplayOriginatingAddress();
                messge += sender;
            }
            Intent smsIntent = new Intent("sms");
            smsIntent.putExtra("sms",messge);
            LocalBroadcastManager.getInstance(context).sendBroadcast(smsIntent);

            Toast.makeText(context,messge,Toast.LENGTH_LONG).show();
        }
    }
}

package com.example.dell.shareperferenace;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView txtMessage;
EditText edtName,edtAddress,edtPhone;
    SharedPreferences sharedpreferences;
    public static final String NAME = "name";
    public static final String ADDRESS = "Address";
    public static final String PHONE = "phone";

    private  static final int REQUEST_PERMISSION_SMS=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         txtMessage = findViewById(R.id.txtMessage);
        edtName=findViewById(R.id.edtName);
        edtAddress=findViewById(R.id.edtAddress);
        edtPhone=findViewById(R.id.edtPhone);
        sharedpreferences=getApplicationContext().getSharedPreferences("MuApp",MODE_PRIVATE);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_SMS,Manifest.permission.RECEIVE_SMS},REQUEST_PERMISSION_SMS);
        }
    }

    public void Save(View view) {
        String name = edtName.getText().toString().trim();
        String address = edtName.getText().toString().trim();
        String phone = edtName.getText().toString().trim();
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("NAME",name);
        editor.putString("ADDRESS",address);
        editor.putString("PHONE",phone);
        editor.apply();


    }

    public void Get(View view) {
        String name = sharedpreferences.getString(NAME, "");
        String address = sharedpreferences.getString(ADDRESS, "");
        String phone = sharedpreferences.getString(PHONE, "");
        edtName.setText(name);
        edtAddress.setText(address);
        edtPhone.setText(phone);
    }
        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if (requestCode== REQUEST_PERMISSION_SMS){
                if (grantResults.length > 0 && grantResults[0] ==  PackageManager.PERMISSION_GRANTED);
            }else {
                Toast.makeText(this,"Permition is mandtory",Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_SMS,Manifest.permission.RECEIVE_SMS},
                        REQUEST_PERMISSION_SMS);
            }
        }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(smsReceiver,new IntentFilter("sms"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(smsReceiver);

    }

    private BroadcastReceiver smsReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String sms = intent.getStringExtra("sms");
                txtMessage.setText(sms);
            }
        };
    }


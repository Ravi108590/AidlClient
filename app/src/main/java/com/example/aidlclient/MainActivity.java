package com.example.aidlclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

import com.example.aidlclient.databinding.ActivityMainBinding;
import com.example.aidlserver.IAdditionInterface;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding activityMainBinding;

    IAdditionInterface iAdditionInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = activityMainBinding.getRoot();
        setContentView(view);

        Intent intent = new Intent("android.intent.action.AdditionService");
        intent.setPackage("com.example.aidlserver");
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE);

        activityMainBinding.sumbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int numf = Integer.parseInt(activityMainBinding.numfirst.getText().toString());
                int nums = Integer.parseInt(activityMainBinding.numsecond.getText().toString());

                if (iAdditionInterface != null) {
                    try {
                        int result = iAdditionInterface.add(numf, nums);
                        activityMainBinding.output.setText(String.valueOf(result));
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e("Service Connection", "iAdditionInterface is null");
                }

            }
        });
    }

    ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            iAdditionInterface = IAdditionInterface.Stub.asInterface(iBinder);
            Log.d("Connection", "ServiceConnection Done");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
         Log.d("Disconnect", "Service Disconnected");
        }
    };
}
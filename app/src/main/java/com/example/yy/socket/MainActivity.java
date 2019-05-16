package com.example.yy.socket;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {


    private TextView messageView;
    private TextView gotMsgView;
    private Button requestBtn;
    private Button stopBtn;

    private ExecutorService exec;

    private static final String TAG = "Main";

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    Log.d(TAG,"get handler message");
                    Log.d(TAG,"obj: " + msg.obj);
                    String s = (String) msg.obj;
                    Log.d(TAG,"s: " + s);
                    messageView.setText(s);
                    break;
                case 2:
                    String ss = (String)msg.obj;
                    gotMsgView.setText(ss);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        messageView = findViewById(R.id.message_to_server);
        gotMsgView = findViewById(R.id.message_from_server);

        requestBtn = findViewById(R.id.get_msg_btn);
        stopBtn = findViewById(R.id.stop_btn);

        requestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new RequestTask(mHandler)).start();
                Log.d(TAG,"task start");

            }
        });

        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //exec.shutdown();
                Log.d(TAG,"task stop");
            }
        });

    }
}

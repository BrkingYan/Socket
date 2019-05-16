package com.example.yy.socket;

import android.os.Message;
import android.util.Base64;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import android.os.Handler;

public class RequestTask implements Runnable {

    private Handler mHandler;

    private static final String TAG = "task";

    public RequestTask(Handler handler){
        mHandler = handler;
    }

    @Override
    public void run() {
            String server = "192.168.31.129";
            int serverPort = 7;

            byte[] data = "request message".getBytes();

            try {
                Log.d(TAG,"before socket");
                Socket socket = new Socket(server,serverPort);
                Log.d(TAG,"after socket");

                InputStream in = socket.getInputStream();
                OutputStream out = socket.getOutputStream();

                //客户端向服务器发送消息
                out.write(data);
                Log.d("client",new String(data));


                int totalBytesRcvd = 0;
                int bytesRcvd;
                while (totalBytesRcvd < data.length){
                    if ((bytesRcvd = in.read(data,totalBytesRcvd,data.length-totalBytesRcvd)) == -1){
                        throw new SocketException("Connection closed premeaturely");
                    }
                    totalBytesRcvd += bytesRcvd;
                }

                Message message = Message.obtain(mHandler);
                message.what = 1;
                String str = new String(data);
                message.obj = str;
                Log.d(TAG,"sent:" + message.obj);
                mHandler.sendMessage(message);

                /*byte[] buffer = new byte[32];
                while (in.read(buffer) != -1){

                }
                Message message1 = Message.obtain(mHandler);
                message1.what = 2;
                String str2 = new String(buffer);
                message1.obj = str2;
                mHandler.sendMessage(message1);*/

                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}

package com.example.project.myspeechtotext.utility;

import android.app.Activity;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class InternetConnectionCheck  extends AsyncTask<Void, Void, Boolean> {
    private Activity mActivity;
    public InternetConnectionCheck(Activity activity) {
        mActivity = activity;
    }


    @Override
    protected Boolean doInBackground(Void... voids) {
        try{
            int timeoutMs = 1500;
            Socket sock = new Socket();
            SocketAddress sockaddr = new InetSocketAddress("8.8.8.8", 53);

            sock.connect(sockaddr, timeoutMs);
            sock.close();

            return true;
        }catch (IOException e) { return false; }
    }

    @Override
    protected void onPostExecute(Boolean mBoolean) {
        super.onPostExecute(mBoolean);
    }


}

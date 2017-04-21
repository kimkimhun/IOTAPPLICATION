package com.example.kimhunz.controlapplication;

/**
 * Created by KiMHUNZ on 20/2/2560.
 */

import android.graphics.Color;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;


import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URISyntaxException;


public class StatusFragment extends Fragment {


    TextView tv_Temp_status, tv_Hum_status, tv_Rotated_status, tv_Day, tv_Connect, tv_Temp_stat, tv_Hum_stat;

    private Socket mSocket;

    {
        try {
            //https://obscure-ravine-60856.herokuapp.com/
            //http://10.58.157.134:8080
            //http://192.168.1.38:8080
            mSocket = IO.socket("http://192.168.1.38:8080");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSocket.connect();
        // SEND Message

        mSocket.on("DeviceSend", onMessageSend);
        Log.i("DeviceSend", onMessageSend.toString());


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSocket.disconnect();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_status, container, false);

        layout_contain(v);

        return v;
    }

    // Read View ID
    private void layout_contain(View v) {
        tv_Temp_status = (TextView) v.findViewById(R.id.tv_Temp_status);
        tv_Hum_status = (TextView) v.findViewById(R.id.tv_Hum_status);
        tv_Rotated_status = (TextView) v.findViewById(R.id.tv_Rotated_status);
        tv_Day = (TextView) v.findViewById(R.id.tv_Day);
        tv_Connect = (TextView) v.findViewById(R.id.tv_Connect);
        tv_Temp_stat = (TextView) v.findViewById(R.id.tv_Temp_stat);
        tv_Hum_stat = (TextView) v.findViewById(R.id.tv_Hum_stat);
    }

    // Receive Status
    private Emitter.Listener onMessageSend = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String[] message = new String[5];
                    try {
                        message[0] = data.getString("temperature");
                        message[1] = data.getString("humidity");
                        message[2] = data.getString("hour");
                        message[3] = data.getString("day");
                        message[4] = data.getString("connect");

                        DisplayMessage(message);
                    } catch (JSONException e) {
                        return;
                    }
                    Log.i("msg", message[0].toString());

                }
            });
        }
    };


    private void DisplayMessage(String[] message) {
        Double temp = Double.parseDouble(message[0]);

        tv_Temp_status.setText(String.format("%.1f", temp) + " C");
        tv_Hum_status.setText(message[1] + " %");
        tv_Rotated_status.setText(message[2] + " H");
        tv_Day.setText(message[3]);

        if (Double.parseDouble(message[0]) < 37) {
            tv_Temp_stat.setText(R.string.text_status_fair);
            tv_Temp_stat.setTextColor(this.getResources().getColor(R.color.fairColor));
        } else if (Double.parseDouble(message[0]) > 37) {
            tv_Temp_stat.setText(R.string.text_status_good);
            tv_Temp_stat.setTextColor(this.getResources().getColor(R.color.goodColor));
        }
        if (Double.parseDouble(message[1]) < 50) {
            tv_Hum_stat.setText(R.string.text_status_fair);
            tv_Hum_stat.setTextColor(this.getResources().getColor(R.color.fairColor));
        } else if (Double.parseDouble(message[1]) > 50) {
            tv_Hum_stat.setText(R.string.text_status_good);
            tv_Hum_stat.setTextColor(this.getResources().getColor(R.color.goodColor));
        }
        if (Integer.parseInt(message[4]) == 1) {
            tv_Connect.setText(R.string.text_connect_stat_connect);
        } else {
            tv_Connect.setText(R.string.text_connect_stat_disconnect);
        }

    }


}

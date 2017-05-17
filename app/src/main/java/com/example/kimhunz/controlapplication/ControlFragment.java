package com.example.kimhunz.controlapplication;

/**
 * Created by KiMHUNZ on 21/2/2560.
 */

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

public class ControlFragment extends Fragment implements View.OnClickListener {

    double valueTemp = 36.00;
    int valueHum = 45;
    int valueHour = 1;
    int valueDay = 1;
    ProgressDialog progressDialog;

    private Socket mSocket;

    {
        try {
            //https://obscure-ravine-60856.herokuapp.com/
            //http://10.58.157.134:8080
            //http://192.168.1.38:8080
            mSocket = IO.socket("https://obscure-ravine-60856.herokuapp.com");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private TextView tvHourStat, tvHumStat, tvTempStat, tvDayStat;
    private Button btnSend;
    private ImageButton img_btn_HumMinus, img_btn_TempMinus, img_btn_DayMinus, img_btn_HourMinus, img_btn_HumPlus, img_btn_TempPlus, img_btn_DayPlus, img_btn_HourPlus;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSocket.connect();


        mSocket.on("DeviceSet", getOnMessageSetDevice);

        // function use about load dialog show data
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
    }

    @Override
    public void onDestroy() {

        mSocket.disconnect();
        Log.i("disconnect", "disconnect");
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_control, container, false);
        layout_containt(v);
        return v;
    }

    private void layout_containt(View v) {

        tvHourStat = (TextView) v.findViewById(R.id.tvHourStat);
        tvHumStat = (TextView) v.findViewById(R.id.tvHumStat);
        tvTempStat = (TextView) v.findViewById(R.id.tvTempStat);
        tvDayStat = (TextView) v.findViewById(R.id.tvDayStat);

        btnSend = (Button) v.findViewById(R.id.btn_send);

        img_btn_TempMinus = (ImageButton) v.findViewById(R.id.img_btn_TempMinus);
        img_btn_TempPlus = (ImageButton) v.findViewById(R.id.img_btn_TempPlus);
        img_btn_HumMinus = (ImageButton) v.findViewById(R.id.img_btn_HumMinus);
        img_btn_HumPlus = (ImageButton) v.findViewById(R.id.img_btn_HumPlus);
        img_btn_DayMinus = (ImageButton) v.findViewById(R.id.img_btn_DayMinus);
        img_btn_DayPlus = (ImageButton) v.findViewById(R.id.img_btn_DayPlus);
        img_btn_HourMinus = (ImageButton) v.findViewById(R.id.img_btn_HourMinus);
        img_btn_HourPlus = (ImageButton) v.findViewById(R.id.img_btn_HourPlus);

        // View Event
        btnSend.setOnClickListener(this);
        img_btn_HumMinus.setOnClickListener(this);
        img_btn_HumPlus.setOnClickListener(this);
        img_btn_TempMinus.setOnClickListener(this);
        img_btn_TempPlus.setOnClickListener(this);
        img_btn_DayMinus.setOnClickListener(this);
        img_btn_DayPlus.setOnClickListener(this);
        img_btn_HourMinus.setOnClickListener(this);
        img_btn_HourPlus.setOnClickListener(this);
    }

    // Button Event
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_btn_TempMinus: {
                do_TempMinus();
                break;
            }
            case R.id.img_btn_TempPlus: {
                do_TempPlus();
                break;
            }
            case R.id.img_btn_HumMinus: {
                do_HumMinus();
                break;
            }
            case R.id.img_btn_HumPlus: {
                do_HumPlus();
                break;
            }
            case R.id.img_btn_DayMinus: {
                do_DayMinus();
                break;
            }
            case R.id.img_btn_DayPlus: {
                do_DayPlus();
                break;
            }
            case R.id.img_btn_HourMinus: {
                do_HourMinus();
                break;
            }
            case R.id.img_btn_HourPlus: {
                do_HourPlus();
                break;
            }
            case R.id.btn_send: {
                String data = setMessage(Math.round(this.valueTemp * 100.0) / 100.0, this.valueHum, this.valueHour, this.valueDay);
                appSetData(data);
                progressDialog.setMessage(getString(R.string.text_sending_data));
                progressDialog.show();
                break;
            }
        }
    }


    protected String setMessage(double temp, int hum, int hour, int day) {
        double sum = 333 + temp + hum + hour + day + 0 + 444;
        return "333/" + temp + "/" + hum + "/" + hour + "/" + day + "/0/444/" + sum;
    }

    public void appSetData(String data) {
        mSocket.emit("SETAPP", "SETAPP", data);
        Log.i("SETAPP", data.toString());
    }

    private void do_HourPlus() {
        if (valueHour >= 8) {
            valueHour = 8;
            tvHourStat.setText(String.valueOf(valueHour));
        } else {
            tvHourStat.setText(String.valueOf(++valueHour));
        }
    }

    private void do_HourMinus() {
        if (valueHour <= 1) {
            valueHour = 1;
            tvHourStat.setText(String.valueOf(valueHour));
        } else {
            tvHourStat.setText(String.valueOf(--valueHour));
        }
    }

    private void do_DayPlus() {
        if (valueDay >= 22) {
            valueDay = 22;
            tvDayStat.setText(String.valueOf(valueDay));
        } else {
            tvDayStat.setText(String.valueOf(++valueDay));
        }
    }

    private void do_DayMinus() {
        if (valueDay <= 1) {
            valueDay = 1;
            tvDayStat.setText(String.valueOf(valueDay));
        } else {
            tvDayStat.setText(String.valueOf(--valueDay));
        }
    }

    private void do_HumPlus() {
        if (valueHum >= 65) {
            valueHum = 65;
            tvHumStat.setText(String.valueOf(valueHum));
        } else {
            tvHumStat.setText(String.valueOf(++valueHum));
        }
    }

    private void do_HumMinus() {
        if (valueHum <= 45) {
            valueHum = 45;
            tvHumStat.setText(String.valueOf(valueHum));
        } else {
            tvHumStat.setText(String.valueOf(--valueHum));
        }
    }

    private void do_TempMinus() {
        if (valueTemp <= 36.00) {
            valueTemp = 36.00;
            tvTempStat.setText(String.format("%.2f", valueTemp));
        } else {
            double tempValue = valueTemp;
            valueTemp = tempValue - 0.1;
            tvTempStat.setText(String.format("%.2f", valueTemp));
        }
    }

    private void do_TempPlus() {
        if (valueTemp >= 38.00) {
            valueTemp = 38.00;
            tvTempStat.setText(String.format("%.2f", valueTemp));
        } else {
            double tempValue = valueTemp;
            valueTemp = tempValue + 0.1;
            tvTempStat.setText(String.format("%.2f", valueTemp));
        }
    }

    private void setDataOnControl(String[] message) {
        valueTemp = Double.parseDouble(message[0]);
        valueHum = Integer.parseInt(message[1]);
        valueHour = Integer.parseInt(message[2]);
        valueDay = Integer.parseInt(message[3]);
        tvTempStat.setText(String.format("%.2f", valueTemp));
        tvHumStat.setText(String.valueOf(valueHum));
        tvHourStat.setText(String.valueOf(valueHour));
        tvDayStat.setText(String.valueOf(valueDay));
    }

    private Emitter.Listener getOnMessageSetDevice = new Emitter.Listener() {
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
                        message[4] = data.getString("reset");

                        setDataOnControl(message);
                    } catch (JSONException e) {
                        return;
                    }
                    //Log.i("msg", message[0].toString());
                    progressDialog.dismiss();
                }
            });
        }
    };

}

package com.example.kimhunz.controlapplication;

/**
 * Created by KiMHUNZ on 21/2/2560.
 */

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

public class ControlFragment extends Fragment implements View.OnClickListener {


    private Emitter.Listener onMessageSetDevice;


    public ControlFragment() {

    }

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

    private SeekBar seekHour;
    private TextView tvHourStat, tvHumStat, tvTempStat, tvDayStat;
    private Button btnSend;
    private ImageButton img_btn_HumMinus, img_btn_TempMinus, img_btn_DayMinus, img_btn_HumPlus, img_btn_TempPlus, img_btn_DayPlus;


    double valueTemp = 36.00;
    int valueHum = 45;
    int valueHour = 1;
    int valueDay = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSocket.connect();

        mSocket.emit("SETAPP", "SETAPP", "ON");
        Log.i("SETAPP", "ON");

        //mSocket.on("DeviceSet", onMessageSetDevice);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //mSocket.disconnect();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_control, container, false);

        layout_containt(v);

        seekHour.setOnSeekBarChangeListener(seekBarChangeListner);
        //seekHour.setProgress(valueHour-1);
        return v;
    }

    private void layout_containt(View v) {
        seekHour = (SeekBar) v.findViewById(R.id.seek_Hour);
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

        // View Event
        btnSend.setOnClickListener(this);
        img_btn_HumMinus.setOnClickListener(this);
        img_btn_HumPlus.setOnClickListener(this);
        img_btn_TempMinus.setOnClickListener(this);
        img_btn_TempPlus.setOnClickListener(this);
        img_btn_DayMinus.setOnClickListener(this);
        img_btn_DayPlus.setOnClickListener(this);
    }

    // Seek bar value edit
    private SeekBar.OnSeekBarChangeListener seekBarChangeListner = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            valueHour = progress + 1;
            tvHourStat.setText(String.valueOf(progress + 1));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };


    // Button Event
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_btn_TempMinus:
                if (valueTemp <= 36.00) {
                    valueTemp = 36.00;
                    tvTempStat.setText(String.format("%.2f", valueTemp));
                } else {
                    double tempValue = valueTemp;
                    valueTemp = tempValue - 0.1;
                    tvTempStat.setText(String.format("%.2f", valueTemp));
                }

                break;
            case R.id.img_btn_TempPlus:
                if (valueTemp >= 38.00) {
                    valueTemp = 38.00;
                    tvTempStat.setText(String.format("%.2f", valueTemp));
                } else {
                    double tempValue = valueTemp;
                    valueTemp = tempValue + 0.1;
                    tvTempStat.setText(String.format("%.2f", valueTemp));
                }
                break;

            case R.id.img_btn_HumMinus:
                if (valueHum <= 45) {
                    valueHum = 45;
                    tvHumStat.setText(String.valueOf(valueHum));
                } else {
                    tvHumStat.setText(String.valueOf(--valueHum));
                }
                break;

            case R.id.img_btn_HumPlus:
                if (valueHum >= 65) {
                    valueHum = 65;
                    tvHumStat.setText(String.valueOf(valueHum));
                } else {
                    tvHumStat.setText(String.valueOf(++valueHum));
                }
                break;
            case R.id.img_btn_DayMinus:
                if (valueDay <= 1) {
                    valueDay = 1;
                    tvDayStat.setText(String.valueOf(valueDay));
                } else {
                    tvDayStat.setText(String.valueOf(--valueDay));
                }
                break;
            case R.id.img_btn_DayPlus:
                if (valueDay >= 22) {
                    valueDay = 22;
                    tvDayStat.setText(String.valueOf(valueDay));
                } else {
                    tvDayStat.setText(String.valueOf(++valueDay));
                }
                break;
            case R.id.btn_send:
                String data = setMessage(Math.round(this.valueTemp * 100.0) / 100.0, this.valueHum, this.valueHour, this.valueDay);
                appSetData(data);
                String dataShow = R.string.text_temperature + " : " +
                        String.format("%.2f", this.valueTemp) +
                        R.string.text_humidity + " : " + valueHum +
                        R.string.text_hours + " : " + valueHour +
                        R.string.text_day + " : " + valueDay;
                Toast.makeText(this.getContext(), dataShow, Toast.LENGTH_SHORT).show();
                break;

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


}

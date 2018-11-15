package com.example.user.myapplication123;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity1 extends AppCompatActivity {


    protected EditText tall;
    protected EditText weight;
    protected RadioButton boy;
    protected RadioButton girl;
    protected RadioGroup radioGroup;
    protected Button button;
    protected TextView standardWeight;
    protected TextView bodyFat;

    int gender =1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main1);

        tall = (EditText)findViewById(R.id.editText);
        weight = (EditText)findViewById(R.id.editText2);
        boy = (RadioButton)findViewById(R.id.radioButton);
        girl = (RadioButton)findViewById(R.id.radioButton2);
        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        button = (Button)findViewById(R.id.button);
        standardWeight=(TextView)findViewById(R.id.textView6);
        bodyFat=(TextView)findViewById(R.id.textView8);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (radioGroup.getCheckedRadioButtonId()){
                    case R.id.radioButton:
                        gender = 1;
                        break;
                    case R.id.radioButton2:
                        gender = 2;
                        break;
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runAsyncTask();
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    private void runAsyncTask(){
        AsyncTask<Void, Integer, Boolean> execute = new AsyncTask<Void, Integer, Boolean>() {
            private ProgressDialog dialog = new ProgressDialog(MainActivity1.this);

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dialog.setMessage("calculating...");
                dialog.setCancelable(false);
                dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                dialog.show();
            }

            @Override
            protected Boolean doInBackground(Void... voids) {
                int progress = 0;
                while (progress <= 100) {
                    try {
                        Thread.sleep(50);
                        publishProgress(Integer.valueOf(progress));
                        progress++;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return true;
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                dialog.setProgress(values[0]);
            }

            @Override
            protected void onPostExecute(Boolean status) {
                dialog.dismiss();
                double cal_t = Double.parseDouble(tall.getText().toString());
                double cal_w = Double.parseDouble(weight.getText().toString());
                double cal_s = 0;
                double cal_b = 0;

                if (gender == 1) {
                    cal_s = 22 * cal_t / 100 * cal_t / 100;
                    cal_b = (cal_w - (0.88 * cal_s)) / cal_w * 100;
                }
                if (gender == 2) {
                    cal_s = 22 * cal_t / 100 * cal_t / 100;
                    cal_b = (cal_w - (0.82 * cal_s)) / cal_w * 100;
                }
                standardWeight.setText(String.format("%.2f", cal_s));
                bodyFat.setText(String.format("%.2f", cal_b));
            }
        }.execute();
    }
}

package com.example.user.myapplication;

import android.app.Notification;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    protected Button start;
    protected SeekBar rabbit_seekBar,tortoise_seekBar;
    int rabbit_count =0,tortoise_count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);

        start = (Button)findViewById(R.id.button);
        rabbit_seekBar = (SeekBar)findViewById(R.id.seekBar);
        tortoise_seekBar = (SeekBar)findViewById(R.id.seekBar2);


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"start",Toast.LENGTH_LONG).show();
                runThread();
                runAsyncTask();
            }
        });
    }

    private void runThread(){
        rabbit_count=0;
        new Thread(){
            public void run(){
                do{
                    try{
                        Thread.sleep(100);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    rabbit_count+=(int)(Math.random()*3);
                    Message msg = new Message();
                    msg.what =1;
                    mHandler.sendMessage(msg);
                }while(rabbit_count<=100);
            }
        }.start();
    }

    private Handler mHandler = new Handler(){

        @Override
        public void  handleMessage(Message msg){
            switch (msg.what){
                case 1:
                    rabbit_seekBar.setProgress(rabbit_count);
                    break;
            }

            if(rabbit_count>=100)
                if(tortoise_count<100)
                    Toast.makeText(MainActivity.this, "rabbit done", Toast.LENGTH_SHORT).show();
        }
    };

    private void runAsyncTask(){
        new AsyncTask<Void, Integer,Boolean>(){
            @Override
            protected void onPreExecute(){
                super.onPreExecute();
                tortoise_count=0;
            }
            @Override
            protected Boolean doInBackground(Void... voids){
                do{
                    try{
                        Thread.sleep(100);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    tortoise_count+=(int)(Math.random()*3);
                    publishProgress(tortoise_count);
                }while (tortoise_count<=100);
                return  true;
            }
            @Override
            protected void onProgressUpdate(Integer... values){
                super.onProgressUpdate(values);
                tortoise_seekBar.setProgress(values[0]);
            }
            @Override
            protected void onPostExecute(Boolean status){
                if(rabbit_count<100)
                    Toast.makeText(MainActivity.this,"turtle win",Toast.LENGTH_LONG).show();
            }
        }.execute();
    }
}

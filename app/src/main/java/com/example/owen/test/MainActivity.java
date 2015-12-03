package com.example.owen.test;

import android.content.Context;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.TextView;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    PowerManager pm ;
    PowerManager.WakeLock wl;
    TextView textView2;
    TextView textView3;
    boolean temp=true;
    int i=0;

    public class ThreadExample1 extends Thread {
        public void run() { // override Thread's run()
            //System.out.println("Here is the starting point of Thread.");
            for (;;) { // infinite loop to print message
                i++;
                try {
                    Thread.sleep(3000);
                }catch (Exception e){
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        final TextView textView1 = (TextView) findViewById(R.id.textview1);
        textView1.setText("Press button!!!");
        textView2 = (TextView) findViewById(R.id.textview2);
        textView2.setText("number"+i);
        textView3 = (TextView) findViewById(R.id.textview3);
        textView2.setText("number"+i);
         Thread threadExample1 = new ThreadExample1();
        threadExample1.start();

        Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //會正常關掉螢幕
                wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "PARTIAL_WAKE_LOCK Tag");
                wl.acquire();//使用wakelock
                boolean a;
                a = wl.isHeld();//是否有被acquire
                Log.i("PowerManager", "status:" + a);
                textView1.setText("PARTIAL_WAKE_LOCK");
                wl.setReferenceCounted(true);//有幾次acquire就要幾次release
                wl.acquire();
                wl.acquire();
                wl.acquire();
                Log.i("PowerManager", "number" + i);
            }
        });
        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //屏幕显示至灰階
                wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "SCREEN_DIM_WAKE_LOCK Tag");
                wl.acquire();
                textView1.setText("SCREEN_DIM_WAKE_LOCK");
                boolean a;
                a=wl.isHeld();//是否有被acquire
                Log.i("PowerManager", "status:" + a);
                wl.setReferenceCounted(false);//不管有幾次acquire就能一次release
                wl.acquire();
                wl.acquire();
                wl.acquire();
            }
        });
        Button button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //螢幕亮 鍵盤允許關閉
                wl = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "SCREEN_BRIGHT_WAKE_LOCK Tag");
                wl.acquire();
                textView1.setText("SCREEN_BRIGHT_WAKE_LOCK");
                boolean a;
                a=wl.isHeld();
                Log.i("PowerManager", "status:" + a);
            }
        });
        Button button4 = (Button) findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //螢幕亮 鍵盤亮
                wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "FULL_WAKE_LOCK Tag");
                wl.acquire();
                textView1.setText("FULL_WAKE_LOCK");
                boolean a;
                a=wl.isHeld();
                Log.i("PowerManager","status:"+a);
            }
        });
        Button button5 = (Button) findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                boolean a;
                a=wl.isHeld();

                if(wl.isHeld()) {
                    wl.release();
                    if(wl.isHeld())
                      textView1.setText("Press button");
                }
                Log.i("PowerManager", "status:" + a);
            }
        });
        Button button6 = (Button) findViewById(R.id.button6);
        button6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(temp){
                    textView2.setText("number"+i);
                    temp=!temp;
                }
                else{
                    textView3.setText("number"+i);
                    temp=!temp;
                }
            }
        });
    }

}

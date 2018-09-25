package com.example.administrator.light;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Set;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ProgressDialog progressDialog = null;                           // 搜索进度

    WifiManager wifiManager;
    public static MySocketServer mySocketServer;
    private SeekBar seekBar;
    public static String massage = "";
    public static boolean send_massage = false;
    Button LED2_Mul;
    Button LED2_hu;

    @SuppressLint("WifiManagerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        Button LED1_blue = (Button) findViewById(R.id.LED1_blue);
        LED1_blue.setOnClickListener(this);
        Button LED1_green = (Button) findViewById(R.id.LED1_green);
        LED1_green.setOnClickListener(this);
        Button LED1_red = (Button) findViewById(R.id.LED1_red);
        LED1_red.setOnClickListener(this);
        Button LED1_yellow = (Button) findViewById(R.id.LED1_yellow);
        LED1_yellow.setOnClickListener(this);
        Button LED1_fuchsin = (Button) findViewById(R.id.LED1_fuchsin);
        LED1_fuchsin.setOnClickListener(this);
        Button LED1_cyan = (Button) findViewById(R.id.LED1_cyan);
        LED1_cyan.setOnClickListener(this);
        Button LED1_white = (Button) findViewById(R.id.LED1_white);
        LED1_white.setOnClickListener(this);
        Button LED1_flash = (Button) findViewById(R.id.LED1_flash);
        LED1_flash.setOnClickListener(this);
        Button LED1_close = (Button) findViewById(R.id.LED1_close);
        LED1_close.setOnClickListener(this);

        Button LED2_Color = (Button) findViewById(R.id.LED2_Color);
        LED2_Color.setOnClickListener(this);
        Button LED2_Mode = (Button) findViewById(R.id.LED2_Mode);
        LED2_Mode.setOnClickListener(this);
        Button LED2_Speed = (Button) findViewById(R.id.LED2_Speed);
        LED2_Speed.setOnClickListener(this);
        Button LED2_close = (Button) findViewById(R.id.LED2_close);
        LED2_close.setOnClickListener(this);
        Button LED2_up = (Button) findViewById(R.id.LED2_up);
        LED2_up.setOnClickListener(this);
        Button LED2_down = (Button) findViewById(R.id.LED2_down);
        LED2_down.setOnClickListener(this);
        Button LED2_fup = (Button) findViewById(R.id.LED2_fup);
        LED2_fup.setOnClickListener(this);
        Button LED2_fdown = (Button) findViewById(R.id.LED2_fdown);
        LED2_fdown.setOnClickListener(this);
        LED2_hu = (Button) findViewById(R.id.LED2_hu);
        LED2_hu.setOnClickListener(this);
        LED2_Mul = (Button) findViewById(R.id.LED2_Mul);
        LED2_Mul.setOnClickListener(this);

        ImageView co_r = (ImageView) findViewById(R.id.clo_r);
        co_r.setOnTouchListener(mImageViewTouchHandler);
        ImageView co_a = (ImageView) findViewById(R.id.clo_a);
        co_a.setOnTouchListener(mImageViewTouchHandler);
        ImageView co_b = (ImageView) findViewById(R.id.clo_b);
        co_b.setOnTouchListener(mImageViewTouchHandler);
        ImageView co_c = (ImageView) findViewById(R.id.clo_c);
        co_c.setOnTouchListener(mImageViewTouchHandler);

        ImageView co_g = (ImageView) findViewById(R.id.clo_g);
        co_g.setOnTouchListener(mImageViewTouchHandler);
        ImageView co_y = (ImageView) findViewById(R.id.clo_y);
        co_y.setOnTouchListener(mImageViewTouchHandler);
        ImageView co_o = (ImageView) findViewById(R.id.clo_o);
        co_o.setOnTouchListener(mImageViewTouchHandler);
        ImageView co_w = (ImageView) findViewById(R.id.clo_w);
        co_w.setOnTouchListener(mImageViewTouchHandler);

        wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WebConfig webConfig = new WebConfig();
        webConfig.setPort(1811);
        webConfig.setMaxParallels(10);
        mySocketServer = new MySocketServer(webConfig);
        mySocketServer.startServerAsync();

        setWifiApEnabled(true);
        MainThread.start();
    }

    public ImageView.OnTouchListener mImageViewTouchHandler = new ImageView.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            final int id = v.getId();
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                switch (id) {
                    case R.id.clo_r:
                        mySocketServer.send_b(new byte[]{(byte) 0xab, (byte) 0x01, (byte) 0x02, (byte) 0x55});
                        break;
                    case R.id.clo_a:
                        mySocketServer.send_b(new byte[]{(byte) 0xab, (byte) 0x01, (byte) 0x0d, (byte) 0x55});
                        break;
                    case R.id.clo_b:
                        mySocketServer.send_b(new byte[]{(byte) 0xab, (byte) 0x01, (byte) 0x03, (byte) 0x55});
                        break;
                    case R.id.clo_c:
                        mySocketServer.send_b(new byte[]{(byte) 0xab, (byte) 0x01, (byte) 0x07, (byte) 0x55});
                        break;

                    case R.id.clo_g:
                        mySocketServer.send_b(new byte[]{(byte) 0xab, (byte) 0x01, (byte) 0x01, (byte) 0x55});
                        break;
                    case R.id.clo_y:
                        mySocketServer.send_b(new byte[]{(byte) 0xab, (byte) 0x01, (byte) 0x10, (byte) 0x55});
                        break;
                    case R.id.clo_o:
                        mySocketServer.send_b(new byte[]{(byte) 0xab, (byte) 0x01, (byte) 0x0f, (byte) 0x55});
                        break;
                    case R.id.clo_w:
                        mySocketServer.send_b(new byte[]{(byte) 0xab, (byte) 0x01, (byte) 0x12, (byte) 0x55});
                        break;
                }

            }
            return true;
        }
        };

    private void showPopupMenu1(View view) {
        // View当前PopupMenu显示的相对View的位置
        PopupMenu popupMenu = new PopupMenu(this, view);
        // menu布局
        popupMenu.getMenuInflater().inflate(R.menu.context_menu, popupMenu.getMenu());
        // menu的item点击事件
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
//                Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();

                switch (item.getItemId()) {
                    case R.id.context_item1:
                        mySocketServer.send_b(new byte[]{(byte) 0xab, (byte) 0x01, (byte) 0x21, (byte) 0x55});
                        break;
                    case R.id.context_item2:
                        mySocketServer.send_b(new byte[]{(byte) 0xab, (byte) 0x01, (byte) 0x22, (byte) 0x55});
                        break;
                    case R.id.context_item3:
                        mySocketServer.send_b(new byte[]{(byte) 0xab, (byte) 0x01, (byte) 0x23, (byte) 0x55});
                        break;
                    case R.id.context_item4:
                        mySocketServer.send_b(new byte[]{(byte) 0xab, (byte) 0x01, (byte) 0x24, (byte) 0x55});
                        break;
                    case R.id.context_item5:
                        mySocketServer.send_b(new byte[]{(byte) 0xab, (byte) 0x01, (byte) 0x25, (byte) 0x55});
                        break;
                    case R.id.context_item6:
                        mySocketServer.send_b(new byte[]{(byte) 0xab, (byte) 0x01, (byte) 0x26, (byte) 0x55});
                        break;
                    case R.id.context_item7:
                        mySocketServer.send_b(new byte[]{(byte) 0xab, (byte) 0x01, (byte) 0x27, (byte) 0x55});
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        // PopupMenu关闭事件
//        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
//            @Override
//            public void onDismiss(PopupMenu menu) {
//                Toast.makeText(getApplicationContext(), "关闭PopupMenu", Toast.LENGTH_SHORT).show();
//            }
//        });
        popupMenu.show();
    }

    private void showPopupMenu2(View view) {
        // View当前PopupMenu显示的相对View的位置
        PopupMenu popupMenu = new PopupMenu(this, view);
        // menu布局
        popupMenu.getMenuInflater().inflate(R.menu.context_menu1, popupMenu.getMenu());
        // menu的item点击事件
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.context_item11:
                        mySocketServer.send_b(new byte[]{(byte) 0xab, (byte) 0x01, (byte) 0x28, (byte) 0x55});
                        break;
                    case R.id.context_item12:
                        mySocketServer.send_b(new byte[]{(byte) 0xab, (byte) 0x01, (byte) 0x29, (byte) 0x55});
                        break;
                    case R.id.context_item13:
                        mySocketServer.send_b(new byte[]{(byte) 0xab, (byte) 0x01, (byte) 0x2a, (byte) 0x55});
                        break;
                    case R.id.context_item14:
                        mySocketServer.send_b(new byte[]{(byte) 0xab, (byte) 0x01, (byte) 0x2b, (byte) 0x55});
                        break;
                    case R.id.context_item15:
                        mySocketServer.send_b(new byte[]{(byte) 0xab, (byte) 0x01, (byte) 0x2c, (byte) 0x55});
                        break;
                    case R.id.context_item16:
                        mySocketServer.send_b(new byte[]{(byte) 0xab, (byte) 0x01, (byte) 0x2d, (byte) 0x55});
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        // PopupMenu关闭事件
//        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
//            @Override
//            public void onDismiss(PopupMenu menu) {
//                Toast.makeText(getApplicationContext(), "关闭PopupMenu", Toast.LENGTH_SHORT).show();
//            }
//        });
        popupMenu.show();
    }

//        seekBar = (SeekBar) findViewById(R.id.progress);
//        seekBar.setOnSeekBarChangeListener(sbcl);

//    // 设置拖动条改变监听器
//    SeekBar.OnSeekBarChangeListener sbcl = new SeekBar.OnSeekBarChangeListener() {
//        @Override
//        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//            // 当拖动条的滑块位置发生改变时触发该方法,在这里直接使用参数progress，即当前滑块代表的进度值
//            if(progress/10==0||progress/10==4||progress/10==5||progress/10==9) {
//                if (progress >= 0 && progress < 50)
//                    if (progress == 0 || progress == 4)
//                        mySocketServer.send_b(new byte[]{(byte) 0xab, (byte) 0x01, (byte) 0x02, (byte) 0x55});
//                    else if (progress == 5 || progress == 9)
//                        mySocketServer.send_b(new byte[]{(byte) 0xab, (byte) 0x01, (byte) 0x0c, (byte) 0x55});
//                    else if (progress == 10 || progress == 14)
//                        mySocketServer.send_b(new byte[]{(byte) 0xab, (byte) 0x01, (byte) 0x0d, (byte) 0x55});
//                    else if (progress == 15 || progress == 19)
//                        mySocketServer.send_b(new byte[]{(byte) 0xab, (byte) 0x01, (byte) 0x0e, (byte) 0x55});
//                    else if (progress == 20 || progress == 24)
//                        mySocketServer.send_b(new byte[]{(byte) 0xab, (byte) 0x01, (byte) 0x03, (byte) 0x55});
//                    else if (progress == 25 || progress == 39)
//                        mySocketServer.send_b(new byte[]{(byte) 0xab, (byte) 0x01, (byte) 0x0b, (byte) 0x55});
//                    else if (progress == 30 || progress == 34)
//                        mySocketServer.send_b(new byte[]{(byte) 0xab, (byte) 0x01, (byte) 0x07, (byte) 0x55});
//                    else if (progress == 35 || progress == 39)
//                        mySocketServer.send_b(new byte[]{(byte) 0xab, (byte) 0x01, (byte) 0x08, (byte) 0x55});
//                    else if (progress == 40 || progress == 44)
//                        mySocketServer.send_b(new byte[]{(byte) 0xab, (byte) 0x01, (byte) 0x09, (byte) 0x55});
//                    else if (progress == 45 || progress == 49)
//                        mySocketServer.send_b(new byte[]{(byte) 0xab, (byte) 0x01, (byte) 0x0a, (byte) 0x55});
//
//
//                if (progress >= 50 && progress <= 100)
//                    if (progress == 50 || progress == 54)
//                        mySocketServer.send_b(new byte[]{(byte) 0xab, (byte) 0x01, (byte) 0x01, (byte) 0x55});
//                    else if (progress == 55 || progress == 59)
//                        mySocketServer.send_b(new byte[]{(byte) 0xab, (byte) 0x01, (byte) 0x04, (byte) 0x55});
//                    else if (progress == 60 || progress == 64)
//                        mySocketServer.send_b(new byte[]{(byte) 0xab, (byte) 0x01, (byte) 0x05, (byte) 0x55});
//                    else if (progress == 65 || progress == 69)
//                        mySocketServer.send_b(new byte[]{(byte) 0xab, (byte) 0x01, (byte) 0x06, (byte) 0x55});
//                    else if (progress == 70 || progress == 74)
//                        mySocketServer.send_b(new byte[]{(byte) 0xab, (byte) 0x01, (byte) 0x0f, (byte) 0x55});
//                    else if (progress == 75 || progress == 79)
//                        mySocketServer.send_b(new byte[]{(byte) 0xab, (byte) 0x01, (byte) 0x10, (byte) 0x55});
//                    else if (progress == 80 || progress == 84)
//                        mySocketServer.send_b(new byte[]{(byte) 0xab, (byte) 0x01, (byte) 0x11, (byte) 0x55});
//                    else if (progress == 85 || progress == 89)
//                        mySocketServer.send_b(new byte[]{(byte) 0xab, (byte) 0x01, (byte) 0x12, (byte) 0x55});
//                    else if (progress == 90 || progress == 94)
//                        mySocketServer.send_b(new byte[]{(byte) 0xab, (byte) 0x01, (byte) 0x13, (byte) 0x55});
//                    else if (progress == 95 || progress == 100)
//                        mySocketServer.send_b(new byte[]{(byte) 0xab, (byte) 0x01, (byte) 0x14, (byte) 0x55});
//            }
//        }
//
//        @Override
//        public void onStartTrackingTouch(SeekBar seekBar) {
//            Log.e("------------", "开始滑动！");
//        }
//
//        @Override
//        public void onStopTrackingTouch(SeekBar seekBar) {
//            Log.e("------------", "停止滑动！");
//        }
//    };

    Thread MainThread = new Thread(new Runnable() {
        @Override
        public void run() {
            //直接创建，不需要设置setDataSource
            MediaPlayer mMediaPlayer=MediaPlayer.create(MainActivity.this, R.raw.music);
            mMediaPlayer.start();
            while (true) {
                if( send_massage == true) {
                    send_massage = false;
                    MainHandler.sendEmptyMessage(3);
                }
            }
        }
    });

    // wifi热点开关
    public boolean setWifiApEnabled(boolean enabled) {

        if (enabled) { // disable WiFi in any case
            //wifi和热点不能同时打开，所以打开热点的时候需要关闭wifi
            wifiManager.setWifiEnabled(false);
        }
        try {
            //热点的配置类
            WifiConfiguration apConfig = new WifiConfiguration();
            //配置热点的名称(可以在名字后面加点随机数什么的)
            apConfig.SSID = "init";
            //配置热点的密码
            apConfig.preSharedKey="12345678";
            apConfig.allowedKeyManagement.set(4);
            //通过反射调用设置热点
            Method method = wifiManager.getClass().getMethod(
                    "setWifiApEnabled", WifiConfiguration.class, Boolean.TYPE);
            return (Boolean) method.invoke(wifiManager, apConfig, enabled);
        } catch (Exception e) {
            return false;
        }
    }



    protected void onDestroy() {
        super.onDestroy();
    }

    /****************************************/
    /*
    Button控制
     */
    /****************************************/

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.LED1_red:
                mySocketServer.send_b(new byte[]{(byte) 0xab, (byte) 0x00, (byte) 0x01, (byte) 0x55});
                break;
            case R.id.LED1_green:
                mySocketServer.send_b(new byte[]{(byte) 0xab, (byte) 0x00, (byte) 0x02, (byte) 0x55});
                break;
            case R.id.LED1_blue:
                mySocketServer.send_b(new byte[]{(byte) 0xab, (byte) 0x00, (byte) 0x03, (byte) 0x55});
                break;
            case R.id.LED1_yellow:
                mySocketServer.send_b(new byte[]{(byte) 0xab, (byte) 0x00, (byte) 0x04, (byte) 0x55});
                break;
            case R.id.LED1_fuchsin:
                mySocketServer.send_b(new byte[]{(byte) 0xab, (byte) 0x00, (byte) 0x05, (byte) 0x55});
                break;
            case R.id.LED1_cyan:
                mySocketServer.send_b(new byte[]{(byte) 0xab, (byte) 0x00, (byte) 0x06, (byte) 0x55});
                break;
            case R.id.LED1_white:
                mySocketServer.send_b(new byte[]{(byte) 0xab, (byte) 0x00, (byte) 0x07, (byte) 0x55});
                break;
            case R.id.LED1_flash:
                mySocketServer.send_b(new byte[]{(byte) 0xab, (byte) 0x00, (byte) 0x08, (byte) 0x55});
                break;
            case R.id.LED1_close:
                mySocketServer.send_b(new byte[]{(byte) 0xab, (byte) 0x00, (byte) 0x09, (byte) 0x55});
                break;

            case R.id.LED2_Mode:
                mySocketServer.send_b(new byte[]{(byte) 0xab, (byte) 0x01, (byte) 0x15, (byte) 0x55});
                break;
            case R.id.LED2_Speed:
                mySocketServer.send_b(new byte[]{(byte) 0xab, (byte) 0x01, (byte) 0x16, (byte) 0x55});
                break;
            case R.id.LED2_Color:
                mySocketServer.send_b(new byte[]{(byte) 0xab, (byte) 0x01, (byte) 0x17, (byte) 0x55});
                break;
            case R.id.LED2_close:
                mySocketServer.send_b(new byte[]{(byte) 0xab, (byte) 0x01, (byte) 0x18, (byte) 0x55});
                break;

            case R.id.LED2_hu:
                showPopupMenu1(LED2_hu);
                break;
            case R.id.LED2_Mul:
                showPopupMenu2(LED2_Mul);
                break;


            case R.id.LED2_up:
                mySocketServer.send_b(new byte[]{(byte) 0xab, (byte) 0x01, (byte) 0x41, (byte) 0x55});
                break;
            case R.id.LED2_down:
                mySocketServer.send_b(new byte[]{(byte) 0xab, (byte) 0x01, (byte) 0x42, (byte) 0x55});
                break;
            case R.id.LED2_fup:
                mySocketServer.send_b(new byte[]{(byte) 0xab, (byte) 0x01, (byte) 0x43, (byte) 0x55});
                break;
            case R.id.LED2_fdown:
                mySocketServer.send_b(new byte[]{(byte) 0xab, (byte) 0x01, (byte) 0x44, (byte) 0x55});
                break;
        }
    }

    public static void delay(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @SuppressLint("HandlerLeak")
    public Handler MainHandler = new Handler() {
        @SuppressLint("WrongViewCast")
        public void handleMessage(Message msg) {
            if (msg.what == 3) {
                弹幕(massage);
            }
        }
    };

    @SuppressLint("WrongConstant")
    public void 弹幕(String x) {
        Toast.makeText(MainActivity.this, x, 500).show();
    }


}

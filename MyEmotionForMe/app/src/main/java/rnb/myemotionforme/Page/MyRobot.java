package rnb.myemotionforme.Page;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.PrintWriter;

import rnb.myemotionforme.R;
import rnb.myemotionforme.SocketUtil;

/**
 * Created by yj on 16. 5. 24..
 */
public class MyRobot extends ActionBarActivity {

    PrintWriter out;
    EditText et_ip;

    private VideoView videoView;
    private int position = 0;
    private ProgressDialog progressDialog;
    private android.widget.MediaController mediaControls;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("MyRobot");
        if(Build.VERSION.SDK_INT>9){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        setContentView(R.layout.activity_myrobot);

        et_ip = (EditText) findViewById(R.id.et_ip_myrobot);
        et_ip.setText("192.168.0.97");
        videoPlayer();

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(MyRobot.this, Menu.class);
        startActivity(i);
        finish();
        super.onBackPressed();
    }

    void videoPlayer(){
        videoView = (VideoView)findViewById(R.id.videoView_myrobot);


        if (mediaControls == null) {
            mediaControls = new android.widget.MediaController(MyRobot.this);
        }

        // progressDialog = new ProgressDialog(myrobot.this);
        // progressDialog.setTitle("Android Video");
        // progressDialog.setMessage("Loading...");
        // progressDialog.setCancelable(false);
        // progressDialog.show();

        String url = et_ip.getText().toString();
        try {
            Uri video = Uri.parse("rtsp://"+url+":8554/test");
            videoView.setVideoURI(video);
            videoView.setMediaController(mediaControls);
        }catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        videoView.requestFocus();


        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer){
                //  progressDialog.dismiss();
                videoView.seekTo(position);
                if (position == 0) {
                    videoView.start();
                }else{
                    videoView.pause();
                }
            }
        });
    }

    void openSocket(String msg){
        SocketUtil mysocket = new SocketUtil(et_ip.getText().toString(), 5100);
        mysocket.setmessage(msg);
        mysocket.run();
    }
    public void MyRobot_LedbuttonClick(View v) {
        Button bt_led = (Button) findViewById(R.id.bt_led_myrobot);
        String led_state = bt_led.getText().toString();

        if(led_state=="LedOn"){
            Toast.makeText(getApplicationContext(), "LedOn", Toast.LENGTH_SHORT).show();
            openSocket("led=On");
            bt_led.setText("LedOff");
        }else{
            Toast.makeText(getApplicationContext(), "LedOff", Toast.LENGTH_SHORT).show();
            openSocket("led=Off");
            bt_led.setText("LedOn");
        }
    }

    public void MyRobot_Upbutton(View v) {
        Toast.makeText(getApplicationContext(), "위", Toast.LENGTH_SHORT).show();
        openSocket("motor=1");
    }
    public void MyRobot_Downbutton(View v) {
        Toast.makeText(getApplicationContext(), "아래", Toast.LENGTH_SHORT).show();
        openSocket("motor=2");
    }
    public void MyRobot_Leftbutton(View v) {
        Toast.makeText(getApplicationContext(), "왼쪽", Toast.LENGTH_SHORT).show();
        openSocket("motor=3");
    }
    public void MyRobot_Rightbutton(View v) {
        Toast.makeText(getApplicationContext(), "오른쪽", Toast.LENGTH_SHORT).show();
        openSocket("motor=4");
    }
    public void MyRobot_Stopbutton(View v) {
        Toast.makeText(getApplicationContext(), "오른쪽", Toast.LENGTH_SHORT).show();
        openSocket("motor=5");
    }


}
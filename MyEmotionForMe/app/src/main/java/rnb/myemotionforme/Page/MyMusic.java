package rnb.myemotionforme.Page;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import rnb.myemotionforme.R;
import rnb.myemotionforme.SocketUtil;

/**
 * Created by yj on 16. 5. 24..
 */
public class MyMusic extends ActionBarActivity {

    TextView tv_musicName;
    String[] musicList={"Summer_Nights","phone","Carefree"," Get_Outside_Jason_Farnham_pop"," Vacation_Uke_ALBLS_pop"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mymusic);
        getSupportActionBar().setTitle("MyMusic");
        tv_musicName = (TextView) findViewById(R.id.tv_musicName);
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(MyMusic.this, Menu.class);
        startActivity(i);
        finish();
        super.onBackPressed();
    }

    void openSocket(String msg){
        SocketUtil mysocket = new SocketUtil("192.168.0.97", 5100);
        mysocket.setmessage(msg);
        mysocket.run();
    }
    public void musicPlayClick(View v){
        Toast.makeText(getApplicationContext(), "play", Toast.LENGTH_SHORT).show();
        openSocket("music=play");
    }
    public void musicStopClick(View v) {
        Toast.makeText(getApplicationContext(), "stop", Toast.LENGTH_SHORT).show();
        openSocket("music=stop");
    }
    public void test1Music(View v){
        Toast.makeText(getApplicationContext(), "test1", Toast.LENGTH_SHORT).show();
        tv_musicName.setText(musicList[0]);
        openSocket("music="+musicList[0]);
    }
    public void test2Music(View v){
        Toast.makeText(getApplicationContext(), "test2", Toast.LENGTH_SHORT).show();
        tv_musicName.setText(musicList[1]);
        openSocket("music="+musicList[1]);
    }
    public void test3Music(View v){
        Toast.makeText(getApplicationContext(), "test3", Toast.LENGTH_SHORT).show();
        tv_musicName.setText(musicList[2]);
        openSocket("music="+musicList[2]);
    }
}
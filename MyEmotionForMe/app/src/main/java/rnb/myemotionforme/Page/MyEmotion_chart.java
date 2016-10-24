package rnb.myemotionforme.Page;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import rnb.myemotionforme.R;
import rnb.myemotionforme.hzgrapher.CircleGraphActivity;

public class MyEmotion_chart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_emotion_chart);
        Intent i = new Intent(this, CircleGraphActivity.class);
        startActivity(i);
    }

}

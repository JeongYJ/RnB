package rnb.myemotionforme.Page;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import rnb.myemotionforme.R;
import rnb.myemotionforme.key.Key;

/**
 * Created by yj on 16. 5. 24..
 */
public class MyStory_MyStoryListShow extends ActionBarActivity {

    TextView tv_date_listshow;
    TextView tv_emotion_listshow;
    TextView tv_title_listshow;
    TextView tv_text_listshow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("MyStory");
        setContentView(R.layout.activity_mystory_listshow);

        tv_date_listshow = (TextView)findViewById(R.id.tv_date_listshow);
        tv_emotion_listshow = (TextView)findViewById(R.id.tv_emotion_listshow);
        tv_title_listshow = (TextView) findViewById(R.id.tv_title_listshow);
        tv_text_listshow = (TextView)findViewById(R.id.tv_text_listshow);

        tv_title_listshow.setText(Key.titleData);
        tv_date_listshow.setText(Key.dateData);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(MyStory_MyStoryListShow.this, MyStory.class);
        startActivity(i);
        finish();
        super.onBackPressed();
    }
}

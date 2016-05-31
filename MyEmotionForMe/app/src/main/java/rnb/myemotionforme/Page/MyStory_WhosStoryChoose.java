package rnb.myemotionforme.Page;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Toast;

import rnb.myemotionforme.R;

/**
 * Created by yj on 16. 5. 24..
 */
public class MyStory_WhosStoryChoose extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mystory_whosstory);
        getSupportActionBar().setTitle("Who's Story?");
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(MyStory_WhosStoryChoose.this, MyStory .class);
        startActivity(i);
        finish();
        super.onBackPressed();
    }

    public void WhosStory_GoLikesMeButtonClicked(View v) throws Exception {
        Toast.makeText(getApplicationContext(), "GO", Toast.LENGTH_LONG).show();
       // Intent i = new Intent(MyStory_WhosStoryChoose.this, MyPage_ChangePW.class);
       // startActivity(i);
       // finish();
    }

    public void WhosStory_GoTodayButtonClicked(View v) throws Exception {

        Toast.makeText(getApplicationContext(), "GO", Toast.LENGTH_LONG).show();
       // Intent i = new Intent(MyStory_WhosStoryChoose.this, MyStory.class);
       // startActivity(i);
       // finish();
    }

}

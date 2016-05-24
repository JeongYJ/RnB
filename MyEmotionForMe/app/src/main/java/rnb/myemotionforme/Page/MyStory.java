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
public class MyStory extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mystory);
        getSupportActionBar().setTitle("MyStory");
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(MyStory.this, Menu.class);
        startActivity(i);
        finish();
        super.onBackPressed();
    }

    public void MyStory_WriteStoryButtonClicked(View v) throws Exception {
        Toast.makeText(getApplicationContext(), "나의 이야기를 작성합니다.", Toast.LENGTH_LONG).show();
       // Intent i = new Intent(MyStory.this, MyPage_ChangePW.class);
      //  startActivity(i);
       // finish();
    }

    public void MyStory_AnotherStoryButtonClicked(View v) throws Exception {
        Toast.makeText(getApplicationContext(), "누군가의 이야기를 엿보러 갑니다.", Toast.LENGTH_LONG).show();
       // Intent i = new Intent(MyStory.this, MyPage_ChangePW.class);
       // startActivity(i);
       // finish();
    }

}
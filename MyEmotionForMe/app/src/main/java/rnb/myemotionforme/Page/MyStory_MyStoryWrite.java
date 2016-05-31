package rnb.myemotionforme.Page;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import rnb.myemotionforme.Events.BackPressButtonActivity;
import rnb.myemotionforme.R;
import rnb.myemotionforme.key.Key;

/**
 * Created by yj on 16. 5. 24..
 */
public class MyStory_MyStoryWrite extends ActionBarActivity {
    private BackPressButtonActivity bp;
    TextView tv_date_write;
    TextView tv_emotion_write;
    EditText et_title_write;
    EditText et_text_write;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("오늘의 이야기 작성");
        setContentView(R.layout.activity_mystory_write);
        bp = new BackPressButtonActivity(this);
        tv_date_write = (TextView)findViewById(R.id.tv_date_write);
        tv_emotion_write = (TextView)findViewById(R.id.tv_emotion_write);
        et_title_write = (EditText) findViewById(R.id.et_title_write);
        et_text_write = (EditText)findViewById(R.id.et_text_write);

    }


    @Override
    public void onBackPressed()
    {
        bp.onBackPressed();
    }



    public void Write_StoreButtonClicked(View v) throws Exception {

            String date = tv_date_write.getText().toString();
            String title = et_title_write.getText().toString();
            String text = et_text_write.getText().toString();

                Log.d("TAG_DATE", date);
                Log.d("TAG_title", title);
                Log.d("TAG_text", text);
                Key.myStory_date = date;
                Key.myStory_title = title;
                Key.myStory_text = text;

                title = title.trim();
                text = text.trim();

                Toast.makeText(getApplicationContext(), "작성된 이야기를 저장합니다.", Toast.LENGTH_LONG).show();

                //   MyStory.mAdapter.addItem(getResources().getDrawable(R.drawable.myme_icon),
                //              Key.myStory_title,
                //              Key.myStory_date);

                //        MyStory.mAdapter.notifyDataSetChanged();
                Intent i = new Intent(MyStory_MyStoryWrite.this, MyStory.class);
                startActivity(i);
                finish();

    }

    public void Write_CancelButtonClicked(View v) throws Exception {

        Toast.makeText(getApplicationContext(), "취소", Toast.LENGTH_LONG).show();
        Intent i = new Intent(MyStory_MyStoryWrite.this, MyStory.class);
        startActivity(i);
        finish();
    }

}

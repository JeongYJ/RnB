package rnb.myemotionforme.Page;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import rnb.myemotionforme.Events.BackPressButtonActivity;
import rnb.myemotionforme.HttpTask;
import rnb.myemotionforme.JsonParse;
import rnb.myemotionforme.R;
import rnb.myemotionforme.key.JsonKey_Details;
import rnb.myemotionforme.key.JsonKey_User;

/**
 * Created by yj on 16. 5. 24..
 */
public class MyStory_MyStoryWrite extends ActionBarActivity {
    private BackPressButtonActivity bp;
    TextView tv_date_write;
    TextView tv_emotion_write;
    EditText et_title_write;
    EditText et_text_write;
    CheckBox cb_show_write;
    String date = "";
    String res;

    private static final String TAG = "MyStoryWrite";
    // HTTPUtil httpUtil = new HTTPUtil();
    JsonParse json= new JsonParse();
    JSONObject obj;

    int check_show = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("오늘의 이야기 작성");
        setContentView(R.layout.activity_mystory_write);
        bp = new BackPressButtonActivity(this);

        long now = System.currentTimeMillis();

        Date todate = new Date(now);
        SimpleDateFormat CurDateFormat = new SimpleDateFormat("yyyy.MM.dd");
        date = CurDateFormat.format(todate);

        tv_date_write = (TextView) findViewById(R.id.tv_date_write);
        tv_emotion_write = (TextView) findViewById(R.id.tv_emotion_write);
        et_title_write = (EditText) findViewById(R.id.et_title_write);
        et_text_write = (EditText) findViewById(R.id.et_text_write);
        cb_show_write = (CheckBox) findViewById(R.id.cb_show_write);

        tv_date_write.setText(date.toString());
        tv_emotion_write.setText(MyEmotion.emotion[JsonKey_Details.dno]);


        cb_show_write.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (buttonView.getId() == R.id.cb_show_write) {
                    if (isChecked) {
                        check_show = 1;
                    } else {
                        check_show = 0;
                    }
                }
            }
        });

    }


    @Override
    public void onBackPressed() {
        bp.onBackPressed();
    }


    public void Write_StoreButtonClicked(View v) throws Exception {

        //tv_date_write.getText().toString();
        String title = et_title_write.getText().toString();
        String text = et_text_write.getText().toString();
        int show = check_show;

        if (title.getBytes().length <= 0) {//빈값이 넘어올때의 처리
            Toast.makeText(MyStory_MyStoryWrite.this, "제목을 입력하세요.", Toast.LENGTH_SHORT).show();
        } else if (text.getBytes().length <= 0) {
            Toast.makeText(MyStory_MyStoryWrite.this, "내용을 입력하세요.", Toast.LENGTH_SHORT).show();
        } else {

            obj = new JSONObject();
            obj.put("suno", JsonKey_User.uno);
            obj.put("srno", JsonKey_User.rno);
            obj.put("stitle", title);
            obj.put("stext", text);
            obj.put("sshow", show);
            Log.e(TAG, "json : " + obj.toString());//json 객체 확인

            //서버로 보냄 파라미터 : "url"동적으로 변화되는 경로, "jsonObject"서버로 보내질 객체
            HttpTask task = new HttpTask("/RnB/myStory_insert.php", obj.toString());
            res = task.execute().get(); //결과값을 받음
            Log.e(TAG, "myStory_insert.php result : " + res);//결과 객체 확인

            if (json.MyStoryWriteJsonParse(res)) {
                Toast.makeText(getApplicationContext(), "작성된 이야기를 저장합니다.", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MyStory_MyStoryWrite.this, MyStory.class);
                startActivity(i);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "이야기 저장에 실패했습니다.", Toast.LENGTH_SHORT).show();
            }

        }

    }


    public void Write_CancelButtonClicked(View v) throws Exception {

        Toast.makeText(getApplicationContext(), "이전 화면으로 돌아갑니다.", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(MyStory_MyStoryWrite.this, MyStory.class);
        startActivity(i);
        finish();
    }

}

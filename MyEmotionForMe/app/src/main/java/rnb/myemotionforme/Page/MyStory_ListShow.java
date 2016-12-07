package rnb.myemotionforme.Page;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import rnb.myemotionforme.HttpTask;
import rnb.myemotionforme.JsonParse;
import rnb.myemotionforme.ListView.MyStory_Comments_ListVIewAdapter;
import rnb.myemotionforme.R;
import rnb.myemotionforme.key.JsonKey_User;
import rnb.myemotionforme.key.JsonKey_myStory;
import rnb.myemotionforme.key.Json_Comments;

/**
 * Created by yj on 16. 5. 24..
 */

/*
* 댓글을 한꺼번에 확인할 수 있도록 수정함
* */
public class MyStory_ListShow extends ActionBarActivity {


    TextView tv_date_listshow;
    TextView tv_emotion_listshow;
    TextView tv_title_listshow;
    TextView tv_text_listshow;
    EditText et_text_write_comments;
    String res;

    private static final String TAG = "DEBUG";
    private ArrayAdapter<String> mSpinnerAdapter = null;
    private ListView mListView = null;
    public static MyStory_Comments_ListVIewAdapter mAdapter = null;
    private TextView tv_empty_mystory_comments;
    Context mContext;

    public static Json_Comments[] cmt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("MyStory");
        setContentView(R.layout.activity_mystory_listshow);

      //  Json_myStory.sno = (int) json.getJsonArrayData(i, "sno");
      //  Json_myStory.rdno = (int) json.getJsonArrayData(i, "rdno");

        //웹뷰에서 텍스트 입력시 키보드가 올라올때 UI가 화면에 보이도록 하는 방법
        //단, 안드로이드 테마 스타일에 android:windowFullscreen를 쓰는 경우 작동하지 않는다. 그 부분을 지워야함.
       // getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE );

        tv_date_listshow = (TextView)findViewById(R.id.tv_date_listshow);
        tv_emotion_listshow = (TextView)findViewById(R.id.tv_emotion_listshow);
        tv_title_listshow = (TextView) findViewById(R.id.tv_title_listshow);
        tv_text_listshow = (TextView)findViewById(R.id.tv_text_listshow);

        et_text_write_comments = (EditText)findViewById(R.id.et_text_write_comments);

        tv_empty_mystory_comments = (TextView) findViewById(R.id.tv_empty_mystory_comments);
        mListView = (ListView) findViewById(R.id.lv_mystory_comments);
        mAdapter = new MyStory_Comments_ListVIewAdapter(this);
        mListView.setAdapter(mAdapter);

        // JsonKey_myStory.sshow

        tv_title_listshow.setText(JsonKey_myStory.stitle);
        tv_date_listshow.setText(JsonKey_myStory.sdate);
        tv_text_listshow.setText(JsonKey_myStory.stext);
        tv_emotion_listshow.setText(MyEmotion.emotion[JsonKey_myStory.sdno]);

        try {
            MyStory_Comments_setting();
            CommentsListUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //[함수] 사용자 디비에서 정보 가져오기
        //만약 정보가 있다면 additem으로 리스트에 저장
    }


    public void CommentsListUpdate()
    {
        mAdapter.notifyDataSetChanged();
        if(mListView!=null) {
            tv_empty_mystory_comments.setVisibility(View.INVISIBLE);
        }
        else
            tv_empty_mystory_comments.setVisibility(View.VISIBLE);
    }




    //댓글 추가 기능
    public void Mystory_Comments_SavedButtonClicked(View v) throws Exception {

        String comment = et_text_write_comments.getText().toString();

        if(comment.getBytes().length <= 0)
        {
            Toast.makeText(getApplicationContext(), "댓글을 작성해주세요.", Toast.LENGTH_SHORT).show();
        }
        else {

            JSONObject obj = new JSONObject();
            JsonParse json= new JsonParse();

            obj.put("sno", JsonKey_myStory.sno);
            obj.put("uno", JsonKey_User.uno);
            obj.put("ntext", comment);

            Log.e(TAG, "json : " + obj.toString());//json 객체 확인

            //서버로 보냄 파라미터 : "url"동적으로 변화되는 경로, "jsonObject"서버로 보내질 객체
            HttpTask task = new HttpTask("/RnB/myStory_Comments_insert.php", obj.toString());
            res = task.execute().get(); //결과값을 받음
            Log.e(TAG, "myStory_Comments_insert.php result : " + res);//결과 객체 확인

            if (json.UserCheckJsonParse(res)) {
                mAdapter.addItem(getResources().getDrawable(R.drawable.myme_icon), comment);
                Toast.makeText(getApplicationContext(), "댓글이 등록되었습니다.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "댓글 등록에 실패했습니다.", Toast.LENGTH_SHORT).show();
            }

            /*만약 댓글을 등록했다면, 댓글을 DB에 저장해야 한다*/

            CommentsListUpdate();
            et_text_write_comments.setText("");
        }

    }


    public void MyStory_Comments_setting() throws Exception{

        JSONObject obj = new JSONObject();
        obj.put("uno", JsonKey_User.uno); //
        obj.put("sno",JsonKey_myStory.sno);

        Log.e("uemail","uemail, sno:" + obj.toString());

        HttpTask task = new HttpTask("/RnB/myStory_Comments.php", obj.toString());
        String res = task.execute().get(); //결과값을 받음

        JsonParse json = new JsonParse();
        json.makeJsonObject(res);
        if(!json.getJsonState()) return;

        int size = json.getJsonArraySize();

        if(size == 0)
        {
            Toast.makeText(getApplicationContext(), "현재 댓글이 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
        }
        else {
            cmt = new Json_Comments[size];

            for (int i = 0; i < size; i++) {
                //nno, nsno, nwho, ntext
                int nno = Integer.parseInt((String)json.getJsonArrayData(i,"nno"));
                String ntext = (String) json.getJsonArrayData(i, "ntext");
                int nwho = Integer.parseInt((String) json.getJsonArrayData(i, "nwho"));
                int nsno = Integer.parseInt((String)json.getJsonArrayData(i, "nsno"));

                Log.e("sno","ntext : " + ntext);

                cmt[i] = new Json_Comments(ntext, nwho, nsno, nno);
                mAdapter.addItem(getResources().getDrawable(R.drawable.myme_icon), ntext);

            }
        }
    }



    @Override
    public void onBackPressed() {
        Intent i = new Intent(MyStory_ListShow.this, MyStory.class);
        startActivity(i);
        finish();
        super.onBackPressed();
    }
}




  /*
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                    // Toast.makeText(MyStory.this, Key.textData, Toast.LENGTH_SHORT).show();
                    ListData mData = mAdapter.mListData.get(position);

                    JsonKey_Comments.ntext = cmt[position].getText();
                    JsonKey_Comments.nno = cmt[position].getNno();
                    JsonKey_Comments.nsno = cmt[position].getSno();
                    JsonKey_Comments.nwho = cmt[position].getWho();

                }
            });
        */

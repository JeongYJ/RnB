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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import rnb.myemotionforme.HttpTask;
import rnb.myemotionforme.JsonParse;
import rnb.myemotionforme.ListView.WhosStory_Comments_ListVIewAdapter;
import rnb.myemotionforme.R;
import rnb.myemotionforme.key.JsonKey_User;
import rnb.myemotionforme.key.JsonKey_myStory;
import rnb.myemotionforme.key.Key;

/**
 * Created by yj on 16. 5. 24..
 */
public class WhosStory_ListShow extends ActionBarActivity {

    TextView tv_date_listshow;
    TextView tv_emotion_listshow;
    TextView tv_title_listshow;
    TextView tv_text_listshow;

    EditText et_text_whossroty_comments;

    String res;

    private static final String TAG = "DEBUG";
    private ArrayAdapter<String> mSpinnerAdapter = null;
    private ListView mListView = null;
    public static WhosStory_Comments_ListVIewAdapter mAdapter = null;
    private TextView tv_empty_whosstory_comments;
    Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Who's Story");
        setContentView(R.layout.activity_whosstory_listshow);

        tv_date_listshow = (TextView)findViewById(R.id.tv_date_whosstory_listshow);
        tv_emotion_listshow = (TextView)findViewById(R.id.tv_emotion_whosstory_listshow);
        tv_title_listshow = (TextView) findViewById(R.id.tv_title_whosstory_listshow);
        tv_text_listshow = (TextView)findViewById(R.id.tv_text_whosstory_listshow);



        et_text_whossroty_comments = (EditText)findViewById(R.id.et_text_whosstory_comments);

        tv_empty_whosstory_comments = (TextView) findViewById(R.id.tv_empty_whosstory_comments);
        mListView = (ListView) findViewById(R.id.lv_whosstory_comments);
        mAdapter = new WhosStory_Comments_ListVIewAdapter(this);
        mListView.setAdapter(mAdapter);

        tv_title_listshow.setText(Key.titleData);
        tv_date_listshow.setText(Key.dateData);
        tv_text_listshow.setText(Key.textData);

        //[함수] 사용자 디비에서 정보 가져오기
        //만약 정보가 있다면 additem으로 리스트에 저장

        try {
            MyStoryCommentsList();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        CommentsListUpdate();
    }


/*
    public void MyStoryCommentsList()
    {
        if(Key.textData.equals("진짜 너무 신나~~~!")) {

            mAdapter.addItem(getResources().getDrawable(R.drawable.myme_icon),
                    "신나서 나도 좋아~");
            mAdapter.addItem(getResources().getDrawable(R.drawable.myme_icon),
                    "고마워~~!");
            mAdapter.addItem(getResources().getDrawable(R.drawable.myme_icon),
                    "ㅎ.ㅎ");
        }
        else if(Key.textData.equals("케이크 맛있었는데 다 못먹고 와서 아쉬웠다.."))
        {
            mAdapter.addItem(getResources().getDrawable(R.drawable.myme_icon),
                    "케이크~!!");
            mAdapter.addItem(getResources().getDrawable(R.drawable.myme_icon),
                    "티라미수 먹구싶당");
        }
        else if(Key.textData.equals("오버워치 하고 싶다"))
        {

        }

    }
*/


    public void MyStoryCommentsList() throws JSONException {
        String comment =  et_text_whossroty_comments.getText().toString();

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
            try {
                res = task.execute().get(); //결과값을 받음
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            Log.e(TAG, "myStory_Comments_insert.php result : " + res);//결과 객체 확인

            if (json.UserCheckJsonParse(res)) {
                mAdapter.addItem(getResources().getDrawable(R.drawable.myme_icon), comment);
                Toast.makeText(getApplicationContext(), "댓글이 등록되었습니다.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "댓글 등록에 실패했습니다.", Toast.LENGTH_SHORT).show();
            }

            /*만약 댓글을 등록했다면, 댓글을 DB에 저장해야 한다*/

            CommentsListUpdate();
            et_text_whossroty_comments.setText("");
        }

    }



    public void CommentsListUpdate()
    {
        mAdapter.notifyDataSetChanged();
        if(mListView!=null) {
            tv_empty_whosstory_comments.setVisibility(View.INVISIBLE);
        }
        else
            tv_empty_whosstory_comments.setVisibility(View.VISIBLE);
    }

    //댓글 추가 기능 임시
    public void WhosStory_Comments_SavedButtonClicked(View v) throws Exception {

        String comment = et_text_whossroty_comments.getText().toString();

        if(comment.getBytes().length <= 0)
        {
            Toast.makeText(getApplicationContext(), "댓글을 작성해주세요.", Toast.LENGTH_SHORT).show();
        }
        else {
            mAdapter.addItem(getResources().getDrawable(R.drawable.myme_icon),
                    comment);
            Toast.makeText(getApplicationContext(), "댓글이 등록되었습니다.", Toast.LENGTH_SHORT).show();
            CommentsListUpdate();
            et_text_whossroty_comments.setText("");
        }
    }


    @Override
    public void onBackPressed() {
        Intent i = new Intent(WhosStory_ListShow.this, MyStory.class);
        startActivity(i);
        finish();
        super.onBackPressed();
    }
}

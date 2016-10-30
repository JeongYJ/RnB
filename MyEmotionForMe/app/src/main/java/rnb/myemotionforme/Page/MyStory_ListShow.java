package rnb.myemotionforme.Page;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import rnb.myemotionforme.ListView.MyStory_Comments_ListVIewAdapter;
import rnb.myemotionforme.R;
import rnb.myemotionforme.key.Key;

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


    private static final String TAG = "DEBUG";
    private ArrayAdapter<String> mSpinnerAdapter = null;
    private ListView mListView = null;
    public static MyStory_Comments_ListVIewAdapter mAdapter = null;
    private TextView tv_empty_mystory_comments;
    Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("MyStory");
        setContentView(R.layout.activity_mystory_listshow);


        //웹뷰에서 텍스트 입력시 키보드가 올라올때 UI가 화면에 보이도록 하는 방법
        //단, 안드로이드 테마 스타일에 android:windowFullscreen를 쓰는 경우 작동하지 않는다. 그 부분을 지워야함.
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE );


        tv_date_listshow = (TextView)findViewById(R.id.tv_date_listshow);
        tv_emotion_listshow = (TextView)findViewById(R.id.tv_emotion_listshow);
        tv_title_listshow = (TextView) findViewById(R.id.tv_title_listshow);
        tv_text_listshow = (TextView)findViewById(R.id.tv_text_listshow);



        et_text_write_comments = (EditText)findViewById(R.id.et_text_write_comments);

        tv_empty_mystory_comments = (TextView) findViewById(R.id.tv_empty_mystory_comments);
        mListView = (ListView) findViewById(R.id.lv_mystory_comments);
        mAdapter = new MyStory_Comments_ListVIewAdapter(this);
        mListView.setAdapter(mAdapter);

        tv_title_listshow.setText(Key.titleData);
        tv_date_listshow.setText(Key.dateData);
        tv_text_listshow.setText(Key.textData);




        //[함수] 사용자 디비에서 정보 가져오기
        //만약 정보가 있다면 additem으로 리스트에 저장

        MyStoryCommentsList();
        CommentsListUpdate();
    }



    public void MyStoryCommentsList()
    {
        if(Key.textData.equals("진짜 너무 신나~~~!")) {

            mAdapter.addItem(getResources().getDrawable(R.drawable.myme_icon),
                    "신나서 나도 좋아~");
        }
        else if(Key.textData.equals("케이크 맛있었는데 다 못먹고 와서 아쉬웠다.."))
        {
            mAdapter.addItem(getResources().getDrawable(R.drawable.myme_icon),
                    "케이크~!!");
            mAdapter.addItem(getResources().getDrawable(R.drawable.myme_icon),
                    "티라미수 먹구싶당");
        }

    }


/*
    public void MyStoryCommentsList()
    {
        if(Key.textData.equals("여긴 어디인가")) {

            mAdapter.addItem(getResources().getDrawable(R.drawable.myme_icon),
                    "밤새서 많이 힘드니");
            mAdapter.addItem(getResources().getDrawable(R.drawable.myme_icon),
                    "응..");
            mAdapter.addItem(getResources().getDrawable(R.drawable.myme_icon),
                    "전기장판이 그립다");
            mAdapter.addItem(getResources().getDrawable(R.drawable.myme_icon),
                    "힘내");
            mAdapter.addItem(getResources().getDrawable(R.drawable.myme_icon),
                    "빨리 과제 끝났으면 좋겠다..");
        }
        else if(Key.textData.equals("치킨먹고 싶다..."))
        {
            mAdapter.addItem(getResources().getDrawable(R.drawable.myme_icon),
                    "나도");
            mAdapter.addItem(getResources().getDrawable(R.drawable.myme_icon),
                    "치킨먹구싶자나");
            mAdapter.addItem(getResources().getDrawable(R.drawable.myme_icon),
                    "배고파");
        }

    }
    */

    public void CommentsListUpdate()
    {
        mAdapter.notifyDataSetChanged();
        if(mListView!=null) {
            tv_empty_mystory_comments.setVisibility(View.INVISIBLE);
        }
        else
            tv_empty_mystory_comments.setVisibility(View.VISIBLE);
    }

    //댓글 추가 기능 임시
    public void Mystory_Comments_SavedButtonClicked(View v) throws Exception {

        String comment = et_text_write_comments.getText().toString();

        if(comment.getBytes().length <= 0)
        {
            Toast.makeText(getApplicationContext(), "댓글을 작성해주세요.", Toast.LENGTH_SHORT).show();
        }
        else {
                mAdapter.addItem(getResources().getDrawable(R.drawable.myme_icon),
                        comment);
            Toast.makeText(getApplicationContext(), "댓글이 등록되었습니다.", Toast.LENGTH_SHORT).show();
            CommentsListUpdate();
            et_text_write_comments.setText("");
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

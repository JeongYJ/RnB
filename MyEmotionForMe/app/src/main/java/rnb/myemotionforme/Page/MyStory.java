package rnb.myemotionforme.Page;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import rnb.myemotionforme.Events.ListData;
import rnb.myemotionforme.Events.MyStory_ListVIewAdapter;
import rnb.myemotionforme.Events.SwipeDismissListViewTouchListener;
import rnb.myemotionforme.R;
import rnb.myemotionforme.key.Key;

/**
 * Created by yj on 16. 5. 24..
 */
public class MyStory extends ActionBarActivity {

    private static final String TAG = "DEBUG";
    private ArrayAdapter<String> mSpinnerAdapter = null;
    private ListView mListView = null;
    public static MyStory_ListVIewAdapter mAdapter = null;
    private TextView tv_empty_mystory;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mystory);
        getSupportActionBar().setTitle("MyStory");

        tv_empty_mystory = (TextView) findViewById(R.id.tv_empty_mystory);
        mListView = (ListView) findViewById(R.id.lv_mystory);
        mAdapter = new MyStory_ListVIewAdapter(this);
        mListView.setAdapter(mAdapter);

        //mListView.setEmptyView(tv_empty_mystory);

        //[함수] 사용자 디비에서 정보 가져오기
        //만약 정보가 있다면 additem으로 리스트에 저장

        MyStoryList();
        mAdapter.notifyDataSetChanged();
        if(mListView!=null) {
            tv_empty_mystory.setVisibility(View.INVISIBLE);
            SwipeDismissListViewTouchListener touchListener =
                    new SwipeDismissListViewTouchListener(mListView,
                            new SwipeDismissListViewTouchListener.DismissCallbacks() {
                                @Override
                                public boolean canDismiss(int position) {
                                    return true;
                                }

                                @Override
                                public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                                    for (int position : reverseSortedPositions) {
                                        mAdapter.remove(position);
                                        Log.d(TAG,"position :" + position);
                                        // listArr[] = 0; //position이 아니라.. 지웠던 걸 찾아가야댐
                                    }
                                    mAdapter.notifyDataSetChanged();
                                }
                            });
            mListView.setOnTouchListener(touchListener);
            mListView.setOnScrollListener(touchListener.makeScrollListener());
        }
        else
            tv_empty_mystory.setVisibility(View.VISIBLE);

    }

    public void MyStoryList()
    {
        mAdapter.addItem(getResources().getDrawable(R.drawable.myme_icon),
                "두번째 테스트 제목",
                "2016-05-25");

        if(!Key.myStory_title.equals("")) {
            mAdapter.addItem(getResources().getDrawable(R.drawable.myme_icon),
                    Key.myStory_title,
                    Key.myStory_date);
        }

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                ListData mData = mAdapter.mListData.get(position);
                Key.titleData = mData.mTitle;
                Key.dateData = mData.mDate;
                Toast.makeText(MyStory.this, mData.mTitle, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MyStory.this, MyStory_MyStoryListShow.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(MyStory.this, Menu.class);
        startActivity(i);
        finish();
        super.onBackPressed();
    }

    public void MyStory_WriteStoryButtonClicked(View v) throws Exception {
        Toast.makeText(getApplicationContext(), "오늘의 이야기를 작성합니다.", Toast.LENGTH_LONG).show();
        Intent i = new Intent(MyStory.this, MyStory_MyStoryWrite.class);
        startActivity(i);
        finish();
    }

    public void MyStory_AnotherStoryButtonClicked(View v) throws Exception {

        Toast.makeText(getApplicationContext(), "누군가의 이야기를 엿보러 갑니다.", Toast.LENGTH_LONG).show();
        Intent i = new Intent(MyStory.this, MyStory_WhosStoryChoose.class);
        startActivity(i);
        finish();
    }

}
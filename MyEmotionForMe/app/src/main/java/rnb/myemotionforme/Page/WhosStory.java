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

import rnb.myemotionforme.Events.SwipeDismissListViewTouchListener;
import rnb.myemotionforme.ListView.ListData;
import rnb.myemotionforme.ListView.WhosStory_ListVIewAdapter;
import rnb.myemotionforme.R;
import rnb.myemotionforme.key.Key;

/**
 * Created by yj on 16. 5. 24..
 */
public class WhosStory extends ActionBarActivity {

    private static final String TAG = "DEBUG";
    private ArrayAdapter<String> mSpinnerAdapter = null;
    private ListView mListView = null;
    public static WhosStory_ListVIewAdapter mAdapter = null;
    private TextView tv_empty_whosstory;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whosstory);
        getSupportActionBar().setTitle("Who's Story");

        tv_empty_whosstory = (TextView) findViewById(R.id.tv_empty_whosstory);
        mListView = (ListView) findViewById(R.id.lv_whosstory);
        mAdapter = new WhosStory_ListVIewAdapter(this);
        mListView.setAdapter(mAdapter);

        //mListView.setEmptyView(tv_empty_mystory);

        //[함수] 사용자 디비에서 정보 가져오기
        //만약 정보가 있다면 additem으로 리스트에 저장

        //MyStoryList();
        WhosStoryList();
        mAdapter.notifyDataSetChanged();
        if(mListView!=null) {
            tv_empty_whosstory.setVisibility(View.INVISIBLE);
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
            tv_empty_whosstory.setVisibility(View.VISIBLE);

    }

    public void MyStoryList()
    {
        mAdapter.addItem(getResources().getDrawable(R.drawable.myme_icon),
                "오늘 너무 기분이 좋다",
                "2016-05-25");

        mAdapter.addItem(getResources().getDrawable(R.drawable.myme_icon),
                "동생 생일이야~",
                "2016-09-25");

        mAdapter.addItem(getResources().getDrawable(R.drawable.myme_icon),
                "힝",
                "2016-10-26");

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

                if(Key.titleData.equals("오늘 너무 기분이 좋다")) {

                    Key.textData = "진짜 너무 신나~~~!";
                }
                else if(Key.titleData.equals("동생 생일이야~"))
                {
                    Key.textData = "케이크 맛있었는데 다 못먹고 와서 아쉬웠다..";
                }
                else
                {
                    Key.textData = "오버워치 하고 싶다";
                }

                // Toast.makeText(MyStory.this, Key.textData, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(WhosStory.this, WhosStory_ListShow.class);
                startActivity(i);
                finish();
            }
        });
    }


    public void WhosStoryList()
    {
        mAdapter.addItem(getResources().getDrawable(R.drawable.myme_icon),
                "나는 누구인가",
                "2016-10-3");

        mAdapter.addItem(getResources().getDrawable(R.drawable.myme_icon),
                "웅냥냥",
                "2016-10-10");

        if(!Key.whosStory_title.equals("")) {
            mAdapter.addItem(getResources().getDrawable(R.drawable.myme_icon),
                    Key.whosStory_title,
                    Key.whosStory_date);
        }

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                ListData mData = mAdapter.mListData.get(position);
                Key.titleData = mData.mTitle;
                Key.dateData = mData.mDate;

                if(Key.titleData.equals("나는 누구인가")) {
                    Key.textData = "여긴 어디인가";
                }
                else if(Key.titleData.equals("웅냥냥"))
                {
                    Key.textData = "치킨먹고 싶다...";
                }

               // Toast.makeText(WhosStory.this, Key.textData, Toast.LENGTH_SHORT).show();

                Intent i = new Intent(WhosStory.this, WhosStory_ListShow.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(WhosStory.this, Menu.class);
        startActivity(i);
        finish();
        super.onBackPressed();
    }
}
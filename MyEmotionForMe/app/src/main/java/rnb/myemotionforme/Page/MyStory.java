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

import org.json.JSONObject;

import rnb.myemotionforme.Events.SwipeDismissListViewTouchListener;
import rnb.myemotionforme.HttpTask;
import rnb.myemotionforme.JsonParse;
import rnb.myemotionforme.ListView.ListData;
import rnb.myemotionforme.ListView.MyStory_ListVIewAdapter;
import rnb.myemotionforme.R;
import rnb.myemotionforme.key.JsonKey_User;
import rnb.myemotionforme.key.JsonKey_myStory;
import rnb.myemotionforme.key.Json_myStory;

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
    public static Json_myStory[] mys;

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

        try {
            MyStory_setting();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //WhosStoryList();
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


    //sno, sdate, stitle, stext, sshow, rdno 값 받기
    //rdno는 사용자의 감정을 MyStoryList에 써주기 위해 사용

    public void MyStory_setting() throws Exception{

        JSONObject obj = new JSONObject();
        obj.put("uemail", JsonKey_User.uemail); //
        Log.e("uemail","uemail:" + obj.toString());

        HttpTask task = new HttpTask("/RnB/myStory.php", obj.toString());
        String res = task.execute().get(); //결과값을 받음

        JsonParse json = new JsonParse();
        json.makeJsonObject(res);
        if(!json.getJsonState()) return;

        int size = json.getJsonArraySize();


        if(size == 0)
        {
            Toast.makeText(getApplicationContext(), "현재 사용자 스토리가 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
        }
        else {
            mys = new Json_myStory[size];

            for (int i = 0; i < size; i++) {

                String sdate = (String) json.getJsonArrayData(i, "sdate");
                String stitle = (String) json.getJsonArrayData(i, "stitle");
                String stext = (String) json.getJsonArrayData(i, "stext");
                int sshow = Integer.parseInt((String) json.getJsonArrayData(i, "sshow"));
                int sdno = Integer.parseInt((String)json.getJsonArrayData(i, "rdno"));
                int sno = Integer.parseInt((String)json.getJsonArrayData(i,"sno"));
                   //     MyEmotion.emotion[Integer.parseInt(dno)]
         //       Json_myStory.rdno = (String) json.getJsonArrayData(i, "rdno");
                //String date, String title, String text, int show, int dno)
                mys[i] = new Json_myStory(sdate, stitle, stext, sshow, sdno, sno);
                mAdapter.addItem(getResources().getDrawable(R.drawable.myme_icon), mys[i].getTitle(), mys[i].getDate(), MyEmotion.emotion[mys[i].getDno()]);
            }

            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                    // Toast.makeText(MyStory.this, Key.textData, Toast.LENGTH_SHORT).show();
                    ListData mData = mAdapter.mListData.get(position);

                    JsonKey_myStory.stitle = mys[position].getTitle();
                    JsonKey_myStory.stext = mys[position].getText();
                    JsonKey_myStory.sshow = mys[position].getShow();
                    JsonKey_myStory.sdate = mys[position].getDate();
                    JsonKey_myStory.sdno = mys[position].getDno();
                    JsonKey_myStory.sno = mys[position].getSno();

                    Intent i = new Intent(MyStory.this, MyStory_ListShow.class);
                    startActivity(i);
                    finish();
                }
            });

        }
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
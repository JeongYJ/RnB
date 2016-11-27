package rnb.myemotionforme.Page;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import rnb.myemotionforme.ListView.ListData;
import rnb.myemotionforme.ListView.MyMusic_ListVIewAdapter;
import rnb.myemotionforme.HttpTask;
import rnb.myemotionforme.JsonParse;
import rnb.myemotionforme.R;
import rnb.myemotionforme.SocketUtil;
import rnb.myemotionforme.key.JsonKey_Details;
import rnb.myemotionforme.key.JsonKey_User;

/**
 * Created by yj on 16. 5. 24..
 */
public class MyMusic extends ActionBarActivity {

    TextView music_emotion_df;
    TextView music_emotion;
    TextView tv_musicName;
    ListView music_list;
    MyMusic_ListVIewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mymusic);
        getSupportActionBar().setTitle("MyMusic");
        tv_musicName = (TextView) findViewById(R.id.tv_musicName);
        music_emotion_df = (TextView)findViewById(R.id.tx_todayemotion_df_music);
        music_emotion = (TextView)findViewById(R.id.tx_todayemotion_music);
        music_list = (ListView) findViewById(R.id.lv_musicList);
        adapter = new MyMusic_ListVIewAdapter(this);
        music_list.setAdapter(adapter);
        try {
            music_setting();
        } catch (Exception e) {
            e.printStackTrace();
        }

        music_emotion.setText(MyEmotion.emotion[JsonKey_Details.dno]);
    }


    public void music_setting() throws Exception{

        JSONObject obj = new JSONObject();
        obj.put("uemail", JsonKey_User.uemail); //
        Log.e("uemail","uemail:" + obj.toString());

        HttpTask task = new HttpTask("/RnB/myMusic_select.php", obj.toString());
        String res = task.execute().get(); //결과값을 받음

        JsonParse json = new JsonParse();
        json.makeJsonObject(res);
        if(!json.getJsonState()) return;

        int size = json.getJsonArraySize();

        if(size == 0)
        {
            Toast.makeText(getApplicationContext(), "현재 감정에 맞는 음악이 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
        }
        else {
            for (int i = 0; i < size; i++) {
                String title = (String) json.getJsonArrayData(i, "mtitle");
                String musician = (String) json.getJsonArrayData(i, "mmusician");
                String kind = (String) json.getJsonArrayData(i, "mkind");
                adapter.addItem(getResources().getDrawable(R.drawable.myme_icon), title, "작곡가 :" + musician + "/장르 : " + kind);
            }
            music_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                    ListData mData = adapter.mListData.get(position);
                    tv_musicName.setText(mData.mTitle);
                    openSocket("music=" + mData.mTitle);
                    Toast.makeText(MyMusic.this, mData.mTitle, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    @Override
    public void onBackPressed() {
        Intent i = new Intent(MyMusic.this, Menu.class);
        startActivity(i);
        finish();
        super.onBackPressed();
    }

    void openSocket(String msg){
        SocketUtil mysocket = new SocketUtil("192.168.0.97", 5100);
        mysocket.setmessage(msg);
        mysocket.run();
    }

    public void musicPlayClick(View v){
        Toast.makeText(getApplicationContext(), "play", Toast.LENGTH_SHORT).show();
        openSocket("music=play");
    }
    public void musicStopClick(View v) {
        Toast.makeText(getApplicationContext(), "stop", Toast.LENGTH_SHORT).show();
        openSocket("music=stop");
    }

}
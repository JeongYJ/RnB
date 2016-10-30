package rnb.myemotionforme.Page;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import rnb.myemotionforme.HttpTask;
import rnb.myemotionforme.JsonParse;
import rnb.myemotionforme.R;
import rnb.myemotionforme.key.JsonKey_Details;
import rnb.myemotionforme.key.JsonKey_User;

/**
 * Created by yj on 16. 5. 24..
 */
public class MyEmotion extends ActionBarActivity {

    private static final String TAG = "DEBUG";
    JsonParse Json = new JsonParse();
    JSONObject obj;
    HttpTask task;
    //emotion index번호 = details Table의 dno 번호
    public static String emotion[] = {"soso", "good", "happy", "laughing", "excited", "inlove", "annoyance", "exasperated", "mad", "surprised","shocked", "disbelief","sad","worried","crying","doubtful","scared","tired"};
    Boolean UserCheck = false;
    TextView tx_date_myemotion;
    TextView tv_myemotion_is;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myemotion);
        getSupportActionBar().setTitle("MyEmotion");

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        String str_date = df.format(new Date());

        tx_date_myemotion = (TextView)findViewById(R.id.tx_date_myemotion);
        tx_date_myemotion.setText(str_date);

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(MyEmotion.this, Menu.class);
        startActivity(i);
        finish();
        super.onBackPressed();
    }

    public void bt_go_to_myEmotionChart(View v)throws Exception {
        Intent i = new Intent(MyEmotion.this, MyEmotion_chart.class);
        startActivity(i);
        finish();
    }
//common (1)
    public void sosoEmotionOnClicked(View v) throws Exception {
        detailsDataJsonParse(0);
    }

    public void goodEmotionOnClicked(View v) throws Exception {
        detailsDataJsonParse(1);
    }

    public void happyEmotionOnClicked(View v) throws Exception {
        detailsDataJsonParse(2);
    }
    //enjoy (2)
    public void laughingEmotionOnClicked(View v) throws Exception {
        detailsDataJsonParse(3);
    }

    public void excitedEmotionOnClicked(View v) throws Exception {
        detailsDataJsonParse(4);
    }

    public void inloveEmotionOnClicked(View v) throws Exception {
        detailsDataJsonParse(5);
    }

    //angry(3)

    public void annoyanceEmotionOnClicked(View v) throws Exception {
        detailsDataJsonParse(6);
    }

    public void exasperatedEmotionOnClicked(View v) throws Exception {
        detailsDataJsonParse(7);
    }

    public void madEmotionOnClicked(View v) throws Exception {
        detailsDataJsonParse(8);
    }

    //shocked (4)
    public void surprisedEmotionOnClicked(View v) throws Exception {
        detailsDataJsonParse(9);
    }

    public void shockedEmotionOnClicked(View v) throws Exception {
        detailsDataJsonParse(10);
    }

    public void disbeliefEmotionOnClicked(View v) throws Exception {
        detailsDataJsonParse(11);
    }

    //sad (5)
    public void sadEmotionOnClicked(View v) throws Exception {
        detailsDataJsonParse(12);
    }

    public void worriedEmotionOnClicked(View v) throws Exception {
        detailsDataJsonParse(13);
    }

    public void cryingEmotionOnClicked(View v) throws Exception {
        detailsDataJsonParse(14);
    }

    //scared(6)
    public void doubtfulEmotionOnClicked(View v) throws Exception {
        detailsDataJsonParse(15);
    }

    public void scaredEmotionOnClicked(View v) throws Exception {
        detailsDataJsonParse(16);
    }

    //tired(7)
    public void tiredEmotionOnClicked(View v) throws Exception {
        detailsDataJsonParse(17);
    }


    /*
    public void todayCheck() throws Exception{

        obj = new JSONObject();
        obj.put("uemail", JsonKey_User.uemail);
        Log.e(TAG, "json : " + obj.toString());//json 객체 확인
        String phpPath = "/RnB/getUserState.php";

        task = new HttpTask(phpPath, obj.toString());
        String res = task.execute().get();
        Log.e(TAG, "getUserState : " + res);

        //Json 결과 파서
        if (Json.StatusJsonParse(res) == true) {

            Log.e(TAG, "이미 감정이 존재.");
            UserCheck = true;

            JSONObject obj = new JSONObject();
            obj.put("uemail", JsonKey_User.uemail);
            Log.e(TAG, "json : " + obj.toString());//결과 객체 확인

            HttpTask task = new HttpTask("/RnB/getUserState_graph.php", obj.toString());
            String res2 = task.execute().get(); //결과값을 받음
            Log.e(TAG, "result : " + res);//결과 객체 확인


            JsonParse json = new JsonParse();
            json.makeJsonObject(res);
            if(!json.getJsonState()) {
                return;
            }

            tv_myemotion_is.setText(emotion[JsonKey_Details.dno]);

        } else {
            Log.e(TAG, "오늘 감정을 입력하지 않음.");
            tv_myemotion_is.setText(" 아직 입력되지 않았어요!");
            UserCheck = false;
        }
    }

*/

    public void todayClickedCheck() throws Exception
    {
        //버튼을 누르기 전에   //usercheck = true //오늘 날짜가 이미 존재한다면 => 오늘은 기분 선택을 더이상 하지 못하도록
        //uemail 값을 보내서 만약 true 일 경우 전체적으로 버튼을 누르지 못하도록 변경
        obj = new JSONObject();
        obj.put("uemail", JsonKey_User.uemail);
        Log.e(TAG, "json : " + obj.toString());//json 객체 확인
        String phpPath = "/RnB/getUserState.php";

        task = new HttpTask(phpPath, obj.toString());
        String res = task.execute().get();
        Log.e(TAG, "getUserState : " + res);

        //Json 결과 파서
        if (Json.StatusJsonParse(res) == true) {

            Log.e(TAG, "이미 감정이 존재.");
            Toast.makeText(getApplicationContext(), "오늘은 더이상 감정을 입력할 수 없어요~", Toast.LENGTH_LONG).show();
            UserCheck = true;

        } else {
            Log.e(TAG, "오늘 감정을 입력하지 않음.");
            UserCheck = false;
        }
    }


    public void detailsDataJsonParse(int ind) throws Exception {

        try {
            todayClickedCheck();
        } catch (Exception e) {
            e.printStackTrace();
        }


        if(UserCheck == false) {
          obj = new JSONObject();
          obj.put("ddetails", emotion[ind+1]);
          Log.e(TAG, "json : " + obj.toString());//json 객체 확인
          String phpPath = "/RnB/getDetails.php";

          task = new HttpTask(phpPath, obj.toString());
          String res = task.execute().get();
          Log.e(TAG, "res결과값 : " + res);
          // Log.e(TAG, "result : " +   +","+ deno +","+ dred +","+ dgreen +","+ dblue +","+ diconpath);//결과 객체 확인

          //Json 결과 파서
          if (Json.UserStateParse(res)) {
              UserCheck = true;

              InsertDataJsonParse(ind);
              Log.e(TAG, "Details Table에서 오늘의 감정을 받아왔습니다");

          } else {

              Log.e(TAG, "Details Table에서 오늘의 감정을 받아오지 못했습니다.");
          }
      }
      else
      {
          Toast.makeText(getApplicationContext(), "오늘은 더이상 감정을 입력할 수 없어요~", Toast.LENGTH_LONG).show();
      }
    }


    public void InsertDataJsonParse(int ind) throws Exception
    {
            obj = new JSONObject();
            obj.put("uemail", JsonKey_User.uemail);
            obj.put("reno", JsonKey_Details.deno);
            obj.put("rdno", JsonKey_Details.dno);

            Log.e(TAG, "json : " + obj.toString());//json 객체 확인
            String phpPath = "/RnB/setUserState.php";

            task = new HttpTask(phpPath, obj.toString());
            String res = task.execute().get();
            Log.e(TAG, "res결과값 : " + res);

            //Json 결과 파서
            if (Json.UserCheckJsonParse(res)) {

                Toast.makeText(getApplicationContext(), "오늘의 감정 " + emotion[ind] + " 입력 완료!", Toast.LENGTH_LONG).show();
                Log.e(TAG, "UserState Table에 값이 저장되었습니다");
            } else {
                Log.e(TAG, "UserState Table에 값이 저장되지 않았습니다");
            }
        }

    /*
    private void startActivity(Class<?> cls) {
        Intent i = new Intent(this, cls);
        startActivity(i);
    }

    public void onClick(View v){
        switch (v.getId()) {

            case R.id.btnCircleGraph:
                startActivity(CircleGraphActivity.class);
                break;

            case R.id.btnPieGraph:
                startActivity(PieGraphActivity.class);
                break;

            default:
                break;
        }
    }
    */
}

    /*
        //JSON Node Names

        JSONArray user = null;

        // Creating new JSON Parser
        JSONParser jParser = new JSONParser();

        // Getting JSON from URL
        JSONObject json = jParser.getJSONFromUrl(url);

        try {
            // Getting JSON Array
            user = json.getJSONArray(TAG_USER);
            JSONObject c = user.getJSONObject(0);

            // Storing  JSON item in a Variable
            String id = c.getString(TAG_ID);
            String name = c.getString(TAG_NAME);

        }
        catch(Exception e)
        { System.out.println(e.getMessage()); }


    */


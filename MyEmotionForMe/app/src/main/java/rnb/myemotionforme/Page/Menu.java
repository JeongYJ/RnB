package rnb.myemotionforme.Page;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;

import org.json.JSONObject;

import rnb.myemotionforme.Events.BackPressButtonActivity;
import rnb.myemotionforme.HttpTask;
import rnb.myemotionforme.JsonParse;
import rnb.myemotionforme.Login;
import rnb.myemotionforme.R;
import rnb.myemotionforme.key.JsonKey_Details;
import rnb.myemotionforme.key.JsonKey_User;

/**
 * Created by yj on 16. 5. 20..
 */
public class Menu extends FragmentActivity {

    JsonParse Json = new JsonParse();
    JSONObject obj;
    HttpTask task;
    Boolean UserCheck = false; //true = 오늘의 감정을 입력함. false = 오늘의 감정을 입력하지 않음.

    private BackPressButtonActivity bp;
    public static final String TAG = "Menu";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_menu);
        bp = new BackPressButtonActivity(this);

        //유저가 오늘 감정을 입력했는지 입력하지 않았는지 확인하기
        //만약 false를 반환했다면, 오늘의 감정 먼저 입력하도록 유도하기
        try {
            todayClickedCheck();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(UserCheck == false)
            Please_setEmotion();

    }

    public void Please_setEmotion()
    {
        new AlertDialog.Builder(this)
        // 메시지를 설정한다.
        .setMessage("먼저 오늘의 감정을 입력해주세요! \n확인 버튼을 누르면 MyEmotion 메뉴로 이동합니다.")
// positive 버튼을 설정한다.
        .setPositiveButton("확인", new DialogInterface.OnClickListener()
        {
            // positive 버튼에 대한 클릭 이벤트 처리를 구현한다.
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Log.e(TAG, "확인 클릭");
                Intent i = new Intent(Menu.this, MyEmotion.class);
                startActivity(i);
                // 다이얼로그를 화면에서 사라지게 한다.
                dialog.dismiss();
            }
        })
        .show();
    }

    public void LogoutAlert()
    {
        new AlertDialog.Builder(this)
                // 메시지를 설정한다.
                .setMessage("정말 로그아웃 하시겠습니까")
// positive 버튼을 설정한다.
                .setPositiveButton("확인", new DialogInterface.OnClickListener()
                {
                    // positive 버튼에 대한 클릭 이벤트 처리를 구현한다.
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Log.e(TAG, "확인 클릭");
                        Intent i = new Intent(Menu.this, Login.class);
                        startActivity(i);
                        // 다이얼로그를 화면에서 사라지게 한다.
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Log.e(TAG, "취소 클릭");
                        // 다이얼로그를 화면에서 사라지게 한다.
                        dialog.dismiss();
                    }
                })
                .show();
    }

    public void Menu_LogoutClicked(View v) throws Exception{
        LogoutAlert();
    }


    public void Menu_MyPageButtonClicked(View v) throws Exception {
       // Toast.makeText(getApplicationContext(), "MyPage", Toast.LENGTH_LONG).show();
        Intent i = new Intent(Menu.this, MyPage.class);
        startActivity(i);
    }

    public void Menu_MyEmotionButtonClicked(View v) throws Exception {
       // Toast.makeText(getApplicationContext(), "MyEmotion", Toast.LENGTH_LONG).show();
        Intent i = new Intent(Menu.this, MyEmotion.class);
        startActivity(i);
        finish();
    }

    public void Menu_MyMusicButtonClicked(View v) throws Exception {
        if(UserCheck == false)
            Please_setEmotion();
        else {
           // Toast.makeText(getApplicationContext(), "MyMusic", Toast.LENGTH_LONG).show();
            Intent i = new Intent(Menu.this, MyMusic.class);
            startActivity(i);
            finish();
        }
    }

    public void Menu_MyRobotButtonClicked(View v) throws Exception {
        if(UserCheck == false)
            Please_setEmotion();
        else {
          //  Toast.makeText(getApplicationContext(), "MyRobot", Toast.LENGTH_LONG).show();
            Intent i = new Intent(Menu.this, MyRobot.class);
            startActivity(i);
            finish();
        }
    }

    public void Menu_MyStoryButtonClicked(View v) throws Exception {
        if(UserCheck == false)
            Please_setEmotion();
        else {
        //    Toast.makeText(getApplicationContext(), "MyStory", Toast.LENGTH_LONG).show();
            Intent i = new Intent(Menu.this, MyStory.class);
            startActivity(i);
            finish();
        }
    }

    @Override
    public void onBackPressed()
    {
        bp.onBackPressed();
    }


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


        if(Json.MenuJsonParse(res) == false)
        {
            Log.e(TAG, "오늘 감정을 입력하지 않음.");
            UserCheck = false;
        }
        else{
            Log.e(TAG, "이미 감정이 존재.");
            UserCheck = true;
            Log.e(TAG, "dno: "+JsonKey_Details.dno+"  eno : "+JsonKey_Details.eno);
          //  Toast.makeText(getApplicationContext(), "오늘의 감정은?" + MyEmotion.emotion[JsonKey_Details.dno] + " 입니다.", Toast.LENGTH_LONG).show();
        }
    }
}

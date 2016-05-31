package rnb.myemotionforme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONObject;

import rnb.myemotionforme.Events.BackPressButtonActivity;
import rnb.myemotionforme.Page.Menu;
import rnb.myemotionforme.Page.SignUp;
import rnb.myemotionforme.key.Key;

public class Login extends FragmentActivity {

    private BackPressButtonActivity bp;
    private static final String TAG = "DEBUG";
    private String res = "test";
    private ProgressBar spinner;
    //HTTPUtil http = new HTTPUtil();
    JsonParse Json = new JsonParse();
    private EditText email;
    private EditText passwd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        bp = new BackPressButtonActivity(this);

        spinner = (ProgressBar) findViewById(R.id.pb_login);
        spinner.setVisibility(View.GONE);

        email = (EditText)findViewById(R.id.et_email_login);
        passwd = (EditText)findViewById(R.id.et_passwd_login);

    }

    public void Login_OkButtonClicked(View v) throws Exception {

        String email_id = email.getText().toString();
        String password = passwd.getText().toString();

        email_id = email_id.trim();
        password = password.trim();

        if(email_id.getBytes().length <= 0 || password.getBytes().length <= 0){//빈값이 넘어올때의 처리
            Toast.makeText(Login.this, "ID 또는 비밀번호를 다시 입력해주세요.", Toast.LENGTH_SHORT).show();
        }
        else {
            JSONObject obj = new JSONObject();
            obj.put("uemail", email_id);
            obj.put("upasswd", password);
            Log.e(TAG, "json : " + obj.toString());//json 객체 확인

            HttpTask task = new HttpTask("/login.php", obj.toString());
            String res = task.execute().get(); //결과값을 받음
            Log.e(TAG, "result : " + res);//결과 객체 확인


            //Json 결과 파서
            if (Json.StatusJsonParse(res)) {
                // Json.getUserInfo(res);
                Key.user_email = email_id;
                Key.user_passwd = password;
                Toast.makeText(getApplicationContext(), "환영합니다.", Toast.LENGTH_LONG).show();
                //spinner.setVisibility(View.INVISIBLE);
                Intent i = new Intent(Login.this, Menu.class);
                startActivity(i);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "ID 또는 비밀번호를 다시 입력해주세요.", Toast.LENGTH_LONG).show();
                //spinner.setVisibility(View.INVISIBLE);
            }
        }

    }

    public void Login_SignUpButtonClicked(View v) {
        Toast.makeText(getApplicationContext(), " 회원 가입", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(Login.this, SignUp.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed()
    {
        bp.onBackPressed();
    }


}

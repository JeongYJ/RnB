package rnb.myemotionforme.Page;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import rnb.myemotionforme.Events.BackPressButtonActivity;
import rnb.myemotionforme.HttpTask;
import rnb.myemotionforme.JsonParse;
import rnb.myemotionforme.Login;
import rnb.myemotionforme.R;

public class SignUp extends FragmentActivity {

    private BackPressButtonActivity bp;
    private static final String TAG = "DEBUG";
   // HTTPUtil httpUtil = new HTTPUtil();
    JsonParse json= new JsonParse();
    JSONObject obj;
    String res;
    //private ProgressBar spinner;

    EditText email;
    EditText name;
    EditText password;
    EditText confirmPasswd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sign_up);

        bp = new BackPressButtonActivity(this);

        email = (EditText) findViewById(R.id.et_email_signup);
        name = (EditText) findViewById(R.id.et_name_signup);
        //EditText birth = (EditText) findViewById(R.id.et_birth);
        password = (EditText) findViewById(R.id.et_passwd_signup);
        confirmPasswd = (EditText) findViewById(R.id.et_confirmPW_signup);

       // spinner = (ProgressBar) findViewById(R.id.pb_signup);
       // spinner.setVisibility(View.GONE);

    }

    public void Signup_OkButtonClicked(View v) throws Exception {


        String passwd = password.getText().toString();
        String confirmPW = confirmPasswd.getText().toString();
        String uemail = email.getText().toString();
        String uname = name.getText().toString();

        if(passwd.getBytes().length <= 0 || confirmPW.getBytes().length <= 0 || uemail.getBytes().length <= 0 || uname.getBytes().length <= 0){//빈값이 넘어올때의 처리
            Toast.makeText(SignUp.this, "값을 입력하세요.", Toast.LENGTH_SHORT).show();
        }
        else {
            if (passwd.length() > 7 && passwd.length() < 15) {
                password.setTextColor(Color.BLACK);
                confirmPasswd.setTextColor(Color.BLACK);
                if (passwd.equals(confirmPW)) {
                    //비밀번호 확인 하기
                    confirmPasswd.setTextColor(Color.BLACK);

                    //객체 변수명 DB명 과 맞춰주33333
                    obj = new JSONObject();
                    obj.put("uemail", uemail);
                    obj.put("upasswd", passwd);
                    obj.put("uname", uname);
                    Log.e(TAG, "json : " + obj.toString());//json 객체 확인

                    //서버로 보냄 파라미터 : "url"동적으로 변화되는 경로, "jsonObject"서버로 보내질 객체
                    HttpTask task = new HttpTask("/join.php", obj.toString());
                    res = task.execute().get(); //결과값을 받음
                    Log.e(TAG, "result : " + res);//결과 객체 확인

                   // Log.e("TEST", "obj String : " + obj.toString());

                   // spinner.setVisibility(View.VISIBLE);

                    if (json.StatusJsonParse(res)) {
                        Toast.makeText(getApplicationContext(), uname + "님 회원가입되셨습니다.", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(SignUp.this, Login.class);
                        startActivity(i);
                        //finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "회원가입실패", Toast.LENGTH_LONG).show();
                    }

                    //spinner.setVisibility(View.GONE);

                } else {
                    confirmPasswd.setTextColor(Color.RED);
                    Toast.makeText(getApplicationContext(), "비밀번호가 다릅니다. 다시 입력해주세요.", Toast.LENGTH_LONG).show();
                }
            } else {
                password.setTextColor(Color.RED);
                Toast.makeText(getApplicationContext(), "비밀번호를 다시 입력해주세요.", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void Signup_CancelButtonClicked(View v){
        Toast.makeText(getApplicationContext(), "로그인 화면으로 돌아갑니다.", Toast.LENGTH_LONG).show();
        Intent i = new Intent(SignUp.this, Login.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onBackPressed()
    {
        bp.onBackPressed();
    }


}

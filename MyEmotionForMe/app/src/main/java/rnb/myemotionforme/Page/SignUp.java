package rnb.myemotionforme.Page;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONObject;

import rnb.myemotionforme.R;
import rnb.myemotionforme.*;

public class SignUp extends FragmentActivity {
    HTTPUtil httpUtil = new HTTPUtil();
    JsonParse json= new JsonParse();
    JSONObject obj;
    String res;
    private ProgressBar spinner;

    EditText email;
    EditText name;
    EditText password;
    EditText confirmPasswd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sign_up);

        email = (EditText) findViewById(R.id.et_email);
        name = (EditText) findViewById(R.id.et_name);
        //EditText birth = (EditText) findViewById(R.id.et_birth);
        password = (EditText) findViewById(R.id.et_password);
        confirmPasswd = (EditText) findViewById(R.id.et_confirmPasswd);

        spinner = (ProgressBar) findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);
    }



    public void OkButtonClicked(View v) throws Exception {

        String passwd = password.getText().toString();
        String confirmPW = confirmPasswd.getText().toString();

        if(passwd.length() > 7 && passwd.length() < 15){
            password.setTextColor(Color.BLACK);
            confirmPasswd.setTextColor(Color.BLACK);
            if (passwd.equals(confirmPW)) {
                //비밀번호 확인 하기
                confirmPasswd.setTextColor(Color.BLACK);
                obj = new JSONObject();

                obj.put("email", email.getText().toString());
                obj.put("name", name.getText().toString());
                //obj.put("birth",birth.getText().toString());
                obj.put("password", passwd);

                /*
                //test용
                obj.put("user_id", email.getText().toString());
                obj.put("gender", 1);
                obj.put("phone", "010-0000-0000");
                obj.put("verify", "verified");
                */

                Log.e("TEST", "obj String : " + obj.toString());

                spinner.setVisibility(View.VISIBLE);

                Thread thread = new Thread() {
                    public void run() {
                        res = httpUtil.signUp(obj.toString());
                    }
                };

                thread.start();
                thread.join();
                spinner.setVisibility(View.GONE);

                if (json.StatusJsonParse(res)) {
                    Toast.makeText(getApplicationContext(), "회원가입되셨습니다.", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(SignUp.this, Login.class);
                    startActivity(i);
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(), "회원가입실패", Toast.LENGTH_LONG).show();
                }
            }
            else
            {
                confirmPasswd.setTextColor(Color.RED);
                Toast.makeText(getApplicationContext(), "비밀번호가 다릅니다. 다시 입력해주세요.", Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            password.setTextColor(Color.RED);
            Toast.makeText(getApplicationContext(), "비밀번호를 다시 입력해주세요.", Toast.LENGTH_LONG).show();
        }
    }

    public void MatrixTime(int delayTime){
        long saveTime = System.currentTimeMillis();
        long currTime = 0;
        while( currTime - saveTime < delayTime){
            currTime = System.currentTimeMillis();
        }
    }

    public void CancelButtonClicked(View v){
        finish();
    }
}

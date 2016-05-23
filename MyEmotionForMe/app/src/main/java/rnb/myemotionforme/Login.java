package rnb.myemotionforme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import rnb.myemotionforme.Page.SignUp;

public class Login extends FragmentActivity {

    private static final String TAG = "DEBUG";
    String res = "test";
    private ProgressBar spinner;
 //   HTTPUtil http = new HTTPUtil();
  //  JsonParse Json = new JsonParse();
    EditText email;
    EditText passwd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        spinner = (ProgressBar) findViewById(R.id.pb_login);
        spinner.setVisibility(View.GONE);

        email = (EditText)findViewById(R.id.et_email_login);
        passwd = (EditText)findViewById(R.id.et_passwd_login);

    }

    public void Login_OkButtonClicked(View v) throws Exception {

        Intent intent = new Intent(getApplicationContext(), Menu.class);
        startActivity(intent);
        Toast.makeText(getApplicationContext(), "로그인되었습니다.", Toast.LENGTH_LONG).show();

        /*
        spinner.setVisibility(View.VISIBLE);
        Thread thread = new Thread() {
            public void run() {
                EditText user_email = (EditText) findViewById(R.id.user_Email);
                EditText password = (EditText) findViewById(R.id.password);
                res = http.signin(user_email.getText().toString(), password.getText().toString());
            }
        };
        thread.start();
        thread.join();

        spinner.setVisibility(View.GONE);
        Log.e(TAG, "result : " + res);
        if (Json.StatusJsonParse(res)) {
            Json.getUserInfo(res);
            Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_LONG).show();
            Intent i = new Intent(Login.this, Menu.class);
            startActivity(i);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_LONG).show();
        }
    }
    */
    }

    public void Login_SignUpButtonClicked(View v) {
        Intent intent = new Intent(getApplicationContext(), SignUp.class);
        startActivity(intent);
        Toast.makeText(getApplicationContext(), " 회원 가입", Toast.LENGTH_LONG).show();

    }
}

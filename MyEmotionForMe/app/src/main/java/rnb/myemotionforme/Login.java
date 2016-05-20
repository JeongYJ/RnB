package rnb.myemotionforme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import rnb.myemotionforme.Events.*;
import rnb.myemotionforme.Page.*;
public class Login extends AppCompatActivity {

    private static final String TAG = "DEBUG";
    String res = "test";
    private ProgressBar spinner;
    HTTPUtil http = new HTTPUtil();
    JsonParse Json = new JsonParse();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        startActivity(new Intent(this, Splash.class));

        spinner = (ProgressBar) findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);

    }

    public void LoginButtonClicked(View v) throws Exception {

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

    public void SignUpButtonClicked(View v) {
        Intent intent = new Intent(getApplicationContext(), SignUp.class);
        startActivity(intent);
        Toast.makeText(getApplicationContext(), " 회원 가입", Toast.LENGTH_LONG).show();
    }
}

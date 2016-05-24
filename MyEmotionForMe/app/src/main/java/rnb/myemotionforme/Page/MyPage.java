package rnb.myemotionforme.Page;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Toast;

import rnb.myemotionforme.R;

/**
 * Created by yj on 16. 5. 24..
 */
public class MyPage extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);
        getSupportActionBar().setTitle("MyPage");
    }

    public void MyPage_ChangePWButtonClicked(View v) throws Exception {
        Toast.makeText(getApplicationContext(), "비밀번호를 변경합니다.", Toast.LENGTH_LONG).show();
        Intent i = new Intent(MyPage.this, MyPage_ChangePW.class);
        startActivity(i);
        finish();
    }


}

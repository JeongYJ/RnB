package rnb.myemotionforme.Page;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import rnb.myemotionforme.Events.BackPressButtonActivity;
import rnb.myemotionforme.R;

/**
 * Created by yj on 16. 5. 24..
 */

//데이터베이스와 연동해서 유저 정보 비밀번호 파트 업데이트 시켜주기.
//다시 로그인 화면으로 돌아가기(?)

public class MyPage_ChangePW extends ActionBarActivity {
    private BackPressButtonActivity bp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage_changepw);
        getSupportActionBar().setTitle("MyPage");
        bp = new BackPressButtonActivity(this);
    }

    @Override
    public void onBackPressed()
    {
        bp.onBackPressed();
    }


}

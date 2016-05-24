package rnb.myemotionforme.Page;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import rnb.myemotionforme.Events.BackPressButtonActivity;
import rnb.myemotionforme.R;

/**
 * Created by yj on 16. 5. 20..
 */
public class Menu extends FragmentActivity {

    private BackPressButtonActivity bp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_menu);
        bp = new BackPressButtonActivity(this);
    }

    public void Menu_MyPageButtonClicked(View v) throws Exception {
        Toast.makeText(getApplicationContext(), "MyPage", Toast.LENGTH_LONG).show();
        Intent i = new Intent(Menu.this, MyPage.class);
        startActivity(i);
    }

    public void Menu_MyEmotionButtonClicked(View v) throws Exception {
        Toast.makeText(getApplicationContext(), "MyEmotion", Toast.LENGTH_LONG).show();
        Intent i = new Intent(Menu.this, MyEmotion.class);
        startActivity(i);
        finish();
    }

    public void Menu_MyMusicButtonClicked(View v) throws Exception {
        Toast.makeText(getApplicationContext(), "MyMusic", Toast.LENGTH_LONG).show();
            Intent i = new Intent(Menu.this, MyMusic.class);
           startActivity(i);
           finish();
    }

    public void Menu_MyRobotButtonClicked(View v) throws Exception {
        Toast.makeText(getApplicationContext(), "MyRobot", Toast.LENGTH_LONG).show();
            Intent i = new Intent(Menu.this, MyRobot.class);
           startActivity(i);
           finish();
    }

    public void Menu_MyStoryButtonClicked(View v) throws Exception {
        Toast.makeText(getApplicationContext(), "MyStory", Toast.LENGTH_LONG).show();
        Intent i = new Intent(Menu.this, MyStory.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onBackPressed()
    {
        bp.onBackPressed();
    }

}

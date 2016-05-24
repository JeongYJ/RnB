package rnb.myemotionforme.Page;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import rnb.myemotionforme.R;

/**
 * Created by yj on 16. 5. 24..
 */
public class MyEmotion extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myemotion);
        getSupportActionBar().setTitle("MyEmotion");

    }

}

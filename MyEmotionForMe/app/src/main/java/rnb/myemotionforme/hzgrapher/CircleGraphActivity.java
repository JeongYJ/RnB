package rnb.myemotionforme.hzgrapher;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import rnb.myemotionforme.HttpTask;
import rnb.myemotionforme.JsonParse;
import rnb.myemotionforme.Page.MyEmotion;
import rnb.myemotionforme.R;
import rnb.myemotionforme.com.handstudio.android.hzgrapherlib.animation.GraphAnimation;
import rnb.myemotionforme.com.handstudio.android.hzgrapherlib.graphview.CircleGraphView;
import rnb.myemotionforme.com.handstudio.android.hzgrapherlib.vo.GraphNameBox;
import rnb.myemotionforme.com.handstudio.android.hzgrapherlib.vo.circlegraph.CircleGraph;
import rnb.myemotionforme.com.handstudio.android.hzgrapherlib.vo.circlegraph.CircleGraphVO;
import rnb.myemotionforme.key.JsonKey_User;

public class CircleGraphActivity extends ActionBarActivity {
	private static final String TAG = "Graph 출력";
	private ViewGroup layoutGraphView;
	String colorArr[] = {"#f35932","#ff9454","#eed762","#f4bb01","#2a8635","#00cc66","#52c6b6","#248ca6","#82d1e9",
			"#4c7bbf","#7d96cc","#8286cc","#886699","#761ea2","#993833","#ff3f6b","#ffa8d8","#ff6464"};
	//rdno-1 = index number
	public int check_rdno[] = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_emotion_chart);
		getSupportActionBar().setTitle("나의 감정 그래프");
		layoutGraphView = (ViewGroup) findViewById(R.id.layoutGraphView);

		try {
			graph_setting();
		} catch (Exception e) {
			e.printStackTrace();
		}
			setCircleGraph();
	}

		public void graph_setting() throws Exception{


			JSONObject obj = new JSONObject();
			obj.put("uemail", JsonKey_User.uemail);
			Log.e(TAG, "json : " + obj.toString());//결과 객체 확인

			HttpTask task = new HttpTask("/RnB/getUserState_graph.php", obj.toString());
			String res = task.execute().get(); //결과값을 받음
			Log.e(TAG, "result : " + res);//결과 객체 확인


			JsonParse json = new JsonParse();
			json.makeJsonObject(res);
			if(!json.getJsonState()) {
				Toast.makeText(CircleGraphActivity.this, "헉.. 데이터를 제대로 불러오지 못했어요ㅠㅠ 다시 접속해주시겠어요?", Toast.LENGTH_SHORT).show();
				return;
			}
			int size = json.getJsonArraySize();

			for(int i=0; i<size; i++) {
				String title = (String) json.getJsonArrayData(i,"rdno");
				check_rdno[(Integer.parseInt(title))-1] += 1;
			}
	}


	@Override
	public void onBackPressed() {
		Intent i = new Intent(CircleGraphActivity.this, MyEmotion.class);
		startActivity(i);
		finish();
		super.onBackPressed();
	}

	private void setCircleGraph() {
		
		CircleGraphVO vo = makeLineGraphAllSetting();
		
		layoutGraphView.addView(new CircleGraphView(this,vo));
	}
	
	/**
	 * make line graph using options
	 * @return
	 */
	private CircleGraphVO makeLineGraphAllSetting() {
		//BASIC LAYOUT SETTING
		//padding
		int paddingBottom 	= CircleGraphVO.DEFAULT_PADDING;
		int paddingTop 		= CircleGraphVO.DEFAULT_PADDING;
		int paddingLeft 	= CircleGraphVO.DEFAULT_PADDING;
		int paddingRight 	= CircleGraphVO.DEFAULT_PADDING;

		//graph margin
		int marginTop 		= CircleGraphVO.DEFAULT_MARGIN_TOP;
		int marginRight 	= CircleGraphVO.DEFAULT_MARGIN_RIGHT;

		// radius setting
		int radius = 130;

		List<CircleGraph> arrGraph 	= new ArrayList<CircleGraph>();


		//arrGraph.add(new CircleGraph(MyEmotion.emotion[2],Color.parseColor(colorArr[2]),1));
		try {
			for(int i=0; i<18; i++)
			{
				if(check_rdno[i] == 0)
					continue;
				else
					arrGraph.add(new CircleGraph(MyEmotion.emotion[i],Color.parseColor(colorArr[i]),check_rdno[i]));
			}
		}
		catch(Exception ex)
		{
			Toast.makeText(CircleGraphActivity.this, "헉.. 데이터를 제대로 불러오지 못했어요ㅠㅠ 다시 접속해주시겠어요?", Toast.LENGTH_SHORT).show();
			arrGraph.add(new CircleGraph(MyEmotion.emotion[1],Color.parseColor(colorArr[1]),1));
		}


		CircleGraphVO vo = new CircleGraphVO(paddingBottom, paddingTop, paddingLeft, paddingRight,marginTop, marginRight,radius, arrGraph);
		
		// circle Line 
		vo.setLineColor(Color.WHITE);
		
		// set text setting
		vo.setTextColor(Color.WHITE);
		vo.setTextSize(25);
		
		// set circle center move X ,Y
		vo.setCenterX(0);
		vo.setCenterY(0);
		
		//set animation
		vo.setAnimation(new GraphAnimation(GraphAnimation.LINEAR_ANIMATION, 2000));
		//set graph name box
		
		GraphNameBox graphNameBox = new GraphNameBox();
		
		// nameBox 
		graphNameBox.setNameboxMarginTop(25);
		graphNameBox.setNameboxMarginRight(25);
		
		vo.setGraphNameBox(graphNameBox);

		return vo;
	}
	
}

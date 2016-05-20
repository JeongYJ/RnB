package rnb.myemotionforme.Page;

import org.json.JSONException;
import org.json.JSONObject;


public class JsonParse {

    public JsonParse(){}

    public boolean StatusJsonParse(String data){
        boolean UserCheck = false;
        try {
            JSONObject  jsonRootObject = new JSONObject(data);
            UserCheck =jsonRootObject.optBoolean("status");
            return UserCheck;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return UserCheck;
    }

    public void getUserInfo(String res){
        try {
            JSONObject  jsonRootObject = new JSONObject(res);
            JSONObject data =jsonRootObject.optJSONObject("data");
            UserInfo userInfo = UserInfo.getInstance();
            String user_id= data.optString("user_id");
            String password= data.optString("password");
            String name= data.optString("name");
            //String birth= data.optString("birth");
            userInfo.setUserData(user_id, password,name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

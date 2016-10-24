package rnb.myemotionforme;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import rnb.myemotionforme.key.JsonKey_Details;
import rnb.myemotionforme.key.JsonKey_User;


public class JsonParse {

    public JsonParse(){}

    JSONObject json = null;
    JSONArray jsonArray = null;


    public boolean StatusJsonParse(String data){
        boolean status = false;
        if(data==null)
            return false;
        try {
            JSONObject  jsonRootObject = new JSONObject(data);
            status =jsonRootObject.optBoolean("UserCheck");
            JsonKey_User.uno = jsonRootObject.optInt("uno");
            return status;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return status;
    }


    public boolean UserCheckJsonParse(String data){
        boolean status = false;
        if(data==null)
            return false;
        try {
            JSONObject  jsonRootObject = new JSONObject(data);
            status =jsonRootObject.optBoolean("UserCheck");
            return status;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return status;
    }


    public boolean UserStateParse(String data)
    {
        boolean status = false;
        if(data == null)
            return false;
        try{
            JSONObject jsonRootObject = new JSONObject(data);
            JsonKey_Details.dno = jsonRootObject.optInt(JsonKey_Details.TAG_dno);
            JsonKey_Details.deno = jsonRootObject.optInt(JsonKey_Details.TAG_deno);
            JsonKey_Details.dred = jsonRootObject.optInt(JsonKey_Details.TAG_dred);
            JsonKey_Details.dgreen = jsonRootObject.optInt(JsonKey_Details.TAG_dgreen);
            JsonKey_Details.dblue = jsonRootObject.optInt(JsonKey_Details.TAG_dblue);
            JsonKey_Details.diconpath = jsonRootObject.optString(JsonKey_Details.TAG_diconpath);
            status = jsonRootObject.optBoolean("UserCheck");
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
        return status;
    }

    public void getUserInfo(String res){
        try {
            JSONObject  jsonRootObject = new JSONObject(res);
            JSONObject data =jsonRootObject.optJSONObject("data");
            UserInfo userInfo = UserInfo.getInstance();
            String user_id= data.optString("user_id");
            String password= data.optString("password");
            String name= data.optString("name");
            userInfo.setUserData(user_id, password, name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void makeJsonObject(String data) throws JSONException{
        boolean status = false;
        if(data==null) return;
        json = new JSONObject(data);
        jsonArray = json.optJSONArray("Data");
    }
    public int getJsonArraySize(){
        return jsonArray.length();
    }
    public Object getJsonArrayData(int idx, String opt)throws JSONException{
        if(jsonArray==null) return null;
        JSONObject tmp = jsonArray.getJSONObject(idx);
        return tmp.opt(opt);
    }
    public boolean getJsonState(){
        if(json==null) return false;
        return json.optBoolean("UserCheck");
    }


}

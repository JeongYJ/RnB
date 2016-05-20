package rnb.myemotionforme.Page;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;


/**
 * Created by kimminyoung on 2016-05-13.
 */
public class HTTPUtil  extends  Thread{
    private static final String TAG ="DEBUG" ;
    OkHttpClient client = new OkHttpClient();
    Request request;
    Response response;



    final String base_url  = "http://115.68.182.138:22";


    public HTTPUtil(){ }

    public String  signin(String user_id,String password ){
        try {
            RequestBody query = new FormEncodingBuilder()
                    .add("user_id", user_id)
                    .add("password", password)
                    .build();
            request = new Request.Builder()
                    .url(base_url + "/api/user/signin")
                    .post(query)
                    .build();
            response = client.newCall(request).execute();

            return  response.body().string();

        }catch (Exception e){
            e.printStackTrace();
        }finally{
        }
        return null;
    }

    public String  signUp(String jsonString){
        try {
            RequestBody query =RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString);
            request = new Request.Builder()
                    .url(base_url + "/api/user/signin")
                    .post(query)
                    .build();
            response = client.newCall(request).execute();
            return  response.body().string();
        }catch (Exception e){
            e.printStackTrace();
        }finally{
        }
        return null;
    }


}

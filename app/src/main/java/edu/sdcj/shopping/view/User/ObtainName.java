package edu.sdcj.shopping.view.User;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by liangshan on 2017/11/7.
 */

public class ObtainName {
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private String username;
    private Context context;

    public ObtainName(Context context) {
        this.context = context;
        sp=context.getSharedPreferences("UserInfo",LoginActivity.MODE_PRIVATE);
        //生成UserInfo文件
        editor = sp.edit();//成功后将用户信息存入文件，失败后不登录
    }

    public String getUsernameBysp(){
        //从SharedPreferences中获取登录信息
        username=sp.getString("username",null);
        return username;
    }

    public void clean(){
        editor.clear();
        editor.commit();
    }
}

package edu.sdcj.shopping.view.User;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import edu.sdcj.shopping.dao.UserDao;

public class LoginActivity extends AppCompatActivity {
    private TextView tv_register,text;
    private EditText userEditText;
    private EditText passEditText;
    private CheckBox jizhumima;
    private Button loginButton;
    private String username, password;
    private SharedPreferences spf;
    private SharedPreferences.Editor editor;
    private  UserDao userDao;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userEditText=(EditText)findViewById(R.id.login_username);//获取用户名输入框组件
        passEditText=(EditText)findViewById(R.id.login_password);//获取密码输入框组件
        loginButton=(Button)findViewById(R.id.login);//获取登陆按钮组件
        tv_register=(TextView)findViewById(R.id.tv_register);
        jizhumima=(CheckBox)findViewById(R.id.jizhumima);
        userDao=new UserDao(LoginActivity.this);
        text=new TextView(LoginActivity.this);
        spf=getSharedPreferences("UserInfo",LoginActivity.MODE_PRIVATE);
        //生成UserInfo文件
        editor=spf.edit();//成功后将用户信息存入文件，失败后不登录

        if(spf.getBoolean("auto",true)){
            userEditText.setText(spf.getString("username", null));
            passEditText.setText(spf.getString("password", null));
            jizhumima.setChecked(true);
        }
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                username=userEditText.getText().toString().trim();//获取输入的用户名
                 password=passEditText.getText().toString().trim();//获取输入的密码


                text.setTextColor(Color.RED);
                if("".equals(username)||username==null){//验证输入的用户名是否为空
                    text.setText("用户名不能为空!!");
                    Toast toast=new Toast(LoginActivity.this);
                    toast.setGravity(Gravity.CENTER,0,50);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(text);
                    toast.show();
                }else if("".equals(password)|| password==null){
                    text.setText("密码不能为空!!");
                    Toast toast=new Toast(LoginActivity.this);
                    toast.setGravity(Gravity.CENTER,0,50);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(text);
                    toast.show();
                }else if(username.length()>20 ||username.length()<=2){
                    text.setText("用户名长度必须大于2小于等于20!！");
                    Toast toast=new Toast(LoginActivity.this);
                    toast.setGravity(Gravity.CENTER,0,50);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(text);
                    toast.show();
                }else if(password.length()>20||password.length()<6){
                    text.setText("密码长度必须大于等于6小于20!！");
                    Toast toast=new Toast(LoginActivity.this);
                    toast.setGravity(Gravity.CENTER,0,50);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(text);
                    toast.show();
                }else{
                    //跟表中的数据做验证，如果是表中的用户，就登陆成功，如果不是，则给用户友好的提示
                    boolean flag=userDao.queryLogin(username,password);
                    if(flag){
                        editor.putString("username", username);
                        editor.putString("password" , password);
                        if(jizhumima.isChecked()){
                        editor.putBoolean("auto",true);
                           }else {
                            editor.putBoolean("auto",false);
                        }
                        editor.commit();
                        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();//退出LoginActivity界面(销毁LoginActivity)
                    }else{
                        text.setText("用户名或密码错误，重新输入!！");
                        Toast toast=new Toast(LoginActivity.this);
                        toast.setGravity(Gravity.CENTER,0,50);
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.setView(text);
                        toast.show();
                    }
                }


            }
        });
    }
}

package edu.sdcj.shopping.view.User;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import edu.sdcj.shopping.dao.UserDao;

public class RegisterActivity extends AppCompatActivity {
    private EditText userName,passWord ,passWord2,Age,telePhone,Address;
    private Button reg;
    private String username,password,password2,sex,telephone,address;
    private  int age;
    private UserDao userDao;
    private RadioGroup sexRadioGrouo;
    RadioButton FeMale,Male;
    private ImageView iv_return;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //一、获取所有组件的文本内容
        //二、创建UserDao对象、

        //三、调用insert()
       // userDao.insert();
        userName=(EditText)findViewById(R.id.et_username);
        passWord=(EditText)findViewById(R.id.et_password);
        passWord2=(EditText)findViewById(R.id.et_conpassword);
        Age=(EditText)findViewById(R.id.et_age);
        telePhone=(EditText)findViewById(R.id.et_phone);
        Address=(EditText)findViewById(R.id.et_address);
        sexRadioGrouo=(RadioGroup)findViewById(R.id.sex);
        Male=(RadioButton)findViewById(R.id.male);
        FeMale=(RadioButton)findViewById(R.id.fmale);
        reg=(Button)findViewById(R.id.register);
        iv_return=(ImageView)findViewById(R.id.register_back);
        iv_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
//        sexRadioGrouo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int i) {
//                RadioButton r=(RadioButton)findViewById(i);
//                sex=r.getText().toString();
//            }
//        });
        userDao=new UserDao(this);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username=userName.getText().toString();
                password=passWord.getText().toString();
                password2=passWord2.getText().toString();
                age=Integer.parseInt(Age.getText().toString());
                if(Male.isChecked()){
                    sex=Male.getText().toString();
                }
                if( FeMale.isChecked()){
                    sex=FeMale.getText().toString();
                }

                address=Address.getText().toString();
                telephone=telePhone.getText().toString();
                judge();
            }
        });




    }
    public void judge(){
        boolean isSgin=userDao.queryReg(username);
        if(username==null||username.length()<=0){
            Toast.makeText(RegisterActivity.this,"用户名不能为空！",Toast.LENGTH_SHORT).show();

        }else if(username.length()<=5){
            Toast.makeText(RegisterActivity.this,"用户名太短！",Toast.LENGTH_SHORT).show();
        }else if(username.length()>=20){
            Toast.makeText(RegisterActivity.this,"用户名太长！请重新输入！",Toast.LENGTH_SHORT).show();
        }else if(!isSgin){
            Toast.makeText(RegisterActivity.this,"用户名已经存在，请重新输入！",Toast.LENGTH_SHORT).show();
        }else if(password==null||password.length()<=0){
            Toast.makeText(RegisterActivity.this,"密码不能为空！",Toast.LENGTH_SHORT).show();
        }else if (password.length()<=5){
            Toast.makeText(RegisterActivity.this,"密码太短！请重新输入！",Toast.LENGTH_SHORT).show();
        }else if(passWord.length()>=20){
            Toast.makeText(RegisterActivity.this,"密码太长！请重新输入！",Toast.LENGTH_SHORT).show();
        }else if(!(password.equals(password2))){
            Toast.makeText(RegisterActivity.this,"两次密码不一致！",Toast.LENGTH_SHORT).show();
        }else if(address==null||address.length()<=0){
            Toast.makeText(RegisterActivity.this,"地址为空，请重新输入!",Toast.LENGTH_SHORT).show();
        }else if(telephone==null||telephone.length()==0){
            Toast.makeText(RegisterActivity.this,"手机号不能为空，请重新输入！",Toast.LENGTH_SHORT).show();
        }else if(!telephone.matches("[1][3578][0-9]{9}$")){
            Toast.makeText(RegisterActivity.this,"手机号格式不正确，请重新输入！",Toast.LENGTH_SHORT).show();
        }else if(age<0||age>150){
            Toast.makeText(RegisterActivity.this,"年龄有误，请重新输入！",Toast.LENGTH_SHORT).show();
        }

        else{
            userDao.insert(username,password,sex,age,telephone,address);
            Toast.makeText(RegisterActivity.this,"注册成功！",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();

        }



    }
}

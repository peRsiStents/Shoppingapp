package edu.sdcj.shopping.view.User;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import edu.sdcj.shopping.dao.UserDao;
import edu.sdcj.shopping.entity.User;

/**
 * Created by liangshan on 2017/11/7.
 */

public class MyInfoActivity extends AppCompatActivity {
    private TextView textName,textPass,textSex,textAge,textPhone,textAAdress,backMain;
    private Button updateBt;
    private ImageView imageback;
    private UserDao userDao;
    private User user=null;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfo);
        textPhone=(TextView)findViewById(R.id.teleinfo) ;
        textName=(TextView)findViewById(R.id.nameinfo);
        textPass=(TextView)findViewById(R.id.passinfo);
        textSex=(TextView)findViewById(R.id.sexinfo);
        textAAdress=(TextView)findViewById(R.id.adressinfo);
        textAge=(TextView)findViewById(R.id.ageinfo);
        backMain=(TextView)findViewById(R.id.backmain);
        updateBt=(Button)findViewById(R.id.button_update);
        imageback=(ImageView)findViewById(R.id.return_back);
//        Intent  传数据
        Intent intent=getIntent();
        username=intent.getStringExtra("username");
//        Toast.makeText(MyInfoActive.this,"" ,Toast.LENGTH_SHORT).show();

        userDao=new UserDao(this);
        user=userDao.query(username);

        textName.setText(user.getUsername());
        textPass.setText(user.getPassword());

        textSex.setText(user.getSex());
        textAge.setText(String.valueOf(user.getAge()));
        textPhone.setText(user.getPhone());
        textAAdress.setText(user.getAddress());
        //修改用户信息
        updateBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MyInfoActivity.this,UpdateUserActivity.class);
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });
        //返回我的页面
        imageback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(MyInfoActivity.this,MyselfActivity.class);
                intent1.putExtra("username",username);
                startActivity(intent1);
            }
        });
        //返回主页
        backMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MyInfoActivity.this,MainActivity.class);
                intent.putExtra("username",username);
                startActivity(intent);

            }
        });
    }
}

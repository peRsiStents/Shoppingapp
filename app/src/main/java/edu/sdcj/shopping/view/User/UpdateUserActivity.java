package edu.sdcj.shopping.view.User;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import edu.sdcj.shopping.dao.UserDao;

/**
 * Created by liangshan on 2017/11/7.
 */

public class UpdateUserActivity extends AppCompatActivity {
    private EditText newName, newPassword1, newPassword2, newAge, newPhone, newAddress;
    private Button confirmBt, checkBt;
    private ImageView backImage;
    private UserDao userDao;
    private RadioGroup sexRadioGro;
    private RadioButton Male, Female;
    String newname, newpass1, newpass2, newsex, newphone, newaddress, oldname;
    int newage;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateuser);
        init();
//        Intent intent=getIntent();
//        username=intent.getStringExtra("username");
        newName.setText(oldname);
        newname = newName.getText().toString();
        confirmBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newname = newName.getText().toString();
                newpass1 = newPassword1.getText().toString();
                newpass2 = newPassword2.getText().toString();
                newage = Integer.parseInt(newAge.getText().toString());
                newphone = newPhone.getText().toString();
                newaddress = newAddress.getText().toString();
                if (Male.isChecked()) {
                    newsex = Male.getText().toString();
                }
                if (Female.isChecked()) {
                    newsex = Female.getText().toString();
                }
                judge();
            }
        });
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(UpdateUserActivity.this,MyInfoActivity.class);
                intent.putExtra("username",newname);
                startActivity(intent);
            }
        });
        checkBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(UpdateUserActivity.this,MyInfoActivity.class);
                intent.putExtra("username",newname);
                startActivity(intent);

            }
        });


    }

    private void init() {
        newName = (EditText) findViewById(R.id.udname);
        newPassword1 = (EditText) findViewById(R.id.udpass1);
        newPassword2 = (EditText) findViewById(R.id.uppass2);
        newAge = (EditText) findViewById(R.id.udage);
        newPhone = (EditText) findViewById(R.id.udphone);
        newAddress = (EditText) findViewById(R.id.udaddress);
        confirmBt = (Button) findViewById(R.id.surebt);
        checkBt = (Button) findViewById(R.id.querrybt);
        backImage = (ImageView) findViewById(R.id.back);
        sexRadioGro = (RadioGroup) findViewById(R.id.udsexgroup);
        Male = (RadioButton) findViewById(R.id.udmale);
        Female = (RadioButton) findViewById(R.id.udfemale);
        userDao = new UserDao(this);

        Intent intent = getIntent();
        oldname = intent.getStringExtra("username");


    }

    public void judge() {
        Boolean flag;
        if(!newname.equals(oldname)){
            flag=userDao.queryReg(newname);
        }else{
            flag=true;}


        if (newname == null || newname.length() <= 0) {
            Toast.makeText(UpdateUserActivity.this, "用户名不能为空！", Toast.LENGTH_SHORT).show();

        } else if (newname.length() <= 5) {
            Toast.makeText(UpdateUserActivity.this, "用户名太短！", Toast.LENGTH_SHORT).show();
        } else if (newname.length() >= 20) {
            Toast.makeText(UpdateUserActivity.this, "用户名太长！请重新输入！", Toast.LENGTH_SHORT).show();
        }else if(!flag){
            Toast.makeText(UpdateUserActivity.this,"用户名已经存在，请重新输入！",Toast.LENGTH_SHORT).show();
        } else if (newpass1 == null || newpass1.length() <= 0) {
            Toast.makeText(UpdateUserActivity.this, "密码不能为空！", Toast.LENGTH_SHORT).show();
        } else if (newpass1.length() <= 5) {
            Toast.makeText(UpdateUserActivity.this, "密码太短！请重新输入！", Toast.LENGTH_SHORT).show();
        } else if (newpass1.length() >= 20) {
            Toast.makeText(UpdateUserActivity.this, "密码太长！请重新输入！", Toast.LENGTH_SHORT).show();
        } else if (!(newpass1.equals(newpass2))) {
            Toast.makeText(UpdateUserActivity.this, "两次密码不一致！", Toast.LENGTH_SHORT).show();
        } else if (newaddress == null || newaddress.length() <= 0) {
            Toast.makeText(UpdateUserActivity.this, "地址为空，请重新输入!", Toast.LENGTH_SHORT).show();
        } else if (newphone == null || newphone.length() == 0) {
            Toast.makeText(UpdateUserActivity.this, "手机号不能为空，请重新输入！", Toast.LENGTH_SHORT).show();
        } else if (!newphone.matches("[1][3578][0-9]{9}$")) {
            Toast.makeText(UpdateUserActivity.this, "手机号格式不正确，请重新输入！", Toast.LENGTH_SHORT).show();
        } else if (newage < 0 || newage > 150) {
            Toast.makeText(UpdateUserActivity.this, "年龄有误，请重新输入！", Toast.LENGTH_SHORT).show();
        } else {

            userDao.update(oldname,newname, newpass1, newsex, newage, newphone, newaddress);
            Toast.makeText(UpdateUserActivity.this, "修改成功！", Toast.LENGTH_SHORT).show();
        }

    }
}

package edu.sdcj.shopping.view.User;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import edu.sdcj.shopping.view.goods.GoodsRecently;
import edu.sdcj.shopping.view.order.OrderActivity;
import edu.sdcj.shopping.view.setting.AboutActivity;
import edu.sdcj.shopping.view.shoppingcar.ShoppingCar;
import edu.sdcj.shopping.view.weather.WeatherActivity;

/**
 * Created by liangshan on 2017/11/7.
 */

public class MyselfActivity extends AppCompatActivity {
    private ImageView imageHome,imageWeather,imageShopCar,imageMyself,iv_setting;
    private TextView textLogin,textInfo,textFoot,textOrder,textUserName,tv_calculate ;
    private ObtainName obtainName;
    private String username;
    private LinearLayout lin1,lin2,lin3,lin4;
//    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myself);
        init();
        obtainName=new ObtainName(this);
        username=obtainName.getUsernameBysp();
        if(username!=null){
            textUserName.setText(username);
            textLogin.setText("退出登录");
            lin1.setVisibility(View.VISIBLE);
            lin2.setVisibility(View.VISIBLE);
            lin3.setVisibility(View.VISIBLE);
            lin4.setVisibility(View.VISIBLE);

        }

        textLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text=textLogin.getText().toString();
                if (text.equals("登录")){
                    Intent intent=new Intent(MyselfActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    obtainName.clean();
                    textUserName.setText("未登录");
                    textLogin.setText("登录");
                    lin1.setVisibility(View.GONE);
                    lin2.setVisibility(View.GONE);
                    lin3.setVisibility(View.GONE);
                    lin4.setVisibility(View.GONE);

                }
            }
        });

        //进入设置界面
        iv_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MyselfActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });
        //点击头像进入我的信息
        textUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!textUserName.getText().toString().equals("未登录")){
                Intent intent=new Intent(MyselfActivity.this,MyInfoActivity.class);
                intent.putExtra("username",username);
                startActivity(intent);
                }
            }
        });
        //"进入我的信息"
        textInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MyselfActivity.this,MyInfoActivity.class);
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });






        //进入“我的订单”
        textOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MyselfActivity.this,OrderActivity.class);
                startActivity(intent);
            }
        });



        //进入“我的足迹”
        textFoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MyselfActivity.this,GoodsRecently.class);

                startActivity(intent);
            }
        });





        imageMyself.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyselfActivity.this,MyselfActivity.class);
                startActivity(intent);
            }
        });



        //进入天气界面
        imageWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent=new Intent(MyselfActivity.this,WeatherActivity.class);
                startActivity(intent);
            }
        });

        //进入购物车界面
        imageShopCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MyselfActivity.this,ShoppingCar.class);
                startActivity(intent);
            }
        });

        //返回首页
        imageHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyselfActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        //计算
        tv_calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MyselfActivity.this,CalculatorActivity.class);
                startActivity(intent);
            }
        });
    }
    private  void init(){
        imageHome=(ImageView)findViewById(R.id.ivhome);
        imageMyself=(ImageView)findViewById(R.id.ivmyself);
        imageShopCar=(ImageView)findViewById(R.id.ivshoppingCar);
        imageWeather=(ImageView)findViewById(R.id.ivweather);

        textLogin=(TextView)findViewById(R.id.tvlogin);
        tv_calculate=(TextView)findViewById(R.id.caculation);
        textFoot=(TextView)findViewById(R.id.tvmyfootprint);
        textInfo=(TextView)findViewById(R.id.tvmyinfo);
        textOrder=(TextView)findViewById(R.id.tvmyorder);
        textUserName=(TextView)findViewById(R.id.tv_username);
        lin1=(LinearLayout)findViewById(R.id.linear1);
        lin2=(LinearLayout)findViewById(R.id.linear2);
        lin3=(LinearLayout)findViewById(R.id.linear3);
        lin4=(LinearLayout)findViewById(R.id.linear4);
        iv_setting=(ImageView)findViewById(R.id.profile_setting);
        tv_calculate=(TextView)findViewById(R.id.caculation);

    }
}

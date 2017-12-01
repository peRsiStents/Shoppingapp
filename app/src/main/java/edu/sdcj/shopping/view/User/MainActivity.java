package edu.sdcj.shopping.view.User;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




import android.os.Bundle;

import edu.sdcj.shopping.dao.GoodsDao;
import edu.sdcj.shopping.view.goods.GoodsDetailActivity;
import edu.sdcj.shopping.view.goods.GoodsListActivity;
import edu.sdcj.shopping.view.goods.GoodsSearch;
import edu.sdcj.shopping.view.shoppingcar.ShoppingCar;
import edu.sdcj.shopping.view.weather.WeatherActivity;

/**
 * Created by liangshan on 2017/10/11.
 */

public class MainActivity extends Activity {
    private AutoCompleteTextView etitSearch;
    private ImageView ivSearch,ivHome,ivShopingCar,ivMyself,ivWeather;
    private GridView gridView;
    private SimpleAdapter simpleAdapter;
    private int[] pic ={R.drawable.pic1,R.drawable.pic2,R.drawable.pic3,R.drawable.pic4,R.drawable.pic5,R.drawable.pic6};
    private List<Map<String,Object>> list;
    private GoodsDao goodsDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainlayout);

        etitSearch=(AutoCompleteTextView) findViewById(R.id.etsearch);
        ivSearch=(ImageView)findViewById(R.id.ivsearch);
        ivHome=(ImageView)findViewById(R.id.ivhome);
        ivWeather=(ImageView)findViewById(R.id.ivweather);
        ivMyself=(ImageView)findViewById(R.id.ivmyself);
        ivShopingCar=(ImageView)findViewById(R.id.ivshoppingCar);
        gridView=(GridView)findViewById(R.id.gridView);
        goodsDao=new GoodsDao(this);
        String [] COUNTRIES=goodsDao.getGoodsNameArray();
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,COUNTRIES);
        etitSearch.setAdapter(adapter);
        list=new ArrayList<Map<String, Object>>();
        for(int i=0;i<6;i++){
            Map<String,Object> map=new HashMap<>();
            map.put("picture",pic[i]);
            list.add(map);
        }
        simpleAdapter=new SimpleAdapter(MainActivity.this,list,R.layout.gridviewimage,new String[]{"picture"},new int[]{R.id.iv_main_gridView_pic} );
        gridView.setAdapter(simpleAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (i==0) {
                    Toast.makeText(MainActivity.this, "进入食品区", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(MainActivity.this, GoodsListActivity.class);
                            intent.putExtra("type","食品区");
                            startActivity(intent);
                } else if (i==1) {
                    Toast.makeText(MainActivity.this, "进入妇婴区", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, GoodsListActivity.class);
                    intent.putExtra("type","妇婴区");
                    startActivity(intent);
                }else if ( i== 2) {
                    Toast.makeText(MainActivity.this, "进入护肤品区", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, GoodsListActivity.class);
                    intent.putExtra("type","护肤品");
                    startActivity(intent);
                }else if ( i== 3) {
                    Toast.makeText(MainActivity.this, "进入纺织区", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, GoodsListActivity.class);
                    intent.putExtra("type","纺织区");
                    startActivity(intent);
                }else if (i== 4) {
                    Toast.makeText(MainActivity.this, "进入零食区", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, GoodsListActivity.class);
                    intent.putExtra("type","零食区");
                    startActivity(intent);

                }else if(i==5){
                    Toast.makeText(MainActivity.this, "进入日用品区", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(MainActivity.this, GoodsListActivity.class);
                            intent.putExtra("type","日用品");
                            startActivity(intent);

                }
            }
        });

        //点击进入我的账户
        ivMyself.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,MyselfActivity.class);
                startActivity(intent);
            }
        });
        //点击进入购物车
        ivShopingCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,ShoppingCar.class);
                startActivity(intent);

            }
        });
        //点击进入天气
        ivWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,WeatherActivity.class);
                startActivity(intent);
            }
        });
        //点击进入主页面
        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,MainActivity.class);
                startActivity(intent);

            }
        });
        //点击搜索
        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String goodsname=etitSearch.getText().toString();
                Intent intent=new Intent(MainActivity.this, GoodsSearch.class);
                intent.putExtra("goodsname",goodsname);
                startActivity(intent);

            }
        });
    }

}


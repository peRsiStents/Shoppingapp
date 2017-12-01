package edu.sdcj.shopping.view.goods;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;

import java.util.List;

import edu.sdcj.shopping.dao.GoodsDao;
import edu.sdcj.shopping.dao.RecentlyGoodsDao;
import edu.sdcj.shopping.entity.Goods;
import edu.sdcj.shopping.view.User.LoginActivity;
import edu.sdcj.shopping.view.User.MainActivity;
import edu.sdcj.shopping.view.User.R;

/**
 * Created by liangshan on 2017/11/7.
 */

public class GoodsListActivity extends AppCompatActivity {
    private ListView listView;
    private ImageView return_back, iv_search;
    private MultiAutoCompleteTextView et_search;
    private GoodsAdapter goodsAdapter;
    private List<Goods> list;
    private Intent intent;
    private Goods goods;
    private SharedPreferences spf;
    private String username;
    private GoodsDao goodsDao;
    private RecentlyGoodsDao recentlyGoodsDao;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goodslist);
        spf=getSharedPreferences("UserInfo", LoginActivity.MODE_PRIVATE);
        username=spf.getString("username",null);//获取用户名
        goodsDao=new GoodsDao(GoodsListActivity.this);

        return_back=(ImageView)findViewById(R.id.return_back);
        iv_search=(ImageView)findViewById(R.id.iv_search);
        et_search=(MultiAutoCompleteTextView) findViewById(R.id.et_search);
        listView=(ListView)findViewById(R.id.lv_goodslist);
        //所有商品名 的数组
        String[] strings=goodsDao.getGoodsNameArray();
        //设置自动完成框
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,strings);
        et_search.setAdapter(arrayAdapter);
        et_search.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        intent=getIntent();
        String type=intent.getStringExtra("type");//获取商品类型
        //显示相应 的类型
        if(type.equals("食品区")){
            list=goodsDao.queryGoodsByType("食品区");
            goodsAdapter=new GoodsAdapter(list,this);
            listView.setAdapter(goodsAdapter);
            listViewListener (listView);//调用此方法，设置监听  。减少代码的重复
//            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                }
//            });
        }else if(type.equals("妇婴区")){
            list=goodsDao.queryGoodsByType("妇婴区");
            goodsAdapter=new GoodsAdapter(list,this);
            listView.setAdapter(goodsAdapter);
            listViewListener (listView);//调用此方法，设置监听  。减少代码的重复
//            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                }
//            });
        }else if(type.equals("护肤品")){
            list=goodsDao.queryGoodsByType("护肤品");
            goodsAdapter=new GoodsAdapter(list,this);
            listView.setAdapter(goodsAdapter);
            listViewListener (listView);
//            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                }
//            });
        }else if(type.equals("纺织品")){
            list=goodsDao.queryGoodsByType("纺织品");
            goodsAdapter=new GoodsAdapter(list,this);
            listView.setAdapter(goodsAdapter);
            listViewListener (listView);//调用此方法，设置监听  。减少代码的重复
//            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                }
//            });
        }else if(type.equals("零食区")){
            list=goodsDao.queryGoodsByType("零食区");
            goodsAdapter=new GoodsAdapter(list,this);
            listView.setAdapter(goodsAdapter);
            listViewListener (listView);//调用此方法，设置监听  。减少代码的重复
//            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                }
//            });
        }else if(type.equals("日用品")){
            list=goodsDao.queryGoodsByType("日用品");
            goodsAdapter=new GoodsAdapter(list,this);
            listView.setAdapter(goodsAdapter);
            listViewListener (listView);//调用此方法，设置监听  。减少代码的重复
//            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                    goods =list.get(i);
//                    initRrecentlyData(username,goods.getGoodsId(),goods.getGoodsBuyCount(),goods.getGoodsPhoto(),goods.getGoodsName(),goods.getGoodsPrice(),goods.getGoodsType(),goods.getGoodsIntroduce(),goods.getGoodsCostPrice(),goods.getGoodsdelivery(),1);
//                    intent=new Intent(GoodsListActivity.this,GoodsDetailActivity.class);
//                    int goodsid=goods.getGoodsId();
//                    intent.putExtra("goodsid",goodsid);
//                    startActivity(intent);
//
//                }
//            });
        }

        //返回上一页
        return_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(GoodsListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        //查询商品
        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String goodsname=et_search.getText().toString();
                Intent intent=new Intent(GoodsListActivity.this, GoodsSearch.class);
                intent.putExtra("goodsname",goodsname);//传递商品名
                startActivity(intent);
            }
        });

    }
       //将浏览信息插入数据库
        private void initRrecentlyData(String username, int goodsId,int goodsBuyCount, String goodsPhoto, String goodsName, double goodsPrice, String goodsType, String goodsIntroduce, double goodsCostPrice,String goodsdelivery,int prioritycount){
            RecentlyGoodsDao recentlyGoodsDao=new RecentlyGoodsDao(GoodsListActivity.this);
        recentlyGoodsDao.insertRecentlyGoods(username, goodsId, goodsBuyCount, goodsPhoto,  goodsName,  goodsPrice,  goodsType,  goodsIntroduce, goodsCostPrice, goodsdelivery, prioritycount);
        }


    //给listview设置监听    减少代码量
        private  void  listViewListener(ListView listView){
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    recentlyGoodsDao=new RecentlyGoodsDao(GoodsListActivity.this);
                    goods =list.get(i);
                    int goodsid=goods.getGoodsId();
                    //判断之前是不是浏览过次产品
                    boolean flag=recentlyGoodsDao.queryGoods(goodsid);
//                    Log.i("d",flag+"");
                        intent=new Intent(GoodsListActivity.this,GoodsDetailActivity.class);
                    intent.putExtra("goodsid",goodsid);
                    startActivity(intent);
                    /*判断足迹里有没有此商品，若有增加浏览次数
                     若足迹里没有此商品，则加入数据库
                    */
                    if(flag){
//                        Log.i("dd",flag+"");
                        recentlyGoodsDao.updateCount(goodsid);
                    }else{
                        initRrecentlyData(username, goods.getGoodsId(), goods.getGoodsBuyCount(), goods.getGoodsPhoto(), goods.getGoodsName(), goods.getGoodsPrice(), goods.getGoodsType(), goods.getGoodsIntroduce(), goods.getGoodsCostPrice(), goods.getGoodsdelivery(), 1);
                    }

                }
            });

        }
    }


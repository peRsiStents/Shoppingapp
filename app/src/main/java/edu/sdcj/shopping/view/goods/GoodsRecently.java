package edu.sdcj.shopping.view.goods;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import edu.sdcj.shopping.dao.RecentlyGoodsDao;
import edu.sdcj.shopping.entity.Goods;
import edu.sdcj.shopping.entity.RecentlyGoods;
import edu.sdcj.shopping.view.User.LoginActivity;
import edu.sdcj.shopping.view.User.MyInfoActivity;
import edu.sdcj.shopping.view.User.MyselfActivity;
import edu.sdcj.shopping.view.User.R;
import edu.sdcj.shopping.view.User.StartActivity;

/**
 * Created by liangshan on 2017/11/8.
 */

public class GoodsRecently extends AppCompatActivity {


    private SharedPreferences spf;
    private String username;
    private ListView listView;
    private ImageView return_back;
    private Button bt_edit;
    private LinearLayout ll_goodsRencently;
    private ImageView imageView;
    private List<RecentlyGoods> list;
    private GoodsRecentlyAdapter goodsRecentlyAdapter;
    private boolean flag = false;
    private RecentlyGoodsDao recentlyGoodsDao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myfoot);
        init();
        recentlyGoodsDao=new RecentlyGoodsDao(this);
        list=recentlyGoodsDao.searchRecentlyGoods(username);

        if(list.size()==0) {
            imageView.setVisibility(View.VISIBLE);
            ll_goodsRencently.setVisibility(View.GONE);
            bt_edit.setVisibility(View.GONE);
        }else {
            goodsRecentlyAdapter=new GoodsRecentlyAdapter(this,list);
            listView.setAdapter(goodsRecentlyAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent=new Intent(GoodsRecently.this,GoodsDetailActivity.class);
                    int goodsId=list.get(i).getGoodsId();
                    intent.putExtra("goodsid",goodsId);
                    startActivity(intent);
                }
            });
            bt_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    flag=!flag;
                    if(flag){
                        bt_edit.setText("完成");
                        goodsRecentlyAdapter.isShow(false);
                    }else{
                        bt_edit.setText("编辑");
                        goodsRecentlyAdapter.isShow(true);
                    }
                }
            });



        }
        return_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(GoodsRecently.this,MyselfActivity.class);
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });

    }

    private void init(){
        listView=(ListView) findViewById(R.id.lv_goodsrecently);
        bt_edit=(Button) findViewById(R.id.bt_edit);
        imageView=(ImageView) findViewById(R.id.iv_hint);
        return_back=(ImageView) findViewById(R.id.return_backmyinfo);
        ll_goodsRencently=(LinearLayout) findViewById(R.id.ll_goodsRencently);
        list=new ArrayList<>();

        spf=getSharedPreferences("UserInfo", LoginActivity.MODE_PRIVATE);//生成UserInfo文件
        username = spf.getString("username", null);
}
}

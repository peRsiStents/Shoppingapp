package edu.sdcj.shopping.view.goods;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.List;

import edu.sdcj.shopping.dao.GoodsDao;
import edu.sdcj.shopping.entity.Goods;
import edu.sdcj.shopping.view.User.R;

/**
 * Created by liangshan on 2017/11/8.
 */

public class GoodsSearch  extends Activity{
    private ListView lv_searchList;
    private ImageView iv_hint;
    private Intent intent;
    private Goods goods;
    private String goodsname;
    private List<Goods> list;
    private GoodsAdapter goodsAdapter;
    private ImageView iv_search;
    private EditText et_search;
    private GoodsDao goodsDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        iv_hint=(ImageView) findViewById(R.id.iv_hint);
        lv_searchList=(ListView) findViewById(R.id.lv_goodssearch);
        et_search=(EditText) findViewById(R.id.et_search);
        iv_search=(ImageView) findViewById(R.id.iv_search);

        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_search=(EditText) findViewById(R.id.et_search);
                iv_search=(ImageView) findViewById(R.id.iv_search);
                goodsname=et_search.getText().toString();
                initData();

            }
        });
        //主页查询
        goodsDao=new GoodsDao(GoodsSearch.this);
        intent=getIntent();
        goodsname=intent.getStringExtra("goodsname");
        initData();
    }
        private void initData(){
            list=goodsDao.queryGoodsByName(goodsname);
            if(list.size()>0){
                goodsAdapter=new GoodsAdapter(list,GoodsSearch.this);
                lv_searchList.setAdapter(goodsAdapter);
                lv_searchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        intent=new Intent(GoodsSearch.this,GoodsDetailActivity.class);
                        int goodsId=list.get(i).getGoodsId();
                        intent.putExtra("goodsid",goodsId);
                        startActivity(intent);
                    }
                });

            }else{
                lv_searchList.setVisibility(View.GONE);
                iv_hint.setVisibility(View.VISIBLE);
            }


    }
}

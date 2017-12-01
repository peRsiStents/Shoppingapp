package edu.sdcj.shopping.view.goods;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import edu.sdcj.shopping.dao.GoodsDao;
import edu.sdcj.shopping.dao.MessageDao;
import edu.sdcj.shopping.dao.OrderDao;
import edu.sdcj.shopping.dao.ShoppingCarDao;
import edu.sdcj.shopping.dao.UserDao;
import edu.sdcj.shopping.entity.Goods;
import edu.sdcj.shopping.entity.Message;
import edu.sdcj.shopping.view.User.LoginActivity;
import edu.sdcj.shopping.view.User.R;
import edu.sdcj.shopping.view.order.OrderActivity;
import edu.sdcj.shopping.view.shoppingcar.ShoppingCar;

/**
 * Created by liangshan on 2017/11/8.
 */

public class GoodsDetailActivity extends AppCompatActivity {
    private ImageView imageView,return_back;
    private TextView goodsname,price,type,introduce,num,costprice,goodsdelivery,buycount;
    private Button jia,jian;//加减按钮
    private TextView bt_buy,bt_addshoppingcar;// 立即购买，加入购物车
    private Intent intent;
    private int count=1;//默认初始为1
    private Goods goods;
    private ListView messagelist;//评价链表
    private SharedPreferences spf;
    private String username;//用户 名
    private int userid;
    private GoodsDao goodsDao;
   private OrderDao orderDao;
    private UserDao userDao;
    private ShoppingCarDao shoppingCarDao;
    private int max;
    private int id;
    private MessageDao messageDao;//操作评价的数据库
    private  MessageAdapter messageAdapter;
    private List<Message> list;//评价链表

    BitmapFactory.Options decodingOptions = new BitmapFactory.Options();
    DisplayImageOptions m_options =
            new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.default_image)
                    .decodingOptions(decodingOptions)
                    .imageScaleType(ImageScaleType.NONE)
                    .build();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goodsdetial);
        init();//初始化
        intent = getIntent();
        id = intent.getIntExtra("goodsid", 0);//获取goodsid
        list= messageDao.getMessage(id);//获取商品的全部评论
        messageAdapter=new MessageAdapter(this,list);
        messagelist.setAdapter(messageAdapter);
        goodsDao=new GoodsDao(this);//用来查询商品的信息
        goods=goodsDao.queryGoodsById(id);//获取商品信息
        //图片加载不出来
        // com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(goods.getPhoto(),imageView);
        ImageLoader.getInstance().displayImage(goods.getGoodsPhoto(), imageView, m_options);//加载图片
        goodsname.setText(goods.getGoodsName());//设置商品名
        price.setText("￥"+goods.getGoodsPrice()+"");//设置商品价格
        goodsdelivery.setText(goods.getGoodsdelivery());//邮费信息
        buycount.setText("销量："+goods.getGoodsBuyCount()+"");//显示销量
        costprice.setText("￥"+goods.getGoodsCostPrice()+"");//设置原价
        costprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);//中间横线
        type.setText(goods.getGoodsType());
        introduce.setText(goods.getGoodsIntroduce());
        //返回商品列表     需要传回商品类型，否则会出错
        return_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(GoodsDetailActivity.this,GoodsListActivity.class);
                intent.putExtra("type",goods.getGoodsType());
                startActivity(intent);
            }
        });

        //点击“+”
        jia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = Integer.parseInt(num.getText().toString().trim());
                jian.setBackgroundResource(R.drawable.button_blue);
                num.setText(String.valueOf(++count));

            }
        });

        //点击“-”
        jian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = Integer.parseInt(num.getText().toString().trim());
                //判断数字是不是大于等于1
                if (count > 1) {
                    num.setText(String.valueOf(--count));
                    jian.setBackgroundResource(R.drawable.button_blue);
                }else{
                    jian.setBackgroundResource(R.color.hui);
                }
            }
        });
        //点击立刻购买
        bt_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //若用户名为空，则跳到登陆界面
                if (username == null || username.equals("")) {
                    intent = new Intent(GoodsDetailActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    //若登陆了，则向订单数据库里出入订单信息
                    userid=userDao.query(username).getUserid();
                    orderDao=new OrderDao(GoodsDetailActivity.this);
                    orderDao.addOrder(userid, String.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())),goods.getGoodsPrice() * count,username);

                    max = orderDao.getOrderDetailMaxOrderID();//获取最新的订单号
                    //将商品的详情插入数据库
                    orderDao.addOrderDetail(goods.getGoodsId(),max,goods.getGoodsPrice(),count,goods.getGoodsPhoto(),goods.getGoodsCostPrice(),goods.getGoodsName());
                }
                //进入我的订单界面
            Intent intent=new Intent(GoodsDetailActivity.this, OrderActivity.class);
                intent.putExtra("orderid",max);
                startActivity(intent);
            }
        });

        //点击加入购物车
        bt_addshoppingcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //判断是否是在登录状态、若不是则调回登录界面
                if(username==null||username.equals("")){
                    Intent intent=new Intent(GoodsDetailActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    userid=userDao.query(username).getUserid();//获取userid
                }
                /*
                判断购物车里有没有此商品
                若有则直接增加相应的数量
                若没有则加入购物车
                 */
                if(shoppingCarDao.queryGoods(goods.getGoodsId())) {
                    //修改购物车里商品的数量
                    shoppingCarDao.updateCount(goods.getGoodsId(),count);
                }else{
                    //将商品添加至购物车
                    shoppingCarDao.addGoods(goods.getGoodsName(), goods.getGoodsPhoto(), goods.getGoodsdelivery(), goods.getGoodsId(), userid, count, goods.getGoodsPrice(), goods.getGoodsCostPrice());
                }
                intent = new Intent(GoodsDetailActivity.this, ShoppingCar.class);//进入商品界面
                startActivity(intent);
            }
        });


    }



    //初始化，获德各个组件
    private  void init(){
            messagelist=(ListView)findViewById(R.id.messagelist);
            costprice=(TextView)findViewById(R.id.goods_CostPrice);
            goodsdelivery=(TextView)findViewById(R.id.goods_deleviy);
            buycount=(TextView)findViewById(R.id.buycounts);
            imageView=(ImageView) findViewById(R.id.iv_goodsdetail);
            goodsname=(TextView) findViewById(R.id.goods_title);
            price=(TextView) findViewById(R.id.goods_Price);
            type=(TextView) findViewById(R.id.goods_type);
            introduce=(TextView) findViewById(R.id.goods_introduce);
            num=(TextView) findViewById(R.id.num);
            jia=(Button) findViewById(R.id.jia);
            jian=(Button) findViewById(R.id.jian);
            bt_buy=(TextView) findViewById(R.id.bt_buy);
            bt_addshoppingcar=(TextView) findViewById(R.id.bt_addshoppingcar);
            return_back=(ImageView) findViewById(R.id.return_back);


            spf = getSharedPreferences("UserInfo", LoginActivity.MODE_PRIVATE);
            username = spf.getString("username", null);
            userDao=new UserDao(this);
            shoppingCarDao=new ShoppingCarDao(GoodsDetailActivity.this);
            messageDao=new MessageDao(GoodsDetailActivity.this);



    }
}

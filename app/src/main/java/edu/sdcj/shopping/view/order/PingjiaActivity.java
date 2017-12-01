package edu.sdcj.shopping.view.order;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import edu.sdcj.shopping.dao.MessageDao;
import edu.sdcj.shopping.dao.OrderDao;
import edu.sdcj.shopping.entity.Order;
import edu.sdcj.shopping.view.User.R;

/**
 * Created by liangshan on 2017/11/20.
 */

public class PingjiaActivity extends Activity {
    private TextView textView;
    private Button submit;//提交按钮
    private OrderDao orderDao;
    private Intent intent;
    private Order order;
    private MessageDao messageDao;//用来插入数据
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pingjia);
        textView=(TextView)findViewById(R.id.editText);//获取editText组件
        submit=(Button)findViewById(R.id.fabupingia);//获取提交按钮
        imageView=(ImageView)findViewById(R.id.return_back);//获取返回按钮
        intent=getIntent();
        int orderid=intent.getIntExtra("orderid",1000);//获取orderiid
        final int goodsid=intent.getIntExtra("goodsid",1000);//获取goodsid
        orderDao=new OrderDao(this);
        messageDao=new MessageDao(this);
        order=orderDao.getOrders(orderid);//通过orderid获取
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message=textView.getText().toString();//获取用户评价内容
                //判断message是不是为空。若不为空则插入数据库
                if(message==null||message.equals("")){
                    Toast.makeText(PingjiaActivity.this,"评价不能为空",Toast.LENGTH_SHORT);
                }else{
                    messageDao.addMessage(order.getUserName(),order.getUserId(),goodsid,message,String.valueOf(new SimpleDateFormat("yyyy-MM-dd ").format(new Date())));
                    Toast.makeText(PingjiaActivity.this,"发布成功",Toast.LENGTH_SHORT);
                }
            }
        });
        //返回上一页
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}

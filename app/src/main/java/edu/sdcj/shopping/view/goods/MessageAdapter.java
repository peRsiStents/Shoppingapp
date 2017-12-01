package edu.sdcj.shopping.view.goods;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import edu.sdcj.shopping.entity.Message;
import edu.sdcj.shopping.view.User.R;

/**
 * Created by liangshan on 2017/11/20.
 */

public class MessageAdapter extends BaseAdapter {
    private Context context;//上下文
    private List<Message> list;
    //构造方法
    public MessageAdapter(Context context,List<Message> list){
        this.context=context;
        this.list=list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
       ViewHolder viewHolder=null;
        if(view==null){
            viewHolder=new ViewHolder();
            //获取各个组件
            view= LayoutInflater.from(context).inflate(R.layout.activity_messageitem,null);
            viewHolder.tv_username=(TextView)view.findViewById(R.id.tv_user);
            viewHolder.tv_date=(TextView) view.findViewById(R.id.tv_date);
            viewHolder.tv_message=(TextView)view.findViewById(R.id.tv_message);
            view.setTag(viewHolder);

        }else{
            viewHolder=(ViewHolder)view.getTag();
        }
        viewHolder.tv_username.setText("用户："+list.get(position).getUsername());//设用户名
        viewHolder.tv_date.setText(list.get(position).getMessagedate());//设评价日期
        viewHolder.tv_message.setText(list.get(position).getMessagecontent());//评价内容

        return view;
    }
    /*
    内部类

     */
    private class ViewHolder{
        TextView tv_username,tv_date,tv_message;


}}

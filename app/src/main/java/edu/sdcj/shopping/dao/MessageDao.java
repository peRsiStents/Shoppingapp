package edu.sdcj.shopping.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import edu.sdcj.shopping.dbutil.DBOpenHelper;
import edu.sdcj.shopping.entity.Message;

import static android.R.id.list;

/**
 * Created by liangshan on 2017/11/20.
 */

public class MessageDao {
    private DBOpenHelper dbOpenHelper;
    private SQLiteDatabase messagedb;
    //构造 方法
    public MessageDao(Context context) {
        dbOpenHelper=new DBOpenHelper(context);
        messagedb=dbOpenHelper.getWritableDatabase();
    }
    //添加评论
    public void addMessage(String username,int userid,int goodsid,String messagecontent,String messagedate){
        messagedb.execSQL("insert into "+dbOpenHelper.TABLE_NAME_message +" (userid,username,goodsid,messagecontent,messagedate) values ('"+userid+"','"+username+"','"+goodsid+"','"+messagecontent+"','"+messagedate+"')");

    }
    //获取商品的所有评论
    public List<Message> getMessage(int goodsid){
        List<Message> result=new LinkedList<Message>();
        Cursor c =messagedb.query(dbOpenHelper.TABLE_NAME_message,null,"goodsid=?",new String[]{goodsid+""},null,null,null,null);
        if(c!=null){
            while(c.moveToNext()){
                int messageid=c.getInt(c.getColumnIndex("messageid"));
//                int goodsid=c.getInt(c.getColumnIndex("goodsid"));
                String username=c.getString(c.getColumnIndex("username"));//用户名
                int userid=c.getInt(c.getColumnIndex("userid"));//用户 id
                String  messagecontent=c.getString(c.getColumnIndex("messagecontent"));//评价内容
                String  messagedate=c.getString(c.getColumnIndex("messagedate"));//评价日期
                Message message1=new Message(messageid,userid,username,goodsid,messagedate,messagecontent);//实例化一个Message对象
                result.add(message1);//添加到链表
            }
            return result;//返回商品的所有评论
        }
        return null;
    }

}

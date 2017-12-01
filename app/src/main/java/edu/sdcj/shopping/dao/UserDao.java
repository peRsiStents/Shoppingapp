package edu.sdcj.shopping.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import edu.sdcj.shopping.dbutil.DBOpenHelper;
import edu.sdcj.shopping.entity.User;
import edu.sdcj.shopping.view.User.R;

public class UserDao  {
    private DBOpenHelper dbHelp_user;
    private User user;
    SQLiteDatabase usrIofo;

    public UserDao(Context context){
        dbHelp_user=new DBOpenHelper(context);
        usrIofo=dbHelp_user.getWritableDatabase();
    }


    public void insert(String _username,String _password,String _sex,
                       int _age,String _phone,String _address){


        String sql="insert into userInfotb(username,password,sex,age,phone,address)"
                +"values('"+_username+"','"+_password+"','"+_sex+"',"+_age+",'"+_phone+"','"+_address+"')";

        usrIofo.execSQL(sql);

    }

    /*public void insert(User user){

    }*/
    public boolean queryReg(String username){

        //获取游标
        Cursor c=usrIofo.query(dbHelp_user.TABLE_NAME_USER,null,null,null,null,null,null,null);
        if(c!=null){//游标不为空
            while (c.moveToNext()){
                //c.moveToNext();
                String usernames=c.getString(c.getColumnIndex("username"));
                if(username.equals(usernames)){
                    return false;
                }
            }
        }
        return true;
    }
    //登陆验证
    public boolean queryLogin(String name ,String pass) {
        boolean flag = false;
        SQLiteDatabase db = dbHelp_user.getWritableDatabase();
        Cursor cursor = db.query("userInfotb", null, null, null, null, null, null);//select * from userInfotb ;
        while (cursor.moveToNext()) {
            String username = cursor.getString(cursor.getColumnIndex("username"));
            String password = cursor.getString(cursor.getColumnIndex("password"));
            if (username.equals(name) && password.equals(pass)) {//输入的用户名和密码和表中的用户名和密码进行验证
                flag = true;
                cursor.close();
                return flag;
            }
        }
        cursor.close();
        return flag;

    }

    public User query(String username){
        //从表中查询指定name所对应的那个用户
        Cursor c=usrIofo.query(dbHelp_user.TABLE_NAME_USER,null,null,null,null,null,null,null);
        if(c!=null){//游标不为空
            while (!c.isLast()){
                c.moveToNext();
                String usernames=c.getString(c.getColumnIndex("username"));
                if(username.equals(usernames)){
                    int userid=c.getInt(c.getColumnIndex("userid"));
                    String password=c.getString(c.getColumnIndex("password"));
                    String sex=c.getString(c.getColumnIndex("sex"));
                    int age=c.getInt(c.getColumnIndex("age"));
                    String phone=c.getString(c.getColumnIndex("phone"));
                    String address=c.getString(c.getColumnIndex("address"));
                    user=new User(userid, username, password,sex,age,phone,address);
                    return user;
                }
            }
        }
        return user;
    }
    public void update(String username ,String password,String sex,int age,String phone,String address){
        usrIofo.execSQL("update "+dbHelp_user.TABLE_NAME_USER+"set password='"+password+",sex='"+sex+",age='"+age+",phone='"+phone+",address='"+address+" where username='"+username+"'");
    }
    public void update(String oldname,String username ,String password,String sex,int age,String phone,String address){
        usrIofo.execSQL("update "+dbHelp_user.TABLE_NAME_USER+" set password='"+ password +"',sex='"+ sex+"',username='"+ username +"',age='"+ age +"',phone='"+ phone +"',address='"+ address +"' where username='"+oldname +"'");
    }
    public void updatePassword(String username,String newPassword){
        usrIofo.execSQL("update"+dbHelp_user.TABLE_NAME_USER+" set password="+newPassword  +"where username="+username);
    }
}

package edu.sdcj.shopping.entity;

/**
 * Created by liangshan on 2017/11/20.
 */

public class Message {
   /*
   评价日期、评价 内容、用户名、商品id、评价id、用户id.
    */
    private  String messagedate,messagecontent,username;
    private int goodsid,messageid,userid;
    //构造方法
    public Message(int messageid, int userid, String username, int goodsid, String messagedate, String messagecontent) {
        this.messagedate = messagedate;
        this.messagecontent = messagecontent;
        this.username = username;
        this.goodsid = goodsid;
        this.messageid = messageid;
        this.userid = userid;
    }

    public String getMessagedate() {
        return messagedate;
    }

    public void setMessagedate(String messagedate) {
        this.messagedate = messagedate;
    }

    public String getMessagecontent() {
        return messagecontent;
    }

    public void setMessagecontent(String messagecontent) {
        this.messagecontent = messagecontent;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(int goodsid) {
        this.goodsid = goodsid;
    }

    public int getMessageid() {
        return messageid;
    }

    public void setMessageid(int messageid) {
        this.messageid = messageid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}

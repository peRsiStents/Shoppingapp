package edu.sdcj.shopping.entity;

public class User {
    private int userid;//客户编号
    private String username;//客户名
    private String password;//密码
    private String sex;//性别
    private int age;//年龄
    private String phone;//电话
    private String address;//地址


    public User() {

    }

    public User(int userid, String username, String password,
                String sex, int age, String phone, String address) {
        this.userid = userid;
        this.username = username;
        this.password = password;
        this.sex = sex;
        this.age = age;
        this.phone = phone;
        this.address = address;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

//    @Override
//    public String toString() {
//        return "User{" +
//                "userid=" + userid +
//                ", username='" + username + '\'' +
//                ", password='" + password + '\'' +
//                ", sex='" + sex + '\'' +
//                ", age=" + age +
//                ", phone='" + phone + '\'' +
//                ", address='" + address + '\'' +
//                '}';
//    }
}

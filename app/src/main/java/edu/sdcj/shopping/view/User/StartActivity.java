package edu.sdcj.shopping.view.User;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.umeng.analytics.MobclickAgent;

import edu.sdcj.shopping.dao.GoodsDao;

/**
 * Created by liangshan on 2017/11/7.
 */

public class StartActivity extends AppCompatActivity {
    Context context;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_start);
        final View startView = View.inflate(this, R.layout.activity_start, null);
        setContentView(startView);
        MobclickAgent.openActivityDurationTrack(false);
        MobclickAgent.onError(this);
        context = this;
        //渐变

        AlphaAnimation aa = new AlphaAnimation(0.3f, 1.0f);
        aa.setDuration(2000);
        startView.setAnimation(aa);
        aa.setAnimationListener(new Animation.AnimationListener() {

                                    @Override
                                    public void onAnimationStart(Animation animation) {


                                    }

                                    @Override
                                    public void onAnimationRepeat(Animation animation) {


                                    }

                                    @Override
                                    public void onAnimationEnd(Animation animation) {

                                        redirectto();
                                    }
                                }
        );
    }

    private void redirectto() {
        //1.向本地数据库中插入商品信息
        GoodsDao goodsDao=new GoodsDao(StartActivity.this);
//        goodsDao.deleteAll();
        if(goodsDao.isEmpty()){
            goodsDao.insert();
        }
//
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
    }


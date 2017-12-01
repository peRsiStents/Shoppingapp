package edu.sdcj.shopping.view;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by liangshan on 2017/11/9.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoaderConfiguration imageLoaderConfiguration=
                new ImageLoaderConfiguration.Builder(this).build();//初始化设置对象
        ImageLoader.getInstance().init(imageLoaderConfiguration);//获取ImageLoader实例，并初始化


    }
}

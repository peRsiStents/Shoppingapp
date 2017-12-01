package edu.sdcj.shopping.view.weather;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edu.sdcj.shopping.entity.Weather;
import edu.sdcj.shopping.view.User.MainActivity;
import edu.sdcj.shopping.view.User.R;
import okhttp3.Call;

/**
 * Created by liangshan on 2017/11/7.
 */

public class WeatherActivity extends AppCompatActivity {
    private static final String URL ="http://v.juhe.cn/weather/index";
    private String appKey="0af83a2eda5a07abf1f10e0249e9188d";
    private TextView  tv_cityname,tv_temperature,tv_wind,tv_weather,tv_kongqi;
    private EditText et_city;
    private ImageView  iv_back,iv_search;
    private Weather weathers;
    private List<Weather> list;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        tv_cityname=(TextView)findViewById(R.id.tv_cityname);
        tv_temperature=(TextView)findViewById(R.id.tv_wendu);
        tv_wind=(TextView)findViewById(R.id.tv_feng);
        tv_weather=(TextView)findViewById(R.id.tv_weather);
        tv_kongqi=(TextView)findViewById(R.id.tv_kongqi);
        iv_back=(ImageView)findViewById(R.id.iv_back);
        iv_search=(ImageView)findViewById(R.id.iv_search);
        et_city=(EditText)findViewById(R.id.editCity);

        list=new ArrayList<>();

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(WeatherActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cityname=et_city.getText().toString();
                if (cityname!=null&&!cityname.equals("")){
                    weather(cityname);
                    tv_cityname.setText(cityname);
                }
            }
        });






    }

    private void weather(String cityname){

        OkHttpUtils.get()
                .url(URL)
                .addParams("cityname",cityname)
                .addParams("key",appKey)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        Toast.makeText(WeatherActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String s, int i) {
                        try {
                            JSONObject json=new JSONObject(s);
                            String reason=json.getString("reason");

                            if (reason.equals("successed!")) {
                                JSONObject result = json.getJSONObject("result");
                                JSONObject today= result.getJSONObject("today");
//                                JSONObject weather =today.getJSONObject("weather");
                                tv_weather.setText(today.getString("weather"));//当前天气
                                tv_temperature.setText(today.getString("temperature"));//当前温度
                                JSONObject sk = result.getJSONObject("sk");
                                tv_wind.setText(sk.getString("wind_direction") + ":" + sk.getString("wind_strength"));//当前风向风力
//                                JSONObject pm25 = result.getJSONObject("data").getJSONObject("pm25").getJSONObject("pm25");
//                                tv_kongqi.setText(pm25.getString("quality"));//当前空气质量

                                JSONArray nextWeather=result.getJSONObject("data").getJSONArray("weather");
                                for ( i = 0; i <nextWeather.length() ; i++) {
                                    JSONObject wt=nextWeather.getJSONObject(i);
                                    weathers=new Weather();
                                    //第一天跟第二天显示明后天，之后显示周几
                                    switch (i){
                                        case 0:
                                            weathers.setTime("明天");
                                            break;
                                        case 1:
                                            weathers.setTime("后天");
                                            break;
                                        default:
                                            weathers.setTime("周"+wt.getString("week"));
                                            break;
                                    }
                                    //获取天气日期
                                    weathers.setDate(wt.getString("date"));
                                    //获取昼夜天气情况
                                    JSONArray day=wt.getJSONObject("info").getJSONArray("day");
                                    JSONArray night=wt.getJSONObject("info").getJSONArray("night");
                                    if (day.get(1).equals(night.get(1))){
                                        weathers.setWeather(day.get(1)+"");
                                    }else {
                                        weathers.setWeather(day.get(1)+"转"+night.get(1));
                                    }
                                    //获取昼夜温度
                                    weathers.setTemperature(day.get(2)+"℃~"+night.get(2)+"℃");
                                    //获取风向和风力
                                    weathers.setWindDay("白天:"+day.get(3)+":"+day.get(4));
                                    weathers.setWindNight("夜间"+night.get(3)+":"+night.get(4));
                                    list.add(weathers);
                                }

                            }else {
                                Toast.makeText(WeatherActivity.this, "查询失败", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }



                });

    }
}

package edu.sdcj.shopping.view.setting;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import edu.sdcj.shopping.view.User.R;

/**
 * Created by liangshan on 2017/11/13.
 */

public class AboutActivity extends Activity {
    private LinearLayout ll_checkupdate;
    private ImageView return_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guanyu);
        ll_checkupdate=(LinearLayout)findViewById(R.id.ll4);
        return_back=(ImageView)findViewById(R.id.return_back);
        return_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ll_checkupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AboutActivity.this,"已是最新版本",Toast.LENGTH_SHORT).show();
            }
        });
    }
}

package edu.sdcj.shopping.view.shoppingcar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.DecimalFormat;
import java.util.List;

import edu.sdcj.shopping.entity.CarGoods;
import edu.sdcj.shopping.view.Interface.ICheckInterface;
import edu.sdcj.shopping.view.Interface.IModifyCountInterface;
import edu.sdcj.shopping.view.User.R;

/**
 * Created by liangshan on 2017/11/9.
 */

public class CarAdapter extends BaseAdapter {
    private Context context;
    private List<CarGoods> list;//从SQLite数据库中查询购物车中商品
    private CarGoods cargoods;
    private boolean isShow = true;//是否显示编辑/完成
    private ICheckInterface checkInterface;//是否选中
    private IModifyCountInterface modifyCountInterface;

//构造方法
    public CarAdapter (Context context,List<CarGoods> list){
        this.context=context;
        this.list=list;
    }
    /**
     * 单选接口
     * @param checkInterface
     */
    public void setCheckInterface(ICheckInterface checkInterface) {
        this.checkInterface = checkInterface;
    }

    /**
     * 改变商品数量接口
     * @param modifyCountInterface
     */
    public void setModifyCountInterface(IModifyCountInterface modifyCountInterface) {
        this.modifyCountInterface = modifyCountInterface;
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

    /**
     * 是否显示可编辑
     */
    public void isShow(boolean flag) {
        isShow = flag;
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if(view==null){
            viewHolder=new ViewHolder();
            view= LayoutInflater.from(context).inflate(R.layout.activity_caritem,null);
            //获取各个组件
            viewHolder.ll_yunsuanfu=(LinearLayout)view.findViewById(R.id.ll_yunsuanfu);
            viewHolder.ck_chose=(CheckBox)view.findViewById(R.id.ck_chose);//获取多选按钮
            viewHolder.tv_gName=(TextView)view.findViewById(R.id.tv_gName);//获取goof名
            viewHolder.tv_gNum=(TextView)view.findViewById(R.id.tv_gNum);//获取编辑的商品数量
            viewHolder.tv_gPrice=(TextView)view.findViewById(R.id.tv_gPrice);//获取商品价格
            viewHolder.tv_Num=(TextView)view.findViewById(R.id.tv_num);//获取商品数量
            viewHolder.tv1=(TextView)view.findViewById(R.id.tv1);//减
            viewHolder.tv2=(TextView)view.findViewById(R.id.tv2);//加
            viewHolder.iv=(ImageView) view.findViewById(R.id.iv);//获取商品图片
            view.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)view.getTag();
        }

        cargoods=list.get(position);
        ImageLoader.getInstance().displayImage(cargoods.getGoodsPhoto(),viewHolder.iv);//加载图片
        DecimalFormat df=new DecimalFormat("######.00");//给价格设置格式，保留小数点后两位
        viewHolder.tv_gPrice.setText("￥"+  df.format(cargoods.getGoodsQutantity()*cargoods.getGoodsPrice())+"");
        viewHolder.tv_gNum.setText(cargoods.getGoodsQutantity()+"");
        viewHolder.tv_gName.setText(cargoods.getGoodsName());
        viewHolder.tv_Num.setText("X"+cargoods.getGoodsQutantity() + "");
        viewHolder.ck_chose.setChecked(cargoods.getChecked());
        //选择商品，点击多选按钮
        viewHolder.ck_chose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                cargoods.setChecked(((CheckBox) view).isChecked());
//                Toast.makeText(context,position+" "+((CheckBox) view).isChecked()+"a",Toast.LENGTH_SHORT).show();
                checkInterface.checkGroup(position,((CheckBox) view).isChecked());
                Toast.makeText(context,position+" "+((CheckBox) view).isChecked()+"dddd",Toast.LENGTH_SHORT).show();

            }
        });
        //减少商品数量
        viewHolder.tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modifyCountInterface.doDecrease(position,viewHolder.tv_gNum,viewHolder.ck_chose.isChecked());
            }
        });
        //增加商品数量
        viewHolder.tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modifyCountInterface.doIncrease(position,viewHolder.tv_gNum,viewHolder.ck_chose.isChecked());
            }
        });

        //判断是否在编辑状态下
        if (isShow) {
            viewHolder.tv_gName.setVisibility(View.VISIBLE);
            viewHolder.ll_yunsuanfu.setVisibility(View.GONE);
        } else {
            viewHolder.tv_gName.setVisibility(View.VISIBLE);
            viewHolder.ll_yunsuanfu.setVisibility(View.VISIBLE);
        }
        return view;
    }
    //内部类
    class ViewHolder{
        LinearLayout ll_yunsuanfu;
        CheckBox ck_chose;
        TextView tv_gName, tv_gNum, tv_gPrice,tv_Num;
        TextView tv1, tv2;
        ImageView iv;

    }
}



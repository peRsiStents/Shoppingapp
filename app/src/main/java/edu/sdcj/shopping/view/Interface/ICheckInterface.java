package edu.sdcj.shopping.view.Interface;

/**
 * Created by liangshan on 2017/11/12.
 */

public interface ICheckInterface {

    /**
     * 组选框状态改变触发的事件
     *
     * @param position  元素位置
     * @param isChecked 元素选中与否
     */
    void checkGroup(int position, boolean isChecked);
}

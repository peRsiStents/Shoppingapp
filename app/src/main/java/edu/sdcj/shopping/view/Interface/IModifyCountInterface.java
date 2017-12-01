package edu.sdcj.shopping.view.Interface;

import android.view.View;

/**
 * Created by liangshan on 2017/11/12.
 */

public interface IModifyCountInterface {
    /**
     * 增加操作
     *
     * @param position      组元素位置
     * @param showCountView 用于展示变化后数量的View
     * @param isChecked     子元素选中与否
     */
    void doIncrease(int position, View showCountView, boolean isChecked);

    /**
     * 删减操作
     *
     * @param position      组元素位置
     * @param showCountView 用于展示变化后数量的View
     * @param isChecked     子元素选中与否
     */
    void doDecrease(int position, View showCountView, boolean isChecked);
}

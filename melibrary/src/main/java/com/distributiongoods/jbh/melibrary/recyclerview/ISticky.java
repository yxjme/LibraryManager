package com.distributiongoods.jbh.melibrary.recyclerview;

/**
 * Created by zbsdata on 2018/9/15.
 */

public interface ISticky {
    //判断是否为同类别的第一个位置
    boolean isFirstPosition(int pos);
    //获取标题
    String getGroupTitle(int pos);
}

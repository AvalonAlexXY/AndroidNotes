package com.baidu.bdcamlibrary.store.zerobuy.list.view;

import com.baidu.bdcamlibrary.base.BaseView;

import java.util.List;

/**
 * Created by zhe on 2017/5/23.
 * 0元购列表view
 */

public interface ProductListView<T> extends BaseView {

    void setListItem(List<T> data);

}

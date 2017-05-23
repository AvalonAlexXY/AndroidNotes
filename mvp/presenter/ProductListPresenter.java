package com.baidu.bdcamlibrary.store.zerobuy.list.presenter;

import android.content.Context;

import com.alibaba.fastjson.JSONObject;
import com.baidu.bdcamlibrary.base.BasePresenter;
import com.baidu.bdcamlibrary.common.bean.ProductBean;
import com.baidu.bdcamlibrary.common.http.RequestCallback;
import com.baidu.bdcamlibrary.store.StoreApi;
import com.baidu.bdcamlibrary.store.zerobuy.list.view.ProductListView;
import com.baidu.bdcamlibrary.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;


public class ProductListPresenter extends BasePresenter<ProductListView> {


    private List<ProductBean> mData = new ArrayList<>();

    public ProductListPresenter(Context context) {
        StoreApi.zerobuyListProduct(context, getMobileProductListCallback);
    }

    private RequestCallback getMobileProductListCallback = new RequestCallback() {

        @Override
        public void onSuccess(String responseInfo) {
            JSONObject object = JsonUtils.parseObject(responseInfo);
            String products = object.getString("products");
            mData.addAll(JsonUtils.parseArray(products, ProductBean.class));
            mView.setListItem(mData);
        }
    };

}

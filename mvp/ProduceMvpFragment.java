package com.baidu.bdcamlibrary.store.zerobuy.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.andview.refreshview.XRefreshView;
import com.baidu.bdcamlibrary.R;
import com.baidu.bdcamlibrary.base.BaseMvpFragment;
import com.baidu.bdcamlibrary.common.Constants;
import com.baidu.bdcamlibrary.common.commonactivity.FragmentPath;
import com.baidu.bdcamlibrary.common.widgets.Actionbar;
import com.baidu.bdcamlibrary.store.zerobuy.list.presenter.ProductListPresenter;
import com.baidu.bdcamlibrary.store.zerobuy.list.view.ProductListView;

import java.util.List;

@FragmentPath(value = Constants.PRODUCT_LIST)
public class ProduceMvpFragment extends BaseMvpFragment<ProductListView, ProductListPresenter> implements ProductListView {

    private Actionbar    actionbar;
    private XRefreshView mXRefreshView;
    private RecyclerView mRecyclerView;
    private Button       mButton;

    private ProductListAdapter mProductListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.product_list_fragment, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        actionbar = (Actionbar) view.findViewById(R.id.actionbar);
        mXRefreshView = (XRefreshView) view.findViewById(R.id.product_list_fragment_refresh);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.product_list_fragment_recyclerview);
        mButton = (Button) view.findViewById(R.id.product_list_fragment_buy);

        mXRefreshView.setAutoRefresh(true);

        actionbar.setTitle("0元购");
        actionbar.setLeftTitle("返回");
        actionbar.showLeftTitle();

        mProductListAdapter = new ProductListAdapter(getContext());
        mRecyclerView.setAdapter(mProductListAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void setListItem(List data) {
        mProductListAdapter.addData(data);
    }

    @Override
    public ProductListPresenter initPresenter() {
        return new ProductListPresenter(getContext());
    }


}

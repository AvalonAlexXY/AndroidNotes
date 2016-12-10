package com.meishubao.app.common.widgets.recyclerview;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangzhe on 2016/11/18.
 * 通用的Recyclerview adapter
 */

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    public static final int TYPE_FOOTER = 2;

    private List<T> mDatas = new ArrayList<>();

    private View mHeaderView;
    private View mFooterView;

    /**
     * header
     */
    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    /**
     * footer
     */
    public void setFooterView(View footerView) {
        mFooterView = footerView;
        notifyItemInserted(mDatas.size() - 1);
    }

    public View getFooterView() {
        return mFooterView;
    }

    /**
     * 设置数据
     */
    public void addDatas(List<T> datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null && mFooterView != null && position == mDatas.size()) {
            return TYPE_FOOTER;
        }
        if (mHeaderView == null && mFooterView == null) {
            return TYPE_NORMAL;
        }
        if (mHeaderView != null && mFooterView == null && position == 0) {
            return TYPE_HEADER;
        }
        if (mHeaderView != null && mFooterView != null) {
            if (position == 0) {
                return TYPE_HEADER;
            }
            if (position == mDatas.size() + 1) {
                return TYPE_FOOTER;
            }
        }
        return TYPE_NORMAL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER) {
            return new Holder(mHeaderView);
        }
        if (mFooterView != null && viewType == TYPE_FOOTER) {
            return new Holder(mFooterView);
        }
        return onCreate(parent, viewType);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (getItemViewType(position) == TYPE_HEADER || getItemViewType(position) == TYPE_FOOTER) {
            return;
        }
        final int pos = getRealPosition(viewHolder);
        final T data = mDatas.get(pos);
        onBind(viewHolder, pos, data);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (getItemViewType(position) == TYPE_HEADER || getItemViewType(position) == TYPE_FOOTER) {
                        return gridManager.getSpanCount();
                    }
                    return 1;
                }
            });
        }
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            p.setFullSpan(holder.getLayoutPosition() == 0);
        }
    }

    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        if (mHeaderView == null) {
            return position;
        } else {
            return position - 1;
        }
    }

    @Override
    public int getItemCount() {
        if (mHeaderView == null && mFooterView == null) {
            return mDatas.size();
        }
        if (mHeaderView != null && mFooterView != null) {
            return mDatas.size() + 2;
        }
        return mDatas.size() + 1;
    }

    public abstract RecyclerView.ViewHolder onCreate(ViewGroup parent, final int viewType);

    public abstract void onBind(RecyclerView.ViewHolder viewHolder, int RealPosition, T data);

    public class Holder extends RecyclerView.ViewHolder {
        public Holder(View itemView) {
            super(itemView);
        }
    }
}
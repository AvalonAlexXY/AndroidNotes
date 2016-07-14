import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.benbun.evtmaster.common.Constants;
import com.benbun.evtmaster.common.EmToast;

/**
 * Created by wangzhe on 2016/5/11.
 */
public abstract class BaseFragment extends Fragment {

    private View mRootView;
    public Activity mActivity;
    public Resources mResources;
    private boolean isCreate;
    private boolean isLoadData;

    public abstract View initView();

    //用于viewpager+fragment时，只加载当前页数据，且只加载一次
    public abstract void lazyLoad();

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isCreate) {
            if (!isLoadData) {
                lazyLoad();
                isLoadData = true;
            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //第一个fragment会调用
        if (getUserVisibleHint()) {
            if (!isLoadData) {
                lazyLoad();
                isLoadData = true;
            }
        }
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isCreate = true;
        mResources = mActivity.getResources();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = initView();
        return mRootView;
    }


    /**
     * findViewbyId
     *
     * @param id
     * @param <T>
     * @return View
     */
    public <T extends View> T findView(View v, int id) {
        //noinspection unchecked
        return (T) v.findViewById(id);
    }


    /**
     * toast
     *
     * @param text
     */
    public void showToast(String text) {
        EmToast.showToast(mActivity, text, Constants.TOAST_TIME);
    }

}

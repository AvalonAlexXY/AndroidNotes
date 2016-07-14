
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

public abstract class BaseActivity extends FragmentActivity {

    /**
     * 获取资源文件
     *
     * @return 资源文件
     */
    protected abstract int getContentView();

    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 初始化监听
     */
    protected abstract void setListener();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        initView();
        initData();
        setListener();
    }

    /**
     * 获取根布局
     *
     * @return 根布局
     */
    public View getRootView() {
        return View.inflate(this, getContentView(), null);
    }


    /**
     * findViewbyId
     *
     * @param id  控件id
     * @param <T> 控件类型泛型
     * @return 控件
     */
    public <T extends View> T findView(int id) {
        //noinspection unchecked
        return (T) findViewById(id);
    }

    /**
     * toast
     *
     * @param text toast的内容
     */
    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }


}

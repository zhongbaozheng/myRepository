package cn.meiqu.lainmonitor;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;

import cn.meiqu.baseproject.baseUi.BaseActivity;
import cn.meiqu.baseproject.dao.SettingDao;
import cn.meiqu.baseproject.util.ScreenUtil;
import cn.meiqu.baseproject.util.StringUtil;

public class StartActivity extends BaseActivity {

    private View linearLayout;

    @Override
    public void onHttpHandle(String action, String data) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenUtil.fullScreenCompat(this);
        setContentView(R.layout.activity_start);
        linearLayout = (View) findViewById(R.id.ll_start);
        startAnim();
    }

    @Override
    public void initFragment() {

    }

    /**
     * 开启动画
     */
    private void startAnim() {
        // 动画集合
        AnimationSet set = new AnimationSet(false);
//        // 缩放动画
        ScaleAnimation scale = new ScaleAnimation(0.8f, 1, 0.8f, 1,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        scale.setDuration(400);// 动画时间
        scale.setFillAfter(true);// 保持动画状态

        // 渐变动画
        AlphaAnimation alpha = new AlphaAnimation(0.5F, 1);
        alpha.setDuration(2000);// 动画时间
        alpha.setFillAfter(true);// 保持动画状态
        set.addAnimation(scale);
        set.addAnimation(alpha);

        // 设置动画监听
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            // 动画执行结束
            @Override
            public void onAnimationEnd(Animation animation) {
                jumpNextPage();
            }
        });

        linearLayout.startAnimation(set);

    }

    /**
     * 跳转下一个页面
     */
    private void jumpNextPage() {
        if (StringUtil.isEmpty(SettingDao.getInstance().getHostIp()) && StringUtil.isEmpty(SettingDao.getInstance().getHostAddr())) {
            IpSettingActivity.isFirst = true;
            jumpFinish(IpSettingActivity.class);
        } else if (SettingDao.getInstance().getIsLogin()) {
            jumpFinish(MainActivity.class);
        } else {
            jumpFinish(LoginActivity.class);
        }

        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
    }
}

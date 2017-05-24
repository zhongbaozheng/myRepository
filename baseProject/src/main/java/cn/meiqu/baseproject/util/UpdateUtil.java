package cn.meiqu.baseproject.util;

import android.content.Context;

import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;


public class UpdateUtil {
    public static void checkUpdate(final Context activity, final boolean isShow) {
        UmengUpdateAgent.update(activity);
        UmengUpdateAgent.setUpdateAutoPopup(false);
        UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
            @Override
            public void onUpdateReturned(int updateStatus,
                                         UpdateResponse updateInfo) {
                switch (updateStatus) {
                    case 0: // has update
                        UmengUpdateAgent.showUpdateDialog(activity, updateInfo);
                        break;
                    case 1: // has no update
                        if (isShow) {
                            ToastUtil.show(activity, "当前已是最新版本 ！");
                        }
                        break;
                    case 2: // none wifi
                        // Toast.makeText(getParent(), "没有wifi连接 ，只在wifi下更新 !",
                        // Toast.LENGTH_SHORT).show();
                        break;
                    case 3: // time out
                        // Toast.makeText(getParent(), "链接超时！",
                        // Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }
}
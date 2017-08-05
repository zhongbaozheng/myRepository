package cn.meiqu.baseproject.httpGet;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import cn.meiqu.baseproject.R;
import cn.meiqu.baseproject.baseUi.BaseActivity;
import cn.meiqu.baseproject.baseUi.BaseApp;
import cn.meiqu.baseproject.util.JsonUtil;
import cn.meiqu.baseproject.util.ToastUtil;

/**
 * 这里的网络请求的格式：okhttp的访问格式
 * 通过httpGetBase的call方法请求，通过向当前页发送一个对应（class文件名字+url）的广播action
 * 然后在当前页接收广播，解析我们的json数据
 */
public class HttpResponController implements IHttpResponListener {

    private static HttpResponController responController = new HttpResponController();
    public static int token_expiry = -10006;

    private HttpResponController() {
    }

//    public String getmData() {
//        return mData;
//    }
//
//    String mData;

    public static HttpResponController getInstance() {
        return responController;
    }

    @Override
    public void onHttpRespon(String action, String object) {
        // TODO Auto-generated method stub
        if (object != null) {
            if (JsonUtil.getStatus(object) == -10006) {
                Intent intent = new Intent(BaseApp.mContext.getResources().getString(R.string.action_login));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                BaseApp.mContext.startActivity(intent);
                LocalBroadcastManager.getInstance(BaseApp.mContext).sendBroadcast(new Intent(BaseActivity.action_exitApplication));
                ToastUtil.show(BaseApp.mContext, "请重新登录");
                return;
            }
        }
        Intent intent = new Intent(action);
        intent.putExtra("data", object);
        //广播机制
        LocalBroadcastManager.getInstance(BaseApp.mContext).sendBroadcast(intent);
//        mData = object;
    }

}

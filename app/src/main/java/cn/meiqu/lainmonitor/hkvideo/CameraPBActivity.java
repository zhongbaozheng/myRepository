package cn.meiqu.lainmonitor.hkvideo;

import cn.meiqu.baseproject.util.Config;

/**
 * Created by Administrator on 2017/8/8.
 */

public class CameraPBActivity extends PlayBackActivity {

    @Override
    public void getLoginId() {
       loginId = assistant.login("192.168.1.64",8000,"admin","admin123",Config.getInt("channel"));
    }
}

package cn.meiqu.lainmonitor.aui.system;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.meiqu.baseproject.dao.SettingDao;
import cn.meiqu.baseproject.util.Config;
import cn.meiqu.baseproject.util.ToastUtil;
import cn.meiqu.lainmonitor.R;
import cn.meiqu.lainmonitor.aui.FragmentNormal;

/**
 * Created by Administrator on 2017/6/7.
 */

public class FragmentAdmin extends Fragment {

    @BindView(R.id.admin_account_ed)
    EditText mAccountEd;
    @BindView(R.id.admin_password_ed)
    EditText mPasswordEd;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.f_admin,null);
        //注意这里的this，不能用getActivity()
        Config.register(getActivity());
        ButterKnife.bind(this,v);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mAccountEd.setText(SettingDao.getInstance().getAccount());
    }

    @OnClick({R.id.admin_modify_tv})
    public void onClick(View v){
        if(v.getId() == R.id.admin_modify_tv){

            if(!mAccountEd.getText().toString().equals("") && !mPasswordEd.getText().toString().equals("")){

                if(mAccountEd.getText().toString().equals(SettingDao.getInstance().getAccount()) && mPasswordEd.getText().toString().equals(SettingDao.getInstance().getPwd())){
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.contain_id,new FragmentModify());
                    transaction.addToBackStack(null);
                    transaction.commit();
                    ToastUtil.show(getActivity(),"进入修改！");
                }else {
                    ToastUtil.show(getActivity(),"用户名或密码输入有误！");
                }
            }else{
                ToastUtil.show(getActivity(),"输入不能为空！");
            }

        }
    }

}

package cn.meiqu.lainmonitor;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import cn.meiqu.baseproject.dao.SettingDao;
import cn.meiqu.lainmonitor.activity.SettingActivity;
import cn.meiqu.lainmonitor.aui.system.FragmentModify;

/**
 * Created by Administrator on 2017/5/25.
 */

public class MineFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    public MineFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static MineFragment newInstance(int sectionNumber) {
        MineFragment fragment = new MineFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        /**
         * SettingDao.getInstance().setIsLogin();
         SettingDao.getInstance().setAccount(userName);
         SettingDao.getInstance().setPwd(password);
         */
        TextView settingTv = (TextView) rootView.findViewById(R.id.setting_tv);
        settingTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SettingActivity.class));

//                SettingDao.getInstance().setIsLogin(0);
//                SettingDao.getInstance().setAccount("");
//                SettingDao.getInstance().setPwd("");
//
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        getActivity().finish();
//                        Toast.makeText(getActivity(),"已退出！",Toast.LENGTH_SHORT).show();
//                    }
//                },1000);

//                FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                transaction.replace(R.id.contain_id,new SettingFragment());
//                transaction.addToBackStack(null);
//                transaction.commit();


            }
        });


        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        if(!SettingDao.getInstance().getIsLogin()){
            getActivity().finish();
        }
    }
}

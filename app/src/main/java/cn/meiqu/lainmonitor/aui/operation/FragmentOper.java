package cn.meiqu.lainmonitor.aui.operation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import cn.meiqu.lainmonitor.R;
import cn.meiqu.lainmonitor.aui.operation.server.FragmentServer;


/**
 * Created by Fatel on 16-5-24.
 *
 */
public class FragmentOper extends Fragment {

    public static String number1 = "";
    public static String number2 = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFragemnet();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_normal,container,false);
        return view;
    }

    private void initFragemnet(){
        if(number2.equals("1")){
            // 服务器，新建一个Fragment
            AddFragment(new FragmentServer(),R.id.contain_id);
        }

        if(number2.equals("2")){
            // 交换机
        }

        if(number2.equals("3")){
            //路由器
        }

        if(number2.equals("4")){
            //防火墙
        }
    }

    public void AddFragment(Fragment f, int containerId) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(containerId, f,f.getClass().getName());
        getFragmentManager().popBackStack();
        //        transaction.addToBackStack(null);//写了默认返回上一个fragment
        transaction.commit();
    }


}

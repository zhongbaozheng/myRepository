package cn.meiqu.lainmonitor.aui.system.manage;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.meiqu.baseproject.API;
import cn.meiqu.baseproject.httpGet.HttpGetController;
import cn.meiqu.lainmonitor.R;
import cn.meiqu.lainmonitor.adapter.RecycleAlartAdapter;
import cn.meiqu.lainmonitor.aui.FragmentAlert;
import cn.meiqu.lainmonitor.bean.Alart;

/**
 * Created by Fatel on 16-5-10.
 */
public class FragmentAlart extends FragmentAlert implements RecycleAlartAdapter.OnItemClickListner {
    String action_getData = className + API.getAlartList;
    String action_edt = className + API.alartEdit;
    String action_status = className + API.alartStatus;
    ArrayList<Alart> Alarts = new ArrayList<>();
    RecycleAlartAdapter adapter;

    @Override
    public RecyclerView.Adapter getAdapter() {
        adapter = new RecycleAlartAdapter(getActivity(), Alarts);
        adapter.setOnItemClickListner(this);
        return adapter;
    }

    @Override
    public View getTopView() {
        viewGBody.getChildAt(0).setVisibility(View.GONE);
//        mFab.setVisibility(View.VISIBLE);
        return LayoutInflater.from(getActivity()).inflate(R.layout.layout_alart_top, null);
    }

    @Override
    public String getAction() {
        return action_getData;
    }

    @Override
    public String[] getActions() {
        return new String[]{action_getData, action_status, action_edt};
    }

    @Override
    public void requestData(String start, String end, String deviceId) {
        showProgressDialog();
        HttpGetController.getInstance().getAlartList(className);
    }

    public void handleData(String data) {
        ArrayList<Alart> temps = new Gson().fromJson(data, new TypeToken<ArrayList<Alart>>() {
        }.getType());
        Alarts.clear();
        Alarts.addAll(temps);
        adapter.notifyDataSetChanged();
    }

    public void requestEdit(String id, String timeQuantum1, String timeQuantum2, String timeQuantum3, String intervalTime) {
        showProgressDialog();
        HttpGetController.getInstance().alartEdt(id, timeQuantum1, timeQuantum2, timeQuantum3, intervalTime, className);
    }

    public void requestStatus(String id, String number, String status) {
        showProgressDialog();
        HttpGetController.getInstance().alartStatus(id, number, status, className);
    }

    public void handleEdt(String data) {
        try {
            requestData(null, null, null);
            JSONObject object = (JSONObject) new JSONArray(data).get(0);
            showAlart(object.getString("msg"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onHttpHandle(String action, String data) {
        super.onHttpHandle(action, data);
        if (getHttpStatus(data)) {
            if (action.equals(action_edt) || action.equals(action_status)) {
                handleEdt(data);
            }
        }
    }


    @Override
    public String[] getDeviceNames() {
        String names[] = new String[1];
        return names;
    }

    @Override
    public String getDeviceId(int position) {
        return "0";
    }

    public void showEdtDialog(int position) {
        View body = LayoutInflater.from(getActivity()).inflate(R.layout.layout_alart_input, null);
        final EditText mEdtName;
        final EditText mEdtTime1;
        final EditText mEdtTime2;
        final EditText mEdtTime3;
        final EditText mEdtInterval;

        mEdtName = (EditText) body.findViewById(R.id.edt_name);
        mEdtTime1 = (EditText) body.findViewById(R.id.edt_time1);
        mEdtTime2 = (EditText) body.findViewById(R.id.edt_time2);
        mEdtTime3 = (EditText) body.findViewById(R.id.edt_time3);
        mEdtInterval = (EditText) body.findViewById(R.id.edt_interval);

        final Alart alart = Alarts.get(position);

        mEdtName.setText("" + alart.getName());
        mEdtTime1.setText("" + alart.getTimeQuantum1());
        mEdtTime2.setText("" + alart.getTimeQuantum2());
        mEdtTime3.setText("" + alart.getTimeQuantum3());
        mEdtInterval.setText("" + alart.getIntervalTime());

        AlertDialog dialog = new AlertDialog.Builder(getActivity()).setTitle("修改设备报警信息").setPositiveButton("提交", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                requestEdit(alart.getId() + "", mEdtTime1.getText().toString(), mEdtTime2.getText().toString(), mEdtTime3.getText().toString(), mEdtInterval.getText().toString());
            }
        }).setNegativeButton("取消", null).setView(body).show();
    }


    @Override
    public void onItemEmail(int position) {
        Alart alart = Alarts.get(position);
        requestStatus(alart.getId() + "", "1", (alart.getEMailStatus() == 0 ? 1 : 0) + "");
    }

    @Override
    public void onItemMsm(int position) {
        Alart alart = Alarts.get(position);
        requestStatus(alart.getId() + "", "2", (alart.getSoundLightStatus() == 0 ? 1 : 0) + "");
    }

    @Override
    public void onItemPhone(int position) {
        Alart alart = Alarts.get(position);
        requestStatus(alart.getId() + "", "4", (alart.getPhoneStatus() == 0 ? 1 : 0) + "");
    }

    @Override
    public void onItemLight(int position) {
        Alart alart = Alarts.get(position);
        requestStatus(alart.getId() + "", "3", (alart.getSoundLightStatus() == 0 ? 1 : 0) + "");
    }

    @Override
    public void onItemEdit(int position) {
        showEdtDialog(position);
    }
}

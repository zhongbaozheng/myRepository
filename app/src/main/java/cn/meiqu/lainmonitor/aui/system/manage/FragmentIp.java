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
import cn.meiqu.lainmonitor.adapter.RecycleIpAdapter;
import cn.meiqu.lainmonitor.aui.FragmentAlert;
import cn.meiqu.lainmonitor.bean.Ip;
import cn.meiqu.lainmonitor.bean.IpSetion;

/**
 * Created by Fatel on 16-5-10.
 */
public class FragmentIp extends FragmentAlert implements RecycleIpAdapter.OnItemClickListner {
    String action_getData = className + API.getIpList;
    String action_add = className + API.ipAdd;
    String action_edt = className + API.ipEdit;
    String action_del = className + API.ipDel;
    String action_start = className + API.ipStart;
    String action_stop = className + API.ipStop;
    String action_getSetion = className + API.getIpSetionList;
    ArrayList<Ip> Ips = new ArrayList<>();
    ArrayList<IpSetion> ipSetions = new ArrayList<>();
    RecycleIpAdapter adapter;

    @Override
    public RecyclerView.Adapter getAdapter() {
        adapter = new RecycleIpAdapter(getActivity(), Ips);
        adapter.setOnItemClickListner(this);
        return adapter;
    }

    @Override
    public View getTopView() {
        requestIpSetion();
        viewGBody.getChildAt(0).setVisibility(View.GONE);
        mFab.setVisibility(View.VISIBLE);
        return LayoutInflater.from(getActivity()).inflate(R.layout.layout_ip_top, null);
    }

    @Override
    public String getAction() {
        return action_getData;
    }

    @Override
    public String[] getActions() {
        return new String[]{action_getData, action_add, action_del, action_edt, action_start, action_stop, action_getSetion};
    }

    @Override
    public void requestData(String start, String end, String deviceId) {
        showProgressDialog();
        HttpGetController.getInstance().getIpList(className);
    }

    public void handleData(String data) {
        ArrayList<Ip> temps = new Gson().fromJson(data, new TypeToken<ArrayList<Ip>>() {
        }.getType());
        Ips.clear();
        Ips.addAll(temps);
        adapter.notifyDataSetChanged();
    }

    public void requestIpSetion() {
        HttpGetController.getInstance().getIpSetionList(className);
    }

    public void handleIpSetion(String data) {
        ArrayList<IpSetion> temps = new Gson().fromJson(data, new TypeToken<ArrayList<IpSetion>>() {
        }.getType());
        ipSetions.clear();
        ipSetions.addAll(temps);
    }

    public void requestAdd(String deviceId, String ipAddress, String ipPort) {
        showProgressDialog();
        HttpGetController.getInstance().ipAdd(deviceId, ipAddress, ipPort, className);
    }

    public void requestEdt(String deviceId, String ipAddress, String ipPort) {
        showProgressDialog();
        HttpGetController.getInstance().ipEdt(deviceId, ipAddress, ipPort, className);
    }

    //
    public void requestDel(String deviceId, String id) {
        HttpGetController.getInstance().ipDel(deviceId, id, className);
    }

    public void requestStatus(String ip, String port, String number, String id, boolean isStart) {
        showProgressDialog();
        if (isStart)
            HttpGetController.getInstance().ipStart(ip, port, number, id, className);
        else {
            HttpGetController.getInstance().ipStop(ip, port, number, id, className);
        }
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
            if (action.equals(action_add) || action.equals(action_del) || action.equals(action_edt) || action.equals(action_stop) || action.equals(action_start)) {
                handleEdt(data);
            } else if (action.equals(action_getSetion)) {
                handleIpSetion(data);
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

    int currentId;

    public void showEdtDialog(final int position) {
        View body = LayoutInflater.from(getActivity()).inflate(R.layout.layout_ip_input, null);

        final EditText mEdtName;
        final EditText mEdtAddr;
        final EditText mEdtPort;

        mEdtName = (EditText) body.findViewById(R.id.edt_name);
        mEdtAddr = (EditText) body.findViewById(R.id.edt_addr);
        mEdtPort = (EditText) body.findViewById(R.id.edt_port);
        String title = "设备修改";
        if (position != -1) {
            final Ip ip = Ips.get(position);
            mEdtName.setText(ip.getDiName() + "");
            mEdtAddr.setText(ip.getDiAddress() + "");
            mEdtPort.setText(ip.getDiPort() + "");
        } else {
            title = "设备添加";
            if(!ipSetions.isEmpty())
            mEdtName.setText(ipSetions.get(0).getDlName() + "");
            mEdtName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String[] names = new String[ipSetions.size()];
                    if (names.length == 0) {
                        requestIpSetion();
                        return;
                    }
                    int currentPosition = 0;
                    for (int i = 0; i < ipSetions.size(); i++) {
                        names[i] = ipSetions.get(i).getDlName();
                        if (ipSetions.get(i).getDlId() == currentId) {
                            currentPosition = i;
                        }
                    }
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).setSingleChoiceItems(names, currentPosition, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            currentId = ipSetions.get(which).getDlId();
                            mEdtName.setText(ipSetions.get(which).getDlName() + "");
                            dialog.dismiss();
                        }
                    }).create();
                    alertDialog.show();
                }
            });
        }
        AlertDialog dialog = new AlertDialog.Builder(getActivity()).setTitle(title).setPositiveButton("提交", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (position == -1) {
                    requestAdd(currentId + "", mEdtAddr.getText().toString(), mEdtPort.getText().toString());
                } else {
                    requestEdt(Ips.get(position).getDiId() + "", mEdtAddr.getText().toString(), mEdtPort.getText().toString());
                }
            }
        }).setNegativeButton("取消", null).setView(body).show();
    }
//
//    @Override


    //    @Override
//    public void onItemEdt(int position) {
//        showEdtDialog("修改位置名称", false, Ips.get(position).getDlId() + "", Ips.get(position).getDlName() + "");
//    }
    public void onClickAdd() {
        super.onClickAdd();
        showEdtDialog(-1);
//        showEdtDialog("添加所在机房或楼层", true, "", "");
    }

    @Override
    public void onItemStatus(int position) {
        Ip ip = Ips.get(position);
        requestStatus(ip.getDiAddress() + "", ip.getDiPort() + "", ip.getDiDeviceNumber() + "", ip.getDiId() + "", ip.getDiOperate() == 0 ? true : false);
    }

    @Override
    public void onItemDel(final int position) {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).setMessage("是否要删除" + Ips.get(position).getDiName() + "?").setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                requestDel(Ips.get(position).getDiDeviceNumber() + "", Ips.get(position).getDiId() + "");
            }
        }).show();
    }

    @Override
    public void onItemEdit(int position) {
        showEdtDialog(position);
    }
}

package cn.meiqu.lainmonitor.aui.env.co2;

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
import cn.meiqu.baseproject.util.StringUtil;
import cn.meiqu.lainmonitor.R;
import cn.meiqu.lainmonitor.adapter.RecycleCo2ManageAdapter;
import cn.meiqu.lainmonitor.aui.FragmentAlert;
import cn.meiqu.lainmonitor.bean.Co2;
import cn.meiqu.lainmonitor.bean.Ip;
import cn.meiqu.lainmonitor.bean.Location;

/**
 * Created by Fatel on 16-5-10.
 */
public class FragmentCo2Manage extends FragmentAlert implements RecycleCo2ManageAdapter.OnItemClickListner {
    String action_getData = className + API.getCo2Manage;
    String action_add = className + API.addCo2;
    String action_edt = className + API.edtCo2;
    String action_del = className + API.delCo2;
    String action_getIP = className + API.getCo2IP;
    String action_getLocation = className + API.getCo2locations;
    ArrayList<Co2> Co2s = new ArrayList<>();
    ArrayList<Location> locations = new ArrayList<>();
    ArrayList<Ip> ips = new ArrayList<>();
    RecycleCo2ManageAdapter adapter;
    String[] addrs = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"};

    @Override
    public RecyclerView.Adapter getAdapter() {
        adapter = new RecycleCo2ManageAdapter(getActivity(), Co2s);
        adapter.setOnItemClickListner(this);
        return adapter;
    }

    @Override
    public View getTopView() {
        requestLocations();
        requestIps();
        viewGBody.getChildAt(0).setVisibility(View.GONE);
        mFab.setVisibility(View.VISIBLE);
        return LayoutInflater.from(getActivity()).inflate(R.layout.layout_co2_top, null);
    }

    @Override
    public String getAction() {
        return action_getData;
    }

    @Override
    public String[] getActions() {
        return new String[]{action_getData, action_add, action_del, action_edt, action_getIP, action_getLocation};
    }

    @Override
    public void requestData(String start, String end, String deviceId) {
        showProgressDialog();
        HttpGetController.getInstance().getCo2ManageList(className);
    }

    public void handleData(String data) {
        ArrayList<Co2> temps = new Gson().fromJson(data, new TypeToken<ArrayList<Co2>>() {
        }.getType());
        Co2s.clear();
        Co2s.addAll(temps);
        adapter.notifyDataSetChanged();
    }

    public void requestLocations() {
        HttpGetController.getInstance().getCo2LocationsList(className);
    }

    public void handleLocations(String data) {
        ArrayList<Location> temps = new Gson().fromJson(data, new TypeToken<ArrayList<Location>>() {
        }.getType());
        locations.clear();
        locations.addAll(temps);
    }

    public void requestIps() {
        HttpGetController.getInstance().getCo2IpList(className);
    }

    public void handleIps(String data) {
        ArrayList<Ip> temps = new Gson().fromJson(data, new TypeToken<ArrayList<Ip>>() {
        }.getType());
        ips.clear();
        ips.addAll(temps);
    }

    public void requestAdd(String address, String Co2, String ipAddress, String name, String alartData) {
        showProgressDialog();
        HttpGetController.getInstance().addCo2Manage(address, Co2, ipAddress, name, alartData, className);
    }

    public void requestEdt(String id, String name, String alartData) {
        showProgressDialog();
        HttpGetController.getInstance().edtCo2Manage(id, name, alartData, className);
    }

    //
    public void requestDel(String id, String ip) {
        HttpGetController.getInstance().delCo2Manage(id, ip, className);
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
            if (action.equals(action_add) || action.equals(action_del) || action.equals(action_edt)) {
                handleEdt(data);
            } else if (action.equals(action_getLocation)) {
                handleLocations(data);
            } else if (action.equals(action_getIP)) {
                handleIps(data);
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

    int currentAddr = 0;
    int currentCo2 = 0;
    int currentIp = 0;

    public void showEdtDialog(final int position) {
        View body = LayoutInflater.from(getActivity()).inflate(R.layout.layout_co2_input, null);

        final EditText mEdtAddress;
        final EditText mEdtLocation;
        final EditText mEdtIp;
        final EditText mEdtName;
        final EditText mEdtAlart;
        mEdtAddress = (EditText) body.findViewById(R.id.edt_address);
        mEdtLocation = (EditText) body.findViewById(R.id.edt_location);
        mEdtIp = (EditText) body.findViewById(R.id.edt_ip);
        mEdtName = (EditText) body.findViewById(R.id.edt_name);
        mEdtAlart = (EditText) body.findViewById(R.id.edt_alart);
        String title = "设备修改";
        if (position != -1) {
            final Co2 Co2 = Co2s.get(position);
            mEdtAddress.setText("" + Co2.getAddress());
            mEdtLocation.setText("" + Co2.getDeviceLocationPojo().getDlName());
            mEdtIp.setText("" + Co2.getIpPort());
            mEdtName.setText(Co2.getName() + "");
            mEdtAlart.setText(Co2.getAlarmData() + "");
        } else {
            title = "设备添加";
            mEdtAddress.setText("" + addrs[0]);
            if (locations.isEmpty()) {
                requestLocations();
            } else {
                mEdtLocation.setText("" + locations.get(0).getDlName());
            }
            if (ips.isEmpty()) {
                requestIps();
            } else {
                mEdtIp.setText("" + ips.get(0).getDiAddress() + ":" + ips.get(0).getDiPort());
            }
            currentAddr = 0;
            currentIp = 0;
            currentCo2 = 0;
            mEdtLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String[] names = new String[locations.size()];
                    if (names.length == 0) {
                        requestLocations();
                        return;
                    }
                    for (int i = 0; i < Co2s.size(); i++) {
                        names[i] = locations.get(i).getDlName();
                    }
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).setSingleChoiceItems(names, currentCo2, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            currentCo2 = which;
                            mEdtLocation.setText(locations.get(which).getDlName() + "");
                            dialog.dismiss();
                        }
                    }).create();
                    alertDialog.show();
                }
            });
            mEdtAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).setSingleChoiceItems(addrs, currentAddr, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            currentAddr = which;
                            mEdtAddress.setText(addrs[which] + "");
                            dialog.dismiss();
                        }
                    }).create();
                    alertDialog.show();
                }
            });
            mEdtIp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String[] names = new String[ips.size()];
                    if (names.length == 0) {
                        requestIps();
                        return;
                    }
                    for (int i = 0; i < ips.size(); i++) {
                        names[i] = ips.get(i).getDiAddress() + ":" + ips.get(i).getDiPort();
                    }
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).setSingleChoiceItems(names, currentIp, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            currentIp = which;
                            mEdtIp.setText(ips.get(which).getDiAddress() + ":" + ips.get(which).getDiPort());
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
                String name = mEdtName.getText().toString();
                if (StringUtil.isEmpty(name)) {
                    toast("请输入名称");
                    return;
                }
                if (position == -1) {
                    requestAdd(addrs[currentAddr] + "", locations.get(currentCo2).getDlId() + "", ips.get(currentIp).getDiId() + "", name, mEdtAlart.getText().toString());
                } else {
                    requestEdt(Co2s.get(position).getId() + "", name, mEdtAlart.getText().toString());
                }
            }
        }).setNegativeButton("取消", null).setView(body).show();
    }

    public void onClickAdd() {
        super.onClickAdd();
        showEdtDialog(-1);
    }

    @Override
    public void onItemDel(final int position) {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).setMessage("是否要删除" + Co2s.get(position).getName() + "?").setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                requestDel(Co2s.get(position).getId() + "", Co2s.get(position).getIp() + "");
            }
        }).show();
    }

    @Override
    public void onItemEdit(int position) {
        showEdtDialog(position);
    }
}

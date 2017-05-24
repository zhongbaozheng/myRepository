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
import cn.meiqu.baseproject.util.StringUtil;
import cn.meiqu.lainmonitor.R;
import cn.meiqu.lainmonitor.adapter.Recycle80Adapter;
import cn.meiqu.lainmonitor.aui.FragmentAlert;
import cn.meiqu.lainmonitor.bean.Device;
import cn.meiqu.lainmonitor.bean.Device80;
import cn.meiqu.lainmonitor.bean.Ip;
import cn.meiqu.lainmonitor.bean.Location;

/**
 * Created by Fatel on 16-5-10.
 */
public class Fragment8052 extends FragmentAlert implements Recycle80Adapter.OnItemClickListner {
    String action_getData = className + API.get8052;
    String action_add = className + API.add8052;
    String action_edt = className + API.edt8052;
    String action_del = className + API.del8052;
    String action_getIP = className + API.get8060IP;
    String action_getDevice = className + API.get8052Device;
    String action_getLocation = className + API.get8060Location;
    ArrayList<Device80> Device80s = new ArrayList<>();
    ArrayList<Location> locations = new ArrayList<>();
    ArrayList<Ip> ips = new ArrayList<>();
    ArrayList<Device> devices = new ArrayList<>();
    Recycle80Adapter adapter;
    String[] addrs = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"};
    String[] gallarys = new String[]{"DI0", "DI1", "DI2", "DI3", "DI4", "DI5", "DI6", "DI7"};

    @Override
    public RecyclerView.Adapter getAdapter() {
        adapter = new Recycle80Adapter(getActivity(), Device80s);
        adapter.setOnItemClickListner(this);
        return adapter;
    }

    @Override
    public View getTopView() {
        requestLocations();
        requestIps();
        requestDevices();
        viewGBody.getChildAt(0).setVisibility(View.GONE);
        mFab.setVisibility(View.VISIBLE);
        return LayoutInflater.from(getActivity()).inflate(R.layout.layout_8060_top, null);
    }

    @Override
    public String getAction() {
        return action_getData;
    }

    @Override
    public String[] getActions() {
        return new String[]{action_getData, action_add, action_del, action_edt, action_getIP, action_getDevice, action_getLocation};
    }

    @Override
    public void requestData(String start, String end, String deviceId) {
        showProgressDialog();
        HttpGetController.getInstance().get8052List(className);
    }

    public void handleData(String data) {
        ArrayList<Device80> temps = new Gson().fromJson(data, new TypeToken<ArrayList<Device80>>() {
        }.getType());
        Device80s.clear();
        Device80s.addAll(temps);
        adapter.notifyDataSetChanged();
    }

    public void requestDevices() {
        HttpGetController.getInstance().get8052DeviceList(className);
    }

    public void handleDevices(String data) {
        ArrayList<Device> temps = new Gson().fromJson(data, new TypeToken<ArrayList<Device>>() {
        }.getType());
        devices.clear();
        devices.addAll(temps);
    }

    public void requestLocations() {
        HttpGetController.getInstance().get8060LocationList(className);
    }

    public void handleLocations(String data) {
        ArrayList<Location> temps = new Gson().fromJson(data, new TypeToken<ArrayList<Location>>() {
        }.getType());
        locations.clear();
        locations.addAll(temps);
    }

    public void requestIps() {
        HttpGetController.getInstance().get8060IpList(className);
    }

    public void handleIps(String data) {
        ArrayList<Ip> temps = new Gson().fromJson(data, new TypeToken<ArrayList<Ip>>() {
        }.getType());
        ips.clear();
        ips.addAll(temps);
    }

    public void requestAdd(String address, String gallery, String location, String ipAddress, String number, String name) {
        showProgressDialog();
        HttpGetController.getInstance().add8052(address, gallery, location, ipAddress, number, name, className);
    }

    public void requestEdt(String id, String name) {
        showProgressDialog();
        HttpGetController.getInstance().edt8052(id, name, className);
    }

    //
    public void requestDel(String id, String ip) {
        HttpGetController.getInstance().del8052(id, ip, className);
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
            } else if (action.equals(action_getDevice)) {
                handleDevices(data);
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
    int currentGallary = 0;
    int currentLocation = 0;
    int currentIp = 0;
    int currentDevice = 0;

    public void showEdtDialog(final int position) {
        View body = LayoutInflater.from(getActivity()).inflate(R.layout.layout_8060_input, null);

        final EditText mEdtAddress;
        final EditText mEdtGallary;
        final EditText mEdtLocation;
        final EditText mEdtIp;
        final EditText mEdtDevice;
        final EditText mEdtName;

        mEdtAddress = (EditText) body.findViewById(R.id.edt_address);
        mEdtGallary = (EditText) body.findViewById(R.id.edt_gallary);
        mEdtLocation = (EditText) body.findViewById(R.id.edt_location);
        mEdtIp = (EditText) body.findViewById(R.id.edt_ip);
        mEdtDevice = (EditText) body.findViewById(R.id.edt_device);
        mEdtName = (EditText) body.findViewById(R.id.edt_name);

        String title = "设备修改";
        if (position != -1) {
            final Device80 Device80 = Device80s.get(position);
            mEdtAddress.setText("" + Device80.getAddress());
            mEdtGallary.setText("" + Device80.getGallery());
            mEdtLocation.setText("" + Device80.getDeviceLocationPojo().getDlName());
            mEdtIp.setText("" + Device80.getIpPort());
            mEdtDevice.setText("" + Device80.getDeviceName());
            mEdtName.setText(Device80.getName() + "");
        } else {
            title = "设备添加";
            mEdtAddress.setText("" + addrs[0]);
            mEdtGallary.setText("" + gallarys[0]);
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
            if (devices.isEmpty()) {
                requestDevices();
            } else {
                mEdtDevice.setText("" + devices.get(0).getName());
            }
            currentAddr = 0;
            currentDevice = 0;
            currentGallary = 0;
            currentIp = 0;
            currentLocation = 0;
            mEdtLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String[] names = new String[locations.size()];
                    if (names.length == 0) {
                        requestLocations();
                        return;
                    }
                    for (int i = 0; i < locations.size(); i++) {
                        names[i] = locations.get(i).getDlName();
                    }
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).setSingleChoiceItems(names, currentLocation, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            currentLocation = which;
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
            mEdtGallary.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).setSingleChoiceItems(gallarys, currentGallary, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            currentGallary = which;
                            mEdtGallary.setText(gallarys[which] + "");
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

            mEdtDevice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String[] names = new String[devices.size()];
                    if (names.length == 0) {
                        requestDevices();
                        return;
                    }
                    for (int i = 0; i < devices.size(); i++) {
                        names[i] = devices.get(i).getName();
                    }
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).setSingleChoiceItems(names, currentDevice, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            currentDevice = which;
                            mEdtDevice.setText(devices.get(which).getName());
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
                    requestAdd(addrs[currentAddr] + "", gallarys[currentGallary] + "", locations.get(currentLocation).getDlId() + "", ips.get(currentIp).getDiId() + "", devices.get(currentDevice).getNumber() + "", name);
                } else {
                    requestEdt(Device80s.get(position).getId() + "", name);
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
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).setMessage("是否要删除" + Device80s.get(position).getName() + "?").setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                requestDel(Device80s.get(position).getId() + "", Device80s.get(position).getIp() + "");
            }
        }).show();
    }

    @Override
    public void onItemEdit(int position) {
        showEdtDialog(position);
    }
}

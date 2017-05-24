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
import cn.meiqu.lainmonitor.adapter.RecycleLocationAdapter;
import cn.meiqu.lainmonitor.aui.FragmentAlert;
import cn.meiqu.lainmonitor.bean.Location;

/**
 * Created by Fatel on 16-5-10.
 */
public class FragmentLocation extends FragmentAlert implements RecycleLocationAdapter.OnItemClickListner {
    String action_getData = className + API.getLocationList;
    String action_add = className + API.locationAdd;
    String action_edt = className + API.locationEdt;
    String action_del = className + API.locationDel;
    ArrayList<Location> Locations = new ArrayList<>();
    RecycleLocationAdapter adapter;

    @Override
    public RecyclerView.Adapter getAdapter() {
        adapter = new RecycleLocationAdapter(getActivity(), Locations);
        adapter.setOnItemClickListner(this);
        return adapter;
    }

    @Override
    public View getTopView() {
        viewGBody.getChildAt(0).setVisibility(View.GONE);
        mFab.setVisibility(View.VISIBLE);
        return LayoutInflater.from(getActivity()).inflate(R.layout.recycle_location, null);
    }

    @Override
    public String getAction() {
        return action_getData;
    }

    @Override
    public String[] getActions() {
        return new String[]{action_getData, action_add, action_del, action_edt};
    }

    @Override
    public void requestData(String start, String end, String deviceId) {
        showProgressDialog();
        HttpGetController.getInstance().getLocationList(className);
    }

    public void handleData(String data) {
        ArrayList<Location> temps = new Gson().fromJson(data, new TypeToken<ArrayList<Location>>() {
        }.getType());
        Locations.clear();
        Locations.addAll(temps);
        adapter.notifyDataSetChanged();
    }

    public void requestAdd(String name) {
        showProgressDialog();
        HttpGetController.getInstance().locationAdd(name, className);
    }

    public void requestEdt(String id, String name) {
        showProgressDialog();
        HttpGetController.getInstance().locationEdt(id, name, className);
    }

    public void requestDel(String id) {
        HttpGetController.getInstance().locationDel(id, className);
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

    public void showEdtDialog(String title, final boolean isAdd, final String id, String content) {
        View body = LayoutInflater.from(getActivity()).inflate(R.layout.layout_location_input, null);
        final EditText editText = (EditText) body.findViewById(R.id.edt);
        editText.setText("" + content);
        AlertDialog dialog = new AlertDialog.Builder(getActivity()).setTitle(title).setPositiveButton("提交", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String text = editText.getText().toString();
                if (StringUtil.isEmpty(text)) {
                    toast("名字不能为空哦");
                }
                if (isAdd) {
                    requestAdd(text);
                } else {
                    requestEdt(id, text);
                }
            }
        }).setNegativeButton("取消", null).setView(body).show();
    }

    @Override
    public void onClickAdd() {
        super.onClickAdd();
        showEdtDialog("添加所在机房或楼层", true, "", "");
    }

    @Override
    public void onItemEdt(int position) {
        showEdtDialog("修改位置名称", false, Locations.get(position).getDlId() + "", Locations.get(position).getDlName() + "");
    }

    @Override
    public void onItemDel(final int position) {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).setMessage("是否要删除" + Locations.get(position).getDlName() + "?").setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                requestDel(Locations.get(position).getDlId() + "");
            }
        }).show();
    }
}

package cn.meiqu.lainmonitor.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;

import cn.meiqu.baseproject.API;
import cn.meiqu.baseproject.baseUi.BaseActivity;
import cn.meiqu.baseproject.httpGet.HttpGetController;
import cn.meiqu.baseproject.util.Config;
import cn.meiqu.lainmonitor.R;
import cn.meiqu.lainmonitor.adapter.RecycleCheckBoxAdapter;
import cn.meiqu.lainmonitor.bean.DoorMessageBean;
import cn.meiqu.baseproject.bean.MsgBean;
import cn.meiqu.lainmonitor.bean.SelectionBean;

/**
 * Created by Administrator on 2017/5/9.
 */

public class UserManagerActivity extends BaseActivity implements View.OnClickListener{

    String className = getClass().getName();
    String action_questSeclection = className + API.queSectionUrl;
    String action_questCardNumner = className + API.getCardNumberUrl;
    String action_doorMessage = className + API.doorMessageUrl;
    String action_addUser = className +API.addDoorUserUrl;
    ArrayList<SelectionBean> mSelectinList = new ArrayList<>();
    private ArrayList<DoorMessageBean> mList = new ArrayList<>();


    private Spinner mSpinner;
    private EditText mUserNameEd;
    private EditText mCardEd;
    private Button mCommitBtn;
    ArrayList<String> itemArray = new ArrayList<>();
    ArrayList<Integer> itemIdArray = new ArrayList<>();

    private String selectionName;
    private String selectionId;
    private RecyclerView mRv;
    private RecycleCheckBoxAdapter mAdapter;

    private String addressStr="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_control);
        initTransparent();
        initTitle("用户管理");
        initViews();
        initReceiver(new String[]{action_questSeclection,action_questCardNumner,action_doorMessage,action_addUser});
        requestSelection();
        requestCardNumber();
        requestDoorMessage();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initViews(){
        mSpinner = (Spinner)findViewById(R.id.spinner);
        mUserNameEd = (EditText)findViewById(R.id.user_name_ed);
        mCardEd = (EditText)findViewById(R.id.user_card_ed);
        mCommitBtn = (Button)findViewById(R.id.commit_btn);
        mCommitBtn.setOnClickListener(this);
        mRv = (RecyclerView)findViewById(R.id.re_checkBox);
        mAdapter = new RecycleCheckBoxAdapter(this,mList);
        mRv.setLayoutManager(new GridLayoutManager(this,2));
        mRv.setAdapter(mAdapter);
    }

    private void requestSelection(){
        HttpGetController.getInstance().querySelection(className);
    }

    private void requestCardNumber(){
        HttpGetController.getInstance().queryCardNumber(className);
    }

    private void requestDoorMessage(){
        HttpGetController.getInstance().getDoorMessage(className);
    }

    private void addUser(String selectionId,String address,String userName,String card,String className){
        HttpGetController.getInstance().addUser(selectionId,address,userName,card,className);
    }

    private void setModel(){
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,itemArray);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(spinnerAdapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int postion, long id) {
                selectionName = itemArray.get(postion);
                selectionId = itemIdArray.get(postion)+"";
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }


    @Override
    public void onHttpHandle(String action, String data) {
        if(action.equals(action_questSeclection)){
            //arrayList,model setting here
            ArrayList<SelectionBean> temps = new Gson().fromJson(data,new TypeToken<ArrayList<SelectionBean>>(){}.getType());
            if(temps != null){
                mSelectinList.clear();
                itemArray.clear();
                mSelectinList.addAll(temps);
                for(int i = 0;i< mSelectinList.size();i++){
                    itemArray.add(mSelectinList.get(i).name);
                    itemIdArray.add(mSelectinList.get(i).id);
                }
            }
            setModel();
        }

        if(action.equals(action_questCardNumner)){
            //setcard
            try {
                JSONObject object = new JSONObject(data);
                String card = object.getString("cardnum");
                mCardEd.setText(card);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        if(action.equals(action_doorMessage)){
            handleMessage(data);
        }

        if(action.equals(action_addUser)){
            showMsg(data);
        }
    }


    private void handleMessage(String data){
        ArrayList<DoorMessageBean> temps = new Gson().fromJson(data,new TypeToken<ArrayList<DoorMessageBean>>(){}.getType());
        if(temps!=null){
            mList.clear();
            mList.addAll(temps);
            mAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void initFragment() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.commit_btn:
                //提交我们的数据
                if(mList.size()>0 ){
                    addressStr = "";
                    for(int i=0;i<mList.size();i++){
                        addressStr = addressStr+ Config.getString(mList.get(i).name);
                    }
                    if(addressStr.length()>0){
                        addressStr = addressStr.substring(0,addressStr.length()-1);
                        Log.e("address",addressStr);
                    }
                }
                //提交
                if(selectionId!=null && !selectionId.equals("")&& !addressStr.equals("") && !mUserNameEd.getText().toString().equals("") && !mCardEd.getText().toString().equals("") ){
                    addUser(selectionId,addressStr,mUserNameEd.getText().toString(),mCardEd.getText().toString(),className);
                }else{
                    Toast.makeText(this,"您的输入有误！",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}

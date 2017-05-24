package cn.meiqu.baseproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.TextView;

import java.util.ArrayList;

import cn.meiqu.baseproject.adapter.PagerBigImageAdapter;
import cn.meiqu.baseproject.baseUi.BaseActivity;
import cn.meiqu.baseproject.dialog.LoadingDialog;
import cn.meiqu.baseproject.view.photoview.PhotoViewPager;

public class SeeBigImageActivity extends BaseActivity implements
        OnPageChangeListener {
    public static String EXTAR_IMAGEORGIN_PATH = "EXTAR_IMAGEORGIN_PATH";
    public static String EXTAR_IMAGESMALL_PATH = "EXTAR_IMAGESMALL_PATH";
    public static String EXTAR_IMAGE_ID = "ID";
    public static String EXTRA_IMAGE_CURRENT = "EXTRA_IMAGE_CURRENT";
    public static String EXTRA_IMAGE_ACTION = "EXTRA_IMAGE_ACTION";
    public static int EXTRA_ACTION_FLY = 1;
    private Context mContext;
    private TextView tv_count;
    private PhotoViewPager photoViewPager;
    private PagerBigImageAdapter adapter;
    ArrayList<String> imageBigs = new ArrayList<String>();
    ArrayList<String> imageSmalls = new ArrayList<String>();
    ArrayList<String> imageIds = new ArrayList<String>();
    private float touchX = 0;
    private float touchY = 0;
    Handler mHandler;
    private int currentPosition = 0;
    private int imageAction = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_big_image);
        initView();
    }

    @Override
    protected void onResume() {
        initReceiver();
        super.onResume();
    }

    LoadingDialog progressDialog;

    @Override
    public void onHttpHandle(String action, String data) {

    }

    public void showProgressDialog(String content) {
        if (progressDialog == null) {
            progressDialog = new LoadingDialog(this);
        }
        progressDialog.show();
    }

    public void dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    public void initView() {
        mContext = this;
        tv_count = (TextView) findViewById(R.id.tv_currentCount);
        photoViewPager = (PhotoViewPager) findViewById(R.id.viewP_photo);

        imageBigs = getIntent().getStringArrayListExtra(EXTAR_IMAGEORGIN_PATH);
        imageSmalls = getIntent().getStringArrayListExtra(EXTAR_IMAGESMALL_PATH);
        currentPosition = getIntent().getIntExtra(EXTRA_IMAGE_CURRENT, 1);
        imageAction = getIntent().getIntExtra(EXTRA_IMAGE_ACTION, 0);
        imageIds = getIntent().getStringArrayListExtra(EXTAR_IMAGE_ID);
        if (imageBigs == null)
            imageBigs = new ArrayList<String>();
        if (imageSmalls == null)
            imageSmalls = new ArrayList<String>();
        adapter = new PagerBigImageAdapter(mContext, imageBigs, imageSmalls);
        photoViewPager.setAdapter(adapter);
        photoViewPager.setCurrentItem(currentPosition);
        photoViewPager.setOnPageChangeListener(this);
        tv_count.setText((currentPosition + 1) + "/" + imageBigs.size());
        mHandler = new Handler();
    }


    public void initReceiver() {
//        receiver = new TcpBroadcaseReceiver();
//        IntentFilter filter = new IntentFilter();
//        filter.addAction(TcpResponController.action_tcp_respon);
//        LocalBroadcastManager.getInstance(mContext).registerReceiver(receiver, filter);
    }


    @Override
    public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageSelected(int arg0) {
        // TODO Auto-generated method stub
        currentPosition = arg0;
        tv_count.setText((arg0 + 1) + "/" + imageBigs.size());

    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        Intent intent = new Intent();
        intent.putExtra(EXTRA_IMAGE_CURRENT, currentPosition);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(mContext).unregisterReceiver(receiver);
        super.onPause();
    }

    @Override
    public void finish() {
//       ActivityCompat.finishAfterTransition(this);
        super.finish();
        overridePendingTransition(0, R.anim.fade_out_fast);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        //TcpSendController.getInstance().sendLevelImage();
    }

    @Override
    public void initFragment() {

    }
}

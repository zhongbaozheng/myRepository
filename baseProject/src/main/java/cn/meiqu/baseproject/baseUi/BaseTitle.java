package cn.meiqu.baseproject.baseUi;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.TextView;

import cn.meiqu.baseproject.R;

/**
 * Created by Fatel on 16-5-9.
 */
public abstract class BaseTitle {
    public abstract void onCLickBack();

    public BaseTitle(AppCompatActivity activity, View decorView) {
        this.activity = activity;
        this.decorView = decorView;
    }

    View decorView;
    TextView mTvTitle;
    TextView mTvRight;
    Toolbar toolbar;
    AppCompatActivity activity;

    public View findViewById(int id) {
        return decorView.findViewById(id);
    }

    public void initTitle(String title) {
        initTitle(new SpannableStringBuilder(title));
    }

    public void initTitle(SpannableStringBuilder title) {
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCLickBack();
            }
        });
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvRight = (TextView) findViewById(R.id.tv_title_more);
        setTitle(title);
    }

//    public void initTitleWithNavi(String title, View.OnClickListener listener) {
//        initTitle(title);
//        toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.);
//        toolbar.setNavigationOnClickListener(listener);
//    }


    public void setTitleRight(String text, View.OnClickListener listener) {
        if (mTvRight != null) {
            mTvRight.setText(text + "");
            mTvRight.setVisibility(View.VISIBLE);
            mTvRight.setOnClickListener(listener);
        }
    }

    public void setTitle(String title) {
        mTvTitle.setText(title);
    }

    public void setTitle(SpannableStringBuilder title) {
        mTvTitle.setText(title);
    }
}

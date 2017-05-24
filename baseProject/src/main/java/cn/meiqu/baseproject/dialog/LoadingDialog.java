package cn.meiqu.baseproject.dialog;

import android.app.Dialog;
import android.content.Context;

import cn.meiqu.baseproject.R;


/**
 * Created by Fatel on 15-4-9.
 */
public class LoadingDialog extends Dialog {

    public LoadingDialog(Context context) {
        super(context, R.style.defaultDialog);
//        getWindow().setWindowAnimations(R.style.dialog_load_animation);
        getWindow().setDimAmount(0.2f);
        init();
    }

    public LoadingDialog(Context context, int theme) {
        super(context, theme);
    }

    protected LoadingDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public void init() {
        setContentView(R.layout.dialog_fatel_loading);
        setCanceledOnTouchOutside(false);
    }
}

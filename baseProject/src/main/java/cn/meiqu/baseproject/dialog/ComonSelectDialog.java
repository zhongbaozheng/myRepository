package cn.meiqu.baseproject.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import cn.meiqu.baseproject.R;
import cn.meiqu.baseproject.baseRecycle.BaseHolder;
import cn.meiqu.baseproject.util.ScreenUtil;
import cn.meiqu.baseproject.util.StringUtil;
import cn.meiqu.baseproject.view.RippleView;


public class ComonSelectDialog extends Dialog implements
        RippleView.OnRippleCompleteListener {
    public interface OnDialogItemCLickListener {
        public void onDialogItemClick(int position);
    }

    public OnDialogItemCLickListener getOnDialogItemCLickListener() {
        return onDialogItemCLickListener;
    }

    public ComonSelectDialog setOnDialogItemCLickListener(OnDialogItemCLickListener onDialogItemCLickListener) {
        this.onDialogItemCLickListener = onDialogItemCLickListener;
        return this;
    }

    private OnDialogItemCLickListener onDialogItemCLickListener;
    public Context mContext;
    String data[];
    String title;
    Button btnTitle;
    View headView;
    RippleView mRippleCansel;
    ListView mListV;

    public ComonSelectDialog(Context context) {
        super(context, R.style.defaultDialog);
        mContext = context;
        getWindow().setWindowAnimations(R.style.bottom_animation);
        getWindow().setGravity(Gravity.BOTTOM | Gravity.CENTER_VERTICAL);
//        init();
    }

    public ComonSelectDialog setData(String title, String[] data) {
        this.title = title;
        this.data = data;
        return this;
    }

    @Override
    public void show() {
        init();
        super.show();
    }

    public void refreshData(String[] data) {
        this.data = data;
        mListV.setAdapter(new ListItemAdapter());
    }

    public void init() {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.width = ScreenUtil.ScreenWidth(mContext);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        setContentView(LayoutInflater.from(mContext).inflate(
                R.layout.dialog_choice_comon, null), params);
        //
        mListV = (ListView) findViewById(R.id.listV_item);
        mRippleCansel = (RippleView) findViewById(R.id.rippleView_cancel);
        mRippleCansel.setOnRippleCompleteListener(this);
        if (!StringUtil.isEmpty(title)) {
            headView = LayoutInflater.from(mContext).inflate(R.layout.header_dialog_comon, null);
            btnTitle = (Button) headView.findViewById(R.id.btn_item);
            btnTitle.setText("" + title);
            mListV.addHeaderView(headView);
        }
        mListV.setAdapter(new ListItemAdapter());
    }


    @Override
    public void onComplete(RippleView v) {
        if (v.getId() == mRippleCansel.getId()) {
        }
        dismiss();
    }

    class ListItemAdapter extends BaseAdapter {
        public ListItemAdapter() {

        }

        @Override
        public int getCount() {
            return data.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View contain, ViewGroup parent) {
            if (contain == null) {
                contain = LayoutInflater.from(mContext).inflate(R.layout.list_dialog_comon, null);
                contain.setTag(new Holder(contain));
            }
            ((Holder) contain.getTag()).instanceView(position);
            return contain;
        }

        class Holder extends BaseHolder implements RippleView.OnRippleCompleteListener {
            private RippleView rippleItem;
            private Button btnItem;
            int position = 0;

            public Holder(View itemView) {
                super(itemView);
            }


            public void assignViews() {
                rippleItem = (RippleView) findViewById(R.id.ripple_item);
                btnItem = (Button) findViewById(R.id.btn_item);
                rippleItem.setOnRippleCompleteListener(this);

            }

            @Override
            public void instanceView(int position) {
                this.position = position;
                btnItem.setText(data[position]);
            }

            @Override
            public void onComplete(RippleView rippleView) {
                if (onDialogItemCLickListener != null) {
                    onDialogItemCLickListener.onDialogItemClick(position);
                }
                dismiss();
            }
        }
    }
}


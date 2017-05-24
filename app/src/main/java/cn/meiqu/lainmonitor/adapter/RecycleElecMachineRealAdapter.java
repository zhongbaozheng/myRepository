package cn.meiqu.lainmonitor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import cn.meiqu.baseproject.baseRecycle.BaseHolder;
import cn.meiqu.baseproject.baseRecycle.BaseOnRecycleClickListener;
import cn.meiqu.baseproject.baseRecycle.BaseRecycleAdapter;
import cn.meiqu.baseproject.view.RippleView;
import cn.meiqu.lainmonitor.R;
import cn.meiqu.lainmonitor.bean.ElecMachine;


public class RecycleElecMachineRealAdapter extends BaseRecycleAdapter {
    private Context mContent;
    private ArrayList<ElecMachine> ElecMachines;

    public RecycleElecMachineRealAdapter(Context mContent, ArrayList<ElecMachine> ElecMachines) {
        this.mContent = mContent;
        this.ElecMachines = ElecMachines;
    }

    private BaseOnRecycleClickListener clickListener;

    public BaseOnRecycleClickListener getClickListener() {
        return clickListener;
    }

    public void setClickListener(BaseOnRecycleClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(View.inflate(mContent, R.layout.recycle_elecmachine, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((Holder) holder).instanceView(position);
    }

    @Override
    public int getItemCount() {
        return ElecMachines.size();
    }

    class Holder extends BaseHolder implements RippleView.OnRippleCompleteListener {
        public Holder(View itemView) {
            super(itemView);
            itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        private TextView mTvName;
        private TextView mTvAvol;
        private TextView mTvBvol;
        private TextView mTvCvol;
        private TextView mTvAcur;
        private TextView mTvBcur;
        private TextView mTvCcur;
        private TextView mTvABvol;
        private TextView mTvBCvo;
        private TextView mTvCAvo;
        private TextView mTvApap;
        private TextView mTvBpap;
        private TextView mTvCpap;
        private TextView mTvTpap;
        private TextView mTvAprp;
        private TextView mTvBprp;
        private TextView mTvCprp;
        private TextView mTvTprp;
        private TextView mTvAppf;
        private TextView mTvBppf;
        private TextView mTvCppf;
        private TextView mTvTppf;

        public void assignViews() {
            mTvName = (TextView) findViewById(R.id.tv_name);
            mTvAvol = (TextView) findViewById(R.id.tv_Avol);
            mTvBvol = (TextView) findViewById(R.id.tv_Bvol);
            mTvCvol = (TextView) findViewById(R.id.tv_Cvol);
            mTvAcur = (TextView) findViewById(R.id.tv_Acur);
            mTvBcur = (TextView) findViewById(R.id.tv_Bcur);
            mTvCcur = (TextView) findViewById(R.id.tv_Ccur);
            mTvABvol = (TextView) findViewById(R.id.tv_ABvol);
            mTvBCvo = (TextView) findViewById(R.id.tv_BCvo);
            mTvCAvo = (TextView) findViewById(R.id.tv_CAvo);
            mTvApap = (TextView) findViewById(R.id.tv_Apap);
            mTvBpap = (TextView) findViewById(R.id.tv_Bpap);
            mTvCpap = (TextView) findViewById(R.id.tv_Cpap);
            mTvTpap = (TextView) findViewById(R.id.tv_Tpap);
            mTvAprp = (TextView) findViewById(R.id.tv_Aprp);
            mTvBprp = (TextView) findViewById(R.id.tv_Bprp);
            mTvCprp = (TextView) findViewById(R.id.tv_Cprp);
            mTvTprp = (TextView) findViewById(R.id.tv_Tprp);
            mTvAppf = (TextView) findViewById(R.id.tv_Appf);
            mTvBppf = (TextView) findViewById(R.id.tv_Bppf);
            mTvCppf = (TextView) findViewById(R.id.tv_Cppf);
            mTvTppf = (TextView) findViewById(R.id.tv_Tppf);
        }


        @Override
        public void instanceView(final int position) {
            ElecMachine elecMachine = ElecMachines.get(position);
            mTvName.setText(elecMachine.getEmdName() + "");
            mTvAvol.setText(elecMachine.getEmdAvol() + "");
            mTvBvol.setText(elecMachine.getEmdBvol() + "");
            mTvCvol.setText(elecMachine.getEmdCvol() + "");
            mTvAcur.setText(elecMachine.getEmdAcur() + "");
            mTvBcur.setText(elecMachine.getEmdBcur() + "");
            mTvCcur.setText(elecMachine.getEmdCcur() + "");
            mTvABvol.setText(elecMachine.getEmdABvol() + "");
            mTvBCvo.setText(elecMachine.getEmdBCvol() + "");
            mTvCAvo.setText(elecMachine.getEmdCAvol() + "");
            mTvApap.setText(elecMachine.getEmdApap() + "");
            mTvBpap.setText(elecMachine.getEmdBpap() + "");
            mTvCpap.setText(elecMachine.getEmdCpap() + "");
            mTvTpap.setText(elecMachine.getEmdTpap() + "");
            mTvAprp.setText(elecMachine.getEmdAprp() + "");
            mTvBprp.setText(elecMachine.getEmdBprp() + "");
            mTvCprp.setText(elecMachine.getEmdCprp() + "");
            mTvTprp.setText(elecMachine.getEmdTprp() + "");
            mTvAppf.setText(elecMachine.getEmdAppf() + "");
            mTvBppf.setText(elecMachine.getEmdBppf() + "");
            mTvCppf.setText(elecMachine.getEmdCppf() + "");
            mTvTppf.setText(elecMachine.getEmdTppf() + "");
        }

        @Override
        public void onComplete(RippleView rippleView) {
            if (getClickListener() != null) {
                getClickListener().OnRecycleItemClick(getPosition());
            }
        }
    }
}

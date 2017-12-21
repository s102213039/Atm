package com.example.yychiu.atm;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by yychiu on 2017/11/8.
 */

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder>{
    private List<Transaction> trans;

    public TransactionAdapter(List<Transaction> trans){
        this.trans=trans;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView datetv;
        private final TextView amounttv;
        private final TextView typetv;

        public ViewHolder(View itemView) {
            super(itemView);
            datetv = (TextView)itemView.findViewById(R.id.col_date);
            amounttv = (TextView)itemView.findViewById(R.id.col_amount);
            typetv = (TextView)itemView.findViewById(R.id.col_type);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.row_trans,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d("TRANS",position+"");
        Transaction tran = trans.get(position);
        holder.datetv.setText(tran.getDate());
        holder.amounttv.setText(tran.getAmount()+"");
        holder.typetv.setText(tran.getType()+"");
    }

    @Override
    public int getItemCount() {return trans.size();}
}

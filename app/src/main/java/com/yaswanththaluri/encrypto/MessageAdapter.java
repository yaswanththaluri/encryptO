package com.yaswanththaluri.encrypto;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.List;


public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {

    private View view;
    private List<Message> dataList;
    private Context context;



    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView messageType, date;
        private ImageView viewDetailed;

        public MyViewHolder(View view) {
            super(view);


            messageType = view.findViewById(R.id.messageType);
            date = view.findViewById(R.id.messageDate);
            viewDetailed = view.findViewById(R.id.view);
            

        }
    }


    public MessageAdapter(List<Message> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        context = parent.getContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        final Message msg = dataList.get(position);

        holder.messageType.setText(msg.getMessageType());
        holder.date.setText(msg.getDate());


        holder.viewDetailed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), MessageDetailed.class);


                Bundle bundle = new Bundle();
                bundle.putString("MsgType", msg.getMessageType());
                bundle.putString("MsgDate", msg.getDate());
                bundle.putString("encMsg", msg.getMessageEncrypted());
                bundle.putString("decMsg", msg.getMessageDecrypted());

                intent.putExtras(bundle);

                view.getContext().startActivity(intent);

                ((Activity)context).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }







}

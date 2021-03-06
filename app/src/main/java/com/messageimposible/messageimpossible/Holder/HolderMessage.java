package com.messageimposible.messageimpossible.Holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.messageimposible.messageimpossible.R;

public class HolderMessage extends RecyclerView.ViewHolder{

    TextView message;
    TextView time;

    public HolderMessage(View itemView) {
        super(itemView);

        message = itemView.findViewById(R.id.message_owner);
        time = itemView.findViewById(R.id.time_owner);

    }

    public TextView getMessage() {
        return message;
    }

    public void setMessage(TextView message) {
        this.message = message;
    }

    public TextView getTime() {
        return time;
    }

    public void setTime(TextView time) {
        this.time = time;
    }
}

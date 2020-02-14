package com.example.lenovo.emptypro.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.lenovo.emptypro.ModelClasses.AllApiResponse;
import com.example.lenovo.emptypro.R;
import com.example.lenovo.emptypro.Utils.CircleImageView;

import java.util.ArrayList;
import java.util.List;

public class ChatListAdap    extends RecyclerView.Adapter<ChatListAdap.MyViewHolder> {


    private List<AllApiResponse.ChatModel> allTaskList = new ArrayList<>();
    Context context;
    String TAG = "ChatListAdap ";
    private List<Boolean> showDate = new ArrayList<>();
    String updated_date = "";

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public CircleImageView iv_user_forChat;

        public TextView tv_user_forChat;

        public MyViewHolder(View view) {
            super(view);

            iv_user_forChat = view.findViewById(R.id.iv_user_forChat);
            tv_user_forChat= view.findViewById(R.id.tv_user_forChat);
         }
    }


    public ChatListAdap(Context context) {
        this.context = context;
    }

    public void addingJobsData(List<AllApiResponse.ChatModel> allTaskList) {
        this.allTaskList = allTaskList;

        showDate.clear();
        updated_date = "";
         notifyDataSetChanged();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chat_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final AllApiResponse.ChatModel allTask = allTaskList.get(position);
Log.e("ChatListAdap","ChatListAdap");
        holder.tv_user_forChat.setText(allTask.getName());


    }

    @Override
    public int getItemCount() {
        return allTaskList.size();
        //   return 20;
    }


}


package com.example.lenovo.emptypro.Adapters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.lenovo.emptypro.ModelClasses.AllApiResponse;
import com.example.lenovo.emptypro.R;

import java.util.ArrayList;
import java.util.List;

public class SubCatAdapter extends RecyclerView.Adapter<SubCatAdapter.MyViewHolder> {

    String TAG = "SubCatAdapter ";
    private List<AllApiResponse.CategoryResponse.CategoryMainListModel> categoryMainListModels= new ArrayList<>();
    Context context;

    String updated_date = "";
    int TABSELECTED;
    private List<Boolean> showDate = new ArrayList<>();

    public class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView tv_catTxt;
        public ImageView iv_img;

        public LinearLayout rl_other, rl_me;

        public MyViewHolder(View view) {
            super(view);
            tv_catTxt= view.findViewById(R.id.tv_mainCatTxt);
            iv_img= view.findViewById(R.id.iv_mainCatImg);
        }
    }


    public SubCatAdapter(Context context) {
        this.context = context;
    }

    public void addingData(List<AllApiResponse.CategoryResponse.CategoryMainListModel> allCatList) {
        this.categoryMainListModels= allCatList;
        showDate.clear();
        Log.e(TAG, "addingData"  );

        notifyDataSetChanged();
    }


    @Override
    public SubCatAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_top_cate, parent, false);
        Log.e(TAG, "onCreateViewHolder"  );

        return new SubCatAdapter.MyViewHolder(itemView);
    }

    String outputText = "";

    @Override
    public void onBindViewHolder(SubCatAdapter.MyViewHolder holder, final int position) {
        final AllApiResponse.CategoryResponse.CategoryMainListModel allTask = categoryMainListModels.get(position);

        Log.e(TAG, "title" + allTask.getTitle() + "   url=    " + allTask.getImgUrl());

        holder.tv_catTxt.setText(allTask.getTitle());
        //holder.iv_img.


        try {
            String encodedDataString = allTask.getImgUrl().replace("data:image/jpeg;base64,", "");
            byte[] imageAsBytes = Base64.decode(encodedDataString.getBytes(), 0);
            holder.iv_img.setImageBitmap(BitmapFactory.decodeByteArray(
                    imageAsBytes, 0, imageAsBytes.length));
            //Picasso.with(context).load(encodedDataString.replaceAll(" ", "%20")) .placeholder(R.drawable.default_pic)  .error(R.drawable.default_pic).into(holder.img_job);

            /*Picasso.with(context)
                    .load(encodedDataString)
                    .transform(new CircleTransform())
                    .resize(50, 50)
                    .placeholder(R.drawable.default_pic)
                    .error(R.drawable.default_pic)
                    .into(holder.img_job);*/
            Log.e(TAG + "try profile pic", encodedDataString);

        } catch (Exception e) {
            Log.e(TAG + " profile pic", "excep " + e.toString());

        }
    }

    @Override
    public int getItemCount() {
        return categoryMainListModels.size();
    }

}

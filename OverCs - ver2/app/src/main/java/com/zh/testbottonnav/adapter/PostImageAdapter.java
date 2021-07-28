package com.zh.testbottonnav.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.zh.testbottonnav.HomePost;
import com.zh.testbottonnav.R;

import java.util.List;

public class PostImageAdapter extends RecyclerView.Adapter<PostImageAdapter.ViewHolder> {

    private List<String> mImageList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView postImage;

        public ViewHolder(View view) {
            super(view);
            postImage = (ImageView) view.findViewById(R.id.post_image);
        }

    }

    public PostImageAdapter(List<String> ImgList) {
        mImageList = ImgList;
    }

    public void addList(List<String> ImgList){
        this.mImageList = ImgList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_img, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String url = mImageList.get(position);
        if (url.equals("")) {
            holder.postImage.setImageResource(R.drawable.failtoload);
        } else {
            // Transformation transformation = new RoundedTransformationBuilder().cornerRadiusDp(1,13).cornerRadiusDp(0,13).oval(false).build();
            Picasso.get().load(url).placeholder(R.drawable.failtoload).centerCrop().fit().into(holder.postImage);
        }
    }

    @Override
    public int getItemCount() {
        return mImageList.size();
    }
}

package com.zh.testbottonnav.adapter;

import android.content.ComponentName;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.zh.testbottonnav.HomePost;
import com.zh.testbottonnav.R;
import com.zh.testbottonnav.net.Post;

import java.util.List;

public class HomePostAdapter extends RecyclerView.Adapter<HomePostAdapter.ViewHolder> {

    private List<Post> mPostList;
    private boolean is_gridview = false;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View postView;
        ImageView postImage;
        TextView postName;
        TextView postDescription;
        ImageView typeImage;

        public ViewHolder(View view) {
            super(view);
            postView = view;
            postImage = (ImageView) view.findViewById(R.id.homePost_image);
            postName = (TextView) view.findViewById(R.id.homePost_name);
            postDescription = (TextView) view.findViewById(R.id.homePost_desc);
            typeImage = (ImageView) view.findViewById(R.id.homePost_type);
        }

    }

    public void setIs_gridview(boolean is_gridview) {
        this.is_gridview = is_gridview;
    }

    public HomePostAdapter(List<Post> postList) {
        mPostList = postList;
    }

    public void addList(List<Post> postList){
        this.mPostList = postList;
        notifyDataSetChanged();
    }

    @Override

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homepost_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);


        /*
        holder.fruitView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Fruit fruit = mFruitList.get(position);
                Intent i = new Intent();
                i.setComponent(new ComponentName("com.zh.testbottonnav", "com.zh.testbottonnav.DetailContent"));
                view.getContext().startActivity(i);
                //Toast.makeText(view.getContext(), "you clicked view" + fruit.getName(), Toast.LENGTH_SHORT).show();
            }
        });

         */

        /*

        holder.postImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                HomePost post = mPostList.get(position);
                Intent i = new Intent();
                i.setComponent(new ComponentName("com.zh.testbottonnav", "com.zh.testbottonnav.DetailContent"));
                view.getContext().startActivity(i);
            }
        });

         */

        holder.postView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                int position = holder.getAdapterPosition();
                if (is_gridview) {
                    position -= 1;
                }
                Post post = mPostList.get(position);
                i.putExtra("post", post);
                i.setComponent(new ComponentName("com.zh.testbottonnav", "com.zh.testbottonnav.PostDetail"));
                view.getContext().startActivity(i);
            }
        });

        return holder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Post post = mPostList.get(position);
        if (post.getImage1() == null || post.getImage1().equals("")) {
            holder.postImage.setImageResource(R.drawable.failtoload);
        } else {
            Transformation transformation = new RoundedTransformationBuilder().cornerRadiusDp(1,13).cornerRadiusDp(0,13).oval(false).build();
            Picasso.get().load(post.getImage1()).placeholder(R.drawable.failtoload).centerCrop().fit().transform(transformation).into(holder.postImage);
        }
        if (post.getType() == null) {
            holder.typeImage.setImageResource(R.drawable.typenull);
        } else if (post.getType().equals("REQUEST")) {
            holder.typeImage.setImageResource(R.drawable.request);
        } else if (post.getType().equals("HELP")) {
            holder.typeImage.setImageResource(R.drawable.help);
        }
        holder.postDescription.setText(post.getDescription());
        holder.postName.setText(post.getTitle());
    }

    @Override
    public int getItemCount() {
        return mPostList.size();
    }
}

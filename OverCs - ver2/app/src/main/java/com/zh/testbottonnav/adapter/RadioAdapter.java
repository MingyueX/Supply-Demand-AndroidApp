package com.zh.testbottonnav.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;

import androidx.recyclerview.widget.RecyclerView;

import com.zh.testbottonnav.R;

import java.util.List;

public abstract class RadioAdapter<T> extends RecyclerView.Adapter<RadioAdapter.ViewHolder> {
    public String mSelectedText = "";
    public int mSelectedItem = -1;
    public List<T> mItems;
    private Context mContext;

    public RadioAdapter(Context context, List<T> items) {
        mContext = context;
        mItems = items;
    }

    @Override
    public void onBindViewHolder(RadioAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.cateBox.setChecked(i == mSelectedItem);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public int getmSelectedItem() {return mSelectedItem; }

    public String getSelectedText() {
        return mSelectedText;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        final View view = inflater.inflate(R.layout.category_item, viewGroup, false);
        return new ViewHolder(view);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        // public RadioButton mRadio;
        // public TextView mText;
        public CheckBox cateBox;

        public ViewHolder(final View inflate) {
            super(inflate);
            // mText = (TextView) inflate.findViewById(R.id.text);
            cateBox = (CheckBox) inflate.findViewById(R.id.item_frameRb);
            View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSelectedItem = getAdapterPosition();
                    mSelectedText = cateBox.getText().toString();
                    notifyDataSetChanged();
                }
            };
            // itemView.setOnClickListener(clickListener);
            cateBox.setOnClickListener(clickListener);
        }
    }
}
package com.zh.testbottonnav.adapter;

import android.content.Context;

import java.util.List;

public class CategoryAdapter extends RadioAdapter<String> {
    public CategoryAdapter(Context context, List<String> items){
        super(context, items);
    }

    @Override
    public void onBindViewHolder(RadioAdapter.ViewHolder viewHolder, final int i) {
        super.onBindViewHolder(viewHolder, i);
        viewHolder.cateBox.setText(mItems.get(i));
    }

    @Override
    public String getSelectedText() {
        return super.getSelectedText();
    }

    @Override
    public int getmSelectedItem() {
        return super.getmSelectedItem();
    }
}
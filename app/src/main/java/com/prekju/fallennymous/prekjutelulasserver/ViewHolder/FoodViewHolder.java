package com.prekju.fallennymous.prekjutelulasserver.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.prekju.fallennymous.prekjutelulasserver.Common.Common;
import com.prekju.fallennymous.prekjutelulasserver.Interface.ItemClickListener;
import com.prekju.fallennymous.prekjutelulasserver.R;

/**
 * Created by fallennymous on 14/08/2018.
 */

public class FoodViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener,
        View.OnCreateContextMenuListener{
    public TextView food_name;
    public ImageView food_image;

    private ItemClickListener itemClickListener;

    public FoodViewHolder(View itemView) {
        super(itemView);

        food_name = itemView.findViewById(R.id.food_name);
        food_image = itemView.findViewById(R.id.food_image);

        itemView.setOnClickListener(this);
        itemView.setOnCreateContextMenuListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(), false);
    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        contextMenu.setHeaderTitle("Pilih");

        contextMenu.add(0, 0, getAdapterPosition(), Common.UPDATE);
        contextMenu.add(0, 1, getAdapterPosition(), Common.DELETE);
    }
}


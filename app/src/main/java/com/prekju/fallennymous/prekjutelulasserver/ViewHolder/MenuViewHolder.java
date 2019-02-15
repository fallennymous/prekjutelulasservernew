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

public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener{
    public TextView txtMenuName;
    public ImageView imageMenu;

    private ItemClickListener itemClickListener;

    public MenuViewHolder(View itemView) {
        super(itemView);

        txtMenuName = itemView.findViewById(R.id.menu_name);
        imageMenu = itemView.findViewById(R.id.menu_image);

        itemView.setOnCreateContextMenuListener(this);
        itemView.setOnClickListener(this);

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

       // contextMenu.add(0, 0, getAdapterPosition(), Common.UPDATE);
        contextMenu.add(0, 1, getAdapterPosition(), Common.DELETE);
    }
}


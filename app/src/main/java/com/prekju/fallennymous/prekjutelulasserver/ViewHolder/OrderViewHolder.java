package com.prekju.fallennymous.prekjutelulasserver.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.TextView;

import com.prekju.fallennymous.prekjutelulasserver.Interface.ItemClickListener;
import com.prekju.fallennymous.prekjutelulasserver.R;

/**
 * Created by fallennymous on 02/09/2018.
 */

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener {

    public TextView txtOrderId, txtOrderStatus, txtOrderPhone, txtOrAddress;

    private ItemClickListener itemClickListener;

    public OrderViewHolder(View itemView) {
        super(itemView);

        txtOrderId = itemView.findViewById(R.id.order_id);
        txtOrderStatus = itemView.findViewById(R.id.order_status);
        txtOrderPhone = itemView.findViewById(R.id.order_phone);
        txtOrAddress = itemView.findViewById(R.id.order_address);

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
        contextMenu.setHeaderTitle("pilih");

        contextMenu.add(0,0,getAdapterPosition(),"perbaharui");
        contextMenu.add(0,1,getAdapterPosition(),"hapus");

    }
}


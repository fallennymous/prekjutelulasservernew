package com.prekju.fallennymous.prekjutelulasserver;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.prekju.fallennymous.prekjutelulasserver.Common.Common;
import com.prekju.fallennymous.prekjutelulasserver.Interface.ItemClickListener;
import com.prekju.fallennymous.prekjutelulasserver.Model.MyResponse;
import com.prekju.fallennymous.prekjutelulasserver.Model.Notification;
import com.prekju.fallennymous.prekjutelulasserver.Model.Order;
import com.prekju.fallennymous.prekjutelulasserver.Model.Request;
import com.prekju.fallennymous.prekjutelulasserver.Model.Sender;
import com.prekju.fallennymous.prekjutelulasserver.Model.Token;
import com.prekju.fallennymous.prekjutelulasserver.Remote.APIService;
import com.prekju.fallennymous.prekjutelulasserver.ViewHolder.OrderViewHolder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderStatus extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Request,OrderViewHolder> adapter;

    FirebaseDatabase db;
    DatabaseReference requests;

    MaterialSpinner spinner;
    APIService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        //Firebase
        db = FirebaseDatabase.getInstance();
        requests = db.getReference("Requests");

        //init service
        mService = Common.getFCMClient();

        //init
        recyclerView = (RecyclerView)findViewById(R.id.listOrders);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadOreders(); //load all orders
    }

    private void loadOreders() {
        adapter = new FirebaseRecyclerAdapter<Request, OrderViewHolder>(
                Request.class,
                R.layout.order_layout,
                OrderViewHolder.class,
                requests
        ) {
            @Override
            protected void populateViewHolder(OrderViewHolder viewHolder, final Request model, int position) {

                viewHolder.txtOrderId.setText(adapter.getRef(position).getKey());
                viewHolder.txtOrderStatus.setText(Common.convertCodeStatus(model.getStatus()));
                viewHolder.txtOrAddress.setText(model.getAddress());
                viewHolder.txtOrderPhone.setText(model.getPhone());

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent trackingOrder = new Intent(OrderStatus.this,TrackingOrder.class);
                        Common.currentRequest = model;
                        startActivity(trackingOrder);
                    }
                });
            }
        };
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getTitle().equals(Common.UPDATE))
            showUpdateDialog(adapter.getRef(item.getOrder()).getKey(),adapter.getItem(item.getOrder()));
        else if(item.getTitle().equals(Common.DELETE))
            deleteOrder(adapter.getRef(item.getOrder()).getKey());
        return super.onContextItemSelected(item);
    }

    private void deleteOrder(String key) {
        requests.child(key).removeValue();
    }

    private void showUpdateDialog(String key, final Request item) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(OrderStatus.this);
        alertDialog.setTitle("Order Update");
        alertDialog.setMessage("pilih status");

        LayoutInflater inflater = this.getLayoutInflater();
        final View view = inflater.inflate(R.layout.update_order_layout,null);

        spinner = (MaterialSpinner)view.findViewById(R.id.statusSpinner);
        spinner.setItems("Pesanan diproses","Pesanan dalam perjalanan","pesanan diterima");

        alertDialog.setView(view);

        final String localkey = key;
        alertDialog.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                item.setStatus(String.valueOf(spinner.getSelectedIndex()));

                requests.child(localkey).setValue(item);
                
                sendOrderStatusToUser(localkey,item);
            }
        });

        alertDialog.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        alertDialog.show();
    }

    private void sendOrderStatusToUser(final String key, final Request item) {
        DatabaseReference tokens = db.getReference("Tokens");
        tokens.orderByKey().equalTo(item.getPhone())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapShot:dataSnapshot.getChildren())
                        {
                            Token token = postSnapShot.getValue(Token.class);
                            Notification notification = new Notification("Prekju","Pesanan kamu "+key+" telah diperbaharui");
                            Sender content = new Sender(token.getToken(),notification);

                            mService.sendNotification(content)
                                    .enqueue(new Callback<MyResponse>() {
                                        @Override
                                        public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                            if (response.body().success == 1) {
                                                Toast.makeText(OrderStatus.this, "pesanan diperbaharui", Toast.LENGTH_SHORT).show();

                                            } else {
                                                Toast.makeText(OrderStatus.this, "pesanan diperbaharui tapi tidak dapat mengirim notifikasi!", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<MyResponse> call, Throwable t) {
                                            Log.e("ERROR", t.getMessage());
                                        }
                                    });

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
}

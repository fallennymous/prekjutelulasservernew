package com.prekju.fallennymous.prekjutelulasserver;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prekju.fallennymous.prekjutelulasserver.Common.Common;
import com.prekju.fallennymous.prekjutelulasserver.Model.User;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.hoang8f.widget.FButton;

/**
 * Created by fallennymous on 14/08/2018.
 */

public class SignIn extends AppCompatActivity {
    @BindView(R.id.edtPhone)
    MaterialEditText edtPhone;
    @BindView(R.id.edtPassword) MaterialEditText edtPassword;
    @BindView(R.id.btnSignIn)
    FButton btnSignIn;

    FirebaseDatabase db;
    DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);

        // Init Firebase
        db = FirebaseDatabase.getInstance();
        users = db.getReference("User");

        // sign in
        onClickSignIn();
    }

    /**
     * Clicking sign in button
     */
    private void onClickSignIn() {
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInUser(edtPhone.getText().toString(), edtPassword.getText().toString());
            }
        });
    }

    /**
     * Sign in to app
     * @param phone
     * @param password
     */
    private void signInUser(final String phone, final String password) {
        final ProgressDialog mDialog = new ProgressDialog(SignIn.this);
        mDialog.setMessage("Tunggu Sebentar");
        mDialog.show();


        final String localPhone = phone;
        final String LocalPassword = password;
        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Check if user is exits
                if(dataSnapshot.child(localPhone).exists()) {
                    mDialog.dismiss();
                    // get user and set phone
                    User user = dataSnapshot.child(phone).getValue(User.class);
                    user.setPhone(localPhone);
                    // check user is staff
                    if(Boolean.parseBoolean(user.getIsStaff())) {
                        // check password
                        if(user.getPassword().equals(password)) {
                            // Login ok
                            Intent homeIntent = new Intent(SignIn.this, Home.class);
                            Common.currentUser = user;
                            startActivity(homeIntent);
                            finish();
                        } else {
                            Toast.makeText(SignIn.this, "Password Salah", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(SignIn.this, "Tolong Login dengan Akun Admin", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    mDialog.dismiss();
                    Toast.makeText(SignIn.this, "User Tidak Tersedia", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}


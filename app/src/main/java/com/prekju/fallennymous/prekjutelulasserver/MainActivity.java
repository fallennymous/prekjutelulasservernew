package com.prekju.fallennymous.prekjutelulasserver;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.hoang8f.widget.FButton;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.btnSignIn)
    FButton btnSignIn;
    @BindView(R.id.txtSlogan)
    TextView txtSlogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // set typeface
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/NABILA.TTF");
        txtSlogan.setTypeface(face);

        // sign in
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signIn = new Intent(MainActivity.this, SignIn.class);
                startActivity(signIn);
            }
        });
    }
}

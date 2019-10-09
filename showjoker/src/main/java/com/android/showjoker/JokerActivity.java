package com.android.showjoker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class JokerActivity extends AppCompatActivity {

    private static String KEY_JOKER = "KEY_JOKER";
    private TextView tv_joker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joker);

        tv_joker = findViewById(R.id.tv_joker);

        if (getIntent() != null) {
            String value =getIntent().getStringExtra(KEY_JOKER);
            tv_joker.setText(value);
        }

    }

    public static Intent newIntent(Context ctx, String joker) {
        return new Intent(ctx, JokerActivity.class).putExtra(KEY_JOKER,joker);
    }
}

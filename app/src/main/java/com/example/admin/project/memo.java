package com.example.admin.project;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class memo extends Activity {
    EditText memo;
    Button btnFin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);

        memo = (EditText)findViewById(R.id.memo);
        btnFin = (Button)findViewById(R.id.btnFin);
        btnFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences activity = getPreferences(MODE_PRIVATE);
        memo.setText(activity.getString("memo",null));
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences activity = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = activity.edit();

        editor.putString("memo",memo.getText().toString());
        editor.commit();
    }
}

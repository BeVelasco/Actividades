package com.integraideassoluciones.actividades;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class escanear extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.escanear);

        final Button btnescan = findViewById(R.id.btnesc);

        btnescan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iactivityscan = new Intent(escanear.this, ScanActivity.class);
                startActivity(iactivityscan);
            }
        });
    }
}

package com.integraideassoluciones.actividades;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Ajustes extends AppCompatActivity {

    String valip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        TextView text_ip = (TextView)findViewById(R.id.dip);
        registro.classGlobal global = (registro.classGlobal) getApplication();
        valip = global.getIp_save();

        text_ip.setText(valip);
    }

    public void guardar(View view){
        TextView text_ip = (TextView) findViewById(R.id.dip);

        registro.classGlobal global = (registro.classGlobal) getApplication();

        if (!text_ip.getText().toString().equals(global.getIp_save().toString())) {
            global.setIp_save(text_ip.getText().toString());
        }

        Toast okresult = Toast.makeText(this, "Guardado", Toast.LENGTH_SHORT);
        okresult.show();
    }

    public void cancel(View view){
        setResult(RESULT_OK);
        finish();
    }
}

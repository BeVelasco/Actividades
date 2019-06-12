package com.integraideassoluciones.actividades;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class registro extends AppCompatActivity {

    public static String resultado_escan;

    public static class classGlobal extends Application{
        private String ip_save;

        public classGlobal(){
            super();
        }

        public void setIp_save(String ip){
            ip_save = ip;
            SharedPreferences conf = getSharedPreferences("config.txt", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = conf.edit();
            editor.putString("ddir",ip_save);
            editor.commit();
        }

        public String getIp_save(){
            SharedPreferences conf = getSharedPreferences("config.txt", Context.MODE_PRIVATE);
            String ip_recuperado = conf.getString("ddir","localhost");
            return ip_recuperado;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        int versionCode = BuildConfig.VERSION_CODE;
        TextView tver = findViewById(R.id.tversion);
        //tver.setText(versionCode);

        final Button btn_registro = findViewById(R.id.bregistro);

        btn_registro.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iescan = new Intent(getApplicationContext(), escanear.class);
                startActivity(iescan);
            }
        });

        final ImageButton bajuste = findViewById(R.id.bajuste);

        bajuste.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Ajustes.class);
                startActivity(intent);
            }
        });
    }

}

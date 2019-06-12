package com.integraideassoluciones.actividades;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class datos extends AppCompatActivity {

    String enestatus;
    String endesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos);

        EditText texted = (EditText)findViewById(R.id.edempleado);
        texted.setText(registro.resultado_escan);

        final Spinner spx = (Spinner) findViewById(R.id.select_estatus);
        String[] estatus = {"Seleccione un estatus", "Inicio", "Fin"};
        spx.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, estatus));

        final Spinner spdesc = (Spinner) findViewById(R.id.select_actividad);
        String[] sdesc = {"Seleccione una actividad","Limpieza", "Acomodo", "Entrega", "Capacitacion", "Apoyo", "Pavonado", "Programacion"};
        spdesc.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, sdesc));

        final String[] datosx = {spx.getItemAtPosition(spx.getSelectedItemPosition()).toString()};
        final String[] datosd = {spdesc.getItemAtPosition(spdesc.getSelectedItemPosition()).toString()};

        spdesc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                datosd[0] = spdesc.getItemAtPosition(spdesc.getSelectedItemPosition()).toString();
                if(datosd[0].equals("Seleccione una actividad")){
                    endesc = " ";
                }else{
                    endesc = datosd[0];
                    try{
                        endesc = URLEncoder.encode(endesc,"UTF-8");
                    }catch(Exception e){

                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast toast = Toast.makeText(getApplicationContext(), "Actividad no seleccionada, Seleccione Actividad", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        spx.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                datosx[0] = spx.getItemAtPosition(spx.getSelectedItemPosition()).toString();
                if(datosx[0].equals("Seleccione un estatus")){
                    enestatus = " ";
                } else {
                    enestatus = datosx[0];
                    try{
                        enestatus = URLEncoder.encode(enestatus,"UTF-8");
                    }catch(Exception e){

                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast toast = Toast.makeText(getApplicationContext(), "Estatus no seleccionado, Seleccionar Estatus", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    public void envia(View v) throws Exception {

        ///////////////////////////////////////////////////////
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.
                Builder().permitNetwork().build());
        ///////////////////////////////////////////////////////

        String contenido = "";
        HttpURLConnection conexion = null;

        EditText d1 = (EditText)findViewById(R.id.edempleado);
        String datemple = d1.getText().toString();

        String devuelve = "";
        Context context = getApplicationContext();

        Spinner spx = (Spinner)findViewById(R.id.select_estatus);
        String selecionestatus = spx.getSelectedItem().toString();

        Spinner spd = (Spinner)findViewById(R.id.select_actividad);
        String selecionactividad = spd.getSelectedItem().toString();
        if(selecionactividad=="Seleccione una actividad"){
            Toast toast = Toast.makeText(getApplicationContext(), "Actividad no seleccionada, Seleccionar Actividad", Toast.LENGTH_SHORT);
            toast.show();
        }else if(selecionestatus=="Seleccione un estatus"){
            Toast toast = Toast.makeText(getApplicationContext(), "Estatus no seleccionado, Seleccionar Estatus", Toast.LENGTH_SHORT);
            toast.show();
        }else {

            registro.classGlobal global = (registro.classGlobal) getApplication();


            contenido = "http://"+global.getIp_save()+"/api/app/acti";

            JSONObject jo = new JSONObject();
            jo.put("user", datemple);
            jo.put("act", selecionestatus);
            jo.put("desc", selecionactividad);

            URL url = new URL(contenido);

            try {
                conexion = (HttpURLConnection) url.openConnection();
                conexion.setDoOutput(true);
                conexion.setRequestMethod("PUT");
                conexion.setRequestProperty("Content-Type", "application/json");

                JSONObject jsonParam = new JSONObject();
                jsonParam.put("data", jo.toString());

                Log.i("JSON", jo.toString());

                DataOutputStream os = null;
                try {
                    os = new DataOutputStream(conexion.getOutputStream());
                }catch (IOException e){
                    Log.i("Stream", e.getMessage());
                }
                //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                os.writeBytes(jo.toString());

                os.flush();
                os.close();

                Log.i("STATUS", String.valueOf(conexion.getResponseCode()));
                Log.i("MSG" , conexion.getResponseMessage());

                String sss = conexion.getResponseMessage();

                if (conexion.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    devuelve = conexion.getResponseMessage();

                    StringBuilder result = new StringBuilder();
                    InputStream in = new BufferedInputStream(conexion.getInputStream());

                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    Log.i("Result ", result.toString());
                    JSONObject obj = new JSONObject(result.toString());

                    Toast t2 = Toast.makeText(this, "NF: " + getmessjson(obj), Toast.LENGTH_LONG);
                    t2.show();


                    Intent intregreso = new Intent(getApplicationContext(), registro.class);
                    startActivity(intregreso);
                    finish();

                } else {
                    devuelve = conexion.getResponseMessage();

                    StringBuilder result = new StringBuilder();
                    InputStream in = new BufferedInputStream(conexion.getInputStream());

                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    JSONObject obj = new JSONObject(line);

                    Toast t2 = Toast.makeText(this, "rr: " + getmessjson(obj), Toast.LENGTH_SHORT);
                    t2.show();
                }

            } catch (Exception e) {
                Toast t1 = Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT);
                t1.show();
            }
        }
    }

    private String getmessjson(JSONObject obj){
        String messresp = "";
        try {
            Integer urlresponse = obj.getInt("status");

            if (urlresponse == 10) {
                messresp = "Actividad ya registrada";
            } else if (urlresponse == 1) {
                messresp = "Registro Exitoso";
            } else if (urlresponse == 0) {
                messresp = "Error : " + obj.getString("ultact");
            } else if (urlresponse == 11) {
                messresp = "Actividad Finalizada";
            }
        } catch (Exception ex){}

        return messresp;
    }
}

package com.example.ednaldojunior89.resoftsystemas;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Ednaldo Junior on 03/05/2018.
 */

public class enviaPedido  extends AsyncTask<String, Void, String>{

    String ss;
    String email_login_1;
    private Context context;

    public enviaPedido(Context context) {
        this.context = context;
    }

    protected void onPreExecute() {

    }

    @Override
    public String doInBackground(String... arg0) {
        String id1 = arg0[0];
        String email_login = arg0[1];
        String id_mesa  = Main2Activity_mesas.cod_1_result;



        String link;
        String data;
        BufferedReader bufferedReader;
        String result;

        try {
           // email_login_1 = new Main2Activity_scanner().getEmail_login();

            data = "?id1=" + URLEncoder.encode(id1, "UTF-8");
            data += "&email_login=" + URLEncoder.encode(email_login, "UTF-8");
            data += "&id_mesa=" + URLEncoder.encode(id_mesa, "UTF-8");

            link = "http://192.168.100.136/db_1/pedido.php" + data;
            URL url = new URL(link);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            result = bufferedReader.readLine();
            return result;
        } catch (Exception e) {
            return new String("Exception: " + e.getMessage());
        }

    }

    @Override
    protected void onPostExecute(String result) {
        String jsonStr = result;
        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);
                String query_result = jsonObj.getString("query_result");
                if (query_result.equals("SUCCESS")) {
                    Toast.makeText(context, "Data inserted successfully. Signup successful.", Toast.LENGTH_SHORT).show();
                } else if (query_result.equals("FAILURE")) {
                    Toast.makeText(context, "Data could not be inserted. Signup failed.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Couldn't connect to remote database.", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(context, "Error parsing JSON data.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
        }
    }
}

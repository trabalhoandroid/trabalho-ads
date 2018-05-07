package com.example.ednaldojunior89.resoftsystemas;

/**
 * Created by Ednaldo Junior on 24/04/2018.
 */


/**
 * Created by Ednaldo Junior on 17/04/2018.
 */
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

public class SignupActivity extends AsyncTask<String, Void, String> {

    private Context context;

    public SignupActivity(Context context) {
        this.context = context;
    }

    protected void onPreExecute() {

    }

    @Override
    public String doInBackground(String... arg0) {
        String nome = arg0[0];
        String email = arg0[1];
        String celular = arg0[2];
        String cpf = arg0[3];
        String senha = arg0[4];
        String endereco = arg0[5];


        String link;
        String data;
        BufferedReader bufferedReader;
        String result;

        try {
            data = "?nome=" + URLEncoder.encode(nome, "UTF-8");
            data += "&email=" + URLEncoder.encode(email, "UTF-8");
            data += "&celular=" + URLEncoder.encode(celular, "UTF-8");
            data += "&cpf=" + URLEncoder.encode(cpf, "UTF-8");
            data += "&senha=" + URLEncoder.encode(senha, "UTF-8");
            data += "&endereco=" + URLEncoder.encode(endereco, "UTF-8");

            link = "http://192.168.100.136/db_1/signup.php" + data;
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
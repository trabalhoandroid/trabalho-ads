

package com.example.ednaldojunior89.resoftsystemas;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Main2Activity_mesas extends AppCompatActivity implements Serializable {

    Button bt_chama_scanner;
    //String cod = "junior";
    public static String email_login = null;
    private boolean loggedIn = false;
    private SharedPreferences.Editor editor;
    public static String cod_1_result;
    String cod_1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2_mesas);
        SharedPreferences sharedPreferences = Main2Activity_mesas.this.getSharedPreferences(busca_mesa.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();


        bt_chama_scanner = (Button)  findViewById(R.id.bt_chama_scanner);
        final Activity activity = this;

        bt_chama_scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Escaneando Código");
                integrator.setCameraId(0);
                integrator.initiateScan();
                login();
            }
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        email_login = bundle.getString("guardainfo1");
        //mostrar.setText( email_login );

    }
public String getCod_1_result(){
    return cod_1_result;
}
public void setCod_1_result(String cod_1_result){
    this.cod_1_result = cod_1_result;
}

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (result != null){
            cod_1_result = result.getContents();
            if (result.getContents().equals(cod_1_result)){

                cod_1 =result.getContents();
                  login();
                alert(result.getContents());
            }else{
                alert("Cancelado");
            }

        }else {

            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    private void alert(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG ).show();
    }


    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences(busca_mesa.SHARED_PREF_NAME, Context.MODE_PRIVATE);



    //      if(loggedIn){
         //SE ESTIVER LOGADO ENTÃO AO ENTRAR NA APLICAÇÃO VAI PARA TELA SEGUINTE
        //    Intent intent = new Intent(Main2Activity_mesas.this, Main2Activity_scanner.class);
      //    startActivity(intent);
        //}
    }
    //Busca se existe uma mesa com esse codigo cadastrado
    private void login(){

        StringRequest stringRequest = new StringRequest( Request.Method.POST, busca_mesa.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(response.equalsIgnoreCase(busca_mesa.LOGIN_SUCCESS)){
                            editor.putBoolean(busca_mesa.LOGGEDIN_SHARED_PREF, true);
                            editor.putString(busca_mesa.USUARIO_SHARED_PREF, cod_1);
                            editor.commit();
                            //Toast.makeText( Main2Activity_mesas.this, busca_mesa.CODIGO, Toast.LENGTH_SHORT ).show();
                             intent_1();
                            //intent_2();
                        }else{
                            Toast.makeText(Main2Activity_mesas.this, "Código inexistente ou a mesa está ocupada!", Toast.LENGTH_LONG).show();
                            //Toast.makeText( Main2Activity_mesas.this, busca_mesa.USUARIO_SHARED_PREF, Toast.LENGTH_SHORT ).show();
                        }
                    }
                },


                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {}
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put(busca_mesa.CODIGO, cod_1);
                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }



    public void intent_1(){
        Intent intent = new Intent(Main2Activity_mesas.this, Main2Activity_cardapio.class);
        //SE O LOGIN E SENHA FOR IGUAL AO QUE CONSTA NA TABELA DO BANCO DE DADOS ENTÃO VAI PARA OUTRA TELA
        startActivity(intent);
    }

}
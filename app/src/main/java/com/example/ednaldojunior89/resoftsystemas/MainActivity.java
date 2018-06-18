package com.example.ednaldojunior89.resoftsystemas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements Serializable{

    Button bt_chama_mesas;
    EditText edt_email;
    EditText edt_senha;
    TextView text_nome;
    private boolean loggedIn = false;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = MainActivity.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();



        this.text_nome = (TextView) findViewById(R.id.text_nome);
        this.edt_senha = (EditText) findViewById(R.id.edt_senha);
        this.bt_chama_mesas = (Button) findViewById(R.id.bt_chama_mesas);
        this.edt_email = (EditText) findViewById(R.id.edt_email);




            bt_chama_mesas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    login();
                }
            });
        }


    public void chama_cadastro(View view) {

        Intent secondActivity = new Intent(this, cadastro.class);

        startActivity(secondActivity);

    }


    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);



        if(loggedIn){
            // SE ESTIVER LOGADO ENTÃO AO ENTRAR NA APLICAÇÃO VAI PARA TELA SEGUINTE
            Intent intent = new Intent(MainActivity.this, Main2Activity_scanner.class);
            startActivity(intent);
        }
    }


    private void login(){
        final String usuario = edt_email.getText().toString().trim();
        final String senha = edt_senha.getText().toString().trim();


        StringRequest stringRequest = new StringRequest( Request.Method.POST, Config.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equalsIgnoreCase(Config.LOGIN_SUCCESS)){
                            editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, true);
                            editor.putString(Config.USUARIO_SHARED_PREF, usuario);
                            editor.putString(Config.USUARIO_SHARED_PREF, senha);
                            editor.commit();

                            intent_1();
                            //intent_2();
                        }else{
                            Toast.makeText(MainActivity.this, "Usuário ou senha inválidos!", Toast.LENGTH_LONG).show();
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
                params.put(Config.USUARIO, usuario);
                params.put(Config.SENHA, senha);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
public void intent_1(){
    Intent intent = new Intent(MainActivity.this, Main2Activity_mesas.class);
    //SE O LOGIN E SENHA FOR IGUAL AO QUE CONSTA NA TABELA DO BANCO DE DADOS ENTÃO VAI PARA OUTRA TELA

    String email = edt_email.getText().toString();

    //Collections.shuffle(array);
    String texto = email;
    Bundle bundle = new Bundle();
    bundle.putString("guardainfo1", texto);
    intent.putExtras(bundle);
    startActivity(intent);
    startActivity(intent);
}

}


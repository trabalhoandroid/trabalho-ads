package com.example.ednaldojunior89.resoftsystemas;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Comanda extends Activity implements AdapterView.OnItemClickListener {

    private AdapterListViewComanda adapterListView;
    private ListView listView;
    String it;
    private TextView total;
    private TextView tx;
    public String email_login;
    Button pedir_conta;
    private String mesa_1;
    private boolean loggedIn = false;
    String encerrado;
    private SharedPreferences.Editor editor;
    public static String cod_1_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comanda);


        SharedPreferences sharedPreferences = Comanda.this.getSharedPreferences(solicita_encerra.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        mesa_1 = Main2Activity_mesas.cod_1_result;

        pedir_conta = (Button)  findViewById(R.id.login2);
        final Activity activity = this;



        pedir_conta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                login2();
            }
        });



        //Pega a referencia do ListView
        listView = (ListView) findViewById(R.id.list_comanda);
        //Pega a referencia do ListView
        tx = (TextView) findViewById(R.id.tx);
        //Define o Listener quando alguem clicar no item.
        listView.setOnItemClickListener(this);
        this.total = (TextView) findViewById( R.id.total );

        createListView();

        email_login = Main2Activity_mesas.email_login;
          //  Era um Teste  -  tx.setText( email_login );
    }


    private void createListView()
    {

        JSONObject postData = new JSONObject();
        try {

            postData.put("usuario", Main2Activity_mesas.email_login);
            postData.put("senha", "123");
            postData.put("mesa",  Main2Activity_mesas.cod_1_result);

            Comanda.BuscarLista_comanda busca = new Comanda.BuscarLista_comanda();
            busca.execute("http://192.168.100.136/db_1/lista_comanda.php", postData.toString());


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
    {
        //Pega o item que foi selecionado.
       // Contato_comanda item = adapterListView.getItem(arg2);
        //Demostração
       // Toast.makeText(this, "Você Clicou em: " + item.getNome(), Toast.LENGTH_LONG).show();
      //  it = item.getNome();
       // String id1 = it;


       // Toast.makeText(this, "Signing up..." + id1, Toast.LENGTH_SHORT).show();
        //new enviaPedido (this).execute(id1);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public class BuscarLista_comanda extends AsyncTask<String, Void, String> {
        private ProgressDialog progress = new ProgressDialog(Comanda.this);



        protected void onPreExecute() {
            //display progress dialog.
            this.progress.setMessage("Aguarde...");
            this.progress.show();
        }

        @Override
        public String doInBackground(String... params) {

            String data = "";

            HttpURLConnection httpURLConnection = null;
            try {

                httpURLConnection = (HttpURLConnection) new URL(params[0]).openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=utf-8");

                httpURLConnection.setReadTimeout(15000 /* milliseconds */);
                httpURLConnection.setConnectTimeout(15000 /* milliseconds */);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);

                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                wr.writeBytes(params[1]);
                wr.flush();
                wr.close();


                //pega o codigo da requisicao http
                int responseCode = httpURLConnection.getResponseCode();

                InputStream in = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(in);

                int inputStreamData = inputStreamReader.read();
                while (inputStreamData != -1) {
                    char current = (char) inputStreamData;
                    inputStreamData = inputStreamReader.read();
                    data += current;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            }

            return data;
        }

        @Override
        public void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("TAG", result); // this is expecting a response code to be sent from your server upon receiving the POST data
            if (progress.isShowing()) {
                progress.dismiss();
            }

            ListView listview = findViewById(R.id.list);
            JSONObject json = null;
            Long codigo = null;
            String msg = null;
            JSONArray lista;
            String titulo = "Sucesso";

            try {
                json = new JSONObject(result);
                codigo = json.getLong("status");

                if (codigo != 1L) {
                    titulo = "Erro";
                    msg = json.getString("msg");
                } else {
                    lista = json.getJSONArray("lista");
                    ArrayList listar = new ArrayList();

                    for (int i = 0; i < lista.length(); i++) {
                        JSONArray objeto = lista.getJSONArray(i);
                        Log.i("BUSCA ARRAY", objeto.getString(1));
                        Contato_comanda c = new Contato_comanda(objeto.getString(0),objeto.getString(1));
                        String ob = objeto.getString(2);
                        total.setText( ob );
                        listar.add(c);

                    }

                    //Cria o adapter
                    adapterListView = new AdapterListViewComanda(Comanda.this, listar);


                    //Define o Adapter
                    listView.setAdapter(adapterListView);
                    //Cor quando a lista é selecionada para ralagem.
                    listView.setCacheColorHint( Color.TRANSPARENT);

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(Comanda.this);

            builder.setMessage(msg)
                    .setTitle(titulo);
            builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });

            // 3. Get the AlertDialog from create()
            AlertDialog dialog = builder.create();

           // dialog.show();

        }

    }
    //função para abrir outra activity
    public void chamacomanda(View view) {

        Intent secondActivity = new Intent(this, Main2Activity_cardapio.class);
        //passa variavel para segunda activity
        ///   String txt = "";
        //  txt = edt_email.getText().toString();
        //Bundle bundle = new Bundle();

        //         /bundle.putString("txt", txt);
//           secondActivity.putExtras(bundle);

        startActivity(secondActivity);
    }
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences(solicita_encerra.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        //      if(loggedIn){
        //SE ESTIVER LOGADO ENTÃO AO ENTRAR NA APLICAÇÃO VAI PARA TELA SEGUINTE
        //    Intent intent = new Intent(Main2Activity_mesas.this, Main2Activity_scanner.class);
        //    startActivity(intent);
        //}
    }
    //Busca se existe uma mesa com esse codigo cadastrado
    public void login2(){
            encerrado = "1";
        StringRequest stringRequest = new StringRequest( Request.Method.POST, solicita_encerra.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equalsIgnoreCase(solicita_encerra.LOGIN_SUCCESS)){
                            editor.putBoolean(solicita_encerra.LOGGEDIN_SHARED_PREF, true);
                            editor.putString(solicita_encerra.USUARIO_SHARED_PREF, encerrado);
                            editor.commit();
                            Toast.makeText( Comanda.this, busca_mesa.CODIGO, Toast.LENGTH_SHORT ).show();
                           // intent_1();
                            //intent_2();
                        }else{
                            Toast.makeText(Comanda.this, "Usuário ou senha inválidos!", Toast.LENGTH_LONG).show();
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
                params.put(solicita_encerra.CODIGO, encerrado);
                params.put(solicita_encerra.MESA, mesa_1);
                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}






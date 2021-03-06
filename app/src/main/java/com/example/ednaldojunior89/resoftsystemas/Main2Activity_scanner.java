package com.example.ednaldojunior89.resoftsystemas;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Main2Activity_scanner extends Activity implements AdapterView.OnItemClickListener {

    private AdapterListView adapterListView;
    private ListView listView;
    //public String email_login;
    public TextView mostrar;
    public String email_login;

    String it;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2_scanner);
        mostrar = (TextView) findViewById( R.id.mostrar );
        //Pega a referencia do ListView
        listView = (ListView) findViewById(R.id.list);
        //Define o Listener quando alguem clicar no item.
        listView.setOnItemClickListener(this);


        createListView();


    }


    private void createListView ()
    {



        JSONObject postData = new JSONObject();
        try {
            email_login = Main2Activity_mesas.email_login;
            postData.put("usuario", email_login);
            postData.put("senha", "123");
            postData.put("mesa",  Main2Activity_mesas.cod_1_result);

            BuscarLista busca = new BuscarLista();
            busca.execute("http://192.168.100.136/db_1/lista.php", postData.toString());


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public void setChama_comanda1(){
        Intent intent = new Intent(Main2Activity_scanner.this, Comanda.class);
        //SE O LOGIN E SENHA FOR IGUAL AO QUE CONSTA NA TABELA DO BANCO DE DADOS ENTÃO VAI PARA OUTRA TELA



        //Collections.shuffle(array);
        String texto = email_login;
        Bundle bundle = new Bundle();
        bundle.putString("guardainfo1", texto);
        intent.putExtras(bundle);
        startActivity(intent);



        startActivity(intent);
    }
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
    {
        //Pega o item que foi selecionado.
        Contato item = adapterListView.getItem(arg2);
        //Demostração
        //Toast.makeText(this, "Você Clicou em: " + item.getNome(), Toast.LENGTH_LONG).show();
         it = item.getNome();


        onBackPressed();

    }

    public void escolheitem(){
        //String id1 = it;
        Toast.makeText(this, "Signing up..." + it, Toast.LENGTH_SHORT).show();
        new enviaPedido (this).execute(it ,email_login);
    }

    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Confirma "+it+" ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Main2Activity_scanner.this.escolheitem();
                    }
                })
                .setNegativeButton("No", null)
                .show();
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


    public class BuscarLista extends AsyncTask<String, Void, String> {
        private ProgressDialog progress = new ProgressDialog(Main2Activity_scanner.this);



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
           // Log.e("TAG", result); // this is expecting a response code to be sent from your server upon receiving the POST data
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
                       // Log.i("BUSCA ARRAY", objeto.getString(1));
                        Contato c = new Contato(objeto.getString(0),objeto.getString(1));
                        listar.add(c);
                    }

                    //Cria o adapter
                    adapterListView = new AdapterListView(Main2Activity_scanner.this, listar);


                    //Define o Adapter
                    listView.setAdapter(adapterListView);
                    //Cor quando a lista é selecionada para ralagem.
                    listView.setCacheColorHint(Color.TRANSPARENT);

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(Main2Activity_scanner.this);

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

        Intent secondActivity = new Intent(this, Comanda.class);
        //passa variavel para segunda activity
        ///   String txt = "";
        //  txt = edt_email.getText().toString();
        //Bundle bundle = new Bundle();

        //         /bundle.putString("txt", txt);
//           secondActivity.putExtras(bundle);

        startActivity(secondActivity);

    }



}






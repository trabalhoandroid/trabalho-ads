package com.example.ednaldojunior89.resoftsystemas;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class cadastro extends Activity {
    private EditText nome,email, celular, senha, cpf,endereco ;
    Button btSalvar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        this.nome = (EditText) findViewById(R.id.nome);
        this.cpf = (EditText) findViewById(R.id.cpf);
        this.celular = (EditText) findViewById(R.id.celular);
        this.email = (EditText) findViewById(R.id.email);
        this.senha = (EditText) findViewById(R.id.senha);
        this.endereco = (EditText) findViewById(R.id.endereco);
        this.btSalvar = (Button) findViewById(R.id.btSalvar);



    }

    public void signup(View v) {
        String fullName = nome.getText().toString();
        String Celular = celular.getText().toString();
        String Cpf = cpf.getText().toString();
        String Senha = senha.getText().toString();
        String Email = email.getText().toString();
        String Endereco = endereco.getText().toString();

        Toast.makeText(this, "Signing up...", Toast.LENGTH_SHORT).show();
        new SignupActivity(this).execute(fullName, Email, Celular,Cpf,Senha,Endereco);
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


}
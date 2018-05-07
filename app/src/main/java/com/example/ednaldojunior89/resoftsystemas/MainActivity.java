package com.example.ednaldojunior89.resoftsystemas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button bt_chama_mesas;
    EditText edt_email;
    EditText edt_senha;
    String email_1 = "Junior";
    TextView text_nome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //personalisa barra de status
     //   getSupportActionBar().setDisplayShowHomeEnabled(true);
       // getSupportActionBar().setIcon(R.mipmap.logo);
        //getSupportActionBar().setTitle(email);
        // fim de personaliza barra de status
        this.text_nome = (TextView) findViewById(R.id.text_nome);

        this.bt_chama_mesas = (Button) findViewById(R.id.bt_chama_mesas);
        this.edt_email = (EditText) findViewById(R.id.edt_email);
        bt_chama_mesas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edt_email.getText().toString();
                if (email.equals( email_1)){

                    Toast toast = Toast.makeText(getApplicationContext(),"Seja Bem vindo "+ email, Toast.LENGTH_LONG);
                    toast.show();
                    startSecondActivity(view);



                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(), " Você ainda não possui cadastro", Toast.LENGTH_LONG);
                    toast.show();

                }

            }
        });

        this.edt_senha = (EditText) findViewById(R.id.edt_senha);


    }


    //função para abrir outra activity
       public void startSecondActivity(View view) {

        Intent secondActivity = new Intent(this, Main2Activity_cardapio.class);
      //passa variavel para segunda activity
       ///   String txt = "";
         //  txt = edt_email.getText().toString();
           //Bundle bundle = new Bundle();

  //         /bundle.putString("txt", txt);
//           secondActivity.putExtras(bundle);

        startActivity(secondActivity);

    }

    public void chama_cadastro(View view) {

        Intent secondActivity = new Intent(this, cadastro.class);
        //passa variavel para segunda activity
        ///   String txt = "";
        //  txt = edt_email.getText().toString();
        //Bundle bundle = new Bundle();

        //         /bundle.putString("txt", txt);
//           secondActivity.putExtras(bundle);

        startActivity(secondActivity);

    }

}


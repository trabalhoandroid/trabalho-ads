package com.example.ednaldojunior89.resoftsystemas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;



public class Main2Activity_cardapio extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2_cardapio);



    }
    public void chama_pratos(View view){

        Intent secondActivity = new Intent(this, Main2Activity_scanner.class);
        startActivity(secondActivity);


    }

}

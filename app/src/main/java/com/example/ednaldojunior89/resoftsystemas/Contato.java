package com.example.ednaldojunior89.resoftsystemas;


/**
 * Created by Ednaldo Junior on 21/04/2018.
 */

public class Contato extends Main2Activity_cardapio {


    private String nome;
    private String telefonne;


    public Contato(String nome, String telefone) {


        this.nome = nome;
        this.telefonne = telefone;

    }


    public String getTelefonne() {
        return telefonne;
    }

    public void setTelefonne(String telefonne) {
        this.telefonne = telefonne;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}


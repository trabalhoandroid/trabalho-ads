package com.example.ednaldojunior89.resoftsystemas;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterListViewComanda extends BaseAdapter
{
    private LayoutInflater mInflater;
    private ArrayList<Contato_comanda> itens;

    public AdapterListViewComanda(Comanda context, ArrayList<Contato_comanda> itens)
    {
        //Itens que preencheram o listview
        this.itens = itens;
        //responsavel por pegar o Layout do item.
        mInflater = LayoutInflater.from(context);
    }



    /**
     * Retorna a quantidade de itens
     *
     * @return
     */
    public int getCount()
    {
        return itens.size();
    }

    /**
     * Retorna o item de acordo com a posicao dele na tela.
     *
     * @param position
     * @return
     */
    public Contato_comanda getItem(int position)
    {
        return itens.get(position);
    }

    /**
     * Sem implementação
     *
     * @param position
     * @return
     */
    public long getItemId(int position)
    {
        return position;
    }

    public View getView(int position, View view, ViewGroup parent)
    {





        //Pega o item de acordo com a posção.
        Contato_comanda item = itens.get(position);

        //infla o layout para podermos preencher os dados
        view = mInflater.inflate(R.layout.list_comanda, null);

        //atravez do layout pego pelo LayoutInflater, pegamos cada id relacionado
        //ao item e definimos as informações.
        ((TextView) view.findViewById(R.id.tvNome)).setText(item.getNome());
        ((TextView) view.findViewById(R.id.tvTelefone)).setText(item.getTelefonne());

        if (position % 2 == 1) {
            view.setBackgroundColor( Color.rgb( 238, 221, 130 ));
        } else {
            view.setBackgroundColor(Color.rgb( 238, 201, 0 ));
        }

        return view;
    }
}
package com.example.treinamentovolley;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.treinamentovolley.models.Produto;

import java.io.PipedOutputStream;
import java.util.List;

public class ProdutoAdapter extends BaseAdapter {
    private Context context;
    private List<Produto> produtoList;

    public ProdutoAdapter(Context context, List<Produto> produtoList) {
        this.context = context;
        this.produtoList = produtoList;
    }

    @Override
    public int getCount() {
        return produtoList.size();
    }

    @Override
    public Object getItem(int position) {
        return produtoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return produtoList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.item_list_produto, parent, false);
        TextView txvNome = convertView.findViewById(R.id.item_list_nome_produto);
        TextView txvPreco = convertView.findViewById(R.id.item_list_preco_produto);
        ImageView imgEdit = convertView.findViewById(R.id.item_list_edit_produto);
        ImageView imgDelete = convertView.findViewById(R.id.item_list_delete_produto);

        Produto produto = (Produto) getItem(position);

        txvNome.setText(produto.getNome());
        txvPreco.setText(String.format("%s2", produto.getPreco()));
        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Editando", Toast.LENGTH_SHORT).show();
            }
        });

        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Deletando", Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }
}

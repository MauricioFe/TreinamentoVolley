package com.example.treinamentovolley;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.treinamentovolley.models.Usuario;

import java.util.List;

public class UsuarioAdapter extends BaseAdapter {
    public Context context;
    public List<Usuario> usuarioList;

    public UsuarioAdapter(Context context, List<Usuario> usuarioList) {
        this.context = context;
        this.usuarioList = usuarioList;
    }

    @Override
    public int getCount() {
        return usuarioList.size();
    }

    @Override
    public Object getItem(int position) {
        return usuarioList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.item_list_usuario, parent, false);
        TextView txvNome = convertView.findViewById(R.id.item_list_nome);
        TextView txvTelefone = convertView.findViewById(R.id.item_list_telefone);
        ImageView imgEdit = convertView.findViewById(R.id.item_list_edit);
        ImageView imgDelete = convertView.findViewById(R.id.item_list_delete);

        Usuario usuario = (Usuario) getItem(position);
        txvNome.setText(usuario.getNome());
        txvTelefone.setText(usuario.getTelefone());

        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "vamos deletar", Toast.LENGTH_SHORT).show();
            }
        });
        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "vamos editar", Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }
}

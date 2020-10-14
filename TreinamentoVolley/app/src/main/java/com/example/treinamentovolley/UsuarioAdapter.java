package com.example.treinamentovolley;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaMetadata;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.treinamentovolley.models.Usuario;

import org.json.JSONObject;

import java.lang.reflect.Method;
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
        return ((Usuario) getItem(position)).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
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
                new AlertDialog.Builder(context).setTitle("Confirmação de exclusão")
                        .setMessage("Você realmente deseja realizar a exclusão?")
                        .setNegativeButton("Não", null)
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                long idUsuario = getItemId(position);
                                RequestQueue queue = Volley.newRequestQueue(context);
                                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE, "", null, new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        Toast.makeText(context, "Excluida com sucesso", Toast.LENGTH_SHORT).show();
                                        notifyDataSetChanged();
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.d("Error", "Erro ao realizar a requisição ");
                                    }
                                });
                            }
                        }).show();
            }
        });
        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "vamos editar", Toast.LENGTH_SHORT).show();
            }
        });
        this.notifyDataSetChanged();
        return convertView;
    }
}

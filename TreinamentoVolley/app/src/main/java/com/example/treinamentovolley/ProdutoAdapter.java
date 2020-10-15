package com.example.treinamentovolley;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.treinamentovolley.models.Produto;

import java.io.PipedOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.AEADBadTagException;

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
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.item_list_produto, parent, false);
        TextView txvNome = convertView.findViewById(R.id.item_list_nome_produto);
        TextView txvPreco = convertView.findViewById(R.id.item_list_preco_produto);
        ImageView imgEdit = convertView.findViewById(R.id.item_list_edit_produto);
        ImageView imgDelete = convertView.findViewById(R.id.item_list_delete_produto);

        final Produto produto = (Produto) getItem(position);

        txvNome.setText(produto.getNome());
        txvPreco.setText("" + produto.getPreco());
        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CadastrarProdutoActivity.class);
                intent.putExtra("produto", produto);
                context.startActivity(intent);
            }
        });

        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context).setTitle("Confirmação de Exclusão")
                        .setMessage("Você realmente deseja realizar a exclusão").setNegativeButton("Não", null)
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final long produtoId = getItemId(position);
                                RequestQueue queue = Volley.newRequestQueue(context);
                                StringRequest deleteRequest = new StringRequest(Request.Method.POST, SoapActivity.URL_DELETE, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        produtoList.remove(position);
                                        ProdutoAdapter.this.notifyDataSetChanged();
                                        Toast.makeText(context, "Excluida com sucesso", Toast.LENGTH_SHORT).show();
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.d("Error", "Errou ne filhão");
                                    }
                                }) {
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        Map<String, String> params = new HashMap<>();
                                        params.put("id", String.valueOf(produtoId));
                                        return params;
                                    }
                                };
                                queue.add(deleteRequest);
                            }
                        })
                        .show();
            }
        });
        return convertView;
    }
}

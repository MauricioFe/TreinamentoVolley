package com.example.treinamentovolley;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.treinamentovolley.models.Produto;

import java.util.HashMap;
import java.util.Map;

public class CadastrarProdutoActivity extends AppCompatActivity {

    EditText edtNome;
    EditText edtPreco;
    EditText edtEstoque;
    EditText edtDescricao;
    Button btnSalvar;
    Produto produto;
    int idProduto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_produto);
        Intent intent = getIntent();
        if (intent.hasExtra("idProduto")) {
            idProduto = intent.getIntExtra("idProduto", 0);
        }
        edtNome = findViewById(R.id.cadastrar_produto_txt_nome);
        edtPreco = findViewById(R.id.cadastrar_produto_txt_preco);
        edtEstoque = findViewById(R.id.cadastrar_produto_txt_estoque);
        edtDescricao = findViewById(R.id.cadastrar_produto_txt_descricao);
        btnSalvar = findViewById(R.id.cadastrar_produto_btn_salvar);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtNome.getText().length() > 0 && edtPreco.getText().length() > 0 &&
                        edtEstoque.getText().length() > 0 && edtDescricao.getText().length() > 0) {
                    produto = new Produto();
                    produto.setId(idProduto++);
                    produto.setNome(edtNome.getText().toString());
                    produto.setPreco(Double.parseDouble(edtPreco.getText().toString()));
                    produto.setEstoque(Integer.parseInt(edtEstoque.getText().toString()));
                    produto.setDescricao(edtDescricao.getText().toString());
                    salvarProduto(produto);

                } else {
                    new AlertDialog.Builder(CadastrarProdutoActivity.this).setNeutralButton("Ok", null)
                            .setMessage("Preecha todos os campos").setTitle("Erro a cadastrar um produto").show();
                }
            }
        });
    }

    private void salvarProduto(final Produto produto) {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest requestPost = new StringRequest(Request.Method.POST, SoapActivity.URL_POST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                startActivity(new Intent(getApplicationContext(), SoapActivity.class));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", "Deu erro meu mano: " + error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Id", String.valueOf(produto.getId()));
                params.put("Nome", produto.getNome());
                params.put("Preco", String.valueOf(produto.getPreco()));
                params.put("Estoque", String.valueOf(produto.getEstoque()));
                params.put("Descricao", produto.getDescricao());
                return params;
            }
        };
        queue.add(requestPost);
    }
}
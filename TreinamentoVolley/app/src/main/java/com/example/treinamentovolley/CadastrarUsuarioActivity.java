package com.example.treinamentovolley;

import androidx.annotation.MainThread;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.treinamentovolley.models.Usuario;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CadastrarUsuarioActivity extends AppCompatActivity {

    EditText edtNome;
    EditText edtEmail;
    EditText edtTelefone;
    EditText edtSenha;
    Spinner spnFuncao;
    Button btnSalvar;
    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_usuario);
        inicializaComponentes();
        capturaDadosSpinner();
        Intent intent = getIntent();
        if (intent.hasExtra("usuario")) {
            usuario = (Usuario) intent.getSerializableExtra("usuario");
            assert usuario != null;
            preencheComponentesEdit(usuario);
            onClickEditar();
        } else {
            usuario = new Usuario();
            onClickSalvar();
        }
    }

    private void preencheComponentesEdit(Usuario usuario) {
        edtNome.setText(usuario.getNome());
        edtTelefone.setText(usuario.getTelefone());
        edtEmail.setText(usuario.getEmail());
        edtSenha.setText(usuario.getSenha());
    }

    private void onClickEditar() {
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtNome.getText().length() > 0 && edtEmail.getText().length() > 0 &&
                        edtTelefone.getText().length() > 0 && edtSenha.getText().length() > 0) {
                    preencheUsuario();
                    editarUsuario();
                }else {
                    new AlertDialog.Builder(CadastrarUsuarioActivity.this).setNeutralButton("Ok", null)
                            .setMessage("Preecha todos os campos").setTitle("Erro a cadastrar um usuário").show();
                }
            }
        });

    }

    private void editarUsuario() {
        JSONObject jsonObject = getJsonObject(usuario);
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest putRequest = new JsonObjectRequest(Request.Method.PUT, RestActivity.URL + "/" + usuario.getId(),
                jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(CadastrarUsuarioActivity.this, "Editado com sucesso", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), RestActivity.class));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Erro", "Deu errado meu chapa");
            }
        });

        queue.add(putRequest);
    }

    private void onClickSalvar() {
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtNome.getText().length() > 0 && edtEmail.getText().length() > 0 &&
                        edtTelefone.getText().length() > 0 && edtSenha.getText().length() > 0) {
                    preencheUsuario();
                    salvarUsuario(usuario);
                } else {
                    new AlertDialog.Builder(CadastrarUsuarioActivity.this).setNeutralButton("Ok", null)
                            .setMessage("Preecha todos os campos").setTitle("Erro a cadastrar um usuário").show();
                }
            }
        });
    }

    private void capturaDadosSpinner() {
        List<String> funcaoList = new ArrayList<>();
        funcaoList.add("Administrador");
        funcaoList.add("Usuario");
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, funcaoList);
        spnFuncao.setAdapter(adapter);
        spnFuncao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                usuario.setFuncaoId(position + 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void inicializaComponentes() {
        edtNome = findViewById(R.id.cadastrar_usuario_txt_nome);
        edtEmail = findViewById(R.id.cadastrar_usuario_txt_email);
        edtTelefone = findViewById(R.id.cadastrar_usuario_txt_telefone);
        edtSenha = findViewById(R.id.cadastrar_usuario_txt_senha);
        spnFuncao = findViewById(R.id.cadastrar_usuario_spn_funcao);
        btnSalvar = findViewById(R.id.cadastrar_usuario_btn_salvar);
    }

    private void preencheUsuario() {
        usuario.setNome(edtNome.getText().toString());
        usuario.setTelefone(edtTelefone.getText().toString());
        usuario.setEmail(edtEmail.getText().toString());
        usuario.setSenha(edtSenha.getText().toString());
        if (usuario.getFuncaoId() <= 0) {
            usuario.setFuncaoId(1);
        }
    }

    private void salvarUsuario(final Usuario usuario) {
        JSONObject params = getJsonObject(usuario);
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest requestPostUser =
                new JsonObjectRequest(Request.Method.POST, RestActivity.URL, params, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response", response.toString());
                        startActivity(new Intent(getApplicationContext(), RestActivity.class));
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CadastrarUsuarioActivity.this, "erro", Toast.LENGTH_SHORT).show();
                        VolleyLog.d("TAG", "Error: " + error.getMessage());
                        Log.d("TAG", "" + error.getMessage() + "," + error.toString());
                    }
                });
        queue.add(requestPostUser);
    }

    private JSONObject getJsonObject(Usuario usuario) {
        JSONObject params = new JSONObject();
        try {
            params.put("nome", usuario.getNome());
            params.put("telefone", usuario.getTelefone());
            params.put("email", usuario.getEmail());
            params.put("senha", usuario.getSenha());
            params.put("funcaoId", usuario.getFuncaoId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return params;
    }
}
package com.example.treinamentovolley;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.treinamentovolley.models.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RestActivity extends AppCompatActivity {
    public static final String URL = "http://192.168.0.108:5000/api/usuarios";
    ListView listaUsuarios;
    List<Usuario> usuarioList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest);
        listaUsuarios = findViewById(R.id.list_view_usuario);
        usuarioList = new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest requestGetUser =
                new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        usuarioList = retornaUsuario(response);
                        for (Usuario item:usuarioList) {
                            Toast.makeText(RestActivity.this, item.getNome(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Erro na requisição", error.getMessage());
                    }
                });
        queue.add(requestGetUser);

    }

    private List<Usuario> retornaUsuario(JSONArray response) {
        List<Usuario> usuarioList = new ArrayList<>();
        for (int i = 0; i < response.length(); i++) {
            try {
                JSONObject jsonObject = response.getJSONObject(i);
                Usuario usuario = new Usuario();
                usuario.setId(jsonObject.getInt("id"));
                usuario.setNome(jsonObject.getString("nome"));
                usuario.setEmail(jsonObject.getString("email"));
                usuario.setTelefone(jsonObject.getString("telefone"));
                usuario.setSenha(jsonObject.getString("senha"));
                usuario.setFuncaoId(jsonObject.getInt("funcaoId"));
                usuarioList.add(usuario);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return usuarioList;
    }
}
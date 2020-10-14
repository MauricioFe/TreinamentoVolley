package com.example.treinamentovolley;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.treinamentovolley.models.Produto;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class SoapActivity extends AppCompatActivity {

    public static final String URL_GET = "http://192.168.0.108:50210/ProdutoService.asmx/GetProdutos";
    public static final String URL_POST = "http://192.168.0.108:50210/ProdutoService.asmx/Post";
    public static final String URL_PUT = "http://192.168.0.108:50210/ProdutoService.asmx/Put";
    public static final String URL_DELETE = "http://192.168.0.108:50210/ProdutoService.asmx/Delete";
    List<Produto> produtoList;
    ListView listaProdutos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soap);
        listaProdutos = findViewById(R.id.list_view_produto);
        produtoList = new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.POST, URL_GET, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               produtoList = AtualizaLista(response);
               ProdutoAdapter adapter = new ProdutoAdapter(SoapActivity.this, produtoList);
               listaProdutos.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Erro na requisição", error.getMessage());
            }
        });
        queue.add(request);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.adicionar_dados){
            int idProduto = 0 ;
            for (Produto produto:produtoList) {
                idProduto  = produto.getId();
            }
            Intent intent = new Intent(this, CadastrarProdutoActivity.class);
            intent.putExtra("idProduto", idProduto);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private List<Produto> AtualizaLista(String response) {
        List<Produto> produtoList = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document d1 = builder.parse(new InputSource(new StringReader(response)));
            NodeList nList = d1.getElementsByTagName("Produtos");
            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    Produto produto = new Produto();
                    produto.setId(Integer.parseInt(eElement.getElementsByTagName("Id").item(0).getTextContent()));
                    produto.setPreco(Double.parseDouble(eElement.getElementsByTagName("Preco").item(0).getTextContent()));
                    produto.setNome(eElement.getElementsByTagName("Nome").item(0).getTextContent());
                    produto.setEstoque(Integer.parseInt(eElement.getElementsByTagName("Estoque").item(0).getTextContent()));
                    produto.setDescricao(eElement.getElementsByTagName("Descricao").item(0).getTextContent());
                    produtoList.add(produto);
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        return produtoList;
    }
}
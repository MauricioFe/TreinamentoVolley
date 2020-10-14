package com.example.treinamentovolley.models;

import java.io.Serializable;

public class Usuario implements Serializable {
    private int Id;
    private String Nome;
    private String Email;
    private String Telefone;
    private String Senha;
    private int FuncaoId;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getTelefone() {
        return Telefone;
    }

    public void setTelefone(String telefone) {
        Telefone = telefone;
    }

    public String getSenha() {
        return Senha;
    }

    public void setSenha(String senha) {
        Senha = senha;
    }

    public int getFuncaoId() {
        return FuncaoId;
    }

    public void setFuncaoId(int funcaoId) {
        FuncaoId = funcaoId;
    }
}

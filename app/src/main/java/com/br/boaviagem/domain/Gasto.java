package com.br.boaviagem.domain;

import java.util.Date;

/**
 * Created by marco on 27/07/2017.
 */

public class Gasto {

    private Integer id;
    private String categoria;
    private Date data;
    private Double valor;
    private String descricao;
    private String local;
    private Integer viagemId;

    public Gasto(){

    }

    public Gasto(Integer id, String categoria, Date data, Double valor, String descricao, String local, Integer viagemId){
        this.id = id;
        this.categoria = categoria;
        this.data = data;
        this.valor = valor;
        this.descricao = descricao;
        this.local = local;
        this.viagemId = viagemId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public Integer getViagemId() {
        return viagemId;
    }

    public void setViagemId(Integer viagemId) {
        this.viagemId = viagemId;
    }
}

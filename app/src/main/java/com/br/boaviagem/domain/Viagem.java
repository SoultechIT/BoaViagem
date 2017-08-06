package com.br.boaviagem.domain;

import java.util.Date;

/**
 * Created by marco on 27/07/2017.
 */

public class Viagem {

    private Integer id;
    private String destino;
    private Integer tipoViagem;
    private Date dataChegada;
    private Date dataSaida;
    private Double orcamento;
    private Integer qtdePessoas;

    public Viagem(){

    }

    public Viagem(Integer id, String destino, Integer tipoViagem, Date dataChegada, Date dataSaida, Double orcamento, Integer qtdePessoas){
        this.id = id;
        this.destino = destino;
        this.tipoViagem = tipoViagem;
        this.dataChegada = dataChegada;
        this.dataSaida = dataSaida;
        this.orcamento = orcamento;
        this.qtdePessoas = qtdePessoas;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public Integer getTipoViagem() {
        return tipoViagem;
    }

    public void setTipoViagem(Integer tipoViagem) {
        this.tipoViagem = tipoViagem;
    }

    public Date getDataChegada() {
        return dataChegada;
    }

    public void setDataChegada(Date dataChegada) {
        this.dataChegada = dataChegada;
    }

    public Date getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(Date dataSaida) {
        this.dataSaida = dataSaida;
    }

    public Double getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(Double orcamento) {
        this.orcamento = orcamento;
    }

    public Integer getQtdePessoas() {
        return qtdePessoas;
    }

    public void setQtdePessoas(Integer qtdePessoas) {
        this.qtdePessoas = qtdePessoas;
    }
}

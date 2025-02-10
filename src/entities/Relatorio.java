package entities;

import java.util.Date;

public class Relatorio {
    private int id;
    private int usuarioId;
    private int eventoId;
    private String tipo;
    private Date dataGeracao;
    private String conteudo;

    public Relatorio(int id,int usuarioId, int eventoId, String tipo, Date dataGeracao, String conteudo) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.eventoId = eventoId;
        this.tipo = tipo;
        this.dataGeracao = dataGeracao;
        this.conteudo = conteudo;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }
    public int getEventoId() { return eventoId; }
    public void setEventoId(int eventoId) { this.eventoId = eventoId; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public Date getDataGeracao() { return dataGeracao; }
    public void setDataGeracao(Date dataGeracao) { this.dataGeracao = dataGeracao; }
    public String getConteudo() { return conteudo; }
    public void setConteudo(String conteudo) { this.conteudo = conteudo; }
}

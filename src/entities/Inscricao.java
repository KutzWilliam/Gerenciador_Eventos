package entities;

import java.util.Date;

public class Inscricao {
    private int id;
    private int usuarioId;
    private int eventoId;
    private Date dataInscricao;
    private String status;
    private boolean presencaConfirmada;

    public Inscricao(int id, int usuarioId, int eventoId, Date dataInscricao, String status, boolean presencaConfirmada) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.eventoId = eventoId;
        this.dataInscricao = dataInscricao;
        this.status = status;
        this.presencaConfirmada = presencaConfirmada;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }
    public int getEventoId() { return eventoId; }
    public void setEventoId(int eventoId) { this.eventoId = eventoId; }
    public Date getDataInscricao() { return dataInscricao; }
    public void setDataInscricao(Date dataInscricao) { this.dataInscricao = dataInscricao; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public boolean isPresencaConfirmada() { return presencaConfirmada; }
    public void setPresencaConfirmada(boolean presencaConfirmada) { this.presencaConfirmada = presencaConfirmada; }
}


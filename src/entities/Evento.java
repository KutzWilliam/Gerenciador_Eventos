package entities;

import java.util.Date;

public class Evento {
    private int id;
    private String titulo;
    private String descricao;
    private Date dataHora;
    private int duracao;
    private String local;
    private int capacidadeMaxima;
    private String status;
    private String categoria;
    private double preco;
    private int organizadorId;

    public Evento(int id, String titulo, String descricao, Date dataHora, int duracao, String local, int capacidadeMaxima, String status, String categoria, double preco, int organizadorId) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataHora = dataHora;
        this.duracao = duracao;
        this.local = local;
        this.capacidadeMaxima = capacidadeMaxima;
        this.status = status;
        this.categoria = categoria;
        this.preco = preco;
        this.organizadorId = organizadorId;
    }

    public Evento() {
		// TODO Auto-generated constructor stub
	}

	// Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public Date getDataHora() { return dataHora; }
    public void setDataHora(Date dataHora) { this.dataHora = dataHora; }
    public int getDuracao() { return duracao; }
    public void setDuracao(int duracao) { this.duracao = duracao; }
    public String getLocal() { return local; }
    public void setLocal(String local) { this.local = local; }
    public int getCapacidadeMaxima() { return capacidadeMaxima; }
    public void setCapacidadeMaxima(int capacidadeMaxima) { this.capacidadeMaxima = capacidadeMaxima; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }
    public int getOrganizadorId() { return organizadorId; }
    public void setOrganizadorId(int organizadorId) { this.organizadorId = organizadorId; }
}

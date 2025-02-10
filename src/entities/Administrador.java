package entities;

import java.util.Date;

public class Administrador extends Usuario {
    private String cargo;
    private Date dataContratacao;

    public Administrador(int id, String nome, String email, String senha, String cargo, Date dataContratacao) {
        super(id, nome, email, senha, "Administrador");
        this.cargo = cargo;
        this.dataContratacao = dataContratacao;
    }

    // Getters e Setters
    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }
    public Date getDataContratacao() { return dataContratacao; }
    public void setDataContratacao(Date dataContratacao) { this.dataContratacao = dataContratacao; }
}


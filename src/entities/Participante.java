package entities;

import java.util.Date;

public class Participante extends Usuario {
    private Date dataNascimento;
    private String cpf;

    public Participante(int id, String nome, String email, String senha, Date dataNascimento, String cpf) {
        super(id, nome, email, senha, "Participante");
        this.dataNascimento = dataNascimento;
        this.cpf = cpf;
    }

    // Getters e Setters
    public Date getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(Date dataNascimento) { this.dataNascimento = dataNascimento; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
}

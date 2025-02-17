package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

import entities.Relatorio;

public class RelatorioAdminView extends JDialog {
    private JTable tabelaRelatorio;
    private DefaultTableModel modeloTabela;
    private List<Relatorio> relatorios;

    public RelatorioAdminView(JFrame parent, List<Relatorio> relatorios) {
        super(parent, "Relatório de Participantes", true);
        this.relatorios = relatorios;

        setTitle("Relatório de Participantes");
        setSize(800, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel painelPrincipal = new JPanel(new BorderLayout());

        String[] colunas = {"ID", "Evento ID", "Tipo", "Data Geração", "Conteúdo"};
        modeloTabela = new DefaultTableModel(colunas, 0);
        tabelaRelatorio = new JTable(modeloTabela);
        JScrollPane scrollPane = new JScrollPane(tabelaRelatorio);
        painelPrincipal.add(scrollPane, BorderLayout.CENTER);

        JButton btnDownload = new JButton("Download Relatório");
        btnDownload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportarParaCSV();
            }
        });

        JPanel painelBotoes = new JPanel();
        painelBotoes.add(btnDownload);
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);

        add(painelPrincipal);
        exibirRelatorio();
    }

    private void exibirRelatorio() {
        for (Relatorio relatorio : relatorios) {
            modeloTabela.addRow(new Object[]{
                relatorio.getId(),
                relatorio.getEventoId(),
                relatorio.getTipo(),
                relatorio.getDataGeracao(),
                relatorio.getConteudo()
            });
        }
    }

    private void exportarParaCSV() {
    	Random gerador = new Random();
		int id = gerador.nextInt(500);
        String userHome = System.getProperty("user.home");
        String caminhoDownload = Paths.get(userHome, "Downloads", id + "relatorioAdm.csv").toString();
        
        try (FileWriter writer = new FileWriter(new File(caminhoDownload))) {
            // Escrever cabeçalho
            writer.write("Relatorio do Evento: \n");
            
            // Escrever os dados da tabela
            for (Relatorio relatorio : relatorios) {
                writer.write("ID: " + relatorio.getId() + "\n"
                        + "Evento ID: "+ relatorio.getEventoId() + "\n"
                        + "Tipo: "+ relatorio.getTipo() + "\n"
                        + "Inscritos: "+ relatorio.getConteudo().replaceAll(",", " ") + "\n"
                        + "Data de Geração: "+ relatorio.getDataGeracao() + "\n");
            }
            
            JOptionPane.showMessageDialog(this, "Relatório salvo em: " + caminhoDownload, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar o relatório", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}

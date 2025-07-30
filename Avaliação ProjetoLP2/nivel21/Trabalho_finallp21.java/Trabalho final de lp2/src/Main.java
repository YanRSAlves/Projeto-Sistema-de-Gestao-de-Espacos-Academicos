
// Classe principal que inicializa o sistema, cria arquivos necessarios, aplica tema escuro e abre a tela de login.

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Cria pastas e arquivos 
        PastaUtils.criarPastaBase();

        ArquivoUtils.criarArquivoSeNaoExistir(Constantes.ARQ_USUARIOS);
        ArquivoUtils.criarArquivoSeNaoExistir(Constantes.ARQ_ESPACOS);
        ArquivoUtils.criarArquivoSeNaoExistir(Constantes.ARQ_RESERVAS);
        ArquivoUtils.criarArquivoSeNaoExistir(Constantes.ARQ_LOG);

        // Aplica tema escuro via UIManager
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());

            UIManager.put("Panel.background", new java.awt.Color(40, 40, 40));
            UIManager.put("Button.background", new java.awt.Color(70, 70, 70));
            UIManager.put("Button.foreground", java.awt.Color.WHITE);
            UIManager.put("Label.foreground", java.awt.Color.WHITE);
            UIManager.put("TextField.background", new java.awt.Color(60, 60, 60));
            UIManager.put("TextField.foreground", java.awt.Color.WHITE);
            UIManager.put("TextField.caretForeground", java.awt.Color.WHITE);
            UIManager.put("ComboBox.background", new java.awt.Color(60, 60, 60));
            UIManager.put("ComboBox.foreground", java.awt.Color.WHITE);
            UIManager.put("ScrollPane.background", new java.awt.Color(50, 50, 50));
            UIManager.put("Table.background", new java.awt.Color(50, 50, 50));
            UIManager.put("Table.foreground", java.awt.Color.WHITE);
            UIManager.put("Table.selectionBackground", new java.awt.Color(100, 100, 100));
            UIManager.put("Table.selectionForeground", java.awt.Color.WHITE);

        } catch (Exception e) {
            System.out.println("Erro ao aplicar tema escuro: " + e.getMessage());
        }

        // Abre a tela de login
        SwingUtilities.invokeLater(() -> {
            new TelaLogin().setVisible(true);
        });
    }
}

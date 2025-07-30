/*
 * Esta classe representa uma janela para cadastro de espacos no sistema,
 * permitindo que o usuario insira informacoes como ID, nome, tipo,
 * capacidade, localizacao e caracteristicas especificas de cada tipo de espaco.
 *
 * A interface usa GridBagLayout para organizar campos de texto, combo box
 * para selecao do tipo, e botoes para salvar ou cancelar.
 *
 * Ao salvar, valida os campos, converte os dados especificos conforme o tipo,
 * cria o objeto correspondente (ex: SalaAula, Laboratorio etc), salva via controlador,
 * registra a acao no log e fecha a janela.
 *
 * Tambem aplica tema escuro a interface para melhor aparencia e dinanmica.
 */
import java.awt.*;
import javax.swing.*;

public class TelaCadastroEspaco extends JFrame {
    private JTextField campoId, campoNome, campoCapacidade, campoLocalizacao, campoEspecifico;
    private JComboBox<String> comboTipo;
    private JButton btnSalvar, btnCancelar;

    public TelaCadastroEspaco() {
        setTitle("Cadastro de Espaço");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(new Color(40, 40, 40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel titulo = new JLabel("Cadastro de Espaço", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setForeground(Color.WHITE);
        gbc.gridwidth = 2;
        painel.add(titulo, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        painel.add(criarLabel("ID:"), gbc);
        gbc.gridx = 1;
        campoId = criarCampo();
        painel.add(campoId, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        painel.add(criarLabel("Nome:"), gbc);
        gbc.gridx = 1;
        campoNome = criarCampo();
        painel.add(campoNome, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        painel.add(criarLabel("Tipo:"), gbc);
        gbc.gridx = 1;
        comboTipo = new JComboBox<>(new String[]{"SalaAula", "Laboratorio", "SalaReuniao", "QuadraEsportiva", "Auditorio"});
        comboTipo.setBackground(new Color(60, 60, 60));
        comboTipo.setForeground(Color.WHITE);
        painel.add(comboTipo, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        painel.add(criarLabel("Capacidade:"), gbc);
        gbc.gridx = 1;
        campoCapacidade = criarCampo();
        painel.add(campoCapacidade, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        painel.add(criarLabel("Localização:"), gbc);
        gbc.gridx = 1;
        campoLocalizacao = criarCampo();
        painel.add(campoLocalizacao, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        painel.add(criarLabel("Características específicas:"), gbc);
        gbc.gridx = 1;
        campoEspecifico = criarCampo();
        painel.add(campoEspecifico, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        btnSalvar = criarBotao("Salvar");
        painel.add(btnSalvar, gbc);
        gbc.gridx = 1;
        btnCancelar = criarBotao("Cancelar");
        painel.add(btnCancelar, gbc);

        add(painel);

        btnSalvar.addActionListener(e -> salvarEspaco());
        btnCancelar.addActionListener(e -> dispose());
    }

    private JLabel criarLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setForeground(Color.WHITE);
        return label;
    }

    private JTextField criarCampo() {
        JTextField campo = new JTextField();
        campo.setBackground(new Color(60, 60, 60));
        campo.setForeground(Color.WHITE);
        campo.setCaretColor(Color.WHITE);
        return campo;
    }

    private JButton criarBotao(String texto) {
        JButton btn = new JButton(texto);
        btn.setBackground(new Color(70, 70, 70));
        btn.setForeground(Color.WHITE);
        return btn;
    }

    private void salvarEspaco() {
        try {
            String id = campoId.getText().trim();
            String nome = campoNome.getText().trim();
            String tipo = (String) comboTipo.getSelectedItem();
            int capacidade = Integer.parseInt(campoCapacidade.getText().trim());
            String localizacao = campoLocalizacao.getText().trim();
            String especifico = campoEspecifico.getText().trim();

            if (id.isEmpty() || nome.isEmpty() || localizacao.isEmpty() || especifico.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Espaco espaco = null;
            switch (tipo) {
                case "SalaAula":
                    boolean temProjetor = Boolean.parseBoolean(especifico);
                    espaco = new SalaAula(id, nome, capacidade, localizacao, temProjetor);
                    break;
                case "Laboratorio":
                    espaco = new Laboratorio(id, nome, capacidade, localizacao, especifico);
                    break;
                case "SalaReuniao":
                    espaco = new SalaReuniao(id, nome, capacidade, localizacao, especifico);
                    break;
                case "QuadraEsportiva":
                    espaco = new QuadraEsportiva(id, nome, capacidade, localizacao, especifico);
                    break;
                case "Auditorio":
                    boolean temSom = Boolean.parseBoolean(especifico);
                    espaco = new Auditorio(id, nome, capacidade, localizacao, temSom);
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "Tipo inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
            }

            EspacoController.salvar(espaco);
            LogController.registrar("Espaço cadastrado: " + nome, "admin");
            JOptionPane.showMessageDialog(this, "Espaço salvo com sucesso!");
            dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Capacidade deve ser um número inteiro.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar espaço: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}

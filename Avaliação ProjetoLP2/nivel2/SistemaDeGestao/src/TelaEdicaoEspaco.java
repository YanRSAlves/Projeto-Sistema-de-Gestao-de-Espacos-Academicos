import javax.swing.*;
import java.awt.*;

public class TelaEdicaoEspaco extends JDialog {
    private JTextField campoNome, campoTipo, campoCapacidade, campoDescricao;
    private Espaco espaco;
    private boolean salvo = false;

    public TelaEdicaoEspaco(Frame owner, Espaco espaco) {
        super(owner, "Editar Espaço: " + espaco.getNome(), true);
        this.espaco = espaco;

        setSize(450, 280);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(10, 10));

        JPanel painelFormulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        painelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Linha Nome
        gbc.gridx = 0; gbc.gridy = 0;
        painelFormulario.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        campoNome = new JTextField(espaco.getNome(), 20);
        painelFormulario.add(campoNome, gbc);

        // Linha Tipo
        gbc.gridx = 0; gbc.gridy++; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        painelFormulario.add(new JLabel("Tipo:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        campoTipo = new JTextField(espaco.getTipo(), 20);
        painelFormulario.add(campoTipo, gbc);

        // Linha Capacidade
        gbc.gridx = 0; gbc.gridy++; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        painelFormulario.add(new JLabel("Capacidade:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        campoCapacidade = new JTextField(String.valueOf(espaco.getCapacidade()), 20);
        painelFormulario.add(campoCapacidade, gbc);

        // Linha Descrição
        gbc.gridx = 0; gbc.gridy++; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        painelFormulario.add(new JLabel("Descrição:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        campoDescricao = new JTextField(espaco.getDescricao(), 20);
        painelFormulario.add(campoDescricao, gbc);

        // Lógica futura para campos de subtipos (Laboratorio, etc.) pode ser adicionada aqui

        JPanel painelBotao = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton botaoSalvar = new JButton("Salvar Alterações");
        painelBotao.add(botaoSalvar);

        add(painelFormulario, BorderLayout.CENTER);
        add(painelBotao, BorderLayout.SOUTH);

        botaoSalvar.addActionListener(e -> salvarAlteracoes());
    }

    private void salvarAlteracoes() {
        String nome = campoNome.getText().trim();
        String tipo = campoTipo.getText().trim();
        if (nome.isEmpty() || tipo.isEmpty() || campoCapacidade.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome, Tipo e Capacidade são obrigatórios.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int capacidade = Integer.parseInt(campoCapacidade.getText().trim());
            espaco.setNome(nome);
            espaco.setTipo(tipo);
            espaco.setCapacidade(capacidade);
            espaco.setDescricao(campoDescricao.getText().trim());
            
            EspacoDAO.atualizarEspaco(espaco);
            JOptionPane.showMessageDialog(this, "Espaço atualizado com sucesso!");
            this.salvo = true;
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Capacidade deve ser um número válido.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
        }
    }
    public boolean isSalvo() {
        return this.salvo;
    }
}
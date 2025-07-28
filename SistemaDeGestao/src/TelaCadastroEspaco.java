import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaCadastroEspaco extends JDialog {
    private JTextField campoNome, campoTipo, campoCapacidade, campoDescricao, campoEquipamentos;
    private JCheckBox checkProjetor;
    private JButton botaoSalvar;

    public TelaCadastroEspaco(Frame owner) {
        super(owner, "Cadastrar Novo Espaço", true); // O 'true' torna a janela modal
        setSize(400, 300);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(10, 10));

        // --- Painel do Formulário ---
        JPanel painelFormulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        painelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Labels e Campos de Texto
        gbc.gridx = 0;
        gbc.gridy = 0;
        painelFormulario.add(new JLabel("Nome:"), gbc);
        campoNome = new JTextField(20);
        gbc.gridx = 1;
        painelFormulario.add(campoNome, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        painelFormulario.add(new JLabel("Tipo (Ex: Laboratório):"), gbc);
        campoTipo = new JTextField(20);
        gbc.gridx = 1;
        painelFormulario.add(campoTipo, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        painelFormulario.add(new JLabel("Capacidade:"), gbc);
        campoCapacidade = new JTextField(20);
        gbc.gridx = 1;
        painelFormulario.add(campoCapacidade, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        painelFormulario.add(new JLabel("Descrição:"), gbc);
        campoDescricao = new JTextField(20);
        gbc.gridx = 1;
        painelFormulario.add(campoDescricao, gbc);

        // Campos específicos que aparecem/desaparecem
        gbc.gridx = 0;
        gbc.gridy++;
        painelFormulario.add(new JLabel("Equipamentos (se Laboratório):"), gbc);
        campoEquipamentos = new JTextField(20);
        gbc.gridx = 1;
        painelFormulario.add(campoEquipamentos, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        painelFormulario.add(new JLabel("Possui Projetor (se Auditório):"), gbc);
        checkProjetor = new JCheckBox();
        gbc.gridx = 1;
        painelFormulario.add(checkProjetor, gbc);


        // --- Painel do Botão Salvar ---
        JPanel painelBotao = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botaoSalvar = new JButton("Salvar");
        painelBotao.add(botaoSalvar);

        // Adiciona os painéis à janela
        add(painelFormulario, BorderLayout.CENTER);
        add(painelBotao, BorderLayout.SOUTH);

        // --- Ação do Botão Salvar ---
        botaoSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarEspaco();
            }
        });
    }

    private void salvarEspaco() {
        // Coleta os dados dos campos
        String nome = campoNome.getText();
        String tipo = campoTipo.getText();
        String capacidadeStr = campoCapacidade.getText();
        String descricao = campoDescricao.getText();

        // Validação simples
        if (nome.isEmpty() || tipo.isEmpty() || capacidadeStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome, Tipo e Capacidade são obrigatórios.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int capacidade = Integer.parseInt(capacidadeStr);
            Espaco novoEspaco;

            // Cria o objeto do tipo correto (usando nossa lógica de herança)
            if ("Laboratório".equalsIgnoreCase(tipo)) {
                String equipamentos = campoEquipamentos.getText();
                novoEspaco = new Laboratorio(0, nome, tipo, capacidade, descricao, equipamentos);
            } else if ("Auditório".equalsIgnoreCase(tipo)) {
                boolean projetor = checkProjetor.isSelected();
                novoEspaco = new Auditorio(0, nome, tipo, capacidade, descricao, projetor);
            } else {
                novoEspaco = new EspacoGeral(0, nome, tipo, capacidade, descricao);
            }

            // Usa o DAO para salvar no banco de dados
            EspacoDAO.adicionarEspaco(novoEspaco);
            
            JOptionPane.showMessageDialog(this, "Espaço cadastrado com sucesso!");
            dispose(); // Fecha a janela de cadastro

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Capacidade deve ser um número válido.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
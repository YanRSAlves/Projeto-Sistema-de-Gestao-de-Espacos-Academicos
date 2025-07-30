import javax.swing.*;
import java.awt.*;

public class TelaEdicaoUsuario extends JDialog {
    private final JTextField campoNome;
    private final JTextField campoEmail;
    private final JTextField campoTipo;
    private final JPasswordField campoSenha;
    private final Usuario usuario;

    public TelaEdicaoUsuario(Frame owner, Usuario usuario) {
        super(owner, "Editar Usuário: " + usuario.getNome(), true);
        this.usuario = usuario;

        setSize(450, 250);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(10, 10));

        // --- Painel do Formulário ---
        JPanel painelFormulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        painelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- Linha Nome ---
        gbc.gridx = 0; gbc.gridy = 0;
        painelFormulario.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        campoNome = new JTextField(usuario.getNome(), 20);
        painelFormulario.add(campoNome, gbc);

        // --- Linha Email ---
        gbc.gridx = 0; gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        painelFormulario.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        campoEmail = new JTextField(usuario.getEmail(), 20);
        painelFormulario.add(campoEmail, gbc);

        // --- Linha Senha ---
        gbc.gridx = 0; gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        painelFormulario.add(new JLabel("Nova Senha (deixe em branco para não alterar):"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        campoSenha = new JPasswordField();
        painelFormulario.add(campoSenha, gbc);
        
        // --- Linha Tipo ---
        gbc.gridx = 0; gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        painelFormulario.add(new JLabel("Tipo (admin/usuario):"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        campoTipo = new JTextField(usuario.getTipo(), 20);
        painelFormulario.add(campoTipo, gbc);

        // --- Painel do Botão Salvar ---
        JPanel painelBotao = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton botaoSalvar = new JButton("Salvar Alterações");
        painelBotao.add(botaoSalvar);

        add(painelFormulario, BorderLayout.CENTER);
        add(painelBotao, BorderLayout.SOUTH);

        botaoSalvar.addActionListener(e -> salvarAlteracoes());
    }

    private void salvarAlteracoes() {
        // Coleta os dados dos campos
        String nome = campoNome.getText().trim();
        String email = campoEmail.getText().trim();
        String tipo = campoTipo.getText().trim();
        String novaSenha = new String(campoSenha.getPassword());

        // Validações Aprimoradas
        if (nome.isEmpty() || email.isEmpty() || tipo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Os campos Nome, Email e Tipo não podem ficar vazios.", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!tipo.equalsIgnoreCase("admin") && !tipo.equalsIgnoreCase("usuario")) {
            JOptionPane.showMessageDialog(this, "O campo 'Tipo' deve ser 'admin' ou 'usuario'.", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Atualização do Objeto
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setTipo(tipo);
        
        if (!novaSenha.isEmpty()) {
            usuario.setSenha(novaSenha);
        }

        // Persistência no Banco
        UsuarioDAO.atualizarUsuario(usuario);

        JOptionPane.showMessageDialog(this, "Usuário atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        dispose(); // Fecha a janela de edição
    }
}
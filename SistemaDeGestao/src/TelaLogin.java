import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaLogin extends JFrame {
    private JTextField campoEmail;
    private JPasswordField campoSenha;
    private JButton botaoEntrar;
    private JButton botaoCadastrar; // Botão novo para cadastro

    public TelaLogin() {
        // --- Configurações da Janela ---
        setTitle("Login - Sistema de Gestão de Espaços");
        setSize(400, 220); // Aumentei um pouco a altura
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza a janela

        // --- Painel Principal ---
        JPanel painel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Espaçamento entre os componentes
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- Componentes do Formulário ---
        // Rótulo Email
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        painel.add(new JLabel("Email:"), gbc);

        // Campo de Texto Email
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        campoEmail = new JTextField(20);
        painel.add(campoEmail, gbc);

        // Rótulo Senha
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        painel.add(new JLabel("Senha:"), gbc);

        // Campo de Senha
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        campoSenha = new JPasswordField();
        painel.add(campoSenha, gbc);

        // --- Botões ---
        botaoEntrar = new JButton("Entrar");
        botaoCadastrar = new JButton("Cadastrar-se");

        // Painel para os botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBotoes.add(botaoCadastrar);
        painelBotoes.add(botaoEntrar);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_END;
        painel.add(painelBotoes, gbc);

        // --- Adicionar Ações aos Botões ---
        botaoEntrar.addActionListener(e -> executarLogin());

        botaoCadastrar.addActionListener(e -> abrirTelaCadastro());

        // Adiciona o painel principal à janela
        add(painel);
    }

    private void executarLogin() {
        String email = campoEmail.getText();
        String senha = new String(campoSenha.getPassword());

        if (email.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Email e senha devem ser preenchidos.", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Usuario usuario = UsuarioDAO.buscarPorEmailESenha(email, senha);

        if (usuario != null) {
            JOptionPane.showMessageDialog(this, "Login bem-sucedido! Bem-vindo(a), " + usuario.getNome() + "!");
            LogDAO.registrar(usuario.getId(), "fez login via GUI");

            dispose(); // Fecha a tela de login

            // ABRIR AS PRÓXIMAS TELAS (AINDA VAMOS CRIÁ-LAS)
            if ("admin".equalsIgnoreCase(usuario.getTipo())) {
                new TelaAdmin(usuario).setVisible(true); // Exemplo futuro
                System.out.println("Abrir tela de Admin para: " + usuario.getNome());
            } else {
                new TelaUsuario(usuario).setVisible(true); // Exemplo futuro
                System.out.println("Abrir tela de Usuário para: " + usuario.getNome());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Email ou senha incorretos.", "Erro de Login", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirTelaCadastro() {
        // AINDA VAMOS CRIAR A TELA DE CADASTRO
        // new TelaCadastro().setVisible(true);
        JOptionPane.showMessageDialog(this, "Funcionalidade de cadastro ainda não implementada.");
    }


    public static void main(String[] args) {
        // Garante que a GUI será executada na thread de eventos do Swing
        SwingUtilities.invokeLater(() -> {
            // Adicionando um Look and Feel para deixar a aparência mais moderna
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new TelaLogin().setVisible(true);
        });
    }
}

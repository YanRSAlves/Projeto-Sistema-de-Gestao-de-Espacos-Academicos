// Tela de login com campos de email e senha; autentica usuario e abre o menu conforme o tipo.

import java.awt.*;
import javax.swing.*;

public class TelaLogin extends JFrame {
    private JTextField campoEmail;
    private JPasswordField campoSenha;
    private JButton btnEntrar;

    public TelaLogin() {
        setTitle("Login - Trabalho Final LP2");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        getContentPane().setBackground(new Color(40, 40, 40));

        // Título centralizado no topo
        JLabel titulo = new JLabel("Trabalho Final LP2", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setForeground(Color.WHITE);
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(titulo, BorderLayout.NORTH);

        // Painel central com BoxLayout (vertical)
        JPanel painelCentro = new JPanel();
        painelCentro.setLayout(new BoxLayout(painelCentro, BoxLayout.Y_AXIS));
        painelCentro.setBackground(new Color(40, 40, 40));

        // Campos de email e senha
        campoEmail = new JTextField(20);
        campoSenha = new JPasswordField(20);

        JLabel lblEmail = new JLabel("Email:");
        JLabel lblSenha = new JLabel("Senha:");
        lblEmail.setForeground(Color.WHITE);
        lblSenha.setForeground(Color.WHITE);

        // Painel de formulario com alinhamento central
        JPanel painelFormulario = new JPanel();
        painelFormulario.setLayout(new GridLayout(2, 2, 10, 10));
        painelFormulario.setBackground(new Color(40, 40, 40));
        painelFormulario.add(lblEmail);
        painelFormulario.add(campoEmail);
        painelFormulario.add(lblSenha);
        painelFormulario.add(campoSenha);
        painelFormulario.setMaximumSize(new Dimension(300, 80));
        painelFormulario.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Botao de entrar
        btnEntrar = new JButton("Entrar");
        btnEntrar.setBackground(new Color(70, 70, 70));
        btnEntrar.setForeground(Color.WHITE);
        btnEntrar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnEntrar.setPreferredSize(new Dimension(100, 30));

        // Adiciona ao painel central
        painelCentro.add(Box.createVerticalGlue());
        painelCentro.add(painelFormulario);
        painelCentro.add(Box.createRigidArea(new Dimension(0, 20)));
        painelCentro.add(btnEntrar);
        painelCentro.add(Box.createVerticalGlue());

        add(painelCentro, BorderLayout.CENTER);

        btnEntrar.addActionListener(e -> autenticar());
    }

    private void autenticar() {
        String email = campoEmail.getText().trim();
        String senha = new String(campoSenha.getPassword()).trim();

        if (email.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha email e senha.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Usuario user = UsuarioController.autenticar(email, senha);
        if (user == null) {
            JOptionPane.showMessageDialog(this, "Email ou senha incorretos.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this, "Login realizado com sucesso!");
        dispose();

        if ("admin".equalsIgnoreCase(user.getTipo())) {
            new TelaMenuAdmin(user).setVisible(true);
        } else {
            new TelaMenuUsuario(user).setVisible(true);
        }
    }
}

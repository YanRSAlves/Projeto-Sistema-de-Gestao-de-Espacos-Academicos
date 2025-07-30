/*
 * Esta classe representa a tela de cadastro de usuarios no sistema.
 *
 * O formulario permite ao administrador inserir nome, e-mail, senha
 * e tipo de usuario ("usuario" ou "admin") para cadastrar um novo usuario.
 *
 * Ao clicar em "Cadastrar", os dados sao validados e, se validos, e criado
 * um novo objeto Usuario com ID gerado automaticamente (UUID),
 * que e salvo por meio do UsuarioController.
 *
 * Tambem oferece um botao "Limpar" para apagar os campos e,
 * ao cadastrar com sucesso, atualiza a tabela da tela pai (TelaGerenciarUsuarios),
 * se ela tiver sido passada no construtor.
 *
 * A interface segue o tema escuro para manter a consistencia visual com o sistema.
 */
import java.awt.*;
import java.util.UUID;
import javax.swing.*;

public class TelaCadastroUsuario extends JFrame {

    private JTextField campoNome, campoEmail;
    private JPasswordField campoSenha;
    private JComboBox<String> comboTipo;
    private JButton btnCadastrar, btnLimpar;

    private TelaGerenciarUsuarios telaPai;

    public TelaCadastroUsuario(TelaGerenciarUsuarios telaPai) {
        this.telaPai = telaPai;

        setTitle("Cadastro de Usuário");
        setSize(500, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(new Color(40, 40, 40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titulo = new JLabel("Cadastro de Usuário", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        painel.add(titulo, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.gridx = 0;
        painel.add(criarLabel("Nome:"), gbc);
        gbc.gridx = 1;
        campoNome = criarCampo();
        painel.add(campoNome, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        painel.add(criarLabel("Email:"), gbc);
        gbc.gridx = 1;
        campoEmail = criarCampo();
        painel.add(campoEmail, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        painel.add(criarLabel("Senha:"), gbc);
        gbc.gridx = 1;
        campoSenha = new JPasswordField();
        campoSenha.setBackground(new Color(60, 60, 60));
        campoSenha.setForeground(Color.WHITE);
        campoSenha.setCaretColor(Color.WHITE);
        painel.add(campoSenha, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        painel.add(criarLabel("Tipo:"), gbc);
        gbc.gridx = 1;
        comboTipo = new JComboBox<>(new String[]{"usuario", "admin"});
        comboTipo.setBackground(new Color(60, 60, 60));
        comboTipo.setForeground(Color.WHITE);
        painel.add(comboTipo, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        btnCadastrar = criarBotao("Cadastrar");
        painel.add(btnCadastrar, gbc);
        gbc.gridx = 1;
        btnLimpar = criarBotao("Limpar");
        painel.add(btnLimpar, gbc);

        add(painel);

        btnCadastrar.addActionListener(e -> cadastrarUsuario());
        btnLimpar.addActionListener(e -> limparCampos());
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

    private void cadastrarUsuario() {
        String nome = campoNome.getText().trim();
        String email = campoEmail.getText().trim();
        String senha = new String(campoSenha.getPassword()).trim();
        String tipo = comboTipo.getSelectedItem().toString();

        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String id = UUID.randomUUID().toString();
        Usuario novo = new Usuario(id, nome, email, senha, tipo);
        UsuarioController.salvar(novo);

        JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso!");
        limparCampos();

        if (telaPai != null) {
            telaPai.atualizarListaUsuarios();
        }
    }

    private void limparCampos() {
        campoNome.setText("");
        campoEmail.setText("");
        campoSenha.setText("");
        comboTipo.setSelectedIndex(0);
    }
}

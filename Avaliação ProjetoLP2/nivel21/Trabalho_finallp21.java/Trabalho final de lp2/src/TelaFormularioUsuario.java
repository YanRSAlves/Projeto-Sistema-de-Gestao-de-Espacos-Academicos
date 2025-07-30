/*
 * Esta classe representa uma interface grafica (JFrame) reutilizável para cadastrar ou editar um usuario.
 * Campos disponíveis:
 * - Nome (texto)
 * - E-mail (texto — nao pode ser alterado ao editar)
 * - Senha (campo oculto)
 * - Tipo de usuario (admin ou usuario)
 *
 * O botao "Salvar":
 * - Cria um novo usuario com `UUID` caso esteja cadastrando.
 * - Atualiza o usuario existente na lista ao editar.
 * - Faz validacao para evitar duplicidade de e-mails ao cadastrar.
 * - Usa `UsuarioController` para persistencia e `LogController` para registrar acoes.
 *
 * O botao "Cancelar" fecha a janela sem salvar alteracoes.
 */
import java.awt.*;
import java.util.List;
import java.util.UUID;
import javax.swing.*;

public class TelaFormularioUsuario extends JFrame {
    private JTextField campoNome, campoEmail;
    private JPasswordField campoSenha;
    private JComboBox<String> comboTipo;
    private JButton btnSalvar, btnCancelar;
    private Usuario usuario;

    public TelaFormularioUsuario(Usuario usuario) {
        this.usuario = usuario;

        setTitle(usuario == null ? "Novo Usuário" : "Editar Usuário");
        setSize(500, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(new Color(40, 40, 40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titulo = new JLabel(usuario == null ? "Cadastrar Novo Usuário" : "Editar Usuário", SwingConstants.CENTER);
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
        campoNome = criarCampoTexto();
        painel.add(campoNome, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        painel.add(criarLabel("Email:"), gbc);
        gbc.gridx = 1;
        campoEmail = criarCampoTexto();
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
        comboTipo = new JComboBox<>(new String[]{"admin", "usuario"});
        comboTipo.setBackground(new Color(60, 60, 60));
        comboTipo.setForeground(Color.WHITE);
        painel.add(comboTipo, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        btnSalvar = criarBotao("Salvar");
        painel.add(btnSalvar, gbc);
        gbc.gridx = 1;
        btnCancelar = criarBotao("Cancelar");
        painel.add(btnCancelar, gbc);

        add(painel);

        if (usuario != null) {
            campoNome.setText(usuario.getNome());
            campoEmail.setText(usuario.getEmail());
            campoEmail.setEnabled(false); // email nao editavel
            campoSenha.setText(usuario.getSenha());
            comboTipo.setSelectedItem(usuario.getTipo());
        }

        btnSalvar.addActionListener(e -> salvarUsuario());
        btnCancelar.addActionListener(e -> dispose());
    }

    private JLabel criarLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setForeground(Color.WHITE);
        return label;
    }

    private JTextField criarCampoTexto() {
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

    private void salvarUsuario() {
        String nome = campoNome.getText().trim();
        String email = campoEmail.getText().trim();
        String senha = new String(campoSenha.getPassword()).trim();
        String tipo = (String) comboTipo.getSelectedItem();

        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || tipo == null) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (usuario == null) {
            boolean existe = UsuarioController.listarTodos().stream()
                .anyMatch(u -> u.getEmail().equalsIgnoreCase(email));
            if (existe) {
                JOptionPane.showMessageDialog(this, "Email já cadastrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Usuario novo = new Usuario(UUID.randomUUID().toString(), nome, email, senha, tipo);
            UsuarioController.salvar(novo);
            LogController.registrar("Usuário criado: " + email, email);
            JOptionPane.showMessageDialog(this, "Usuário criado com sucesso.");
        } else {
            Usuario atualizado = new Usuario(usuario.getId(), nome, email, senha, tipo);
            List<Usuario> lista = UsuarioController.listarTodos();
            for (int i = 0; i < lista.size(); i++) {
                if (lista.get(i).getId().equals(usuario.getId())) {
                    lista.set(i, atualizado);
                    break;
                }
            }
            UsuarioController.salvarTodos(lista);
            LogController.registrar("Usuário editado: " + email, email);
            JOptionPane.showMessageDialog(this, "Usuário atualizado com sucesso.");
        }
        dispose();
    }
}

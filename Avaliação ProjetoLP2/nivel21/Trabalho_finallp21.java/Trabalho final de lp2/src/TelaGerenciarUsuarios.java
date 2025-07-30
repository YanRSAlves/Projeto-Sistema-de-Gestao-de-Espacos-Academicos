/*
 * Esta classe representa a tela grafica de gerenciamento de usuarios do sistema.
 * Exibe uma tabela com os usuarios cadastrados, mostrando:
 *    - ID
 *    - Nome
 *    - Email
 *    - Tipo (admin ou usuario)
 *
 *  Botão Adicionar Usuário:
 *  Função carregarUsuarios():
 *  Função atualizarListaUsuarios():
 *  Estilo: Tema escuro personalizado
 */
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TelaGerenciarUsuarios extends JFrame {

    private JTable tabelaUsuarios;
    private DefaultTableModel modeloTabela;

    public TelaGerenciarUsuarios() {
        setTitle("Gerenciar Usuários");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Painel do título (para centralizar e estilizar)
        JPanel painelTitulo = new JPanel(new BorderLayout());
        painelTitulo.setBackground(new Color(40, 40, 40));
        JLabel titulo = criarLabelTitulo("Gerenciamento de Usuários");
        painelTitulo.add(titulo, BorderLayout.CENTER);
        painelTitulo.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        add(painelTitulo, BorderLayout.NORTH);

        // Tabela
        modeloTabela = new DefaultTableModel(new Object[]{"ID", "Nome", "Email", "Tipo"}, 0);
        tabelaUsuarios = new JTable(modeloTabela);
        tabelaUsuarios.setBackground(new Color(50, 50, 50));
        tabelaUsuarios.setForeground(Color.WHITE);
        tabelaUsuarios.setSelectionBackground(new Color(100, 100, 100));
        tabelaUsuarios.setSelectionForeground(Color.WHITE);
        tabelaUsuarios.setGridColor(Color.GRAY);

        JScrollPane scrollPane = new JScrollPane(tabelaUsuarios);
        scrollPane.getViewport().setBackground(new Color(50, 50, 50));
        add(scrollPane, BorderLayout.CENTER);

        // Botoes
        JButton btnAdicionar = criarBotao("Adicionar Usuário");
        btnAdicionar.addActionListener(e -> abrirTelaCadastro());

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelBotoes.setBackground(new Color(40, 40, 40));
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        painelBotoes.add(btnAdicionar);

        add(painelBotoes, BorderLayout.SOUTH);

        getContentPane().setBackground(new Color(40, 40, 40));

        carregarUsuarios();
    }

    private JLabel criarLabelTitulo(String texto) {
        JLabel label = new JLabel(texto, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setForeground(Color.WHITE);
        return label;
    }

    private JButton criarBotao(String texto) {
        JButton btn = new JButton(texto);
        btn.setBackground(new Color(70, 70, 70));
        btn.setForeground(Color.WHITE);
        return btn;
    }

    private void abrirTelaCadastro() {
        TelaCadastroUsuario cadastro = new TelaCadastroUsuario(this);
        cadastro.setVisible(true);
    }

    public void carregarUsuarios() {
        modeloTabela.setRowCount(0);
        List<Usuario> usuarios = UsuarioController.listarTodos();
        for (Usuario u : usuarios) {
            modeloTabela.addRow(new Object[]{u.getId(), u.getNome(), u.getEmail(), u.getTipo()});
        }
    }

    public void atualizarListaUsuarios() {
        carregarUsuarios();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TelaGerenciarUsuarios().setVisible(true);
        });
    }
}

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TelaRelatorios extends JFrame {

    private JTable tabela;

    public TelaRelatorios() {
        // Config da janela
        setTitle("Relatórios de Reservas");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(40, 40, 40));

        // Título
        JLabel titulo = criarLabelTitulo("Relatórios de Reservas");
        JPanel painelTitulo = new JPanel(new BorderLayout());
        painelTitulo.setBackground(new Color(40, 40, 40));
        painelTitulo.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        painelTitulo.add(titulo, BorderLayout.CENTER);
        add(painelTitulo, BorderLayout.NORTH);

        // Tabela
        String[] colunas = {"ID Reserva", "Usuário", "Espaço", "Data", "Hora Início", "Hora Fim"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0);
        tabela = new JTable(model);

        // Estilo da tabela
        tabela.setBackground(new Color(50, 50, 50));
        tabela.setForeground(Color.WHITE);
        tabela.setSelectionBackground(new Color(100, 100, 100));
        tabela.setSelectionForeground(Color.WHITE);
        tabela.setGridColor(Color.GRAY);

        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.getViewport().setBackground(new Color(50, 50, 50));
        add(scrollPane, BorderLayout.CENTER);

        // Carrega dados
        carregarDados(model);
    }

    // Carrega reservas e dados relacionados
    private void carregarDados(DefaultTableModel model) {
        List<Reserva> reservas = ReservaController.listarTodos();
        List<Usuario> usuarios = UsuarioController.listarTodos();
        List<Espaco> espacos = EspacoController.listarTodos();

        for (Reserva r : reservas) {
            Usuario usuario = usuarios.stream()
                .filter(u -> u.getEmail().equals(r.getEmailUsuario()))
                .findFirst()
                .orElse(null);

            Espaco espaco = espacos.stream()
                .filter(e -> e.getId().equals(r.getIdEspaco()))
                .findFirst()
                .orElse(null);

            String nomeUsuario = (usuario != null) ? usuario.getNome() : "Desconhecido";
            String nomeEspaco = (espaco != null) ? espaco.getNome() : "Desconhecido";

            // Adiciona linha
            Object[] linha = {
                r.getIdReserva(),
                nomeUsuario,
                nomeEspaco,
                r.getData(),
                r.getHoraInicio(),
                r.getHoraFim()
            };

            model.addRow(linha);
        }
    }

    // Cria titulo estilizado
    private JLabel criarLabelTitulo(String texto) {
        JLabel label = new JLabel(texto, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 22));
        label.setForeground(Color.WHITE);
        return label;
    }
}

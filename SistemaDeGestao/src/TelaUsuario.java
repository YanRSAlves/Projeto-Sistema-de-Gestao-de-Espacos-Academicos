import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaUsuario extends JFrame {
    private Usuario usuario;
    private JPanel painelConteudo;
    private JTable tabelaExibida;

    public TelaUsuario(Usuario usuario) {
        this.usuario = usuario;

        // --- Configurações da Janela ---
        setTitle("Painel do Usuário - Bem-vindo(a), " + usuario.getNome());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // --- Painel de Botões (Menu) ---
        JPanel painelMenu = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelMenu.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        
        JButton botaoVerEspacos = new JButton("Ver Espaços Disponíveis");
        JButton botaoFazerReserva = new JButton("Fazer Nova Reserva");
        JButton botaoMinhasReservas = new JButton("Ver Minhas Reservas");
        
        painelMenu.add(botaoVerEspacos);
        painelMenu.add(botaoFazerReserva);
        painelMenu.add(botaoMinhasReservas);

        add(painelMenu, BorderLayout.NORTH); // Menu na parte de cima

        // --- Painel de Conteúdo (Área Principal) ---
        painelConteudo = new JPanel(new BorderLayout());
        painelConteudo.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        painelConteudo.add(new JLabel("Selecione uma opção no menu acima.", SwingConstants.CENTER));
        add(painelConteudo, BorderLayout.CENTER);

        // --- Ações dos Botões ---

        // Ação para "Ver Espaços Disponíveis"
        botaoVerEspacos.addActionListener(e -> atualizarTabelaEspacos());

        // Ação para "Ver Minhas Reservas"
        botaoMinhasReservas.addActionListener(e -> atualizarTabelaMinhasReservas());
        
        // Ação para "Fazer Nova Reserva" (implementaremos a seguir)
        botaoFazerReserva.addActionListener(e -> {
            // Pega a linha selecionada na tabela
            int linhaSelecionada = tabelaExibida.getSelectedRow();

            if (linhaSelecionada == -1) {
                JOptionPane.showMessageDialog(this, "Por favor, selecione um espaço na tabela para fazer uma reserva.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Pega o ID do espaço selecionado
            int idEspaco = (int) tabelaExibida.getValueAt(linhaSelecionada, 0);
            Espaco espacoSelecionado = EspacoDAO.buscarPorId(idEspaco);

            if (espacoSelecionado != null) {
                // Abre a tela de reserva, passando o espaço e o usuário
                TelaNovaReserva telaReserva = new TelaNovaReserva(this, espacoSelecionado, this.usuario);
                telaReserva.setVisible(true);

                // Se uma reserva foi feita, atualiza a lista de "Minhas Reservas"
                if (telaReserva.isReservaFeita()) {
                    atualizarTabelaMinhasReservas();
                }
            }
        });

        // Inicia a tela mostrando os espaços disponíveis
        atualizarTabelaEspacos();
    }

    private void atualizarTabelaEspacos() {
        painelConteudo.removeAll();
        List<Espaco> espacos = EspacoDAO.listarEspacos();
        String[] colunas = {"ID", "Nome", "Tipo", "Capacidade", "Descrição"};
        DefaultTableModel modeloTabela = new DefaultTableModel(colunas, 0);
        for (Espaco esp : espacos) {
            Object[] linha = {esp.getId(), esp.getNome(), esp.getTipo(), esp.getCapacidade(), esp.getDescricao()};
            modeloTabela.addRow(linha);
        }
        tabelaExibida = new JTable(modeloTabela);
        configurarTabela(tabelaExibida);
        JScrollPane scrollPane = new JScrollPane(tabelaExibida);
        painelConteudo.add(scrollPane, BorderLayout.CENTER);
        revalidarPainel();
    }

    private void atualizarTabelaMinhasReservas() {
        painelConteudo.removeAll();
        List<Reserva> todasReservas = ReservaDAO.listarReservas();
        String[] colunas = {"ID Reserva", "ID Espaço", "Data", "Início", "Fim"};
        DefaultTableModel modeloTabela = new DefaultTableModel(colunas, 0);
        
        for (Reserva r : todasReservas) {
            // Filtra para mostrar apenas as reservas do usuário logado
            if (r.getIdUsuario() == this.usuario.getId()) {
                Object[] linha = {r.getId(), r.getIdEspaco(), r.getData(), r.getHoraInicio(), r.getHoraFim()};
                modeloTabela.addRow(linha);
            }
        }
        tabelaExibida = new JTable(modeloTabela);
        configurarTabela(tabelaExibida);
        JScrollPane scrollPane = new JScrollPane(tabelaExibida);
        painelConteudo.add(scrollPane, BorderLayout.CENTER);
        revalidarPainel();
    }

    private void configurarTabela(JTable tabela) {
        tabela.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabela.setRowHeight(25);
        tabela.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
    }
    
    private void revalidarPainel() {
        painelConteudo.revalidate();
        painelConteudo.repaint();
    }
}
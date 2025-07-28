import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaAdmin extends JFrame {
    private final Usuario admin;
    private final JPanel painelConteudo; // Painel principal que mudará de conteúdo
    private JTable tabelaExibida; // Referência à tabela atualmente visível

    public TelaAdmin(Usuario admin) {
        this.admin = admin;

        // --- Configurações da Janela ---
        setTitle("Painel do Administrador - Bem-vindo(a), " + admin.getNome());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // --- Painel de Botões (Menu Lateral) ---
        JPanel painelMenu = new JPanel();
        painelMenu.setLayout(new GridLayout(10, 1, 5, 5));
        painelMenu.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Criando os botões
        JButton botaoListarUsuarios = new JButton("Listar Usuários");
        JButton botaoCriarEspaco = new JButton("Criar Novo Espaço");
        JButton botaoListarEspacos = new JButton("Listar Espaços");
        JButton botaoListarReservas = new JButton("Listar Todas as Reservas");
        JButton botaoAtualizarUsuario = new JButton("Atualizar Usuário");
        JButton botaoExcluirUsuario = new JButton("Excluir Usuário");
        JButton botaoAtualizarEspaco = new JButton("Atualizar Espaço");
        JButton botaoExcluirEspaco = new JButton("Excluir Espaço");
        JButton botaoExportarCSV = new JButton("Exportar Reservas (CSV)");
        JButton botaoVerRelatorios = new JButton("Ver Relatórios");

        // Adicionando os botões ao menu
        painelMenu.add(botaoListarUsuarios);
        painelMenu.add(botaoCriarEspaco);
        painelMenu.add(botaoListarEspacos);
        painelMenu.add(botaoListarReservas);
        painelMenu.add(botaoAtualizarUsuario);
        painelMenu.add(botaoExcluirUsuario);
        painelMenu.add(botaoAtualizarEspaco);
        painelMenu.add(botaoExcluirEspaco);
        painelMenu.add(botaoExportarCSV);
        painelMenu.add(botaoVerRelatorios);

        add(painelMenu, BorderLayout.WEST);

        // --- Painel de Conteúdo (Área Principal) ---
        painelConteudo = new JPanel(new BorderLayout());
        painelConteudo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        painelConteudo.add(new JLabel("Selecione uma opção no menu à esquerda.", SwingConstants.CENTER));
        add(painelConteudo, BorderLayout.CENTER);

        // --- AÇÕES DOS BOTÕES ---

        botaoListarUsuarios.addActionListener(e -> atualizarTabelaUsuarios());
        botaoListarEspacos.addActionListener(e -> atualizarTabelaEspacos());

        botaoCriarEspaco.addActionListener(e -> {
            TelaCadastroEspaco telaCadastro = new TelaCadastroEspaco(this);
            telaCadastro.setVisible(true);
            atualizarTabelaEspacos(); // Atualiza a tabela após o cadastro
        });

        botaoListarReservas.addActionListener(e -> {
            painelConteudo.removeAll();
            List<Reserva> reservas = ReservaDAO.listarReservas();
            String[] colunas = {"ID Reserva", "ID Usuário", "ID Espaço", "Data", "Início", "Fim"};
            DefaultTableModel modeloTabela = new DefaultTableModel(colunas, 0);
            for (Reserva r : reservas) {
                Object[] linha = {r.getId(), r.getIdUsuario(), r.getIdEspaco(), r.getData(), r.getHoraInicio(), r.getHoraFim()};
                modeloTabela.addRow(linha);
            }
            tabelaExibida = new JTable(modeloTabela);
            configurarTabela(tabelaExibida);
            JScrollPane scrollPane = new JScrollPane(tabelaExibida);
            painelConteudo.add(scrollPane, BorderLayout.CENTER);
            revalidarPainel();
        });

        botaoAtualizarUsuario.addActionListener(e -> {
            if (tabelaExibida == null || !tabelaExibida.getModel().getColumnName(2).equals("Email")) {
                 JOptionPane.showMessageDialog(this, "Por favor, liste os usuários primeiro.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                 return;
            }
            int linhaSelecionada = tabelaExibida.getSelectedRow();
            if (linhaSelecionada == -1) {
                JOptionPane.showMessageDialog(this, "Por favor, selecione um usuário na tabela para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int idUsuario = (int) tabelaExibida.getValueAt(linhaSelecionada, 0);
            Usuario usuarioParaEditar = UsuarioDAO.buscarPorId(idUsuario);
            if (usuarioParaEditar != null) {
                TelaEdicaoUsuario telaEdicao = new TelaEdicaoUsuario(this, usuarioParaEditar);
                telaEdicao.setVisible(true);
                atualizarTabelaUsuarios();
            }
        });

        botaoExcluirUsuario.addActionListener(e -> {
            if (tabelaExibida == null || !tabelaExibida.getModel().getColumnName(2).equals("Email")) {
                 JOptionPane.showMessageDialog(this, "Por favor, liste os usuários primeiro.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                 return;
            }
            int linhaSelecionada = tabelaExibida.getSelectedRow();
            if (linhaSelecionada == -1) {
                JOptionPane.showMessageDialog(this, "Por favor, selecione um usuário na tabela para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int idUsuario = (int) tabelaExibida.getValueAt(linhaSelecionada, 0);
            String nomeUsuario = (String) tabelaExibida.getValueAt(linhaSelecionada, 1);
            int resposta = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir o usuário '" + nomeUsuario + "'?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (resposta == JOptionPane.YES_OPTION) {
                UsuarioDAO.deletarUsuario(idUsuario);
                JOptionPane.showMessageDialog(this, "Usuário excluído com sucesso.");
                atualizarTabelaUsuarios();
            }
        });
        // Ação para o botão "Atualizar Espaço"
        botaoAtualizarEspaco.addActionListener(e -> {
            if (tabelaExibida == null || !tabelaExibida.getModel().getColumnName(2).equals("Tipo")) {
                 JOptionPane.showMessageDialog(this, "Por favor, liste os espaços primeiro.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                 return;
            }
            int linhaSelecionada = tabelaExibida.getSelectedRow();
            if (linhaSelecionada == -1) {
                JOptionPane.showMessageDialog(this, "Por favor, selecione um espaço na tabela para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int idEspaco = (int) tabelaExibida.getValueAt(linhaSelecionada, 0);
            Espaco espacoParaEditar = EspacoDAO.buscarPorId(idEspaco);
            if (espacoParaEditar != null) {
                TelaEdicaoEspaco telaEdicao = new TelaEdicaoEspaco(this, espacoParaEditar);
                telaEdicao.setVisible(true);
                atualizarTabelaEspacos();
            }
        });

        // Ação para o botão "Excluir Espaço"
        botaoExcluirEspaco.addActionListener(e -> {
            if (tabelaExibida == null || !tabelaExibida.getModel().getColumnName(2).equals("Tipo")) {
                 JOptionPane.showMessageDialog(this, "Por favor, liste os espaços primeiro.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                 return;
            }
            int linhaSelecionada = tabelaExibida.getSelectedRow();
            if (linhaSelecionada == -1) {
                JOptionPane.showMessageDialog(this, "Por favor, selecione um espaço na tabela para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int idEspaco = (int) tabelaExibida.getValueAt(linhaSelecionada, 0);
            String nomeEspaco = (String) tabelaExibida.getValueAt(linhaSelecionada, 1);
            int resposta = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir o espaço '" + nomeEspaco + "'?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (resposta == JOptionPane.YES_OPTION) {
                EspacoDAO.deletarEspaco(idEspaco);
                JOptionPane.showMessageDialog(this, "Espaço excluído com sucesso.");
                atualizarTabelaEspacos();
            }
        });
        
        // Ação para o botão "Atualizar Espaço"
        botaoAtualizarEspaco.addActionListener(e -> {
            if (tabelaExibida == null || !tabelaExibida.getModel().getColumnName(2).equals("Tipo")) {
                 JOptionPane.showMessageDialog(this, "Por favor, liste os espaços primeiro.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                 return;
            }
            int linhaSelecionada = tabelaExibida.getSelectedRow();
            if (linhaSelecionada == -1) {
                JOptionPane.showMessageDialog(this, "Por favor, selecione um espaço na tabela para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int idEspaco = (int) tabelaExibida.getValueAt(linhaSelecionada, 0);
            Espaco espacoParaEditar = EspacoDAO.buscarPorId(idEspaco);
            if (espacoParaEditar != null) {
                TelaEdicaoEspaco telaEdicao = new TelaEdicaoEspaco(this, espacoParaEditar);
                telaEdicao.setVisible(true); // O código pausa aqui até a janela fechar

                // SÓ ATUALIZA A TABELA SE O USUÁRIO SALVOU AS ALTERAÇÕES
                if (telaEdicao.isSalvo()) {
                    atualizarTabelaEspacos();
                }
            }
        });

        // Ação para o botão "Excluir Espaço"
        botaoExcluirEspaco.addActionListener(e -> {
            if (tabelaExibida == null || !tabelaExibida.getModel().getColumnName(2).equals("Tipo")) {
                 JOptionPane.showMessageDialog(this, "Por favor, liste os espaços primeiro.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                 return;
            }
            int linhaSelecionada = tabelaExibida.getSelectedRow();
            if (linhaSelecionada == -1) {
                JOptionPane.showMessageDialog(this, "Por favor, selecione um espaço na tabela para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int idEspaco = (int) tabelaExibida.getValueAt(linhaSelecionada, 0);
            String nomeEspaco = (String) tabelaExibida.getValueAt(linhaSelecionada, 1);
            int resposta = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir o espaço '" + nomeEspaco + "'?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (resposta == JOptionPane.YES_OPTION) {
                EspacoDAO.deletarEspaco(idEspaco);
                JOptionPane.showMessageDialog(this, "Espaço excluído com sucesso.");
                atualizarTabelaEspacos();
            }
        });
        
        // Ações para os outros botões (Atualizar Espaço, etc.) serão adicionadas aqui...
    }

    private void atualizarTabelaUsuarios() {
        painelConteudo.removeAll();
        List<Usuario> usuarios = UsuarioDAO.listarUsuarios();
        String[] colunas = {"ID", "Nome", "Email", "Tipo"};
        DefaultTableModel modeloTabela = new DefaultTableModel(colunas, 0);
        for (Usuario u : usuarios) {
            Object[] linha = {u.getId(), u.getNome(), u.getEmail(), u.getTipo()};
            modeloTabela.addRow(linha);
        }
        tabelaExibida = new JTable(modeloTabela);
        configurarTabela(tabelaExibida);
        JScrollPane scrollPane = new JScrollPane(tabelaExibida);
        painelConteudo.add(scrollPane, BorderLayout.CENTER);
        revalidarPainel();
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
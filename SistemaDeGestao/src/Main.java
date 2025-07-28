import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        // Inicializa o banco de dados (só cria as tabelas se não existirem)
        Database.inicializar();
        Database.popularDadosIniciais(); 
        
        // --- INÍCIO DO CÓDIGO DE DEPURAÇÃO (TEMPORÁRIO) ---
        System.out.println("\n--- VERIFICANDO DADOS NO BANCO ---");
        List<Usuario> usuariosNoBanco = UsuarioDAO.listarUsuarios();
        if (usuariosNoBanco.isEmpty()) {
            System.out.println("!!! A TABELA DE USUÁRIOS ESTÁ VAZIA !!!");
        } else {
            System.out.println("Usuários encontrados no banco:");
            for (Usuario u : usuariosNoBanco) {
                System.out.println("-> Email: " + u.getEmail() + " | Senha: " + u.getSenha());
            }
        }
        System.out.println("------------------------------------");
        // --- FIM DO CÓDIGO DE DEPURAÇÃO ---

        
        while (true) {
            int opcao = menuInicial();

            switch (opcao) {
                case 1: // Fazer Login
                    Usuario usuario = fazerLogin();
                    if (usuario != null) {
                        // Se o login for bem-sucedido, exibe a mensagem e o menu apropriado
                        System.out.println("\nLogin realizado! Bem-vindo(a), " + usuario.getNome() + " (" + usuario.getTipo() + ")");
                        LogDAO.registrar(usuario.getId(), "fez login");

                        if (usuario.getTipo().equalsIgnoreCase("admin")) {
                            menuAdmin();
                        } else {
                            menuUsuario(usuario);
                        }
                        // Após o logout (sair dos menus), o loop termina e o programa encerra.
                        return;
                    } else {
                        // Se o login falhar, o loop continua e o menu inicial é mostrado novamente
                        System.out.println("Falha no login. Verifique seu email e senha.");
                    }
                    break;
                case 2: // Cadastrar Novo Usuário
                    cadastrarNovoUsuario();
                    // Após o cadastro, o loop continua para que o usuário possa fazer login
                    break;
                case 0: // Sair
                    System.out.println("Saindo do sistema... Até logo!");
                    return; // Encerra o programa
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        }
    }

    private static int menuInicial() {
        System.out.println("\n===== BEM-VINDO(A) AO SISTEMA DE GESTÃO DE ESPAÇOS =====");
        System.out.println("1. Fazer Login");
        System.out.println("2. Cadastrar Novo Usuário");
        System.out.println("0. Sair do Sistema");
        System.out.print("Escolha uma opção: ");
        try {
            int opcao = sc.nextInt();
            sc.nextLine(); // Limpar o buffer do scanner
            return opcao;
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, digite um número.");
            sc.nextLine(); // Limpa o buffer para a próxima entrada
            return -1; // Retorna um valor inválido para o switch
        }
    }

    private static void cadastrarNovoUsuario() {
        System.out.println("\n--- CADASTRO DE NOVO USUÁRIO ---");
        System.out.print("Nome completo: ");
        String nome = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Senha: ");
        String senha = sc.nextLine();

        // Por padrão, um novo usuário cadastrado é do tipo "usuario"
        Usuario novoUsuario = new Usuario(0, nome, email, senha, "usuario");

        // Usamos o DAO para adicionar o usuário ao banco de dados
        UsuarioDAO.adicionarUsuario(novoUsuario);
    }

    private static Usuario fazerLogin() {
        System.out.println("\n=== LOGIN ===");
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Senha: ");
        String senha = sc.nextLine();

        return UsuarioDAO.buscarPorEmailESenha(email, senha);
    }

    private static void menuAdmin() {
        int opcao;
        do {
            System.out.println("\n" + "=".repeat(20));
            System.out.println("      MENU ADMIN");
            System.out.println("=".repeat(20));
            System.out.println("1. Listar usuários");
            System.out.println("2. Criar novo espaço");
            System.out.println("3. Listar espaços");
            System.out.println("4. Listar todas as reservas");
            System.out.println("5. Atualizar usuário");
            System.out.println("6. Excluir usuário");
            System.out.println("7. Atualizar espaço");
            System.out.println("8. Excluir espaço");
            System.out.println("9. Exportar reservas para CSV");
            System.out.println("10. Relatórios estatísticos");
            System.out.println("0. Sair (Logout)");
            System.out.print("Opção: ");

            try {
                opcao = sc.nextInt();
                sc.nextLine(); // limpar buffer
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
                sc.nextLine();
                opcao = -1;
                continue;
            }

            switch (opcao) {
                case 1:
                    System.out.println("\n--- Usuários ---");
                    for (Usuario u : UsuarioDAO.listarUsuarios()) {
                        System.out.println(u);
                    }
                    break;
                case 2:
                    cadastrarNovoEspaco();
                    break;
                case 3:
                    System.out.println("\n--- Espaços ---");
                    for (Espaco e : EspacoDAO.listarEspacos()) {
                        System.out.println(e);
                    }
                    break;
                case 4:
                    System.out.println("\n--- Reservas ---");
                    for (Reserva r : ReservaDAO.listarReservas()) {
                        System.out.println(r);
                    }
                    break;
                case 5:
                    atualizarUsuario();
                    break;
                case 6:
                    deletarUsuario();
                    break;
                case 7:
                    atualizarEspaco();
                    break;
                case 8:
                    deletarEspaco();
                    break;
                case 9:
                    ExportadorCSV.exportarReservasCSV();
                    break;
                case 10:
                    System.out.println("\n=== Relatórios ===");
                    RelatorioEstatistico.gerarRelatorioReservasPorEspaco();
                    RelatorioEstatistico.gerarRelatorioReservasPorUsuario();
                    RelatorioEstatistico.mostrarEspacoMaisReservado();
                    break;
                case 0:
                    System.out.println("Fazendo logout...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }
    
    private static void menuUsuario(Usuario usuario) {
        int opcao;
        do {
            System.out.println("\n" + "=".repeat(20));
            System.out.println("    MENU USUÁRIO");
            System.out.println("=".repeat(20));
            System.out.println("1. Ver espaços disponíveis");
            System.out.println("2. Fazer nova reserva");
            System.out.println("3. Ver minhas reservas");
            System.out.println("0. Sair (Logout)");
            System.out.print("Opção: ");

            try {
                opcao = sc.nextInt();
                sc.nextLine(); // limpar buffer
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
                sc.nextLine();
                opcao = -1;
                continue;
            }

            switch (opcao) {
                case 1:
                    System.out.println("\n--- Espaços Disponíveis ---");
                    for (Espaco e : EspacoDAO.listarEspacos()) {
                        System.out.println(e);
                    }
                    break;
                case 2:
                    fazerReserva(usuario);
                    break;
                case 3:
                    System.out.println("\n--- Suas Reservas ---");
                    List<Reserva> minhasReservas = ReservaDAO.listarReservas();
                    boolean encontrou = false;
                    for (Reserva r : minhasReservas) {
                        if (r.getIdUsuario() == usuario.getId()) {
                            System.out.println(r);
                            encontrou = true;
                        }
                    }
                    if (!encontrou) {
                        System.out.println("Você ainda não possui reservas.");
                    }
                    break;
                case 0:
                    System.out.println("Fazendo logout...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private static void fazerReserva(Usuario usuario) {
    System.out.println("\n--- Fazer Reserva ---");
    
    // MELHORIA: Listando os espaços antes de pedir o ID
    System.out.println("Espaços disponíveis:");
    List<Espaco> espacos = EspacoDAO.listarEspacos();
    if (espacos.isEmpty()) {
        System.out.println("Nenhum espaço cadastrado no momento.");
        return;
    }
    for (Espaco e : espacos) {
        System.out.println(e);
    }

    System.out.print("\nDigite o ID do espaço que deseja reservar: ");
    int idEspaco = sc.nextInt();
    sc.nextLine(); // Limpar buffer

    // SUA SOLICITAÇÃO: Mudança no formato da data
    System.out.print("Data (dd/mm/yyyy): ");
    String data = sc.nextLine();
    
    System.out.print("Hora início (HH:MM): ");
    String horaInicio = sc.nextLine();
    System.out.print("Hora fim (HH:MM): ");
    String horaFim = sc.nextLine();

    boolean conflito = false;
    for (Reserva r : ReservaDAO.listarReservas()) {
        if (r.getIdEspaco() == idEspaco && r.getData().equals(data)) {
            if (horariosConflitam(horaInicio, horaFim, r.getHoraInicio(), r.getHoraFim())) {
                conflito = true;
                break;
            }
        }
    }

    if (conflito) {
        System.out.println("Já existe uma reserva para este espaço neste horário. Tente outro horário.");
    } else {
        Reserva nova = new Reserva(0, usuario.getId(), idEspaco, data, horaInicio, horaFim);
        ReservaDAO.adicionarReserva(nova);
        LogDAO.registrar(usuario.getId(), "reservou espaço ID " + idEspaco);
        System.out.println("Reserva realizada com sucesso!");
    }
}
    
    private static void atualizarUsuario() {
        System.out.println("\n--- Atualizar Usuário ---");
        System.out.println("Usuários existentes:");
        for (Usuario u : UsuarioDAO.listarUsuarios()) {
            System.out.println(u);
        }

        System.out.print("\nDigite o ID do usuário que deseja atualizar: ");
        int id = sc.nextInt();
        sc.nextLine(); // Limpar buffer

        Usuario usuarioParaAtualizar = UsuarioDAO.buscarPorId(id);

        if (usuarioParaAtualizar == null) {
            System.out.println("Usuário com ID " + id + " não encontrado.");
            return;
        }

        System.out.println("Editando usuário: " + usuarioParaAtualizar.getNome());
        System.out.println("Deixe em branco e pressione Enter para manter o valor atual.");

        System.out.print("Novo nome (" + usuarioParaAtualizar.getNome() + "): ");
        String novoNome = sc.nextLine();
        if (!novoNome.trim().isEmpty()) {
            usuarioParaAtualizar.setNome(novoNome);
        }

        System.out.print("Novo email (" + usuarioParaAtualizar.getEmail() + "): ");
        String novoEmail = sc.nextLine();
        if (!novoEmail.trim().isEmpty()) {
            usuarioParaAtualizar.setEmail(novoEmail);
        }

        System.out.print("Nova senha (deixe em branco para não alterar): ");
        String novaSenha = sc.nextLine();
        if (!novaSenha.trim().isEmpty()) {
            usuarioParaAtualizar.setSenha(novaSenha);
        }

        System.out.print("Novo tipo (admin/usuario) (" + usuarioParaAtualizar.getTipo() + "): ");
        String novoTipo = sc.nextLine();
        if (!novoTipo.trim().isEmpty()) {
            usuarioParaAtualizar.setTipo(novoTipo);
        }

        UsuarioDAO.atualizarUsuario(usuarioParaAtualizar);
    }

    private static void deletarUsuario() {
        System.out.println("\n--- Excluir Usuário ---");
        System.out.println("Usuários existentes:");
        for (Usuario u : UsuarioDAO.listarUsuarios()) {
            System.out.println(u);
        }

        System.out.print("\nDigite o ID do usuário que deseja EXCLUIR: ");
        int id = sc.nextInt();
        sc.nextLine(); // Limpar buffer

        Usuario usuarioParaDeletar = UsuarioDAO.buscarPorId(id);

        if (usuarioParaDeletar == null) {
            System.out.println("Usuário com ID " + id + " não encontrado.");
            return;
        }
        
        System.out.print("Tem certeza que deseja excluir o usuário '" +
                           usuarioParaDeletar.getNome() + "'? (S/N): ");
        String confirmacao = sc.nextLine();

        if (confirmacao.equalsIgnoreCase("S")) {
            UsuarioDAO.deletarUsuario(id);
        } else {
            System.out.println("Exclusão cancelada.");
        }
    }

    private static void atualizarEspaco() {
        System.out.println("\n--- Atualizar Espaço ---");
        System.out.println("Espaços existentes:");
        for (Espaco e : EspacoDAO.listarEspacos()) {
            System.out.println(e);
        }

        System.out.print("\nDigite o ID do espaço que deseja atualizar: ");
        int id = sc.nextInt();
        sc.nextLine(); // Limpar buffer

        Espaco espacoParaAtualizar = EspacoDAO.buscarPorId(id);

        if (espacoParaAtualizar == null) {
            System.out.println("Espaço com ID " + id + " não encontrado.");
            return;
        }

        System.out.println("Editando espaço: " + espacoParaAtualizar.getNome());
        System.out.println("Deixe em branco e pressione Enter para manter o valor atual.");

        System.out.print("Novo nome (" + espacoParaAtualizar.getNome() + "): ");
        String novoNome = sc.nextLine();
        if (!novoNome.trim().isEmpty()) {
            espacoParaAtualizar.setNome(novoNome);
        }

        System.out.print("Novo tipo (" + espacoParaAtualizar.getTipo() + "): ");
        String novoTipo = sc.nextLine();
        if (!novoTipo.trim().isEmpty()) {
            espacoParaAtualizar.setTipo(novoTipo);
        }
        
        System.out.print("Nova capacidade (" + espacoParaAtualizar.getCapacidade() + "): ");
        String novaCapacidadeStr = sc.nextLine();
        if (!novaCapacidadeStr.trim().isEmpty()) {
            try {
                int novaCapacidade = Integer.parseInt(novaCapacidadeStr);
                espacoParaAtualizar.setCapacidade(novaCapacidade);
            } catch (NumberFormatException ex) {
                System.out.println("Entrada inválida para capacidade. Mantendo o valor original.");
            }
        }

        System.out.print("Nova descrição (" + espacoParaAtualizar.getDescricao() + "): ");
        String novaDescricao = sc.nextLine();
        if (!novaDescricao.trim().isEmpty()) {
             espacoParaAtualizar.setDescricao(novaDescricao);
        }

        EspacoDAO.atualizarEspaco(espacoParaAtualizar);
    }
    
    private static void deletarEspaco() {
        System.out.println("\n--- Excluir Espaço ---");
        System.out.println("Espaços existentes:");
        for (Espaco e : EspacoDAO.listarEspacos()) {
            System.out.println(e);
        }

        System.out.print("\nDigite o ID do espaço que deseja EXCLUIR: ");
        int id = sc.nextInt();
        sc.nextLine(); // Limpar buffer

        Espaco espacoParaDeletar = EspacoDAO.buscarPorId(id);

        if (espacoParaDeletar == null) {
            System.out.println("Espaço com ID " + id + " não encontrado.");
            return;
        }
        
        System.out.print("Tem certeza que deseja excluir o espaço '" +
                           espacoParaDeletar.getNome() + "'? (S/N): ");
        String confirmacao = sc.nextLine();

        if (confirmacao.equalsIgnoreCase("S")) {
            EspacoDAO.deletarEspaco(id);
        } else {
            System.out.println("Exclusão cancelada.");
        }
    }
    
    private static void cadastrarNovoEspaco() {
        System.out.println("\n--- Cadastro de Novo Espaço ---");
        System.out.print("Nome do espaço: ");
        String nome = sc.nextLine();
        
        System.out.print("Tipo do espaço (Ex: Sala de Reunião, Laboratório, Auditório): ");
        String tipo = sc.nextLine();
        
        System.out.print("Capacidade de pessoas: ");
        int capacidade = sc.nextInt();
        sc.nextLine(); // Limpar buffer
        
        System.out.print("Descrição: ");
        String descricao = sc.nextLine();

        Espaco novoEspaco;

        // Verifica se o tipo é especializado para pedir informações extras
        if ("Laboratório".equalsIgnoreCase(tipo)) {
            System.out.print("Lista de equipamentos (separados por vírgula): ");
            String equipamentos = sc.nextLine();
            novoEspaco = new Laboratorio(0, nome, tipo, capacidade, descricao, equipamentos);
        } else if ("Auditório".equalsIgnoreCase(tipo)) {
            System.out.print("Possui projetor? (S/N): ");
            String temProjetor = sc.nextLine();
            boolean possuiProjetor = temProjetor.equalsIgnoreCase("S");
            novoEspaco = new Auditorio(0, nome, tipo, capacidade, descricao, possuiProjetor);
        } else {
            // Para qualquer outro tipo, cria um EspacoGeral
            novoEspaco = new EspacoGeral(0, nome, tipo, capacidade, descricao);
        }

        // Chama o método do DAO que já está pronto para salvar no banco
        EspacoDAO.adicionarEspaco(novoEspaco);
    }

    private static boolean horariosConflitam(String inicio1, String fim1, String inicio2, String fim2) {
        return inicio1.compareTo(fim2) < 0 && fim1.compareTo(inicio2) > 0;
    }
}
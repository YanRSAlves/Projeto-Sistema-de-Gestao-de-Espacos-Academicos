import java.util.*;

public class Main {
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Usuario usuario = fazerLogin();

        if (usuario == null) {
            System.out.println("Falha no login. Encerrando...");
            return;
        }

        System.out.println("Login realizado! Bem-vindo(a), " + usuario.getNome() + " (" + usuario.getTipo() + ")");
        LogDAO.registrar(usuario.getId(), "fez login");

        if (usuario.getTipo().equalsIgnoreCase("admin")) {
            menuAdmin();
        } else {
            menuUsuario(usuario);
        }
    }

    private static Usuario fazerLogin() {
        System.out.println("=== LOGIN ===");
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Senha: ");
        String senha = sc.nextLine();

        return UsuarioDAO.buscarPorEmailESenha(email, senha);
    }

    private static void menuAdmin() {
        int opcao;
        do {
            System.out.println("\n=== MENU ADMIN ===");
            System.out.println("1. Listar usuários");
            System.out.println("2. Listar espaços");
            System.out.println("3. Listar reservas");
            System.out.println("4. Exportar reservas para CSV");
            System.out.println("5. Relatórios estatísticos");
            System.out.println("0. Sair");
            System.out.print("Opção: ");
            opcao = sc.nextInt();
            sc.nextLine(); // limpar buffer

            switch (opcao) {
                case 1:
                    System.out.println("\n--- Usuários ---");
                    for (Usuario u : UsuarioDAO.listarUsuarios()) {
                        System.out.println(u);
                    }
                    break;
                case 2:
                    System.out.println("\n--- Espaços ---");
                    for (Espaco e : EspacoDAO.listarEspacos()) {
                        System.out.println(e);
                    }
                    break;
                case 3:
                    System.out.println("\n--- Reservas ---");
                    for (Reserva r : ReservaDAO.listarReservas()) {
                        System.out.println(r);
                    }
                    break;
                case 4:
                    ExportadorCSV.exportarReservasCSV();
                    break;
                case 5:
                    System.out.println("\n=== Relatórios ===");
                    RelatorioEstatistico.gerarRelatorioReservasPorEspaco();
                    RelatorioEstatistico.gerarRelatorioReservasPorUsuario();
                    RelatorioEstatistico.mostrarEspacoMaisReservado();
                    break;
                case 0:
                    System.out.println("Encerrando...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private static void menuUsuario(Usuario usuario) {
        int opcao;
        do {
            System.out.println("\n=== MENU USUÁRIO ===");
            System.out.println("1. Ver espaços disponíveis");
            System.out.println("2. Fazer nova reserva");
            System.out.println("3. Ver minhas reservas");
            System.out.println("0. Sair");
            System.out.print("Opção: ");
            opcao = sc.nextInt();
            sc.nextLine(); // limpar buffer

            switch (opcao) {
                case 1:
                    System.out.println("\n--- Espaços ---");
                    for (Espaco e : EspacoDAO.listarEspacos()) {
                        System.out.println(e);
                    }
                    break;
                case 2:
                    fazerReserva(usuario);
                    break;
                case 3:
                    System.out.println("\n--- Suas Reservas ---");
                    for (Reserva r : ReservaDAO.listarReservas()) {
                        if (r.getIdUsuario() == usuario.getId()) {
                            System.out.println(r);
                        }
                    }
                    break;
                case 0:
                    System.out.println("Encerrando...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private static void fazerReserva(Usuario usuario) {
        System.out.println("\n--- Fazer Reserva ---");
        System.out.print("ID do espaço: ");
        int idEspaco = sc.nextInt();
        sc.nextLine();
        System.out.print("Data (yyyy-mm-dd): ");
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
            System.out.println(" Já existe uma reserva para esse espaço nesse horário. Tente outro horário.");
        } else {
            int id = ReservaDAO.gerarNovoId();
            Reserva nova = new Reserva(id, usuario.getId(), idEspaco, data, horaInicio, horaFim);
            ReservaDAO.adicionarReserva(nova);
            LogDAO.registrar(usuario.getId(), "reservou espaço ID " + idEspaco);
            ComprovanteDAO.gerar(nova);
            System.out.println(" Reserva realizada com sucesso! Comprovante gerado.");
        }
    }

    private static boolean horariosConflitam(String inicio1, String fim1, String inicio2, String fim2) {
        return inicio1.compareTo(fim2) < 0 && fim1.compareTo(inicio2) > 0;
    }
}

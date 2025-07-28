import java.util.*;

public class RelatorioEstatistico {

    public static void gerarRelatorioReservasPorEspaco() {
        Map<Integer, Integer> contador = new HashMap<>();

        for (Reserva r : ReservaDAO.listarReservas()) {
            contador.put(r.getIdEspaco(), contador.getOrDefault(r.getIdEspaco(), 0) + 1);
        }

        System.out.println("\nTotal de reservas por espaço:");
        for (Espaco e : EspacoDAO.listarEspacos()) {
            int total = contador.getOrDefault(e.getId(), 0);
            System.out.println(e.getNome() + " → " + total + " reserva(s)");
        }
    }

    public static void gerarRelatorioReservasPorUsuario() {
        Map<Integer, Integer> contador = new HashMap<>();

        for (Reserva r : ReservaDAO.listarReservas()) {
            contador.put(r.getIdUsuario(), contador.getOrDefault(r.getIdUsuario(), 0) + 1);
        }

        System.out.println("\n Total de reservas por usuário:");
        for (Usuario u : UsuarioDAO.listarUsuarios()) {
            int total = contador.getOrDefault(u.getId(), 0);
            System.out.println(u.getNome() + " → " + total + " reserva(s)");
        }
    }

    public static void mostrarEspacoMaisReservado() {
        Map<Integer, Integer> contador = new HashMap<>();

        for (Reserva r : ReservaDAO.listarReservas()) {
            contador.put(r.getIdEspaco(), contador.getOrDefault(r.getIdEspaco(), 0) + 1);
        }

        int idMaisReservado = -1;
        int max = 0;

        for (Map.Entry<Integer, Integer> entry : contador.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                idMaisReservado = entry.getKey();
            }
        }

        System.out.println("\n Espaço mais reservado:");
        for (Espaco e : EspacoDAO.listarEspacos()) {
            if (e.getId() == idMaisReservado) {
                System.out.println(e.getNome() + " → " + max + " reserva(s)");
                return;
            }
        }

        System.out.println("Nenhuma reserva encontrada.");
    }
}

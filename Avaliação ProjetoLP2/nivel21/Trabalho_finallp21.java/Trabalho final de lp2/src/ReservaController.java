
// Controla operacoes de reserva, incluindo salvar, listar, buscar e verificar disponibilidade de horarios.

import java.util.ArrayList;
import java.util.List;

public class ReservaController {

    public static void salvar(Reserva r) {
        ArquivoUtils.salvarLinha(Constantes.ARQ_RESERVAS, r.toArquivo());
    }

    public static List<Reserva> listarTodos() {
        List<Reserva> reservas = new ArrayList<>();
        List<String> linhas = ArquivoUtils.lerLinhas(Constantes.ARQ_RESERVAS);
        for (String linha : linhas) {
            Reserva r = Reserva.fromArquivo(linha);
            if (r != null) {
                reservas.add(r);
            }
        }
        return reservas;
    }

    public static Reserva buscarPorId(String idReserva) {
        for (Reserva r : listarTodos()) {
            if (r.getIdReserva().equals(idReserva)) {
                return r;
            }
        }
        return null;
    }

    public static boolean estaDisponivel(String idEspaco, String data, String horaInicio, String horaFim) {
        for (Reserva r : listarTodos()) {
            if (r.getIdEspaco().equals(idEspaco) && r.getData().equals(data)) {
                // Verifica se os horários se sobrepõem
                if (!(horaFim.compareTo(r.getHoraInicio()) <= 0 || horaInicio.compareTo(r.getHoraFim()) >= 0)) {
                    return false;
                }
            }
        }
        return true;
    }
}

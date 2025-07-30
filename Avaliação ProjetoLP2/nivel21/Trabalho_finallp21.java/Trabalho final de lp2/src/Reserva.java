
// Representa uma reserva de espaco feita por um usuario, com dados de horario e identificacao.

public class Reserva {
    private String idReserva;
    private String emailUsuario;
    private String idEspaco;
    private String data;
    private String horaInicio;
    private String horaFim;

    public Reserva(String idReserva, String emailUsuario, String idEspaco, String data, String horaInicio, String horaFim) {
        this.idReserva = idReserva;
        this.emailUsuario = emailUsuario;
        this.idEspaco = idEspaco;
        this.data = data;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
    }

    public String getIdReserva() {
        return idReserva;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public String getIdEspaco() {
        return idEspaco;
    }

    public String getData() {
        return data;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public String getHoraFim() {
        return horaFim;
    }

    public String toArquivo() {
        return idReserva + ";" + emailUsuario + ";" + idEspaco + ";" + data + ";" + horaInicio + ";" + horaFim;
    }

    public static Reserva fromArquivo(String linha) {
        String[] partes = linha.split(";");
        if (partes.length < 6) {
            return null; // linha inválida
        }
        return new Reserva(partes[0], partes[1], partes[2], partes[3], partes[4], partes[5]);
    }
}

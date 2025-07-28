public class Reserva {
    private int id;
    private int idUsuario;
    private int idEspaco;
    private String data;       // formato: yyyy-mm-dd
    private String horaInicio; // formato: HH:MM
    private String horaFim;    // formato: HH:MM

    public Reserva(int id, int idUsuario, int idEspaco, String data, String horaInicio, String horaFim) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idEspaco = idEspaco;
        this.data = data;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
    }

    public int getId() { return id; }
    public int getIdUsuario() { return idUsuario; }
    public int getIdEspaco() { return idEspaco; }
    public String getData() { return data; }
    public String getHoraInicio() { return horaInicio; }
    public String getHoraFim() { return horaFim; }

    @Override
    public String toString() {
        return "Reserva " + id + " - Espaço " + idEspaco + " por Usuário " + idUsuario + " em " + data + " das " + horaInicio + " às " + horaFim;
    }
}

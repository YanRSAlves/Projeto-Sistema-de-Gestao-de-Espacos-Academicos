
// Representa uma acao de log com descricao, usuario responsavel e data/hora do evento.

public class LogAcao {
    private String descricao;
    private String usuario;
    private String dataHora;

    public LogAcao(String descricao, String usuario, String dataHora) {
        this.descricao = descricao;
        this.usuario = usuario;
        this.dataHora = dataHora;
    }

    public String toArquivo() {
        return dataHora + ";" + usuario + ";" + descricao;
    }
}

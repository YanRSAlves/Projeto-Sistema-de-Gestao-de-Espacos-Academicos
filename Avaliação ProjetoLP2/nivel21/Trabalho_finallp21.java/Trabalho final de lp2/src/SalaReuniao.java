
// Representa uma sala de reuniao com recursos especificos disponíveis (projetor, quadro branco).

public class SalaReuniao extends Espaco {
    private String recursos;

    public SalaReuniao(String id, String nome, int capacidade, String localizacao, String recursos) {
        super(id, nome, capacidade, localizacao);
        this.recursos = recursos;
    }

    @Override
    public String getTipo() {
        return "SalaReuniao";
    }

    @Override
    public String dadosEspecificos() {
        return recursos;
    }
}

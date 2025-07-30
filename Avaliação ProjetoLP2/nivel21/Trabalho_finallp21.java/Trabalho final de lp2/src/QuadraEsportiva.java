
// Representa uma Quadra Esportiva.

public class QuadraEsportiva extends Espaco {
    private String tipoEsporte;

    public QuadraEsportiva(String id, String nome, int capacidade, String localizacao, String tipoEsporte) {
        super(id, nome, capacidade, localizacao);
        this.tipoEsporte = tipoEsporte;
    }

    @Override
    public String getTipo() {
        return "QuadraEsportiva";
    }

    @Override
    public String dadosEspecificos() {
        return tipoEsporte;
    }
}

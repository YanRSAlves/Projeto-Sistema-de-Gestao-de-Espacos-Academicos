
// Representa uma sala de aula com capacidade, localizacao e informacao se possui projetor.

public class SalaAula extends Espaco {
    private boolean temProjetor;

    public SalaAula(String id, String nome, int capacidade, String localizacao, boolean temProjetor) {
        super(id, nome, capacidade, localizacao);
        this.temProjetor = temProjetor;
    }

    @Override
    public String getTipo() {
        return "SalaAula";
    }

    @Override
    public String dadosEspecificos() {
        return String.valueOf(temProjetor);
    }
}

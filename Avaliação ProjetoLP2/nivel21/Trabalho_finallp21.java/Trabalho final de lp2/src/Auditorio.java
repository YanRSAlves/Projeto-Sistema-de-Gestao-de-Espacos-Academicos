
// Classe que representa um espaco do tipo Auditorio, com atributo especifico para indicar se possui sistema de som.

public class Auditorio extends Espaco {
    private boolean temSom;

    public Auditorio(String id, String nome, int capacidade, String localizacao, boolean temSom) {
        super(id, nome, capacidade, localizacao);
        this.temSom = temSom;
    }

    @Override
    public String getTipo() {
        return "Auditorio";
    }

    @Override
    public String dadosEspecificos() {
        return String.valueOf(temSom);
    }
}

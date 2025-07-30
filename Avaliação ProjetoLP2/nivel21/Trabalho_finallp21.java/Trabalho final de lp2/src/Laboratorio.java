
// Representa um espaco do tipo Laboratorio, com uma lista de equipamentos especificos.

public class Laboratorio extends Espaco {
    private String equipamentos;

    public Laboratorio(String id, String nome, int capacidade, String localizacao, String equipamentos) {
        super(id, nome, capacidade, localizacao);
        this.equipamentos = equipamentos;
    }

    @Override
    public String getTipo() {
        return "Laboratorio";
    }

    @Override
    public String dadosEspecificos() {
        return equipamentos;
    }
}

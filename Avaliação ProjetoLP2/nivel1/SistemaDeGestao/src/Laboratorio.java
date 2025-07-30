public class Laboratorio extends Espaco {
    private String listaEquipamentos;

    public Laboratorio(int id, String nome, String tipo, int capacidade, String descricao, String listaEquipamentos) {
        super(id, nome, tipo, capacidade, descricao);
        this.listaEquipamentos = listaEquipamentos;
    }

    public String getListaEquipamentos() {
        return listaEquipamentos;
    }

    public void setListaEquipamentos(String listaEquipamentos) {
        this.listaEquipamentos = listaEquipamentos;
    }

    @Override
    public String toString() {
        return super.toString() + " | Equipamentos: " + (listaEquipamentos != null ? listaEquipamentos : "N/A");
    }
}

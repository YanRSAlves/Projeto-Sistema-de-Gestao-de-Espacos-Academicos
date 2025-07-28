public class Auditorio extends Espaco {
    private boolean possuiProjetor;

    public Auditorio(int id, String nome, String tipo, int capacidade, String descricao, boolean possuiProjetor) {
        super(id, nome, tipo, capacidade, descricao);
        this.possuiProjetor = possuiProjetor;
    }

    public boolean isPossuiProjetor() {
        return possuiProjetor;
    }

    public void setPossuiProjetor(boolean possuiProjetor) {
        this.possuiProjetor = possuiProjetor;
    }

    @Override
    public String toString() {
        return super.toString() + " | Projetor: " + (possuiProjetor ? "Sim" : "Não");
    }
}
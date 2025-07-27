public class Espaco {
    private int id;
    private String nome;
    private String tipo; 
    private int capacidade;
    private String descricao;

    public Espaco(int id, String nome, String tipo, int capacidade, String descricao) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.capacidade = capacidade;
        this.descricao = descricao;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getTipo() { return tipo; }
    public int getCapacidade() { return capacidade; }
    public String getDescricao() { return descricao; }

    @Override
    public String toString() {
        return id + " - " + nome + " [" + tipo + "] (" + capacidade + " pessoas)";
    }
}

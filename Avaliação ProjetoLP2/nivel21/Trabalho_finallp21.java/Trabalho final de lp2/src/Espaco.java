
// Classe abstrata que representa um espaco generico com propriedades basicas e metodos
// para serializacao/deserialização de dados.

public abstract class Espaco {
    protected String id;
    protected String nome;
    protected int capacidade;
    protected String localizacao;

    public Espaco(String id, String nome, int capacidade, String localizacao) {
        this.id = id;
        this.nome = nome;
        this.capacidade = capacidade;
        this.localizacao = localizacao;
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public abstract String getTipo();
    public abstract String dadosEspecificos();

    public String toArquivo() {
        return getTipo() + ";" + id + ";" + nome + ";" + capacidade + ";" + localizacao + ";" + dadosEspecificos();
    }

    public static Espaco fromArquivo(String linha) {
        String[] partes = linha.split(";");
        String tipo = partes[0];
        switch (tipo) {
            case "SalaAula": return new SalaAula(partes[1], partes[2], Integer.parseInt(partes[3]), partes[4], Boolean.parseBoolean(partes[5]));
            case "Laboratorio": return new Laboratorio(partes[1], partes[2], Integer.parseInt(partes[3]), partes[4], partes[5]);
            case "SalaReuniao": return new SalaReuniao(partes[1], partes[2], Integer.parseInt(partes[3]), partes[4], partes[5]);
            case "QuadraEsportiva": return new QuadraEsportiva(partes[1], partes[2], Integer.parseInt(partes[3]), partes[4], partes[5]);
            case "Auditorio": return new Auditorio(partes[1], partes[2], Integer.parseInt(partes[3]), partes[4], Boolean.parseBoolean(partes[5]));
            default: return null;
        }
    }
}

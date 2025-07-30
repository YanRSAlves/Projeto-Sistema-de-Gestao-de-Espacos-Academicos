public class Usuario {
    private String id;       // Identificador unico do usuario
    private String nome;     // Nome completo
    private String email;    // Email (login)
    private String senha;    // Senha do usuario
    private String tipo;     // Tipo (ex: "admin", "usuario")

    // Construtor
    public Usuario(String id, String nome, String email, String senha, String tipo) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.tipo = tipo;
    }

    // Getters
    public String getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getSenha() { return senha; }
    public String getTipo() { return tipo; }

    // Converte o objeto para linha de arquivo (serialização simples)
    public String toArquivo() {
        return id + ";" + nome + ";" + email + ";" + senha + ";" + tipo;
    }

    // Cria objeto Usuario a partir de linha do arquivo (desserialização)
    public static Usuario fromArquivo(String linha) {
        String[] partes = linha.split(";");
        return new Usuario(partes[0], partes[1], partes[2], partes[3], partes[4]);
    }
}

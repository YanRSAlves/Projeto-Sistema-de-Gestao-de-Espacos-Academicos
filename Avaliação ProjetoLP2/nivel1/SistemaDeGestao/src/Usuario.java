public class Usuario {
    private int id;
    private String nome;
    private String email;
    private String senha;
    private String tipo; 

    public Usuario(int id, String nome, String email, String senha, String tipo) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.tipo = tipo;
    }

    
    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getSenha() { return senha; }
    public String getTipo() { return tipo; }

    
    public void setNome(String nome) { this.nome = nome; }
    public void setEmail(String email) { this.email = email; }
    public void setSenha(String senha) { this.senha = senha; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    @Override
    public String toString() {
        return id + " - " + nome + " (" + tipo + ")";
    }
}

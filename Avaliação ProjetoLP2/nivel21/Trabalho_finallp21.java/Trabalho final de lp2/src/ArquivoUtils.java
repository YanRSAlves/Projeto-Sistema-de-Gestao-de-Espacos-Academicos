
// Utilitario para ler, escrever e criar arquivos de texto simples.

import java.io.*;
import java.util.*;

public class ArquivoUtils {

    public static void salvarLinha(String caminho, String conteudo) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminho, true))) {
            bw.write(conteudo);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Erro ao salvar: " + e.getMessage());
        }
    }

    public static List<String> lerLinhas(String caminho) {
        List<String> linhas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linhas.add(linha);
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler: " + e.getMessage());
        }
        return linhas;
    }

    public static void salvarTodos(String caminho, List<String> conteudos) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminho))) {
            for (String linha : conteudos) {
                bw.write(linha);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao sobrescrever: " + e.getMessage());
        }
    }

    public static void criarArquivoSeNaoExistir(String caminho) {
        try {
            File f = new File(caminho);
            if (!f.exists()) {
                boolean criado = f.createNewFile();
                if (criado) {
                    System.out.println("Arquivo criado: " + caminho);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao criar arquivo: " + e.getMessage());
        }
    }
}

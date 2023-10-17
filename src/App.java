import java.util.HashMap;
import eleicao.Partido;
import relatorio.Relatorio;

public class App {
    
    public static void main(String[] args) throws Exception {
        Entrada entrada = new Entrada();
        HashMap<Integer, Partido> partidos = new HashMap<>();
        partidos = entrada.readCandidatos("C:\\Users\\renan\\OneDrive\\Área de Trabalho\\trab-1-POO\\src\\consulta_cand_2022_ES.csv", "estadual");
        entrada.readVotacao("C:\\Users\\renan\\OneDrive\\Área de Trabalho\\trab-1-POO\\src\\votacao_secao_2022_ES.csv", "estadual", partidos);

        Relatorio relatorio = new Relatorio();

        System.out.println("Número de vagas: " + relatorio.numeroEleitos(partidos) + "\n");

        System.out.println("Deputados est/fedr eleitos:");
        relatorio.candidatosELeitos(partidos);

        System.out.println("\nCandidatos mais votados (em ordem decrescente de votação e respeitando número de vagas):");
        relatorio.candidatosMaisVotados(partidos);
    }
}

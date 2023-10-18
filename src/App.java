import java.util.HashMap;
import eleicao.Partido;
import relatorio.Relatorio;
import java.text.SimpleDateFormat;

public class App {
    
    public static void main(String[] args) throws Exception {
        Entrada entrada = new Entrada();
        HashMap<Integer, Partido> partidos = new HashMap<>();
        partidos = entrada.readCandidatos("C:\\Users\\renan\\OneDrive\\Área de Trabalho\\trab-1-POO\\src\\consulta_cand_2022_ES.csv", "estadual");
        entrada.readVotacao("C:\\Users\\renan\\OneDrive\\Área de Trabalho\\trab-1-POO\\src\\votacao_secao_2022_ES.csv", "estadual", partidos);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        Relatorio relatorio = new Relatorio(dateFormat.parse("02/10/2022"));

        System.out.println("Número de vagas: " + relatorio.numeroEleitos(partidos) + "\n");

        System.out.println("Deputados est/fedr eleitos:");
        relatorio.candidatosELeitos(partidos);

        System.out.println("\nCandidatos mais votados (em ordem decrescente de votação e respeitando número de vagas):");
        relatorio.candidatosMaisVotados(partidos);

        System.out.println("\nTeriam sido eleitos se a votação fosse majoritária, e não foram eleitos:");
        System.out.println("(com sua posição no ranking de mais votados)");
        relatorio.teriamSidoEleitos(partidos);

        System.out.println("\nEleitos, que se beneficiaram do sistema proporcional:");
        System.out.println("(com sua posição no ranking de mais votados)");
        relatorio.eleitosBeneficiadosSistemaProporcional(partidos);

        System.out.println("\nVotação dos partidos e número de candidatos eleitos:");
        relatorio.votosTotalizadosPorPartido(partidos);

        System.out.println("\nPrimeiro e último colocados de cada partido: ");
        relatorio.primeiroUltimoColocadosPartido(partidos);

        System.out.println("\nEleitos, por faixa etária (na data da eleição):");
        relatorio.eleitosPorFaixaEtaria(partidos);

        System.out.println("\nEleitos por gênero:");
        relatorio.eleitosPorGenero(partidos);

        relatorio.totalVotosValidos(partidos);
    }
}

import java.util.HashMap;
import eleicao.Partido;

public class App {
    
    public static void main(String[] args) throws Exception {
        Entrada entrada = new Entrada();
        HashMap<Integer, Partido> partidos = new HashMap<>();
        partidos = entrada.readCandidatos("C:\\Users\\renan\\OneDrive\\Área de Trabalho\\trab-1-POO\\src\\consulta_cand_2022_ES.csv", "federal");
        entrada.readVotacao("C:\\Users\\renan\\OneDrive\\Área de Trabalho\\trab-1-POO\\src\\votacao_secao_2022_ES.csv", "estadual", partidos);

      for (Partido p : partidos.values()) {
          System.out.println(p.toString());
      }
    }
}

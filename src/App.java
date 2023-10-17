import java.util.HashMap;
import eleicao.Partido;

public class App {
    
    public static void main(String[] args) throws Exception {
        Entrada entrada = new Entrada();
        HashMap<Integer, Partido> partidos = new HashMap<>();
        partidos = entrada.readCandidatos(args[0], args[1]);

        for (int i = 0; i < 1; i++) {
            System.out.println(partidos.get(i));
        }
    }
}

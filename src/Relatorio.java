import java.util.Date;
import java.util.HashMap;
import eleicao.Partido;

public class Relatorio {
    private Date dataEleicao;

    public Relatorio(Date dataEleicao) {
        this.dataEleicao = dataEleicao;
    }
    
    public int numeroEleitos(HashMap<Integer, Partido> partidos) {
        int eleitos = 0;
        for (Partido partido : partidos.values()) {
            eleitos += partido.getEleitos();
        }
        return eleitos;
    }

}

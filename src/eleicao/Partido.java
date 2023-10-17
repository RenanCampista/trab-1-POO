package eleicao;
import java.util.HashMap;

public class Partido {
    HashMap<Integer, Candidato> candidatos = new HashMap<>();
    int numPartido;


    String siglaPartido;
    int qtdVotos;

    public Partido(int numPartido, String siglaPartido) {
        this.numPartido = numPartido;
        this.siglaPartido = siglaPartido;
        this.qtdVotos = 0;
    }

    public void addCandidato(Candidato candidato) {
        candidatos.put(candidato.getNumCandidato(), candidato);
    }

    public boolean contemCandidato(int numCandidato) {
        return candidatos.containsKey(numCandidato);
    }

    public void adicionarVoto(int numVotavel, int qtdVotos) {
        if (numVotavel != numPartido) {
            candidatos.get(numVotavel).adicionarVoto(qtdVotos);
        }
        qtdVotos += qtdVotos;
    }

    public int getNumPartido() {
        return numPartido;
    }
}


package eleicao;
import java.util.HashMap;

public class Partido {
    HashMap<Integer, Candidato> candidatos = new HashMap<>();
    int numPartido;
    String siglaPartido;
    int qtdVotosLegenda;

    public Partido(int numPartido, String siglaPartido) {
        this.numPartido = numPartido;
        this.siglaPartido = siglaPartido;
        this.qtdVotosLegenda = 0;
    }

    public void addCandidato(Candidato candidato) {
        candidatos.put(candidato.getNumCandidato(), candidato);
    }

    public boolean contemCandidato(int numCandidato) {
        return candidatos.containsKey(numCandidato);
    }

    public void adicionarVoto(int numVotavel, int qtdVotos) {
        if (candidatos.get(numVotavel).temFederacao()) {
            candidatos.get(numVotavel).adicionarVoto(qtdVotos);
            this.qtdVotosLegenda += qtdVotos;
        } else {
            candidatos.get(numVotavel).adicionarVoto(qtdVotos);
        }
    }

    public void adicionarVotoLegenda(int qtdVotos) {
        this.qtdVotosLegenda += qtdVotos;
    }

    public int getNumPartido() {
        return numPartido;
    }
    

    @Override
    public String toString() {
        // for (Candidato c : candidatos.values()) {
        //     out += c.toString() + "\n";
        // }
        return "Partido [numPartido=" + numPartido + ", siglaPartido=" + siglaPartido + ", qtdVotosLegenda=" + qtdVotosLegenda + "]\n";
    }
}


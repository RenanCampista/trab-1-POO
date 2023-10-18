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

    public boolean temCandidato() {
        return !candidatos.isEmpty();
    }

    public String getSiglaPartido() {
        return siglaPartido;
    }

    public void adicionarVotoCandidato(int numVotavel, int qtdVotos) {
        if (candidatos.get(numVotavel).getTipoDestinacaoVotos().equals("Válido (legenda)"))
            this.qtdVotosLegenda += qtdVotos;
        else
            candidatos.get(numVotavel).adicionarVoto(qtdVotos);
    }

    public void adicionarVotoLegenda(int qtdVotos) {
        this.qtdVotosLegenda += qtdVotos;
    }

    public int getNumPartido() {
        return numPartido;
    }
    
    public int getEleitos() {
        int eleitos = 0;
        for (Candidato c : candidatos.values()) {
            if (c.eleito()) eleitos++;
        }
        return eleitos;
    }

    public int getVotosNominais() {
        int votos = 0;
        for (Candidato c : candidatos.values()) {
            if (c.votosLegenda())
                votos += c.getQtdVotosNominal();
        }
        return votos;
    }

    public int getVotosLegenda() {
        return this.qtdVotosLegenda;
    }

    public int getTotalVotos() {
        return this.qtdVotosLegenda + this.getVotosNominais();
    }

    public HashMap<Integer, Candidato> getCandidatos() {
        return new HashMap<Integer, Candidato>(candidatos);
    }

    public Candidato getCandidatoMaisVotado() {
        Candidato maisVotado = null;
        for (Candidato c : candidatos.values()) {
            if (maisVotado == null || c.getQtdVotosNominal() > maisVotado.getQtdVotosNominal())
                maisVotado = c;
            else if (c.getQtdVotosNominal() == maisVotado.getQtdVotosNominal() && maisVotado.getDataNascimento().compareTo(c.getDataNascimento()) > 0)
                maisVotado = c;
        }
        return maisVotado;
    }

    public Candidato getCandidatoMenosVotado() {
        Candidato menosVotado = null;
        for (Candidato c : candidatos.values()) {
            if (menosVotado == null || c.getQtdVotosNominal() < menosVotado.getQtdVotosNominal())
                menosVotado = c;
        }
        return menosVotado;
    }

    @Override
    public String toString() {
        return this.siglaPartido + " - " + this.numPartido;
    }
}


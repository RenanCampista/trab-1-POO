package eleicao;
import java.util.Comparator;
import java.util.HashMap;

public class Partido {
    private HashMap<Integer, Candidato> candidatos = new HashMap<>();
    private int numPartido;
    private String siglaPartido;
    private int qtdVotosLegenda;

    public Partido(int numPartido, String siglaPartido) {
        this.numPartido = numPartido;
        this.siglaPartido = siglaPartido;
        this.qtdVotosLegenda = 0;
    }

    public int getNumPartido() {
        return numPartido;
    }

    public String getSiglaPartido() {
        return siglaPartido;
    }

    public int getQtdVotosLegenda() {
        return qtdVotosLegenda;
    }

    public int getEleitos() {
        int eleitos = 0;
        for (Candidato c : candidatos.values()) {
            if (c.isEleito()) eleitos++;
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

    public int getTotalVotos() {
        return this.qtdVotosLegenda + this.getVotosNominais();
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

    public HashMap<Integer, Candidato> getCandidatos() {
        return new HashMap<Integer, Candidato>(candidatos);
    }

    public void addCandidato(Candidato candidato) {
        candidatos.put(candidato.getNumCandidato(), candidato);
    }

    public boolean contemCandidato(int numCandidato) {
        return candidatos.containsKey(numCandidato);
    }

    public boolean haCandidatoCadastrado() {
        return !candidatos.isEmpty();
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

    @Override
    public String toString() {
        return this.siglaPartido + " - " + this.numPartido;
    }

    public static class VotoPartidoComparator implements Comparator<Partido> {
        @Override
        public int compare(Partido p1, Partido p2) {
            int totalVotosP1 = p1.getTotalVotos();
            int totalVotosP2 = p2.getTotalVotos();
            if (totalVotosP1 > totalVotosP2) 
                return -1;
            else if (totalVotosP1 == totalVotosP2) 
                return p1.getNumPartido() < p2.getNumPartido() ? -1 : 1;
            else 
                return 1;   
        }
    }

    public static class MaisVotadoComparator implements Comparator<Partido> {
        @Override
        public int compare(Partido p1, Partido p2) {
            Candidato c1 = p1.getCandidatoMaisVotado();
            Candidato c2 = p2.getCandidatoMaisVotado();
            if (c1 == null || c2 == null) return 0;

            int votosP1 = c1.getQtdVotosNominal();
            int votosP2 = c2.getQtdVotosNominal();

            if (votosP1 > votosP2) 
                return -1;
            else if (votosP1 == votosP2) 
                return p1.getNumPartido() < p2.getNumPartido() ? -1 : 1;
            else 
                return 1;   
        }
    }
}

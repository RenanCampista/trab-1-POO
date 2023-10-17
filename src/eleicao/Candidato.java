package eleicao;

import java.util.Date;

public class Candidato {
    private int codCargo;
    private int codSituacaoCandidato;
    private int numCandidato;
    private String nomeUrna;
    private int numPartido;
    private String siglaPartido;
    private int numFederacao;
    private Date dataNascimento;
    private int codSituacaoCandidatura;
    private int codGenero;
    private String tipoDestinacaoVotos;
    private int qtdVotosNominal;

    public Candidato(int codCargo, int codSituacaoCandidato, int numCandidato, String nomeUrna,
            int numPartido, String siglaPartido, int numFederacao, Date dataNascimento, int codSituacaoCandidatura,
            int codGenero, String tipoDestinacaoVotos) {

        this.codCargo = codCargo;
        this.codSituacaoCandidato = codSituacaoCandidato;
        this.numCandidato = numCandidato;
        this.nomeUrna = nomeUrna;
        this.numPartido = numPartido;
        this.siglaPartido = siglaPartido;
        this.numFederacao = numFederacao;
        this.dataNascimento = dataNascimento;
        this.codSituacaoCandidatura = codSituacaoCandidatura;
        this.codGenero = codGenero;
        this.tipoDestinacaoVotos = tipoDestinacaoVotos;
        this.qtdVotosNominal = 0;
    }

    public int getNumCandidato() {
        return numCandidato;
    }

    public int getNumPartido() {
        return numPartido;
    }

    public boolean temFederacao() {
        return numFederacao != -1;
    }

    public void adicionarVoto(int qtdVotos) {
        this.qtdVotosNominal += qtdVotos;
    }


    @Override
    public String toString() {
        return "Candidato [codCargo=" + codCargo + ", codSituacaoCandidato=" + codSituacaoCandidato + ", numCandidato="
                + numCandidato + ", nomeUrna=" + nomeUrna + ", numPartido=" + numPartido + ", siglaPartido="
                + siglaPartido + ", numFederacao=" + numFederacao + ", dataNascimento=" + dataNascimento
                + ", codSituacaoCandidatura=" + codSituacaoCandidatura + ", codGenero=" + codGenero
                + ", tipoDestinacaoVotos=" + tipoDestinacaoVotos + "Votos" + qtdVotosNominal + "]";
    }



    
}

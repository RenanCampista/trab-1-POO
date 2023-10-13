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
    }


    @Override
    public String toString() {
        return "Candidato [codCargo=" + codCargo + ", codSituacaoCandidato=" + codSituacaoCandidato + ", numCandidato="
                + numCandidato + ", nomeUrna=" + nomeUrna + ", numPartido=" + numPartido + ", siglaPartido="
                + siglaPartido + ", numFederacao=" + numFederacao + ", dataNascimento=" + dataNascimento
                + ", codSituacaoCandidatura=" + codSituacaoCandidatura + ", codGenero=" + codGenero
                + ", tipoDestinacaoVotos=" + tipoDestinacaoVotos + "]";
    }
    
}

package eleicao;

import java.util.Date;

public class Candidato {
   enum Genero {
        MASCULINO, FEMININO
    }

    enum SituacaoCandidato {
        ELEITO, NAO_ELEITO
    }
    

    private int codCargo;
    private int codSituacaoCandidato;
    private int numCandidato;
    private String nomeUrna;
    private int numPartido;
    private String siglaPartido;
    private int numFederacao;
    private Date dataNascimento;
    private SituacaoCandidato statusCandidatura;
    private Genero codGenero;
    private String tipoDestinacaoVotos;
    private int qtdVotosNominal;

    public Candidato(int codCargo, int codSituacaoCandidato, int numCandidato, String nomeUrna,
            int numPartido, String siglaPartido, int numFederacao, Date dataNascimento, int statusCandidatura,
            int codGenero, String tipoDestinacaoVotos) {

        this.codCargo = codCargo;
        this.codSituacaoCandidato = codSituacaoCandidato;
        this.numCandidato = numCandidato;
        this.nomeUrna = nomeUrna;
        this.numPartido = numPartido;
        this.siglaPartido = siglaPartido;
        this.numFederacao = numFederacao;
        this.dataNascimento = dataNascimento;
        if (statusCandidatura == 2 || statusCandidatura == 3)
            this.statusCandidatura = SituacaoCandidato.ELEITO;
        else
            this.statusCandidatura = SituacaoCandidato.NAO_ELEITO;
        
        if (codGenero == 2) 
            this.codGenero = Genero.MASCULINO;
        else if (codGenero == 4) 
            this.codGenero = Genero.FEMININO;
        
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

    public boolean votosLegenda() {
        return this.tipoDestinacaoVotos.equals("Válido");
    }

    public boolean eleito() {
        return this.statusCandidatura == SituacaoCandidato.ELEITO;
    }

    public void adicionarVoto(int qtdVotos) {
        if (this.codSituacaoCandidato == 2 || this.codSituacaoCandidato == 16)
            this.qtdVotosNominal += qtdVotos;
    }


    @Override
    public String toString() {
        return "Candidato [codCargo=" + codCargo + ", codSituacaoCandidato=" + codSituacaoCandidato + ", numCandidato="
                + numCandidato + ", nomeUrna=" + nomeUrna + ", numPartido=" + numPartido + ", siglaPartido="
                + siglaPartido + ", numFederacao=" + numFederacao + ", dataNascimento=" + dataNascimento
                + ", codSituacaoCandidatura=" + statusCandidatura + ", codGenero=" + codGenero
                + ", tipoDestinacaoVotos=" + tipoDestinacaoVotos + "Votos" + qtdVotosNominal + "]";
    }



    
}

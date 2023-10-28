package eleicao;
import java.text.NumberFormat;
import java.time.Period;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class Candidato {
    private Locale locale = new Locale.Builder().setLanguage("pt").setRegion("BR").build();
    private NumberFormat format = NumberFormat.getNumberInstance(locale);
    
    public enum Genero {
        MASCULINO, FEMININO
    }
    enum SituacaoCandidato {
        ELEITO, NAO_ELEITO
    }
    
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

    public Candidato(int codSituacaoCandidato, int numCandidato, String nomeUrna,
            int numPartido, String siglaPartido, int numFederacao, Date dataNascimento, int statusCandidatura,
            int codGenero, String tipoDestinacaoVotos) {

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

    public int getCodSituacaoCandidato() {
        return codSituacaoCandidato;
    }

    public int getNumCandidato() {
        return numCandidato;
    }

    public String getNomeUrna() {
        return nomeUrna;
    }

    public int getNumPartido() {
        return numPartido;
    }

    public String getSiglaPartido() {
        return siglaPartido;
    }

    public int getNumFederacao() {
        return numFederacao;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public SituacaoCandidato getStatusCandidatura() {
        return statusCandidatura;
    }

    public Genero getCodGenero() {
        return codGenero;
    }

    public String getTipoDestinacaoVotos() {
        return tipoDestinacaoVotos;
    }

    public int getQtdVotosNominal() {
        return qtdVotosNominal;
    }

    public int getIdade(Date dataEleicao) {
        int idade;
        Period periodo = Period.between(this.getDataNascimento().toInstant().atZone(ZoneId.of("America/Sao_Paulo")).toLocalDate(), 
                                        dataEleicao.toInstant().atZone(ZoneId.of("America/Sao_Paulo")).toLocalDate());
        idade = periodo.getYears();
        return idade;
    }

    public boolean temFederacao() {
        return numFederacao != -1;
    }

    public boolean votosLegenda() {
        return this.tipoDestinacaoVotos.equals("Válido");
    }

    public boolean isEleito() {
        return this.statusCandidatura == SituacaoCandidato.ELEITO;
    }

    public void adicionarVoto(int qtdVotos) {
        this.qtdVotosNominal += qtdVotos;
    }

    @Override
    public String toString() {
        String out = "";
        if (this.temFederacao())
            out += "*";
        out += this.nomeUrna + " (" + this.siglaPartido + ", " + format.format(this.qtdVotosNominal) + " votos)";
        return out;
    }

    public static class VotoNominalComparator implements Comparator<Candidato> {
        
        @Override
        public int compare(Candidato c1, Candidato c2) {
            return c2.getQtdVotosNominal() - c1.getQtdVotosNominal();
        }
    }
    
}




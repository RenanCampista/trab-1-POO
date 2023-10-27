package relatorio;
import java.util.Date;
import java.util.HashMap;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import eleicao.Partido;
import eleicao.Partido.MaisVotadoComparator;
import eleicao.Partido.VotoPartidoComparator;
import eleicao.Candidato.Genero;
import eleicao.Candidato.VotoNominalComparator;
import eleicao.Candidato;
import java.util.Locale;

public class Relatorio {
    private Locale locale = new Locale.Builder().setLanguage("pt").setRegion("BR").build();
    private NumberFormat nf = NumberFormat.getNumberInstance(locale);
    {
        nf.setGroupingUsed(true);
    }

    DecimalFormat df = (DecimalFormat) NumberFormat.getNumberInstance(locale);
    {
        df.applyPattern("#0.00");    
    }
    
    private Date dataEleicao;
    private ArrayList<Candidato> candidatosList = new ArrayList<>();
    private ArrayList<Partido> partidosList = new ArrayList<>();
    private int numeroTotalEleitos = 0;

    public Relatorio(Date dataEleicao, HashMap<Integer, Partido> partidos) {
        this.dataEleicao = dataEleicao;
        for (Partido p : partidos.values()) {
            this.numeroTotalEleitos += p.getEleitos();
            partidosList.add(p);
            for (Candidato c : p.getCandidatos().values()) {
                candidatosList.add(c);
            }
        }
        Collections.sort(candidatosList, new VotoNominalComparator());
    }

    //Relatorio 1
    public int getNumeroTotalEleitos() {
        return numeroTotalEleitos;
    }

    //Relatorio 2
    public void candidatosELeitos() {
        int i = 0;
        for (Candidato c : candidatosList) {
            if(c.eleito()) {
                i++;
                System.out.println(i + " - " + c);
            }
        }
    }

    //Relatorio 3
    public void candidatosMaisVotados() {
        for (int i = 0; i < numeroTotalEleitos; i++) {
            System.out.println(i+1 + " - " + candidatosList.get(i));
        }
    }

    //Relatorio 4
    public void teriamSidoEleitos() {
        for (int i = 0; i < numeroTotalEleitos; i++) {
            if (!candidatosList.get(i).eleito()) {
                System.out.println(i+1 + " - " + candidatosList.get(i));
            }
        }
    }

    //Relatorio 5
    public void eleitosBeneficiadosSistemaProporcional() {
        for (int i = numeroTotalEleitos; i < candidatosList.size() ; i++) {
            if (candidatosList.get(i).eleito()) {
                System.out.println(i+1 + " - " + candidatosList.get(i));
            }
        }
    }

    //Relatorio 6
    public void votosTotalizadosPorPartido() {
        Collections.sort(partidosList, new VotoPartidoComparator());
        int i = 1;
        for (Partido p : partidosList) {
            System.out.println(i + " - " + p + ", " + nf.format(p.getTotalVotos()) + " votos " + "(" + 
                        nf.format(p.getVotosNominais()) + " nominais e " + nf.format(p.getQtdVotosLegenda()) + " de legenda), " + 
                        nf.format(p.getEleitos()) + " candidatos eleitos");
            i++;
        }
    }

    //Relatorio 7
    public void primeiroUltimoColocadosPartido() {
        Collections.sort(partidosList, new MaisVotadoComparator());
        int i = 1;
        for (Partido p : partidosList) {
            if (p.temCandidato()) {
                Candidato maisVotado = p.getCandidatoMaisVotado();
                Candidato menosVotado = p.getCandidatoMenosVotado();
                System.out.println(i + " - " + p.getSiglaPartido() + " - " + p.getNumPartido() + " " + maisVotado.getNomeUrna() + 
                                    "(" + maisVotado.getNumCandidato() + ", " + nf.format(maisVotado.getQtdVotosNominal()) + " votos)" + " / " + 
                                    menosVotado.getNomeUrna() + "(" + menosVotado.getNumCandidato() + ", " + nf.format(menosVotado.getQtdVotosNominal()) + " votos)");
                i++;
            }
        }
    }

    //Relatorio 8
    public void eleitosPorFaixaEtaria() {
        int idade, eleitos = numeroTotalEleitos;
        int contMenor30 = 0, cont30a39 = 0, cont40a49 = 0, cont50a59 = 0, cont60 = 0;
        for (Candidato c : candidatosList) {
            if (c.eleito()) {
                idade = c.getIdade(dataEleicao);
                if (idade < 30) {
                    contMenor30++;
                } else if (idade >= 30 && idade <= 39) {
                    cont30a39++;
                } else if (idade >= 40 && idade <= 49) {
                    cont40a49++;
                } else if (idade >= 50 && idade <= 59) {
                    cont50a59++;
                } else {
                    cont60++;
                }
            }
        }
        System.out.println("      Idade < 30: " + contMenor30 + " (" + df.format((double) contMenor30 / eleitos * 100) + "%)");
        System.out.println("30 <= Idade < 40: " + cont30a39 + " (" + df.format((double) cont30a39 / eleitos * 100) + "%)");
        System.out.println("40 <= Idade < 50: " + cont40a49 + " (" + df.format((double) cont40a49 / eleitos * 100) + "%)");
        System.out.println("50 <= Idade < 60: " + cont50a59 + " (" + df.format((double) cont50a59 / eleitos * 100) + "%)");
        System.out.println("60 <= Idade\t: " + cont60 + " (" + df.format((double) cont60 / eleitos * 100) + "%)");
    }

    //Relatorio 9
    public void eleitosPorGenero() {
        int eleitosMasculinos = 0;
        int eleitosFemininos = 0;
        for (Candidato c : candidatosList) {
            if (c.getCodGenero() == Genero.MASCULINO && c.eleito()) eleitosMasculinos++;
            else if (c.getCodGenero() == Genero.FEMININO && c.eleito()) eleitosFemininos++;
        }
        System.out.println("Feminino: " + eleitosFemininos + " (" + df.format((double) eleitosFemininos / (eleitosFemininos + eleitosMasculinos) * 100) + "%)");
        System.out.println("Masculino: " + eleitosMasculinos + " (" + df.format((double) eleitosMasculinos / (eleitosFemininos + eleitosMasculinos) * 100) + "%)\n");
    }

    //Relatorio 10
    public void totalVotosValidos() {
        int votosValidos = 0;
        int votosLegenda = 0;
        int votosNominais = 0;
        for (Partido p : partidosList) {
            votosLegenda += p.getQtdVotosLegenda();
            votosNominais += p.getVotosNominais();
        }
        votosValidos = votosLegenda + votosNominais;
        System.out.println("Total de votos válidos: " + nf.format(votosValidos));
        System.out.println("Total de votos nominais: " + nf.format(votosNominais) + " (" + df.format((double) votosNominais / votosValidos * 100) + "%)");
        System.out.println("Total de votos de legenda: " + nf.format(votosLegenda) + " (" + df.format((double) votosLegenda / votosValidos * 100) + "%)");
    }
}

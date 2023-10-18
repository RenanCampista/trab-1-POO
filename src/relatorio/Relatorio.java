package relatorio;
import java.util.Date;
import java.util.HashMap;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import eleicao.Partido;
import eleicao.Candidato.Genero;
import eleicao.Candidato;
import java.util.Locale;

public class Relatorio {
    Locale locale = new Locale.Builder().setLanguage("pt").setRegion("BR").build();
    NumberFormat format = NumberFormat.getNumberInstance(locale);

    private Date dataEleicao;
    
    public Relatorio() {
    }

    // public Relatorio(Date dataEleicao) {
    //     this.dataEleicao = dataEleicao;
    // }
    
    //Relatorio 1
    public int numeroEleitos(HashMap<Integer, Partido> partidos) {
        int eleitos = 0;
        for (Partido partido : partidos.values()) {
            eleitos += partido.getEleitos();
        }
        return eleitos;
    }

    //Relatorio 2
    public void candidatosELeitos(HashMap<Integer, Partido> partidos) {
        ArrayList<Candidato> candidatos = new ArrayList<>();
        for (Partido p : partidos.values()) {
            for (Candidato c : p.getCandidatos().values()) 
                if (c.eleito()) candidatos.add(c);     
        }
        
        Collections.sort(candidatos, new VotoNominalComparator());
        for (int i = 0; i < candidatos.size(); i++) {
            System.out.println(i+1 + " - " + candidatos.get(i));
        }
    }

    //Relatorio 3
    public void candidatosMaisVotados(HashMap<Integer, Partido> partidos) {
        ArrayList<Candidato> candidatos = new ArrayList<>();
        for (Partido p : partidos.values()) {
            for (Candidato c : p.getCandidatos().values()) 
                if (c.getQtdVotosNominal() > 0) candidatos.add(c);     
        }
        
        Collections.sort(candidatos, new VotoNominalComparator());
        for (int i = 0; i < numeroEleitos(partidos); i++) {
            System.out.println(i+1 + " - " + candidatos.get(i));
        }
    }

    //Relatorio 4
    public void teriamSidoEleitos(HashMap<Integer, Partido> partidos) {
        ArrayList<Candidato> candidatos = new ArrayList<>();
        for (Partido p : partidos.values()) {
            for (Candidato c : p.getCandidatos().values()) 
                if (c.getQtdVotosNominal() > 0) candidatos.add(c);     
        }
        
        Collections.sort(candidatos, new VotoNominalComparator());

        for (int i = 0; i < numeroEleitos(partidos); i++) {
            if (!candidatos.get(i).eleito()) {
                System.out.println(i+1 + " - " + candidatos.get(i));
            }
        }
    }

    //Relatorio 5
    public void eleitosBeneficiadosSistemaProporcional(HashMap<Integer, Partido> partidos) {
        ArrayList<Candidato> candidatos = new ArrayList<>();
        for (Partido p : partidos.values()) {
            for (Candidato c : p.getCandidatos().values()) 
                if (c.eleito()) candidatos.add(c);     
        }
        
        Collections.sort(candidatos, new VotoNominalComparator());

        for (int i = numeroEleitos(partidos); i < candidatos.size() ; i++) {
            if (candidatos.get(i).eleito()) {
                System.out.println(i+1 + " - " + candidatos.get(i));
            }
        }
    }

    //Relatorio 6
    public void votosTotalizadosPorPartido(HashMap<Integer, Partido> partidos) {
        ArrayList<Partido> partidosList = new ArrayList<>(partidos.values());
        Collections.sort(partidosList, new VotoPartidoComparator());
        
        for (Partido p : partidosList) {
            System.out.println(p + ", " + format.format(p.getTotalVotos()) + " votos " + "(" + 
                        format.format(p.getVotosNominais()) + "nominais e " + format.format(p.getVotosLegenda()) + " de legenda), " + 
                        format.format(p.getEleitos()) + " candidatos eleitos");
        }
    }

    //Relatorio 7
    public void primeiroUltimoColocadosPartido(HashMap<Integer, Partido> partidos) {
        ArrayList<Partido> partidosList = new ArrayList<>(partidos.values());
    }

    //Relatorio 9
    public void eleitosPorGenero(HashMap<Integer, Partido> partidos) {
        ArrayList<Candidato> candidatos = new ArrayList<>();
        for (Partido p : partidos.values()) {
            for (Candidato c : p.getCandidatos().values()) 
                if (c.eleito()) candidatos.add(c);   
        }

        int eleitosMasculinos = 0;
        int eleitosFemininos = 0;
        for (Candidato c : candidatos) {
            if (c.getCodGenero() == Genero.MASCULINO) eleitosMasculinos++;
            else eleitosFemininos++;
        }

        System.out.println("Masculino: " + eleitosMasculinos + " (" + format.format((double) eleitosMasculinos / (eleitosFemininos + eleitosMasculinos) * 100) + "%)");
        System.out.println("Feminino: " + eleitosFemininos + " (" + format.format((double) eleitosFemininos / (eleitosFemininos + eleitosMasculinos) * 100) + "%)");
    }

    //Relatorio 10
    public void totalVotosValidos(HashMap<Integer, Partido> partidos) {
        int votosValidos = 0;
        int votosLegenda = 0;
        int votosNominais = 0;
        for (Partido p : partidos.values()) {
            votosLegenda += p.getVotosLegenda();
            votosNominais += p.getVotosNominais();
            votosValidos += votosLegenda + votosNominais;
        }
        
        System.out.println("Total de votos válidos: " + format.format(votosValidos));
        System.out.println("Total de votos nominais: " + format.format(votosNominais) + " (" + format.format((double) votosNominais / votosValidos * 100) + "%)");
        System.out.println("Total de votos de legenda: " + format.format(votosLegenda) + " (" + format.format((double) votosLegenda / votosValidos * 100) + "%)");
    }
}

class VotoNominalComparator implements Comparator<Candidato> {
    @Override
    public int compare(Candidato c1, Candidato c2) {
        return c2.getQtdVotosNominal() - c1.getQtdVotosNominal();
    }
}

class VotoPartidoComparator implements Comparator<Partido> {
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
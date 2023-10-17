package relatorio;
import java.util.Date;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import eleicao.Partido;
import eleicao.Candidato;

public class Relatorio {
    private Date dataEleicao;

    public Relatorio() {
    }

    // public Relatorio(Date dataEleicao) {
    //     this.dataEleicao = dataEleicao;
    // }
    
    public int numeroEleitos(HashMap<Integer, Partido> partidos) {
        int eleitos = 0;
        for (Partido partido : partidos.values()) {
            eleitos += partido.getEleitos();
        }
        return eleitos;
    }

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
}

class VotoNominalComparator implements Comparator<Candidato> {
    @Override
    public int compare(Candidato c1, Candidato c2) {
        return c1.getQtdVotosNominal() - c2.getQtdVotosNominal();
    }
}
package leitura;
import java.io.BufferedReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import eleicao.Candidato;
import eleicao.Partido;
import java.util.HashMap;

public class Entrada {
    HashMap<Integer, Partido> partidos = new HashMap<>();

    public enum EntradaCandidato {
        CD_CARGO(13),
        CD_SITUACAO_CANDIDATO_TOT(68),
        NR_CANDIDATO(16),
        NM_URNA_CANDIDATO(18),
        NR_PARTIDO(27),
        SG_PARTIDO(28),
        NR_FEDERACAO(30),
        DT_NASCIMENTO(42),
        CD_SIT_TOT_TURNO(56),
        CD_GENERO(45),
        NM_TIPO_DESTINACAO_VOTOS(67);
    
        private final int value;
    
        EntradaCandidato(int value) {
            this.value = value;
        }
    
        public int getValue() {
            return value;
        }
    }

    public enum EntradaVotacao {
        CD_CARGO(17),
        NR_VOTAVEL(19),
        QT_VOTOS(21);
    
        private final int value;
    
        EntradaVotacao(int value) {
            this.value = value;
        }
    
        public int getValue() {
            return value;
        }
    }
    
    public Entrada() {
    }

    public HashMap<Integer, Partido> readCandidatos(String path, String opcao) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), Charset.forName("ISO-8859-1")))) {
            String line = br.readLine();
            line = br.readLine();
            while (line != null) {
                String[] fields = line.split(";");
                int codCargo = Integer.parseInt(fields[EntradaCandidato.CD_CARGO.getValue()].replace("\"", ""));
                int codSituacaoCandidato = Integer.parseInt(fields[EntradaCandidato.CD_SITUACAO_CANDIDATO_TOT.getValue()].replace("\"", ""));
                int numCandidato = Integer.parseInt(fields[EntradaCandidato.NR_CANDIDATO.getValue()].replace("\"", ""));
                String nomeUrna = fields[EntradaCandidato.NM_URNA_CANDIDATO.getValue()].replace("\"", "");
                int numPartido = Integer.parseInt(fields[EntradaCandidato.NR_PARTIDO.getValue()].replace("\"", ""));
                String siglaPartido = fields[EntradaCandidato.SG_PARTIDO.getValue()].replace("\"", "");
                int numFederacao = Integer.parseInt(fields[EntradaCandidato.NR_FEDERACAO.getValue()].replace("\"", ""));
                Date dataNascimento = null;
                if(fields[EntradaCandidato.DT_NASCIMENTO.getValue()].replace("\"", "") != ""){
                    dataNascimento = new SimpleDateFormat("dd/MM/yyyy").parse(fields[EntradaCandidato.DT_NASCIMENTO.getValue()].replace("\"", ""));
                }
                int statusCandidatura = Integer.parseInt(fields[EntradaCandidato.CD_SIT_TOT_TURNO.getValue()].replace("\"", ""));
                int codGenero = Integer.parseInt(fields[EntradaCandidato.CD_GENERO.getValue()].replace("\"", ""));
                String tipoDestinacaoVotos = fields[EntradaCandidato.NM_TIPO_DESTINACAO_VOTOS.getValue()].replace("\"", "");
                Candidato candidato = new Candidato(codSituacaoCandidato, numCandidato, nomeUrna, numPartido, siglaPartido, 
                numFederacao, dataNascimento, statusCandidatura, codGenero, tipoDestinacaoVotos);
         
                if (partidos.containsKey(numPartido)) {
                    if ((opcao.equals("estadual") && codCargo == 7 || opcao.equals("federal") && codCargo == 6)) {
                        if ((candidato.getCodSituacaoCandidato() == 2 || candidato.getCodSituacaoCandidato() == 16) || tipoDestinacaoVotos.equals("Válido (legenda)"))    
                            partidos.get(numPartido).addCandidato(candidato);
                    }
                } else {
                    Partido partido = new Partido(numPartido, siglaPartido);
                    if ((opcao.equals("estadual") && codCargo == 7 || opcao.equals("federal") && codCargo == 6)) {
                        if ((candidato.getCodSituacaoCandidato() == 2 || candidato.getCodSituacaoCandidato() == 16) || tipoDestinacaoVotos.equals("Válido (legenda)"))    
                            partido.addCandidato(candidato);
                    }
                    partidos.put(numPartido, partido);
                }
                line = br.readLine();
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return new HashMap<Integer, Partido>(partidos);
    }

    public void readVotacao(String path, String arg, HashMap<Integer, Partido> partidos) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), Charset.forName("ISO-8859-1")))) {
            String line = br.readLine();
            line = br.readLine();
            while (line != null) {
                String[] fields = line.split(";");
                int codCargo = Integer.parseInt(fields[EntradaVotacao.CD_CARGO.getValue()].replace("\"", ""));
                if (arg.equals("estadual") && codCargo == 7  || arg.equals("federal") && codCargo == 6) {
                    int numVotavel = Integer.parseInt(fields[EntradaVotacao.NR_VOTAVEL.getValue()].replace("\"", ""));
                    int qtdVotos = Integer.parseInt(fields[EntradaVotacao.QT_VOTOS.getValue()].replace("\"", ""));

                    if (numVotavel < 95 || numVotavel > 98) {
                        for (Partido p : partidos.values()) {
                            if (p.contemCandidato(numVotavel))
                                p.adicionarVotoCandidato(numVotavel, qtdVotos);
                            else if (numVotavel == p.getNumPartido()) 
                                p.adicionarVotoLegenda(qtdVotos);
                        }
                    }
                }
                line = br.readLine();
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } 
    }
}

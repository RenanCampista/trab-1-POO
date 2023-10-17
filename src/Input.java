import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import eleicao.Candidato;
import eleicao.Partido;

import java.util.HashMap;

public class Input {
    HashMap<Integer, Partido> partidos = new HashMap<>();
    
    public enum InputCandidato {
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
    
        InputCandidato(int value) {
            this.value = value;
        }
    
        public int getValue() {
            return value;
        }
    }

    public enum InputVotacao {
        CD_CARGO(17),
        NR_VOTAVEL(19),
        QT_VOTOS(21);
    
        private final int value;
    
        InputVotacao(int value) {
            this.value = value;
        }
    
        public int getValue() {
            return value;
        }
    }
    
    public Input() {
    
    }

    public HashMap<Integer, Partido> readCandidatos(String path, String arg) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = br.readLine();
            line = br.readLine();
            while (line != null) {
                String[] fields = line.split(";");
                int codCargo = Integer.parseInt(fields[InputCandidato.CD_CARGO.getValue()]);
                if (arg.equals("estadual") && codCargo == 7 || arg.equals("federal") && codCargo == 6) {
                    int codSituacaoCandidato = Integer.parseInt(fields[InputCandidato.CD_SITUACAO_CANDIDATO_TOT.getValue()]);
                    int numCandidato = Integer.parseInt(fields[InputCandidato.NR_CANDIDATO.getValue()]);
                    String nomeUrna = fields[InputCandidato.NM_URNA_CANDIDATO.getValue()];
                    int numPartido = Integer.parseInt(fields[InputCandidato.NR_PARTIDO.getValue()]);
                    String siglaPartido = fields[InputCandidato.SG_PARTIDO.getValue()];
                    int numFederacao = Integer.parseInt(fields[InputCandidato.NR_FEDERACAO.getValue()]);
                    Date dataNascimento = new SimpleDateFormat("dd/MM/yyyy").parse(fields[InputCandidato.DT_NASCIMENTO.getValue()]);
                    int codSituacaoCandidatura = Integer.parseInt(fields[InputCandidato.CD_SIT_TOT_TURNO.getValue()]);
                    int codGenero = Integer.parseInt(fields[InputCandidato.CD_GENERO.getValue()]);
                    String tipoDestinacaoVotos = fields[InputCandidato.NM_TIPO_DESTINACAO_VOTOS.getValue()];
                    
                    Candidato candidato = new Candidato(codCargo, codSituacaoCandidato, numCandidato, nomeUrna, numPartido, siglaPartido, 
                    numFederacao, dataNascimento, codSituacaoCandidatura, codGenero, tipoDestinacaoVotos);
                    
                    if (partidos.containsKey(numPartido) || numFederacao == -1) {
                        partidos.get(numPartido).addCandidato(candidato);
                    } else {
                        Partido partido = new Partido(numPartido, siglaPartido);
                        partido.addCandidato(candidato);
                        partidos.put(numPartido, partido);
                    }

                }
                /*
                 * E os candidatos sem partido?
                 * E com candidatura indeferida mas com votaçao para partido?
                 */
                line = br.readLine();
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return new HashMap<Integer, Partido>(partidos);
    }

    public void readVotacao(String path, String arg) {
         try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = br.readLine();
            line = br.readLine();
            while (line != null) {
                String[] fields = line.split(";");
                int codCargo = Integer.parseInt(fields[InputVotacao.CD_CARGO.getValue()]);
                if (arg.equals("estadual") && codCargo == 7  || arg.equals("federal") && codCargo == 6) {
                    int numVotavel = Integer.parseInt(fields[InputVotacao.NR_VOTAVEL.getValue()]);
                    int qtdVotos = Integer.parseInt(fields[InputVotacao.QT_VOTOS.getValue()]);

                    if (numVotavel < 95 || numVotavel > 98) {
                        for (Partido p : partidos.values()) {
                            if (p.contemCandidato(numVotavel)) {
                                p.adicionarVoto(numVotavel, qtdVotos);
                            } else if (numVotavel == p.getNumPartido()) {
                                p.adicionarVoto(numVotavel, qtdVotos);
                            }
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

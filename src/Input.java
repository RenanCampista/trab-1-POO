import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import eleicao.Candidato;
import eleicao.Partido;

import java.util.HashMap;

public class Input {
    // Indices dos campos no arquivo csv dos candidatos
    private static final int CD_CARGO = 13;
    private static final int CD_SITUACAO_CANDIDATO_TOT = 68;
    private static final int NR_CANDIDATO = 16;
    private static final int NM_URNA_CANDIDATO = 18;
    private static final int NR_PARTIDO = 27;
    private static final int SG_PARTIDO = 28;
    private static final int NR_FEDERACAO = 30;
    private static final int DT_NASCIMENTO = 42;
    private static final int CD_SIT_TOT_TURNO = 56;
    private static final int CD_GENERO = 45;
    private static final int NM_TIPO_DESTINACAO_VOTOS = 67;
    HashMap<Integer, Partido> partidos = new HashMap<>();

    public Input() {
    }

    public HashMap<Integer, Partido> readCandidatos(String path, String arg) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = br.readLine();
            line = br.readLine();
            while (line != null) {
                String[] fields = line.split(";");
                int codCargo = Integer.parseInt(fields[CD_CARGO]);
                if (arg.equals("estadual") && codCargo == 7 || arg.equals("federal") && codCargo == 6) {
                    int codSituacaoCandidato = Integer.parseInt(fields[CD_SITUACAO_CANDIDATO_TOT]);
                    int numCandidato = Integer.parseInt(fields[NR_CANDIDATO]);
                    String nomeUrna = fields[NM_URNA_CANDIDATO];
                    int numPartido = Integer.parseInt(fields[NR_PARTIDO]);
                    String siglaPartido = fields[SG_PARTIDO];
                    int numFederacao = Integer.parseInt(fields[NR_FEDERACAO]);
                    Date dataNascimento = new SimpleDateFormat("dd/MM/yyyy").parse(fields[DT_NASCIMENTO]);
                    int codSituacaoCandidatura = Integer.parseInt(fields[CD_SIT_TOT_TURNO]);
                    int codGenero = Integer.parseInt(fields[CD_GENERO]);
                    String tipoDestinacaoVotos = fields[NM_TIPO_DESTINACAO_VOTOS];
                    
                    Candidato candidato = new Candidato(codCargo, codSituacaoCandidato, numCandidato, nomeUrna, numPartido, siglaPartido, 
                    numFederacao, dataNascimento, codSituacaoCandidatura, codGenero, tipoDestinacaoVotos);
                    
                    if (partidos.containsKey(numPartido)) {
                        partidos.get(numPartido).addCandidato(candidato);
                    } else {
                        Partido partido = new Partido(numPartido, siglaPartido);
                        partido.addCandidato(candidato);
                        partidos.put(numPartido, partido);
                    }

                }
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
                int codCargo = Integer.parseInt(fields[CD_CARGO]);
                if (arg.equals("estadual") && codCargo == 7 || arg.equals("federal") && codCargo == 6) {
                    



                }
                line = br.readLine();
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
       
    }


}

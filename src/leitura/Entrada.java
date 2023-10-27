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
    private HashMap<Integer, Partido> partidos = new HashMap<>();

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

            int codCargo = 0, codSituacaoCandidato = 0, numCandidato = 0, numPartido = 0, numFederacao = 0, statusCandidatura = 0, codGenero = 0;
            String nomeUrna, siglaPartido, tipoDestinacaoVotos;
            Date dataNascimento = null; 

            while (line != null) {
                String[] fields = line.split(";");
                try {
                    codCargo = Integer.parseInt(fields[EntradaCandidato.CD_CARGO.getValue()].replace("\"", ""));
                } catch (NumberFormatException e) {
                    System.err.println("Erro ao converter o valor de CD_CARGO para inteiro: " + e.getMessage());
                }

                try {
                    codSituacaoCandidato = Integer.parseInt(fields[EntradaCandidato.CD_SITUACAO_CANDIDATO_TOT.getValue()].replace("\"", ""));
                } catch (NumberFormatException e) {
                    System.err.println("Erro ao converter o valor de CD_SITUACAO_CANDIDATO_TOT para inteiro: " + e.getMessage());
                }

                try {
                    numCandidato = Integer.parseInt(fields[EntradaCandidato.NR_CANDIDATO.getValue()].replace("\"", ""));
                } catch (NumberFormatException e) {
                    System.err.println("Erro ao converter o valor de NR_CANDIDATO para inteiro: " + e.getMessage());
                }
               
                nomeUrna = fields[EntradaCandidato.NM_URNA_CANDIDATO.getValue()].replace("\"", "");
                
                try {
                    numPartido = Integer.parseInt(fields[EntradaCandidato.NR_PARTIDO.getValue()].replace("\"", ""));
                } catch (NumberFormatException e) {
                    System.err.println("Erro ao converter o valor de NR_PARTIDO para inteiro: " + e.getMessage());
                }

                siglaPartido = fields[EntradaCandidato.SG_PARTIDO.getValue()].replace("\"", "");

                try {
                    numFederacao = Integer.parseInt(fields[EntradaCandidato.NR_FEDERACAO.getValue()].replace("\"", ""));
                } catch (NumberFormatException e) {
                    System.err.println("Erro ao converter o valor de NR_FEDERACAO para inteiro: " + e.getMessage());
                }

                if(fields[EntradaCandidato.DT_NASCIMENTO.getValue()].replace("\"", "") != ""){
                    try {
                        dataNascimento = new SimpleDateFormat("dd/MM/yyyy").parse(fields[EntradaCandidato.DT_NASCIMENTO.getValue()].replace("\"", ""));
                    } catch (Exception e) {
                        System.err.println("Erro ao converter o valor de DT_NASCIMENTO para data: " + e.getMessage());
                    }
                }

                try {
                    statusCandidatura = Integer.parseInt(fields[EntradaCandidato.CD_SIT_TOT_TURNO.getValue()].replace("\"", ""));
                } catch (NumberFormatException e) {
                    System.err.println("Erro ao converter o valor de CD_SIT_TOT_TURNO para inteiro: " + e.getMessage());
                }

                try {
                    codGenero = Integer.parseInt(fields[EntradaCandidato.CD_GENERO.getValue()].replace("\"", ""));
                } catch (NumberFormatException e) {
                    System.err.println("Erro ao converter o valor de CD_GENERO para inteiro: " + e.getMessage());
                }

                tipoDestinacaoVotos = fields[EntradaCandidato.NM_TIPO_DESTINACAO_VOTOS.getValue()].replace("\"", "");

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
            System.err.println("Erro de E/S: " + e.getMessage());
        }
        return new HashMap<Integer, Partido>(partidos);
    }

    public void readVotacao(String path, String arg, HashMap<Integer, Partido> partidos) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), Charset.forName("ISO-8859-1")))) {
            String line = br.readLine();
            line = br.readLine();
            int codCargo = 0, numVotavel = 0, qtdVotos = 0;
            while (line != null) {
                String[] fields = line.split(";");

                try {
                    codCargo = Integer.parseInt(fields[EntradaVotacao.CD_CARGO.getValue()].replace("\"", ""));
                } catch (NumberFormatException e) {
                    System.err.println("Erro ao converter o valor de CD_CARGO para inteiro: " + e.getMessage());
                }

                if (arg.equals("estadual") && codCargo == 7  || arg.equals("federal") && codCargo == 6) {
                    try {
                        numVotavel = Integer.parseInt(fields[EntradaVotacao.NR_VOTAVEL.getValue()].replace("\"", ""));
                    } catch (NumberFormatException e) {
                        System.err.println("Erro ao converter o valor de NR_VOTAVEL para inteiro: " + e.getMessage());
                    }

                    try {
                        qtdVotos = Integer.parseInt(fields[EntradaVotacao.QT_VOTOS.getValue()].replace("\"", ""));
                    } catch (NumberFormatException e) {
                        System.err.println("Erro ao converter o valor de QT_VOTOS para inteiro: " + e.getMessage());
                    }

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
            System.err.println("Erro de E/S: " + e.getMessage());
        } 
    }
}

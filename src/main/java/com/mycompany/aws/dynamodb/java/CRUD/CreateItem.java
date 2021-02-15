package com.mycompany.aws.dynamodb.java.CRUD;
import com.amazonaws.services.dynamodbv2.document.*;
import com.mycompany.aws.dynamodb.java.TratamentoTXT.LeituraPalavraAPalavraDoTXT;
import com.mycompany.aws.dynamodb.java.TratamentoTXT.LeituraTxtParaString;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import static com.mycompany.aws.dynamodb.java.TratamentoTXT.LeituraTxtParaString.retornaTextoTXT;

public class CreateItem {
    private static final Logger LOGGER = Logger.getLogger("Crud");

    public static void InsertNewText(String tableName) {

        long totalInsertTime = 0;
        long timeTotalReadTXT = 0;
        long timeStartReadTXT = 0;
        long timeFinishReadTXT = 0;
        int contLivrosInseridos = 0;
        long timeStartReadTXTAndWriteDB = System.currentTimeMillis(), timeFinishReadTXTAndWriteDB = 0, timeTotalReadTXTAndWireDB = 0;

        DynamoDB dynamoDB = AmazonDynamoDBClientManagerr.getDynamoDB();
        Table table = dynamoDB.getTable(tableName);

        for (int i = 1; i < 3; i++) {
            //System.out.println("Entrou no for");
            //301 é o número do ultimo livro que da no total 279 livros
            // - não possui todos pois na hora de baixar nao tinha todos os
            // livros no site é o numero do ultimo livro
            timeStartReadTXT = System.currentTimeMillis();
            String texto = retornaTextoTXT(
                "src/main/java/com/mycompany/aws/dynamodb/java/LivrosTXT/Livro-" + i + ".txt");
            timeFinishReadTXT = System.currentTimeMillis();
            timeTotalReadTXT = timeFinishReadTXT - timeStartReadTXT;
            String nomeLivro = "Livro-" + i;
            //System.out.println(nomeLivro);

            final Map<String, Object> infoMap = new HashMap<>();
            infoMap.put("plot", "Nothing happens at all.");
            infoMap.put("rating", 0);
            Item item = new Item()
                .withPrimaryKey("id_livro", i, "fileNameLivro", nomeLivro)
                .withString("texto", texto)
                .withMap("info", infoMap);
            try {
                long startTime = System.currentTimeMillis();
                PutItemOutcome putItemOutcome = table.putItem(item);
                long finishTime = System.currentTimeMillis();
                long timeInsertItem = finishTime - startTime;
                totalInsertTime += timeInsertItem;
                contLivrosInseridos++;

                LOGGER.info(String.format("Item " + i));
                LOGGER.info("PutItem succeeded:\n" + putItemOutcome.getPutItemResult());
            } catch (Exception ex) {
                LOGGER.severe("Unable to add livro: " //+ i + " " + text
                );
                LOGGER.severe(ex.getMessage());
            }
        }
        timeFinishReadTXTAndWriteDB = System.currentTimeMillis();
        timeTotalReadTXTAndWireDB = timeFinishReadTXTAndWriteDB - timeStartReadTXTAndWriteDB;
        LOGGER.info(String.format("Livros lidos e inseridos: " + contLivrosInseridos));
        LOGGER.info(String.format("Tempo total de leitura dos livros nos arquivos TXT = " + timeTotalReadTXT + " ms"));
        LOGGER.info(String.format("Tempo médio de leitura dos livros nos arquivos TXT = " + timeTotalReadTXT / contLivrosInseridos + " ms"));
        LOGGER.info(String.format("Tempo total de inserção dos livros no DB = " + totalInsertTime + " ms"));
        LOGGER.info(String.format("Tempo médio de inserção dos livros no DB= " + totalInsertTime / contLivrosInseridos + " ms"));
        LOGGER.info(String.format("Tempo Total de leitura dos textos nos TXT e gravação no DB = " + timeTotalReadTXTAndWireDB + " ms"));
    }

    public static void InsertNewPalavra(String tableName) {
        int contPalavrasLidas = 0, contPalavrasInseridas = 0 , contLivrosLidos = 0;
        long totalInsertPalavrasTime = 0;
        long timeTotalReadTXT = 0, timeStartReadTXT = 0, timeFinishReadTXT = 0;
        long timeStartReadTXTAndWriteDB = 0, timeFinishReadTXTAndWriteDB = 0, timeTotalReadTXTAndWireDB = 0;
        long timeStartSplitPalavrasTexto = 0, timeFinishSplitPalavrasTexto = 0, timeTotalSplitPalavrasTexto = 0;
        int qtdeMaximaGravacaoRegistros = 40000;
        boolean temMaisRegistrosQueOLimitePermitido = false;


        timeStartReadTXTAndWriteDB = System.currentTimeMillis();

        DynamoDB dynamoDB = AmazonDynamoDBClientManagerr.getDynamoDB();
        Table table = dynamoDB.getTable(tableName);

        for (int i = 1; i < 15; i++) {
            if(temMaisRegistrosQueOLimitePermitido){
                break;
            }
            //System.out.println("Entrou no for");
            //301 é o número do ultimo livro que da no total 279 livros
            // - não possui todos pois na hora de baixar nao tinha todos os
            // livros no site é o numero do ultimo livro
            timeStartReadTXT = System.currentTimeMillis();
            String texto = LeituraTxtParaString.retornaTextoTXT(
                "src/main/java/com/mycompany/aws/dynamodb/java/LivrosTXT/Livro-" + i + ".txt");
            timeFinishReadTXT = System.currentTimeMillis();
            timeTotalReadTXT= timeFinishReadTXT - timeStartReadTXT;
            contLivrosLidos++;


            //TEMPO DE QUEBRA DAS PALAVRAS NO ARQUIVO
            timeStartSplitPalavrasTexto = System.currentTimeMillis();
            String[] palavrasTexto = LeituraPalavraAPalavraDoTXT.retornaPalavrasTXTComEspacoes(texto);
            timeFinishSplitPalavrasTexto = System.currentTimeMillis();
            timeTotalSplitPalavrasTexto = timeFinishSplitPalavrasTexto - timeStartSplitPalavrasTexto;
            contPalavrasLidas+= palavrasTexto.length;
            //System.out.println("contPalavrasLidas = " + contPalavrasLidas);


            //INSERÇÃO DAS PALAVRAS
            for(int j = 0 ; j < LeituraPalavraAPalavraDoTXT.retornaPalavrasTXTComEspacoes(texto).length ; j++){
                String nomeLivro = "Livro-" + i;
                //System.out.println(nomeLivro);

                //System.out.println("Palavra: " + palavrasTexto[j]);
                final Map<String, Object> infoMap = new HashMap<>();
                infoMap.put("plot", "Nothing happens at all.");
                infoMap.put("rating", 0);
                Item item = new Item()
                    .withPrimaryKey("id_palavra", j, "fileNameLivro", nomeLivro)
                    .withString("palavra", palavrasTexto[j])
                    .withMap("info", infoMap);
                try {
                    long startTime = System.currentTimeMillis();
                    PutItemOutcome putItemOutcome = table.putItem(item);
                    long finishTime = System.currentTimeMillis();
                    long timeInsertItem = finishTime - startTime;
                    totalInsertPalavrasTime += timeInsertItem;
                    contPalavrasInseridas++;
                    System.out.println("Livro "+i+ " - "+contPalavrasInseridas + " palavras inseridas  (Total de "+
                    palavrasTexto.length + " palavras!)"
                    );
                    if(contPalavrasInseridas >=qtdeMaximaGravacaoRegistros){
                        temMaisRegistrosQueOLimitePermitido = true;
                        break;
                    }

                    //LOGGER.info(String.format("Item " + i));
                    //LOGGER.info("PutItem succeeded:\n" + putItemOutcome.getPutItemResult());
                } catch (Exception ex) {
                    LOGGER.severe("Unable to add livro: " //+ i + " " + text
                    );
                    LOGGER.severe(ex.getMessage());
                }
            }

            if(temMaisRegistrosQueOLimitePermitido){
                break;
            }
        }

        timeFinishReadTXTAndWriteDB = System.currentTimeMillis();
        timeTotalReadTXTAndWireDB = timeFinishReadTXTAndWriteDB - timeStartReadTXTAndWriteDB;
        LOGGER.info(String.format("Palavras lidas e inseridas: " + contPalavrasInseridas));
        LOGGER.info(String.format("Tempo total de leitura do texto nos arquivos TXT = " + timeTotalReadTXT + " ms"));
        LOGGER.info(String.format("Tempo médio de leitura do texto nos arquivos TXT = " + timeTotalReadTXT/contLivrosLidos + " ms"));
        LOGGER.info(String.format("Tempo Total do Split das palavras dos arquivos TXT = " + timeTotalSplitPalavrasTexto + " ms"));
        LOGGER.info(String.format("Tempo médio do Split por palavras dos arquivos TXT = " + timeTotalSplitPalavrasTexto/contPalavrasLidas + " ms"));
        LOGGER.info(String.format("Tempo total de inserção das palavras no DB = " + totalInsertPalavrasTime + " ms"));
        LOGGER.info(String.format("Tempo médio de inserção das palavras no DB = " + totalInsertPalavrasTime / contPalavrasInseridas + " ms"));
        LOGGER.info(String.format("Tempo Total de leitura das palavras nos TXT e gravação no DB = " + timeTotalReadTXTAndWireDB + " ms"));

    }
}


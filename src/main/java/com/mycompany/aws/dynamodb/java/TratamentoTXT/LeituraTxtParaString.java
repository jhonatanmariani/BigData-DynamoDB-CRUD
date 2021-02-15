package com.mycompany.aws.dynamodb.java.TratamentoTXT;

import java.io.BufferedReader;
import java.io.FileReader;

public class LeituraTxtParaString {

    public static String retornaTextoTXT(String dirArquivo){
        String textoLivro = "";
        int contLinha= 0;
        try {
            BufferedReader buffRead = new BufferedReader(new FileReader(dirArquivo));
            String linha = "";
            long startTime = System.currentTimeMillis();
            while (true) {
                if (linha != null) {
                    //System.out.println("Linha = " + contLinha++);
                    //System.out.println(linha);
                } else{
                    break;
                }
                linha = buffRead.readLine();
                if(linha != null) {                               //ultima linha retorna null
                    textoLivro = textoLivro.concat(linha + '\n');
                }
            }
            buffRead.close();
        }catch(Exception e){ e.printStackTrace();}

        return textoLivro;
    }

    /*public static void main(String[] args) {
        for(int i = 1 ; i < 300 ; i++){        //400 é o numero do ultimo livro
            System.out.println("Texto encontrado do arquivo TXT: \n" + retornaTextoTXT("src/livros/Livro-"+i+".txt"));
        }
        //"src/Arquivos/arquivo.txt"
    }

     */
}

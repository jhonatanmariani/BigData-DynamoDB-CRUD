package com.mycompany.aws.dynamodb.java.TratamentoTXT;

import java.util.ArrayList;

public class LeituraPalavraAPalavraDoTXT {

    public static String[] retornaPalavrasTXTComEspacoes(String texto){
        //String textoTXT = LeituraTxtParaString.retornaTextoTXT(dirArquivo);
        //String[] palavrasArquivoTXT = textoTXT.split("[ .,;]");
        String[] palavrasArquivoTXT = texto.split(" +|\\.|\\,|\\;|\\'|\\-|\\[|\\]|\\(|\\)|\\*");
        return palavrasArquivoTXT;
    }



}

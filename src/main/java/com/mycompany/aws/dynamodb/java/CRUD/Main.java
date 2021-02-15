package com.mycompany.aws.dynamodb.java.CRUD;

import com.mycompany.aws.dynamodb.java.CRUD.CreateItem;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        //CreateTable.createTableLivros("Livros");
        //CreateItem.InsertNewText("Livros");
        //CreateTable.createTablePalavras("Palavras");
        //CreateItem.InsertNewPalavra("Palavras");
        //LIVRO1 -> 19.924 palavras
        //LIVRO2 ->  1.771 palavras
        //LIVRO3 ->  2.761 palavras
        //LIVRO4 ->  1.596 palavras
        //LIVRO5 ->  7.071 palavras
        //LIVRO6 ->  2.540 palavras
        //LIVRO7 ->  1.585 palavras
        //LIVRO8 ->  3.790 palavras
        //LIVRO9 ->  6.718 palavras
        ReadItem.queryText("Livros");
        //Crud.readItem("Livross");
        //Scan.scan("Livross");
        //PopulateTable.loadData();
    }
}

package com.mycompany.aws.dynamodb.java.CRUD;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.spec.ScanSpec;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class ReadItem {

    private static final Logger LOGGER = Logger.getLogger("ReadItem");

    public static void queryText(String tableName) {

        DynamoDB dynamoDB = AmazonDynamoDBClientManagerr.getDynamoDB();
        Table table = dynamoDB.getTable(tableName);


        int idLivro = 1;
        String fileNameLivro = "Livro-1";

        GetItemSpec spec = new GetItemSpec().withPrimaryKey(
            "id_livro", idLivro, "fileNameLivro", fileNameLivro
        );//, "title", title
        //LOGGER.info(String.format(table.getItem(spec).toString()));

        try {
            System.out.println("Attempting to read the item...");
            Item outcome = table.getItem(spec);
            //System.out.println(outcome);
            System.out.println("GetItem succeeded: " + outcome);

        }
        catch (Exception e) {
            System.err.println("Unable to read item: " + idLivro + " " );//+ fileName);
            System.err.println(e.getMessage());
        }

    }
}


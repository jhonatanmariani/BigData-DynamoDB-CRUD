/*
 * |-------------------------------------------------
 * | Copyright © 2019 Colin But. All rights reserved.
 * |-------------------------------------------------
 */
package com.mycompany.aws.dynamodb.java.CRUD;

import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public final class CreateTable {

    private static final Logger LOGGER = Logger.getLogger("CreateTable");
    private CreateTable(){}

    public static void createTableLivros(String tableName) {
        String hashKey = "id_livro";
        String rangeKey = "fileNameLivro";

        try {
            LOGGER.info("Attempting to create table; please wait...");

            List<KeySchemaElement> keySchemaElements = Arrays.asList(new KeySchemaElement(hashKey, KeyType.HASH),
                new KeySchemaElement(rangeKey, KeyType.RANGE)//,
                //new KeySchemaElement(atribute, KeyType.S)
            );

            List<AttributeDefinition> attributeDefinitions = Arrays.asList(new AttributeDefinition(hashKey, ScalarAttributeType.N),
                new AttributeDefinition(rangeKey, ScalarAttributeType.S)
                //new AttributeDefinition("texto", ScalarAttributeType.S)
            );

            //long startTime= System.currentTimeMillis();

            Table table = AmazonDynamoDBClientManagerr.getDynamoDB().createTable(tableName, keySchemaElements,attributeDefinitions,
                new ProvisionedThroughput(300L, 300L));
            //long finishTime = System.currentTimeMillis();
            //long timeCreateTable = finishTime - startTime;
            //LOGGER.info(String.format("Tempo de criação de tabela  = " + timeCreateTable + " ms"));

            table.waitForActive();

            LOGGER.info(String.format("Success... Created table %s, table status: %s", table.getTableName(), table.getDescription()));

        } catch (Exception ex) {
            LOGGER.severe("Unable to create table: ");
            LOGGER.severe(ex.getMessage());
        }
    }


    public static void createTablePalavras(String tableName) {
        String hashKey = "id_palavra";
        String rangeKey = "fileNameLivro";

        try {
            LOGGER.info("Attempting to create table; please wait...");

            List<KeySchemaElement> keySchemaElements = Arrays.asList(new KeySchemaElement(hashKey, KeyType.HASH),
                new KeySchemaElement(rangeKey, KeyType.RANGE)//,
                //new KeySchemaElement(atribute, KeyType.S)
            );

            List<AttributeDefinition> attributeDefinitions = Arrays.asList(new AttributeDefinition(hashKey, ScalarAttributeType.N),
                new AttributeDefinition(rangeKey, ScalarAttributeType.S)
                //new AttributeDefinition("texto", ScalarAttributeType.S)
            );

            //long startTime= System.currentTimeMillis();

            Table table = AmazonDynamoDBClientManagerr.getDynamoDB().createTable(tableName, keySchemaElements,attributeDefinitions,
                new ProvisionedThroughput(40000L, 40000L));
            //long finishTime = System.currentTimeMillis();
            //long timeCreateTable = finishTime - startTime;
            //LOGGER.info(String.format("Tempo de criação de tabela  = " + timeCreateTable + " ms"));

            table.waitForActive();

            LOGGER.info(String.format("Success... Created table %s, table status: %s", table.getTableName(), table.getDescription()));

        } catch (Exception ex) {
            LOGGER.severe("Unable to create table: ");
            LOGGER.severe(ex.getMessage());
        }
    }
}

package livro;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "Livros")
public class Livro {

    @DynamoDBHashKey(attributeName = "id")
    private int id;

    @DynamoDBAttribute(attributeName = "texto")
    private String texto;

    public void setId(int id){
        this.id = id;
    }

    public void setTexto(String texto){
        this.texto = texto;
    }
}

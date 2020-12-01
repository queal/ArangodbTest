package per.queal.driver;

import com.arangodb.ArangoDB;
import com.arangodb.ArangoDBException;

public class TestCreateDataBase {

    public static void main(String[] args) {

        ArangoDB arangoDB = new ArangoDB.Builder().host("127.0.0.1", 8529).build();

        String dbName = "root-cause";
        try {
            arangoDB.createDatabase(dbName);
            System.out.println("Database created: " + dbName);

            arangoDB.shutdown();
        } catch (ArangoDBException e) {
            System.err.println("Failed to create database: " + dbName + "; " + e.getMessage());
        }


    }
}

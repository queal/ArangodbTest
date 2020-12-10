package per.queal.driver;

import com.arangodb.ArangoDB;
import com.arangodb.ArangoDBException;
import com.arangodb.entity.CollectionEntity;
import com.arangodb.entity.CollectionType;
import com.arangodb.entity.KeyOptions;
import com.arangodb.entity.KeyType;
import com.arangodb.model.CollectionCreateOptions;
import per.queal.pojo.Cause;
import per.queal.pojo.CauseNoExt;
import per.queal.pojo.VInstanceMetric;

public class TestCreateSchema {

    public static void main(String[] args) {

        ArangoDB arangoDB = new ArangoDB.Builder().host("127.0.0.1", 8529).build();
        String dbName = "root-cause";

        try {
            if (!arangoDB.db(dbName).collection(VInstanceMetric.label).exists()) {
                CollectionEntity vInstanceMetricCollection = arangoDB.db(dbName).createCollection(VInstanceMetric.label,
                        new CollectionCreateOptions());
                System.out.println("Collection created: " + vInstanceMetricCollection.getName());
            }

            if (!arangoDB.db(dbName).collection(Cause.label).exists()) {
                CollectionEntity causeCollection = arangoDB.db(dbName).createCollection(Cause.label,
                        new CollectionCreateOptions()
                                .type(CollectionType.EDGES));
                System.out.println("Collection created: " + causeCollection.getName());
            }

            if (!arangoDB.db(dbName).collection(CauseNoExt.label).exists()) {
                CollectionEntity causeCollection = arangoDB.db(dbName).createCollection(CauseNoExt.label,
                        new CollectionCreateOptions()
                                .type(CollectionType.EDGES));
                System.out.println("Collection created: " + causeCollection.getName());
            }

            arangoDB.shutdown();
        } catch (ArangoDBException e) {
            System.err.println("Failed to create collection: " + e.getMessage());
        }


    }
}

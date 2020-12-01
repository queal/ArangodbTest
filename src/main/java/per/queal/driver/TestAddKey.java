package per.queal.driver;

import com.arangodb.*;
import com.arangodb.entity.DocumentCreateEntity;
import per.queal.pojo.Cause;
import per.queal.pojo.VInstanceMetric;

public class TestAddKey {

    public static void main(String[] args) {

        ArangoDB arangoDB = new ArangoDB.Builder().host("127.0.0.1", 8529).build();
        String dbName = "root-cause";
        String graphName = "RootCauseGraph";

        try {
            ArangoGraph graph = arangoDB.db(dbName).graph(graphName);

            ArangoCollection instanceMetricCollection = arangoDB.db(dbName).collection(VInstanceMetric.label);
            instanceMetricCollection.truncate();
            ArangoCollection causeCollection = arangoDB.db(dbName).collection(Cause.label);
            causeCollection.truncate();

            long start = System.currentTimeMillis();
            for (int i = 0; i < 1 ; i++) {
                long _start = System.currentTimeMillis();
                DocumentCreateEntity<VInstanceMetric> fromEntity = instanceMetricCollection.insertDocument(VInstanceMetric.gen());
                DocumentCreateEntity<VInstanceMetric> toEntity = instanceMetricCollection.insertDocument(VInstanceMetric.gen());

                ArangoEdgeCollection causeArangoCollection = graph.edgeCollection(Cause.label);
                causeArangoCollection.insertEdge(Cause.gen(fromEntity.getId(), toEntity.getId()));
                System.out.println(System.currentTimeMillis() - _start);
            }
            System.out.println(System.currentTimeMillis() - start);


        } catch (ArangoDBException e) {
            e.printStackTrace();
        } finally {
            arangoDB.shutdown();
        }


    }
}

package per.queal.driver;

import com.arangodb.*;
import com.arangodb.entity.CollectionEntity;
import com.arangodb.entity.DocumentCreateEntity;
import com.arangodb.model.EdgeCreateOptions;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import per.queal.pojo.Cause;
import per.queal.pojo.VInstanceMetric;

import java.util.List;

public class TestAdd {

    public static void main(String[] args) {

        ArangoDB arangoDB = new ArangoDB.Builder().host("127.0.0.1", 8529).build();
        String dbName = "root-cause";
        String graphName = "RootCauseGraph";

        try {
            ArangoDatabase db = arangoDB.db(dbName);
            ArangoGraph graph = arangoDB.db(dbName).graph(graphName);

            ArangoCollection instanceMetricCollection = arangoDB.db(dbName).collection(VInstanceMetric.label);
            instanceMetricCollection.truncate();
            ArangoCollection causeCollection = arangoDB.db(dbName).collection(Cause.label);
            causeCollection.truncate();

            long start = System.currentTimeMillis();
            for (int i = 0; i < 10; i++) {
                long _start = System.currentTimeMillis();
                DocumentCreateEntity<VInstanceMetric> fromEntity = instanceMetricCollection.insertDocument(VInstanceMetric.gen());
                DocumentCreateEntity<VInstanceMetric> toEntity = instanceMetricCollection.insertDocument(VInstanceMetric.gen());

                ArangoEdgeCollection causeArangoCollection = graph.edgeCollection(Cause.label);
                causeArangoCollection.insertEdge(Cause.gen(fromEntity.getId(), toEntity.getId()));
                System.out.println(System.currentTimeMillis() - _start);
            }
            System.out.println(System.currentTimeMillis() - start);

            arangoDB.shutdown();
        } catch (ArangoDBException e) {
            e.printStackTrace();
        }


    }
}

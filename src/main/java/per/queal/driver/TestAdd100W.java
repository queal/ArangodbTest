package per.queal.driver;

import com.arangodb.*;
import com.arangodb.entity.DocumentCreateEntity;
import per.queal.pojo.Cause;
import per.queal.pojo.VInstanceMetric;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TestAdd100W {

    public static void main(String[] args) {

        Executor executor = Executors.newFixedThreadPool(32);

//        int count = 10000 * 1000;
        int count = 10;
        CountDownLatch latch = new CountDownLatch(count);

        ArangoDB arangoDB = new ArangoDB.Builder().host("127.0.0.1", 8529).build();

        String dbName = "root-cause";
        String graphName = "RootCauseGraph";

        try {
            ArangoDatabase db = arangoDB.db(dbName);
            ArangoGraph graph = arangoDB.db(dbName).graph(graphName);

            ArangoCollection instanceMetricCollection = arangoDB.db(dbName).collection(VInstanceMetric.label);
//            instanceMetricCollection.truncate();
            ArangoCollection causeCollection = arangoDB.db(dbName).collection(Cause.label);
//            causeCollection.truncate();

            long start = System.currentTimeMillis();
            for (int i = 0; i < count; i++) {
                executor.execute(() -> {
                    long _start = System.currentTimeMillis();
                    DocumentCreateEntity<VInstanceMetric> fromEntity = instanceMetricCollection.insertDocument(VInstanceMetric.gen());
                    DocumentCreateEntity<VInstanceMetric> toEntity = instanceMetricCollection.insertDocument(VInstanceMetric.gen());

                    ArangoEdgeCollection causeArangoCollection = graph.edgeCollection(Cause.label);
                    causeArangoCollection.insertEdge(Cause.gen(fromEntity.getId(), toEntity.getId()));
                    System.out.println(Thread.currentThread().toString() + (System.currentTimeMillis() - _start));
                    latch.countDown();
                });
            }

            latch.await();
            System.out.println(System.currentTimeMillis() - start);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            arangoDB.shutdown();
        }
    }
}

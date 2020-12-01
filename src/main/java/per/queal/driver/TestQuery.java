package per.queal.driver;

import com.arangodb.*;
import com.arangodb.entity.BaseDocument;
import com.arangodb.entity.DocumentCreateEntity;
import com.arangodb.util.MapBuilder;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import per.queal.pojo.Cause;
import per.queal.pojo.VInstanceMetric;

import java.util.List;
import java.util.Map;

public class TestQuery {

    public static void main(String[] args) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        ArangoDB arangoDB = new ArangoDB.Builder().host("127.0.0.1", 8529).build();
        String dbName = "root-cause";
        String graphName = "RootCauseGraph";

        try {
            ArangoGraph graph = arangoDB.db(dbName).graph(graphName);

            ArangoCollection instanceMetricCollection = arangoDB.db(dbName).collection(VInstanceMetric.label);
            ArangoCollection causeCollection = arangoDB.db(dbName).collection(Cause.label);

            System.out.println(instanceMetricCollection.count().getCount());
            System.out.println(causeCollection.count().getCount());

            ArangoDatabase db = graph.db();

            {
                long start = System.currentTimeMillis();
                System.out.println("-------------------------------------------------------");
                Long size = db.query("FOR t IN Cause FILTER t.status == 0 COLLECT WITH COUNT INTO length RETURN length", Long.class).next();
                System.out.println("Cause count: " + size);
                System.out.println("Cause cost: " + (System.currentTimeMillis() - start));
            }

            {
                long start = System.currentTimeMillis();
                System.out.println("-------------------------------------------------------");
                Long size = db.query("FOR t IN VInstanceMetric\n" +
                        "    FILTER t.metric == 'cpu'\n" +
                        "    COLLECT WITH COUNT INTO length\n" +
                        "RETURN length", Long.class).next();
                System.out.println("VInstanceMetric count: " + size);
                System.out.println("VInstanceMetric cost: " + (System.currentTimeMillis() - start));

            }

            arangoDB.shutdown();
        } catch (ArangoDBException e) {
            e.printStackTrace();
        }


    }
}

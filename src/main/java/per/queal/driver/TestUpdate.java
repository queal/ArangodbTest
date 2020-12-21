package per.queal.driver;

import com.alibaba.fastjson.JSON;
import com.arangodb.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.RandomUtils;
import per.queal.pojo.Cause;
import per.queal.pojo.VInstanceMetric;

public class TestUpdate {

    public static void main(String[] args) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        ArangoDB arangoDB = new ArangoDB.Builder().host("127.0.0.1", 8529).build();
        String dbName = "root-cause";
        String graphName = "RootCauseGraph";

        try {
            ArangoGraph graph = arangoDB.db(dbName).graph(graphName);

            ArangoCollection instanceMetricCollection = arangoDB.db(dbName).collection(VInstanceMetric.label);
            ArangoEdgeCollection causeCollection = graph.edgeCollection(Cause.label);

            String key = "16668032";
            Cause causeInDB = causeCollection.getEdge(key, Cause.class);
            System.out.println("query: " + JSON.toJSONString(causeInDB));
            causeInDB.setConfidence(RandomUtils.nextFloat(0, 1));
            System.out.println("update: " + JSON.toJSONString(causeInDB));
            causeCollection.updateEdge(key, causeInDB);

        } catch (ArangoDBException e) {
            e.printStackTrace();
        } finally {
            arangoDB.shutdown();
        }

    }
}

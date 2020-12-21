package per.queal.driver;

import com.alibaba.fastjson.JSON;
import com.arangodb.*;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import per.queal.pojo.Cause;
import per.queal.pojo.VInstanceMetric;

import java.util.List;

public class TestQueryById {

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

            Cause c = graph.edgeCollection(Cause.label).getEdge("Cause/16624487", Cause.class);
            Cause c1 = graph.edgeCollection(Cause.label).getEdge("16624487", Cause.class);
            System.out.println(JSON.toJSONString(c));
            System.out.println(JSON.toJSONString(c1));

        } catch (ArangoDBException e) {
            e.printStackTrace();
        } finally {
            arangoDB.shutdown();
        }

    }
}

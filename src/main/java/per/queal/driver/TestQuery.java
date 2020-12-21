package per.queal.driver;

import com.alibaba.fastjson.JSON;
import com.arangodb.*;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import per.queal.pojo.Cause;
import per.queal.pojo.VInstanceMetric;

import java.util.List;

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
                Long size = db.query("FOR t IN Cause COLLECT WITH COUNT INTO length RETURN length", Long.class).next();
                System.out.println("Cause count: " + size);
                System.out.println("Cause cost: " + (System.currentTimeMillis() - start));
            }

            {
                long start = System.currentTimeMillis();
                System.out.println("-------------------------------------------------------");
                Long size = db.query("FOR t IN VInstanceMetric\n" +
                        "    " +
                        "    COLLECT WITH COUNT INTO length\n" +
                        "RETURN length", Long.class).next();
                System.out.println("VInstanceMetric count: " + size);
                System.out.println("VInstanceMetric cost: " + (System.currentTimeMillis() - start));
            }

            {
//                long start = System.currentTimeMillis();
//                System.out.println("-------------------------------------------------------");
//                ArangoCursor<VInstanceMetric> result = db.query("FOR v IN VInstanceMetric " +
//                                "                        FOR inV, e IN OUTBOUND v Cause" +
//                                "                        RETURN v", VInstanceMetric.class);
//                List<VInstanceMetric> list = Lists.newArrayList();
//                while(result.hasNext()){
//                    list.add(result.next());
//                }
//
//                System.out.println("VInstanceMetric : " + JSON.toJSONString(list));
//                System.out.println("VInstanceMetric cost: " + (System.currentTimeMillis() - start));

            }

        } catch (ArangoDBException e) {
            e.printStackTrace();
        } finally {
            arangoDB.shutdown();
        }

    }
}

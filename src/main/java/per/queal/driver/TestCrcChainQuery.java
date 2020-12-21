package per.queal.driver;

import com.alibaba.fastjson.JSON;
import com.arangodb.*;
import com.arangodb.entity.AqlParseEntity;
import com.arangodb.entity.DocumentCreateEntity;
import com.arangodb.velocypack.VPackSlice;
import com.google.gson.Gson;
import per.queal.pojo.Cause;
import per.queal.pojo.VInstanceMetric;

public class TestCrcChainQuery {

    public static void main(String[] args) {

        ArangoDB arangoDB = new ArangoDB.Builder().host("127.0.0.1", 8529).build();
        String dbName = "root-cause";
        String graphName = "RootCauseGraph";

        try {
            ArangoGraph graph = arangoDB.db(dbName).graph(graphName);
            VPackSlice result = graph.db().query("FOR outV IN VInstanceMetric\n" +
                    "    FOR inV, c, p IN 1..1 OUTBOUND outV Cause \n" +
                    "  FILTER \n" +
                    "    c.status == 0\n" +
                    "    COLLECT WITH COUNT INTO length\n" +
                    "RETURN length", VPackSlice.class).next();

            System.out.println(result);

//            result = graph.db().query("FOR outV IN VInstanceMetric\n" +
//                     "FOR inV, c, p IN OUTBOUND outV Cause \n" +
//                     "FILTER \n" +
//                     "    outV.name == 'InstanceMetricGO4LmqUlKzAGqUGvpUHslguo2xiOWNhp' \n" +
//                     "    and inV.name == 'InstanceMetricIWYLQOWadBWETuLgx72rWYhbzZaDR4Ai'\n" +
//                     "    and c.status == 0\n" +
//                     "LIMIT 0,10\n" +
//                     "RETURN {inV, outV, c, p}", VPackSlice.class).next();
//
//            result.get("inV");
//            System.out.println(result);

            Integer count = graph.db().query("WITH VInstanceMetric\n" +
                    "    FOR v, e IN OUTBOUND SHORTEST_PATH 'VInstanceMetric/16585194' TO 'VInstanceMetric/16585186' Cause\n" +
                    "    FILTER e.status == 0\n" +
                    "    COLLECT WITH COUNT INTO length \n" +
                    "RETURN length", Integer.class).next();
            System.out.println("count: " + count);


        } catch (ArangoDBException e) {
            e.printStackTrace();
        } finally {
            arangoDB.shutdown();
        }


    }
}

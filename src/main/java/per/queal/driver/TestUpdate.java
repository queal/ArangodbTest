package per.queal.driver;

import com.alibaba.fastjson.JSON;
import com.arangodb.*;
import com.arangodb.velocypack.VPackSlice;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.RandomUtils;
import per.queal.pojo.Cause;
import per.queal.pojo.VInstanceMetric;

import java.util.Map;

public class TestUpdate {

    public static void main(String[] args) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        ArangoDB arangoDB = new ArangoDB.Builder().host("127.0.0.1", 8529).build();
        String dbName = "root-cause";
        String graphName = "RootCauseGraph";

        try {

            ArangoGraph graph = arangoDB.db(dbName).graph(graphName);
            System.out.println(graph.exists());

            ArangoCollection instanceMetricCollection = arangoDB.db(dbName).collection(VInstanceMetric.label);
            ArangoEdgeCollection causeCollection = graph.edgeCollection(Cause.label);

            String key = "23690505";

            Cause cause = causeCollection.getEdge(key, Cause.class);
            System.out.println(JSON.toJSONString(cause));

            cause.setConfidence(RandomUtils.nextFloat(0, 1));
//            cause.setConfidence(null);
//            causeCollection.updateEdge(key, graph.db().util().serialize(cause));
            causeCollection.updateEdge(key, cause);

            System.out.println(JSON.toJSONString(causeCollection.getEdge(key, Cause.class)));

        } catch (ArangoDBException e) {
            e.printStackTrace();
        } finally {
            arangoDB.shutdown();
        }

    }
}

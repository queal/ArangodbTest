package per.queal.driver;

import com.arangodb.*;
import com.arangodb.entity.DocumentCreateEntity;
import per.queal.pojo.Cause;
import per.queal.pojo.VInstanceMetric;

public class TestAdd123 {

    public static void main(String[] args) {
//        String host = "192.168.1.1";
        String host = "127.0.0.1";
        Integer port = 8529;
        ArangoDB arangoDB = null;
        try {
            arangoDB = new ArangoDB.Builder().host(host, port).timeout(3000).build();
            String dbName = "root-cause";
            String graphName = "RootCauseGraph";

            ArangoDatabase db = arangoDB.db(dbName);
            db.exists();

            ArangoGraph graph = db.graph(graphName);
            graph.exists();

            ArangoCollection instanceMetricCollection = arangoDB.db(dbName).collection(VInstanceMetric.label);
            ArangoCollection causeCollection = arangoDB.db(dbName).collection(Cause.label);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (arangoDB != null) {
                arangoDB.shutdown();
            }
        }


    }
}

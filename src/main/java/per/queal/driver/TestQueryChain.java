package per.queal.driver;

import com.arangodb.*;
import com.arangodb.entity.DocumentCreateEntity;
import per.queal.pojo.Cause;
import per.queal.pojo.VInstanceMetric;

import java.util.List;

public class TestQueryChain {

    public static void main(String[] args) {

        ArangoDB arangoDB = new ArangoDB.Builder().host("127.0.0.1", 8529).build();
        String dbName = "root-cause";
        String graphName = "RootCauseGraph";

        try {
            ArangoDatabase db = arangoDB.db(dbName);


            ArangoCursor<List> cursor = db.query("WITH VInstanceMetric\n" +
                    "\n" +
                    "LET outVs = (\n" +
                    "    FOR v IN VInstanceMetric\n" +
                    "        LET vids = (\n" +
                    "            FOR c IN Cause\n" +
                    "        \tRETURN c._to\n" +
                    "    \t)\n" +
                    "        \n" +
                    "        FILTER !CONTAINS(vids, v._id)\n" +
                    "    RETURN v\n" +
                    ")\n" +
                    "\n" +
                    "LET inVs = (\n" +
                    "    FOR v IN VInstanceMetric\n" +
                    "        LET vids = (\n" +
                    "            FOR c IN Cause\n" +
                    "        \tRETURN c._from\n" +
                    "    \t)\n" +
                    "        \n" +
                    "        FILTER !CONTAINS(vids, v._id)\n" +
                    "    RETURN v\n" +
                    ")\n" +
                    "\n" +
                    "FOR outV IN outVs\n" +
                    "    FOR inV, e, p IN 1..10 OUTBOUND outV Cause\n" +
                    "    FILTER inV in inVs\n" +
                    "RETURN  p.vertices[*].instanceId", List.class);

            while (cursor.hasNext()) {
                List s = cursor.next();
                System.out.println(s);
            }


        } catch (ArangoDBException e) {
            e.printStackTrace();
        } finally {
            arangoDB.shutdown();
        }

    }
}

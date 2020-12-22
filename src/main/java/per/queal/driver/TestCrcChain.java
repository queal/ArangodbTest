package per.queal.driver;

import com.arangodb.*;
import com.arangodb.entity.DocumentCreateEntity;
import com.arangodb.entity.EdgeEntity;
import com.arangodb.entity.QueryCachePropertiesEntity;
import com.arangodb.entity.VertexEntity;
import com.arangodb.model.TransactionOptions;
import per.queal.pojo.Cause;
import per.queal.pojo.VInstanceMetric;

public class TestCrcChain {

    public static void main(String[] args) {

        ArangoDB arangoDB = new ArangoDB.Builder().host("127.0.0.1", 8529).build();
        String dbName = "root-cause";
        String graphName = "RootCauseGraph";

        try {
            ArangoGraph graph = arangoDB.db(dbName).graph(graphName);

            ArangoCollection instanceMetricCollection = arangoDB.db(dbName).collection(VInstanceMetric.label);
            instanceMetricCollection.truncate();
            ArangoCollection causeCollection = arangoDB.db(dbName).collection(Cause.label);
            causeCollection.truncate();

            for (int i = 0; i < 1; i++) {

                ArangoVertexCollection vertexCollection = graph.vertexCollection(VInstanceMetric.label);
                VertexEntity v1 = vertexCollection.insertVertex(VInstanceMetric.gen());
                VertexEntity v2 = vertexCollection.insertVertex(VInstanceMetric.gen());
                VertexEntity v3 = vertexCollection.insertVertex(VInstanceMetric.gen());
                VertexEntity v4 = vertexCollection.insertVertex(VInstanceMetric.gen());
                VertexEntity v5 = vertexCollection.insertVertex(VInstanceMetric.gen());
                VertexEntity v6 = vertexCollection.insertVertex(VInstanceMetric.gen());

                ArangoEdgeCollection causeArangoCollection = graph.edgeCollection(Cause.label);
                causeArangoCollection.insertEdge(Cause.gen(v1.getId(), v2.getId()));
                causeArangoCollection.insertEdge(Cause.gen(v2.getId(), v3.getId()));
                causeArangoCollection.insertEdge(Cause.gen(v3.getId(), v4.getId()));
                causeArangoCollection.insertEdge(Cause.gen(v4.getId(), v5.getId()));
                causeArangoCollection.insertEdge(Cause.gen(v3.getId(), v6.getId()));
//                causeArangoCollection.insertEdge(Cause.gen(v6.getId(), v7.getId()));
//                causeArangoCollection.insertEdge(Cause.gen(v5.getId(), v1.getId()));

            }

        } catch (ArangoDBException e) {
            e.printStackTrace();
        } finally {
            arangoDB.shutdown();
        }

    }
}

package per.queal.driver;

import com.alibaba.fastjson.JSON;
import com.arangodb.*;
import com.arangodb.entity.DocumentCreateEntity;
import com.arangodb.entity.DocumentField;
import com.arangodb.entity.EdgeEntity;
import com.arangodb.util.ArangoSerializer;
import com.arangodb.velocypack.VPackModule;
import com.arangodb.velocypack.VPackSetupContext;
import com.arangodb.velocypack.VPackSlice;
import com.arangodb.velocypack.ValueType;
import com.google.common.collect.Maps;
import per.queal.pojo.CauseNoExt;
import per.queal.pojo.VInstanceMetric;

import java.util.Map;

public class TestAddWithNoExt2 {

    public static void main(String[] args) {
        String dbName = "root-cause";
        String graphName = "RootCauseGraph";

        ArangoDB arangoDB = new ArangoDB.Builder().host("127.0.0.1", 8529).build();

        try {
            ArangoGraph graph = arangoDB.db(dbName).graph(graphName);

            ArangoCollection instanceMetricCollection = arangoDB.db(dbName).collection(VInstanceMetric.label);
            instanceMetricCollection.truncate();
            ArangoCollection causeCollection = arangoDB.db(dbName).collection(CauseNoExt.label);
            causeCollection.truncate();

            long start = System.currentTimeMillis();
            for (int i = 0; i < 1; i++) {
                long _start = System.currentTimeMillis();
                DocumentCreateEntity<VInstanceMetric> fromEntity = instanceMetricCollection.insertDocument(VInstanceMetric.gen());
                DocumentCreateEntity<VInstanceMetric> toEntity = instanceMetricCollection.insertDocument(VInstanceMetric.gen());

                ArangoEdgeCollection causeArangoCollection = graph.edgeCollection(CauseNoExt.label);
                CauseNoExt causeNoExt = CauseNoExt.gen(fromEntity.getId(), toEntity.getId());

                Map addFields = Maps.newHashMap();
                addFields.put(DocumentField.Type.FROM.getSerializeName(), causeNoExt.getFrom());
                addFields.put(DocumentField.Type.TO.getSerializeName(), causeNoExt.getTo());
                VPackSlice vPackSlice = graph.db().util().serialize(causeNoExt, new ArangoSerializer.Options().additionalFields(addFields));

                EdgeEntity causeInGraph = causeArangoCollection.insertEdge(vPackSlice);
                System.out.println(System.currentTimeMillis() - _start);

                CauseNoExt c = causeArangoCollection.getEdge(causeInGraph.getKey(), CauseNoExt.class);
                System.out.println(JSON.toJSONString(c));

            }
            System.out.println(System.currentTimeMillis() - start);
        } catch (ArangoDBException e) {
            e.printStackTrace();
        } finally {
            arangoDB.shutdown();
        }



    }
}

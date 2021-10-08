package per.queal.driver;

import com.arangodb.*;
import com.arangodb.entity.DocumentCreateEntity;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import per.queal.pojo.Cause;
import per.queal.pojo.VInstanceMetric;

import java.util.List;
import java.util.Map;

public class TestAddCollection {

    public static void main(String[] args) {

        ArangoDB arangoDB = new ArangoDB.Builder().host("127.0.0.1", 8529).build();
        String dbName = "root-cause";

        try {
            ArangoDatabase db = arangoDB.db(dbName);

            ArangoCollection instanceMetricCollection = arangoDB.db(dbName).collection(VInstanceMetric.label);
            instanceMetricCollection.truncate();
            ArangoCollection causeCollection = arangoDB.db(dbName).collection(Cause.label);
            causeCollection.truncate();

            List<Map> causeList = Lists.newArrayList();

            long start = System.currentTimeMillis();
            for (int i = 0; i < 1 * 10000; i++) {
                DocumentCreateEntity<VInstanceMetric> fromEntity = instanceMetricCollection.insertDocument(VInstanceMetric.gen());
                DocumentCreateEntity<VInstanceMetric> toEntity = instanceMetricCollection.insertDocument(VInstanceMetric.gen());

                Map<String, Object> props = Maps.newHashMap();
                props.put("_from", fromEntity.getId());
                props.put("_to", toEntity.getId());
                causeList.add(props);
            }

            ArangoCollection causeArangoCollection = db.collection(Cause.label);
            causeArangoCollection.insertDocuments(causeList);
            System.out.println(System.currentTimeMillis() - start);

            arangoDB.shutdown();
        } catch (ArangoDBException e) {
            e.printStackTrace();
        }


    }
}

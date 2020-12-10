package per.queal.driver;

import com.arangodb.ArangoCollection;
import com.arangodb.ArangoDB;
import com.arangodb.ArangoDBException;
import com.arangodb.entity.DocumentCreateEntity;
import com.arangodb.entity.EdgeDefinition;
import com.arangodb.model.GraphCreateOptions;
import com.google.common.collect.Lists;
import per.queal.pojo.Cause;
import per.queal.pojo.CauseNoExt;
import per.queal.pojo.VInstanceMetric;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class TestCreateGraph {

    public static void main(String[] args) {

        ArangoDB arangoDB = new ArangoDB.Builder().host("127.0.0.1", 8529).build();
        String dbName = "root-cause";
        String graphName = "RootCauseGraph";

        try {
            if (!arangoDB.db(dbName).graph(graphName).exists()) {
                List<EdgeDefinition> edgeDefinitionList = Lists.newArrayList();

                {
                    EdgeDefinition edgeDefinition = new EdgeDefinition();
                    edgeDefinition
                            .collection(Cause.label)
                            .from(VInstanceMetric.label)
                            .to(VInstanceMetric.label);
                    edgeDefinitionList.add(edgeDefinition);
                }

                {
                    EdgeDefinition edgeDefinition = new EdgeDefinition();
                    edgeDefinition
                            .collection(CauseNoExt.label)
                            .from(VInstanceMetric.label)
                            .to(VInstanceMetric.label);
                    edgeDefinitionList.add(edgeDefinition);
                }

                arangoDB.db(dbName).createGraph(graphName, edgeDefinitionList);
                System.out.println("init graph success");
            } else {
                System.out.println("graph exists");
            }


            arangoDB.shutdown();

        } catch (ArangoDBException e) {
            e.printStackTrace();
        }


    }
}

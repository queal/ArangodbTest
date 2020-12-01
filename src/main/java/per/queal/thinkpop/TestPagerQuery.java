package per.queal.thinkpop;

import com.arangodb.tinkerpop.gremlin.utils.ArangoDBConfigurationBuilder;
import org.apache.commons.configuration.BaseConfiguration;
import org.apache.tinkerpop.gremlin.process.traversal.Order;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.structure.util.GraphFactory;
import per.queal.pojo.Cause;
import per.queal.pojo.VInstanceMetric;

public class TestPagerQuery {

    public static void main(String[] args) {
        Graph graph = null;
        GraphTraversalSource gts = null;
        long start = System.currentTimeMillis();

        try {
            String dbName = "root-cause";
            String graphName = "janusgraph";

            ArangoDBConfigurationBuilder builder = new ArangoDBConfigurationBuilder();
            BaseConfiguration conf = builder
                    .arangoHosts("127.0.0.1:8529")
                    .arangoUser("root")
                    .arangoPassword("")
                    .dataBase(dbName)
                    .graph(graphName)
                    .withVertexCollection(VInstanceMetric.label)
                    .withEdgeCollection(Cause.label)
                    .configureEdge(Cause.label, VInstanceMetric.label, VInstanceMetric.label)
                    .build();

            graph = GraphFactory.open(conf);
            gts = new GraphTraversalSource(graph);

            GraphTraversalSource g = gts.clone();

            g = gts.clone();
            start = System.currentTimeMillis();
            g.E().hasLabel(Cause.label).order().by("_from").range(1000, 1020).next();
            System.out.println(System.currentTimeMillis() - start);


        } finally {
            try {
                if (gts != null) {
                    gts.close();
                }
                if (graph != null) {
                    graph.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

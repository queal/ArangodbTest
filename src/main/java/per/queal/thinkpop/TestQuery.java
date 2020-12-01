package per.queal.thinkpop;

import com.arangodb.tinkerpop.gremlin.utils.ArangoDBConfigurationBuilder;
import org.apache.commons.configuration.BaseConfiguration;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.structure.util.GraphFactory;
import per.queal.pojo.Cause;
import per.queal.pojo.VInstanceMetric;

public class TestQuery {

    public static void main(String[] args) {
        Graph graph = null;
        GraphTraversalSource gts = null;

        try {
            String dbName = "root-cause";
            String graphName = "janusgraph";

            ArangoDBConfigurationBuilder builder = new ArangoDBConfigurationBuilder();
            BaseConfiguration conf = builder
                    .arangoHosts("127.0.0.1:8529")
                    .arangoUser("root")
                    .arangoPassword("")
                    .arangoMaxConnections(32)
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
            long start = System.currentTimeMillis();
            System.out.println("Cause count: "  + g.E().hasLabel(Cause.label).has("status", 0).count().next());
            System.out.println("Cause cost: " + (System.currentTimeMillis() - start));

            g = gts.clone();
            start = System.currentTimeMillis();
            System.out.println("V count: "  + g.V().hasLabel(VInstanceMetric.label).has("metric", "io").count().next());
            System.out.println("V cost: "  + (System.currentTimeMillis() - start));
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

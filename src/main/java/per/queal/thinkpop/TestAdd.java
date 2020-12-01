package per.queal.thinkpop;

import com.arangodb.tinkerpop.gremlin.utils.ArangoDBConfigurationBuilder;
import org.apache.commons.configuration.BaseConfiguration;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.structure.util.GraphFactory;
import per.queal.pojo.Cause;
import per.queal.pojo.VInstanceMetric;

public class TestAdd {

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

            // add V and E
            g = gts.clone();
            long start = System.currentTimeMillis();
            for (int i = 0; i < 10000 * 10; i++) {
                long _start = System.currentTimeMillis();
                Vertex v1 = VInstanceMetric.gen().toVertex(g);
                Vertex v2 = VInstanceMetric.gen().toVertex(g);

                Cause cause = Cause.gen();
                g.addE(Cause.label)
                        .property("confidence", cause.getConfidence())
                        .property("isManual", cause.getManual())
                        .property("status", cause.getStatus())
                        .from(v1).to(v2).next();

                System.out.println(System.currentTimeMillis() - _start);
            }

            // 836906
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

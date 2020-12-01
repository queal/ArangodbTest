package per.queal.pojo;

import com.arangodb.entity.BaseDocument;
import com.arangodb.entity.DocumentField;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Vertex;

public class Cause extends BaseDocument {
    public static String label = "Cause";

    private static Integer[] statusArray = new Integer[]{0, 1, 2};

    @DocumentField(DocumentField.Type.FROM)
    private String from;
    @DocumentField(DocumentField.Type.TO)
    private String to;

    private Float confidence;
    private Boolean isManual;
    private Integer status;

    public static Cause gen(String from, String to) {
        Cause c = new Cause();
        c.setKey("CID-" + RandomStringUtils.randomAlphanumeric(8));
        c.setFrom(from);
        c.setTo(to);
        c.setConfidence(RandomUtils.nextFloat());
        c.setManual(false);
        c.setStatus(statusArray[RandomUtils.nextInt(0, statusArray.length - 1)]);
        return c;
    }

    public static Cause gen() {
        return gen(null, null);
    }


    public Vertex toVertex(GraphTraversalSource g) {
        return g.addV(label)
                .property("confidence", getConfidence())
                .property("isManual", getManual())
                .property("status", getStatus()).next();
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Float getConfidence() {
        return confidence;
    }

    public void setConfidence(Float confidence) {
        this.confidence = confidence;
    }

    public Boolean getManual() {
        return isManual;
    }

    public void setManual(Boolean manual) {
        isManual = manual;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}

package per.queal.pojo;

import com.arangodb.entity.BaseDocument;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

public class VInstanceMetric extends BaseDocument {

    public static String label = "VInstanceMetric";

    private static String[] metrics = new String[]{"cpu", "io", "memory"};
    private String name;
    private String instanceId;
    private String metric;
    private String signature;
    private Integer status;

    public static VInstanceMetric gen() {
        VInstanceMetric v = new VInstanceMetric();
        v.setName("InstanceMetric" + RandomStringUtils.randomAlphanumeric(32));
        v.setInstanceId(RandomStringUtils.randomAlphanumeric(32));
        v.setMetric(metrics[RandomUtils.nextInt(0, metrics.length - 1)]);
        v.setStatus(RandomUtils.nextInt(0, 10));
        return v;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getMetric() {
        return metric;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}

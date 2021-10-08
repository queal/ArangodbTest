package per.queal.pojo;

import com.arangodb.entity.BaseDocument;
import com.arangodb.entity.DocumentField;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.util.List;

public class CauseVo extends BaseDocument {
    public static String label = "Cause";

    private static Integer[] statusArray = new Integer[]{0, 1, 2};

    private Float confidence;
    private Boolean isManual;
    private String instanceId;
    private Integer status;
    private List<String> ips;

    public static CauseVo gen(String from, String to) {
        CauseVo c = new CauseVo();
        c.setConfidence(RandomUtils.nextFloat(0, 1));
        c.setManual(false);
        c.setInstanceId(RandomStringUtils.randomNumeric(10));
        c.setStatus(statusArray[RandomUtils.nextInt(0, statusArray.length - 1)]);
        c.setIps(Lists.newArrayList("192.168.1.100", "192.168.1.101"));
        return c;
    }

    public static CauseVo gen() {
        return gen(null, null);
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

    public List<String> getIps() {
        return ips;
    }

    public void setIps(List<String> ips) {
        this.ips = ips;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }
}

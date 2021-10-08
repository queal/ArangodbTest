package per.queal.pojo;

import org.apache.commons.lang3.RandomUtils;

public class CauseNoExt {
    public static String label = "CauseNoExt";

    private static Integer[] statusArray = new Integer[]{0, 1, 2};

    private String id;
    private String _from;
    private String _to;

    private Float confidence;
    private Boolean isManual;
    private Integer status;

    public static CauseNoExt gen(String from, String to) {
        CauseNoExt c = new CauseNoExt();
        c.set_from(from);
        c.set_to(to);
        c.setConfidence(RandomUtils.nextFloat());
        c.setManual(false);
        c.setStatus(statusArray[RandomUtils.nextInt(0, statusArray.length - 1)]);
        return c;
    }

    public static CauseNoExt gen() {
        return gen(null, null);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String get_from() {
        return _from;
    }

    public void set_from(String _from) {
        this._from = _from;
    }

    public String get_to() {
        return _to;
    }

    public void set_to(String _to) {
        this._to = _to;
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

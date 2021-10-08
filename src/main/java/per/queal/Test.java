package per.queal;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.commons.lang3.RandomUtils;

import java.util.List;
import java.util.Map;

public class Test {
    public static void main(String[] args) {

        Map<String, String> record = Maps.newLinkedHashMap();

        record.put("2", "2");
        record.put("1", "1");
        record.put("3", "3");
        record.put("2", "2");
        record.put("4", "4");

        System.out.println(record);

        List<String> list = Lists.newLinkedList();



    }
}

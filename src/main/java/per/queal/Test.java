package per.queal;

import org.apache.commons.lang3.time.StopWatch;
import org.apache.commons.lang3.RandomUtils;

public class Test {
    public static void main(String[] args) {

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        for (int i = 0; i < 10; i++) {
            RandomUtils.nextInt(0, 10);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }


        stopWatch.stop();
    }
}

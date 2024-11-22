package lv.javaguru.travel.insurance.loadtesting;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@UtilityClass
class RestCallExample {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestCallExample.class);

    @SuppressWarnings("checkstyle:UncommentedMain")
    public static void main(String[] args) {
        LoadTestingStatistic statisticV1 = new LoadTestingStatistic();
        LoadTestingStatistic statisticV2 = new LoadTestingStatistic();

        int numThreads = 50;
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads * 2);

        for (int i = 0; i < numThreads; i++) {
            executorService.submit(new V1Call(statisticV1), null);
            executorService.submit(new V2Call(statisticV2), null);
        }

        executorService.shutdown();

        try {
            boolean terminated = executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
            if (!terminated) {
                LOGGER.error("Not all tasks were completed before shutdown.");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        LOGGER.info("V1: average processing time {} (ms)", statisticV1.calculateAverage());
        LOGGER.info("V1: min processing time {} (ms)", statisticV1.getMin());
        LOGGER.info("V1: max processing time {} (ms)", statisticV1.getMax());

        LOGGER.info("V2: average processing time {} (ms)", statisticV2.calculateAverage());
        LOGGER.info("V2: min processing time {} (ms)", statisticV2.getMin());
        LOGGER.info("V2: max processing time {} (ms)", statisticV2.getMax());
    }

}

package liruunner.examples.jsr352;

import java.util.Properties;
import java.util.concurrent.TimeoutException;
import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import junit.framework.Assert;
import org.junit.Test;

import static liruunner.examples.jsr352.BatchJobLauncher.JobType.SAMPLE_BATCHLET;
import static liruunner.examples.jsr352.BatchJobLauncher.JobType.SAMPLE_READER_WRITER;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class BatchJobLauncherTest {

    private static final BatchJobLauncher batchJobLauncher = new BatchJobLauncher();
    private static final Properties nullJobParameters = null;
    private static final long TIMEOUT_IN_MS = 1000000;

    @Test
    public void JobOperatorTest() {
        JobOperator jobOperator = BatchRuntime.getJobOperator();
        assertThat(jobOperator, is(not(nullValue())));
    }

    @Test
    public void runSyncJob_withSampleBatchlet_returnTrue() {
        Assert.assertTrue(batchJobLauncher.runSyncJob(SAMPLE_BATCHLET, nullJobParameters));
    }

    @Test
    public void runSyncJob_withSampleReaderWriter_returnTrue() {
        Assert.assertTrue(batchJobLauncher.runSyncJob(SAMPLE_READER_WRITER, nullJobParameters));
    }

    @Test
    public void runAsyncJob_withSampleBatchlet_returnTrue() throws TimeoutException {
        long executionId = batchJobLauncher.runAsyncJob(SAMPLE_BATCHLET, nullJobParameters);

        Assert.assertTrue(batchJobLauncher.waitAsyncJobComplete(executionId, TIMEOUT_IN_MS));
    }

    @Test
    public void runAsyncJob_withSampleReaderWriter_returnTrue() throws TimeoutException {
        long executionId = batchJobLauncher.runAsyncJob(SAMPLE_READER_WRITER, nullJobParameters);

        Assert.assertTrue(batchJobLauncher.waitAsyncJobComplete(executionId, TIMEOUT_IN_MS));
    }
}
package liruunner.examples.jsr352;

import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeoutException;
import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.BatchStatus;
import javax.batch.runtime.JobExecution;
import org.springframework.batch.core.jsr.launch.JsrJobOperator;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SyncTaskExecutor;

@Configuration
@ComponentScan
public class BatchJobLauncher {

    private final JsrJobOperator jsrJobOperator;

    public BatchJobLauncher() {
        JobOperator jobOperator = BatchRuntime.getJobOperator();
        this.jsrJobOperator = (JsrJobOperator) jobOperator;
    }

    public boolean runSyncJob(JobType jobType, Properties jobParameters) {
        jsrJobOperator.setTaskExecutor(new SyncTaskExecutor());
        long jobExecutionId = jsrJobOperator.start(jobType.toString(), jobParameters);

        JobExecution jobExecution = jsrJobOperator.getJobExecution(jobExecutionId);

        return BatchStatus.COMPLETED.equals(jobExecution.getBatchStatus());
    }

    public long runAsyncJob(JobType jobType, Properties properties) {
        return jsrJobOperator.start(jobType.toString(), properties);
    }

    public boolean waitAsyncJobComplete(long executionId, long timeout) throws TimeoutException {
        JobExecution jobExecution = jsrJobOperator.getJobExecution(executionId);

        Date curDate = new Date();
        for(BatchStatus curBatchStatus = jobExecution.getBatchStatus(); curBatchStatus != BatchStatus.STOPPED && curBatchStatus != BatchStatus.COMPLETED && curBatchStatus != BatchStatus.FAILED; curBatchStatus = jobExecution.getBatchStatus()) {
            if((new Date()).getTime() - curDate.getTime() > timeout) {
                throw new TimeoutException("Job processing did not complete in time");
            }

            jobExecution = jsrJobOperator.getJobExecution(executionId);
        }
        return BatchStatus.COMPLETED.equals(jobExecution.getBatchStatus());
    }

    public enum JobType {
        SAMPLE_BATCHLET("batchlet-job"),
        SAMPLE_READER_WRITER("sample-job");

        private final String text;

        JobType(final String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }

}

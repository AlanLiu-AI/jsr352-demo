package liruunner.examples.jsr352;

import javax.batch.api.Batchlet;

public class SampleBatchlet implements Batchlet {

    @Override
    public String process() {
        System.out.println("process");
        return null;
    }

    @Override
    public void stop() {
        System.out.println("stop");
    }
    
}

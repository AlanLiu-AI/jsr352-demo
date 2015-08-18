package liruunner.examples.jsr352;

import java.util.List;
import javax.batch.api.chunk.AbstractItemWriter;


public class SampleItemWriter extends AbstractItemWriter {

    @Override
    public void writeItems(List<Object> items) throws Exception {
        System.out.print("SampleItemWriter: ");
        for (Object item : items) {
            System.out.print(item + " ");
        }
        System.out.println();
    }
}

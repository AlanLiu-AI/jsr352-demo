package liruunner.examples.jsr352;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.batch.api.chunk.AbstractItemReader;

public class SampleItemReader extends AbstractItemReader {

    Iterator<String> iter;

    @Override
    public void open(Serializable checkpoint) throws Exception {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("ObjectID_" + i);
        }
        iter = list.iterator();
    }

    @Override
    public String readItem() throws Exception {
        if (iter.hasNext()) {
            String item = iter.next();
            System.out.println("SampleItemReader: " + item);
            return item;
        } else {
            return null;
        }
    }
}

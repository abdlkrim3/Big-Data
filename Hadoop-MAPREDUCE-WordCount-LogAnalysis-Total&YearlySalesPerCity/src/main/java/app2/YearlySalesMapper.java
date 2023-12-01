package app2;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

// Mapper for Job 2
public class YearlySalesMapper extends Mapper<LongWritable, Text, Text, DoubleWritable> {
    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, DoubleWritable>.Context context) throws IOException, InterruptedException, IOException {
        String[] tokens = value.toString().split(" ");
        if (tokens.length == 4) {
            String date = tokens[0];
            String city = tokens[1];
            String product = tokens[2];
            double price = Double.parseDouble(tokens[3]);
            String year = "2023";
            if (date.startsWith(year)) {
                String keyOutput = city + " - " + product;
                context.write(new Text(keyOutput), new DoubleWritable(price));
            }
        }
    }
}


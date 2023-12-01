package app3;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class LogReducer extends Reducer<Text, Text, Text, Text> {
    public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        int totalRequests = 0;
        int successfulRequests = 0;

        for (Text value : values) {
            int responseCode = Integer.parseInt(value.toString());

            totalRequests++;
            if (responseCode == 200) {
                successfulRequests++;
            }
        }

        // Output the IP address along with the total requests and successful requests
        context.write(key, new Text("Total Requests: " + totalRequests + ", Successful Requests: " + successfulRequests));
    }
}

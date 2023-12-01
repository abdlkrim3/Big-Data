package app3;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import java.io.IOException;

public class LogMapper extends Mapper<Object, Text, Text, Text> {
    private Text ip = new Text();

    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        String[] fields = value.toString().split(" ");
        if (fields.length >= 9) {
            String ipAddress = fields[0];
            int responseCode = Integer.parseInt(fields[fields.length - 2]);

            // Emit key-value pair for IP address and response code
            ip.set(ipAddress);
            context.write(ip, new Text(String.valueOf(responseCode)));
        }
    }
}



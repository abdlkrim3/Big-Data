package ma.enset.hadoopapifilesystems;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io. BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class Application2 {
    public static void main(String[] args) throws IOException {
        Configuration configuration=new Configuration();
        configuration.set("fs.defaultFS", "hdfs://localhost:9000");
        FileSystem fs=FileSystem.get(configuration);
        Path path=new Path("/file1");
        FSDataOutputStream fsdos=fs.create(path);
        BufferedWriter br=new BufferedWriter(new OutputStreamWriter (fsdos, StandardCharsets.UTF_8));
        br.write("BDDC 2");
        br.newLine();
        br.write("BDDC 2");
        br.close();
        fs.close();
    }
}

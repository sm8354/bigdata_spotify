import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class UniqueRecsMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    private final static IntWritable one = new IntWritable(1);
    private Text word = new Text();

    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        // Skip the header line
        if (line.startsWith("Artist")) {
            return;
        }
        String[] columns = line.split(",");
        if(columns.length >= 5) {
            // Concatenate Artist, Peak Position, and PeakListeners with delimiter to ensure uniqueness
            String uniqueKey = columns[0].trim() + "," + columns[3].trim() + "," + columns[4].trim();
            word.set(uniqueKey);
            context.write(word, one);
        }
    }
}

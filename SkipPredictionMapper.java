import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class SkipPredictionMapper extends Mapper<LongWritable, Text, Text, Text> {

    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // Skip the header
        if (key.get() == 0) {
            return;
        }
        
        String[] fields = value.toString().split("\\t", -1); // assuming tab-separated values
        
        String artist = fields[0];
        String listeners = fields[1];
        String dailyTrend = fields[2];
        String peakPosition = fields[3];
        String peakListeners = fields[4];
        
        // Composite value of listeners and trends
        String compositeValue = listeners + "\t" + dailyTrend + "\t" + peakPosition + "\t" + peakListeners;
        
        context.write(new Text(artist), new Text(compositeValue));
    }
}

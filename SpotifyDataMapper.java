import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class SpotifyDataMapper extends Mapper<LongWritable, Text, Text, Text> {

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // Skip the header
        if (key.get() == 0) {
            return;
        }

        String line = value.toString();
        String[] fields = line.split(",");

        if (fields.length < 5) {
            // Skip malformed lines
            return;
        }

        String artist = fields[0].trim();
        String listeners = fields[1].trim();
        String dailyTrend = fields[2].trim();
        String peakPosition = fields[3].trim();
        String peakListeners = fields[4].trim();

        String compositeValue = String.join(",", listeners, dailyTrend, peakPosition, peakListeners);
        context.write(new Text(artist), new Text(compositeValue));
    }
}

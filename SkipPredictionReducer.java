import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class SkipPredictionReducer extends Reducer<Text, Text, Text, Text> {

    public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        int sumListeners = 0;
        int sumDailyTrend = 0;
        int minPeakPosition = Integer.MAX_VALUE;
        int maxPeakListeners = 0;
        
        for (Text value : values) {
            String[] parts = value.toString().split("\\t");
            
            sumListeners += Integer.parseInt(parts[0]);
            sumDailyTrend += Integer.parseInt(parts[1]);
            minPeakPosition = Math.min(minPeakPosition, Integer.parseInt(parts[2]));
            maxPeakListeners = Math.max(maxPeakListeners, Integer.parseInt(parts[3]));
        }
        
        String result = sumListeners + "\t" + sumDailyTrend + "\t" + minPeakPosition + "\t" + maxPeakListeners;
        context.write(key, new Text(result));
    }
}

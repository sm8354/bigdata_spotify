# bigdata_spotify
Spotify code for final project

This repository contains the code and documentation for the Spotify Skip Prediction project. The project's objective is to predict whether a user will skip a song on Spotify based on various features associated with the song and user behavior.

Repository Contents:
CleanMapper.java: Hadoop MapReduce job for cleaning the dataset.
CleanReducer.java:  Hadoop MapReduce job for cleaning the dataset.
UniqueRecs.java: Hadoop MapReduce to profile code
UniqueRecsMapper.java: Hadoop MapReduce to profile code
UniqueRecsReducer.java: Hadoop MapReduce to profile code
SpotifyDataMapper: Hadoop MapReduce to analyze spotify skip metrics
SpotifyDataReducer: Hadoop MapReduce to analyze spotify skip metrics
SpotifyDataDriver: Driver code to analyze spotify skip metrics
README.md: This file, providing an overview of the project and repository.

We use two primary datasets for this analysis:

Spotify Sequential Skip Prediction Dataset: This dataset includes detailed user interactions with tracks, such as skips and listening durations. It contains attributes like danceability, energy, valence, and acousticness.
Spotify Listeners Data Analysis: This dataset offers insights into individual user interactions with tracks on Spotify, which helps analyze factors influencing the likelihood of skipping a track.

The datasets include features such as:

valence, acousticness, danceability: Quantitative measures of the musical attributes of the tracks.
popularity: Integer representing the popularity of the track on Spotify.
duration_ms: The duration of the track in milliseconds.
explicit: Indicates if the track has explicit content.

Running the Hadoop Job
Step 1: Data Ingestion
Upload the Data:
Upload spotify_data.csv to the Hadoop Distributed File System (HDFS) using the following command: 
hdfs dfs -put spotify_data.csv /path/to/hdfs

Step 2: Source Code Preparation
Develop Java code using VS Code. The code includes 
UniqueRecs.java, UniqueRecsMapper.java, and UniqueRecsReducer.java for data profiling and 
Clean.java, CleanMapper.java, CleanReducer.java for data cleaning.

Step 3: Compilation of Java Files

Compile the Java files with the following commands: 
javac -classpath `yarn classpath` -d . UniqueRecs.java
javac -classpath `yarn classpath` -d . UniqueRecsMapper.java
javac -classpath `yarn classpath`:. -d . UniqueRecsReducer.java


Step 4: Creation of JAR Files
Package the files into a JAR: jar -cvf uniqueRecsJob.jar *.class

Run the profiling job using hadoop command:  hadoop jar uniqueRecsJob.jar UniqueRecs spotify_data.csv profiling_code

Step 5: Data Cleaning
Compile the cleaning Java files:
javac -classpath `yarn classpath`:. -d . Clean.java
javac -classpath `yarn classpath`:. -d . CleanMapper.java
javac -classpath `yarn classpath`:. -d . CleanReducer.java

Create a JAR for the cleaning job:
jar -cvf cleanJob.jar *.class

Run the cleaning job: hadoop jar cleanJob.jar Clean spotify_data.csv etl_code

Check the cleaning output: hdfs dfs -ls /output/path/etl_code and run the profiling job again on the cleaned dataset.

Step 6: Verification

After running each job, verify the outputs using the -cat command on relevant part files to review the results:
hdfs dfs -cat etl_code/part-r-00000

Step 7: Running the analysis files:
Develop java code for an analysis metric. 
This MapReduce program  processes the csv to identify patterns that may be predictive of whether a user will skip a track. 
We can aggregate data to understand artist popularity and listening trends that may be used in a more complex model for skip prediction.
The Mapper outputs key-value pairs with the artist's name as the key and a composite of other metrics as the value. 
The Reducer could then sum up these values for each artist, which later can be analyzed to predict skip behavior.

Compile the Java files with the following commands: 
javac -classpath `yarn classpath` -d . SpotifyDataMapper.java
javac -classpath `yarn classpath` -d . SpotifyDataReducer.java
javac -classpath `yarn classpath`:. -d . SpotifyDataDriver.java

Step 8:  Make Jar as before, jar -cvf spotifyData.jar *.class

Step 9: Run the profiling job using hadoop command:  hadoop jar spotifyData.jar SpotifyDataDriver spotify_data.csv ana_code

Step 10: 

Check our output. This data might be used to analyze the popularity of artists in the csv, 
their current traction (daily trend), 
their historical peak performance.
This data can be used to contribute to a model that predicts 
whether a user might skip a track. 
For instance, artists with very high daily trend scores and listenership 
could potentially be less likely to be skipped, 
though more features and a more sophisticated model 
would be necessary to make accurate predictions.



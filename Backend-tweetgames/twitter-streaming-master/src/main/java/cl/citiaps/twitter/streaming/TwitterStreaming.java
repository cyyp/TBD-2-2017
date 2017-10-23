package cl.citiaps.twitter.streaming;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import org.bson.Document;

import org.apache.commons.io.IOUtils;


import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

public class TwitterStreaming {

	private final TwitterStream twitterStream;
	private Set<String> keywords;
	public static MongoDB mongodb = new MongoDB();

	private TwitterStreaming() {
		this.twitterStream = new TwitterStreamFactory().getInstance();
		this.keywords = new HashSet<>();
		loadKeywords();
	}

	private void loadKeywords() {
		try {
			ClassLoader classLoader = getClass().getClassLoader();
			keywords.addAll(IOUtils.readLines(classLoader.getResourceAsStream("words.dat"), "UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void init() {
		StatusListener listener = new StatusListener() {

			public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
				System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
			}

			public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
				System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
			}

			public void onScrubGeo(long userId, long upToStatusId) {
				System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
			}

			public void onException(Exception ex) {
				ex.printStackTrace();
			}

			@Override
			public void onStallWarning(StallWarning arg0) {

			}

			@Override
			public void onStatus(Status status) {
				//obtener la cantidad de los n documentos en la bd de mongo
				//cada cierta cantidad de elementos reconstruir indices
				//consulta: db.twitterCollection.count()
				//Creacion del documento y se agrega a la coleccion

				if(status.getLang().equals("es"))
				{
					System.out.println(status.getText());
					Document tweet = new Document("id",status.getId())
					.append("text", status.getText())
					.append("user_id", status.getUser().getId())
					.append("user_name", status.getUser().getName());
					mongodb.getMongoCol().insertOne(tweet);
				}


	
			}
		};

		FilterQuery fq = new FilterQuery();

		fq.track(keywords.toArray(new String[0]));

		this.twitterStream.addListener(listener);
		this.twitterStream.filter(fq);
	}
	
	public static void main(String[] args) {
		new TwitterStreaming().init();

	}

}

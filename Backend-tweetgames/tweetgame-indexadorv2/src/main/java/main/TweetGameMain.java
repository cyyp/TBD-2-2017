package main;




import MySQLConnection.MySQLDB;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import java.io.IOException;
import MongoConnection.MongoDB;
import luceneIndexador.luceneIndexador;
import java.util.List;

import tweetgame_core.tweetgame_core;





public class TweetGameMain {

    public static void main(String[] args) throws IOException, ParseException {

        /*MongoDB mongo = new MongoDB();
        MongoCollection<org.bson.Document> docs = mongo.gettweetsColl();

        //MongoCursor<org.bson.Document> collCursor = docs.find().iterator();


        luceneIndexador li = new luceneIndexador(docs);
        li.beginIndex();
        List<Document> doc = li.getTweetsRelationated("counter");*/

        tweetgame_core tc = new tweetgame_core();
        /*List<Document> doc = tc.getDocsIndexed("Overwatch");
        for(Document d:doc){
            System.out.println(d.get("tweet"));
        }
        tc.updateMysql(doc,"Overwatch");*/

        MySQLDB m = new MySQLDB();
        /*List<String> x = m.getGamestitles();
        for (String a: x){
            System.out.println(a);
        }*/

        tc.beginProcess();

        //m.addTweet(123321,"FIFA 18");

    }


}

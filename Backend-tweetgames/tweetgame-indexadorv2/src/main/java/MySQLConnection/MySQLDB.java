package MySQLConnection;

import java.sql.*;
import java.util.*;

public class MySQLDB {
    private Connection connection;
    private String username;
    private String password;
    private String host;
    private String db_name;

    public MySQLDB(){
        this.username = "root";
        this.password = "qweert123456";
        this.host = "jdbc:mysql://localhost:3306/";
        this.db_name = "tweet-schema";
        connectDB();
    }
    public void connectDB(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.connection = DriverManager.getConnection(this.host + this.db_name, this.username, this.password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();

        }

    }



    public List<String> getGamestitles(){
        //Set<String> titles = new HashSet<>();
        List<String> titles = new ArrayList<>();
        String query = "SELECT nombre FROM juegos";
        try{
            Statement st = this.connection.createStatement();
            ResultSet resultset = st.executeQuery(query);
            while(resultset.next()){
                titles.add(resultset.getNString("nombre"));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return titles;

    }

    public int addTweet(long id_tweet, String gameName){

        String query_id = "SELECT COUNT(*) as idCount FROM tweet where idtweet = ?";
        int value = 0;

        try {
            PreparedStatement ps = this.connection.prepareStatement(query_id);
            ps.setLong(1,id_tweet);
            ResultSet re = ps.executeQuery();
            while(re.next()){
                value += re.getInt("idCount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(value == 0){
            String query_game = "SELECT id FROM juegos where nombre = ?";
            String query_add = "INSERT INTO tweet (idtweet,id_juego) VALUES (?,?)";
            try {
                PreparedStatement ps = this.connection.prepareStatement(query_game);
                ps.setString(1,gameName);
                ResultSet re = ps.executeQuery();
                int idGame = 0;
                while(re.next()){
                    idGame += re.getInt("id");
                }
                PreparedStatement ps_add = this.connection.prepareStatement(query_add);
                ps_add.setLong(1,id_tweet);
                ps_add.setInt(2,idGame);
                ps_add.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        return value;
    }

    //Obtiene la cantidad de tweets positivos negativos en un arreglo de 2
    public int[] getCountTweets(String gameName)
    {
        //[0] = tweets positivos, [1] = tweets negativos
        int[] return_values = new int[2];
        String query_pos = "SELECT tweets_pos,tweets_neg FROM resume INNER JOIN juegos on juegos.id = resume.id_juego where nombre ="+"'"+gameName+"'";
        String query_neg = "SELECT tweets_neg FROM resume INNER JOIN juegos on juegos.id = resume.id_juego where nombre ="+"'"+gameName+"'";
        try {
            Statement st = this.connection.createStatement();
            ResultSet resultset = st.executeQuery(query_pos);
            while(resultset.next()){
                return_values[0] = resultset.getInt("tweets_pos");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            Statement st = this.connection.createStatement();
            ResultSet resultset = st.executeQuery(query_neg);

            while(resultset.next()){
                return_values[1] = resultset.getInt("tweets_neg");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }



        return return_values;
    }

    //Aumenta la metrica de tweets positivos en 1
    public void aumentPositiveTweets(int[] tweet_values,String gameName){

        int tweets_positivos = tweet_values[0] + 1;

        String query = "UPDATE resume INNER JOIN juegos on juegos.id = resume.id_juego SET tweets_pos = ? WHERE nombre ="+"'"+gameName+"'";
        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setInt(1,tweets_positivos);
            ps.executeUpdate();



        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Aumenta la metrica de tweets negativos en 1
    public void aumentNegativeTweets(int[] tweet_values,String gameName) {

        int tweets_negativos = tweet_values[1] + 1;

        String query = "UPDATE resume INNER JOIN juegos on juegos.id = resume.id_juego SET tweets_neg = ? WHERE nombre =" + "'" + gameName + "'";
        try {
            PreparedStatement p = this.connection.prepareStatement(query);
            p.setInt(1,tweets_negativos);
            p.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}

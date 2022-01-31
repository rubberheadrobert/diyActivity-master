package sample;


import java.sql.*;

public class EnterWordsModel {
    Connection connection;

    public EnterWordsModel(){
        connection = SqliteConnection.Connector();
        if(connection == null) System.exit(1);
    }

    public boolean isDbConnected(){
        try{
            return !connection.isClosed();
        } catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public void addWord(String word) throws SQLException{
        PreparedStatement preparedStatement = null;
        String query = "INSERT INTO Words (word) VALUES (?)";
        try{
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,word);
            preparedStatement.executeUpdate();
        } catch (Exception e){
            System.out.println("Couldn't ass word to Database");
        } finally {
            preparedStatement.close();
        }
    }
}

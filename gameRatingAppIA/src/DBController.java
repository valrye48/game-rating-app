package sample;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBController {

    private static final String CONN = "jdbc:sqlite:gameDb.db";

    public static List<Game> getGamesFromDB() throws SQLException {
        List<Game> games = new ArrayList<>();

        String sqlQuery = "SELECT * FROM Game;";

        Connection connection = DriverManager.getConnection(CONN);
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        ResultSet resultSet = preparedStatement.executeQuery();

        try(connection; preparedStatement; resultSet) {
            while (resultSet.next()) {
                String gameName = resultSet.getString("gameName");
                String companyName = resultSet.getString("companyName");
                int yearOfRelease = resultSet.getInt("yearOfRelease");
                double userRating = resultSet.getDouble("userRating");
                List<Genre> genres = Genre.asList(resultSet.getString("genres"));

                Game game = new Game(gameName, companyName, yearOfRelease, userRating, genres);
                games.add(game);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return games;
    }

    public static void saveGameInDB(Game game) throws SQLException {

        Connection connection = DriverManager.getConnection(CONN);

        String insertSQL = "INSERT INTO Game(gameName, companyName, yearOfRelease, userRating, genres) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
        try(connection; preparedStatement){
            preparedStatement.setString(1, game.getGameName());
            preparedStatement.setString(2, game.getCompanyName());
            preparedStatement.setInt(3, game.getYearOfRelease());
            preparedStatement.setDouble(4, game.getUserRating());
            preparedStatement.setString(5, Genre.asString(game.getGenres()));
            preparedStatement.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }


    public static void removeGameFromDb(Game game) throws SQLException {

        Connection connection = DriverManager.getConnection(CONN);

        String removeSQL = "DELETE FROM Game WHERE gameName=?;";

        PreparedStatement preparedStatement = connection.prepareStatement(removeSQL);
        try(connection; preparedStatement){
            preparedStatement.setString(1, game.getGameName());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }

    }


}


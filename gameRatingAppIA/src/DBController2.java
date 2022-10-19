package sample;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBController2 {

    private static final String CONN = "jdbc:sqlite:gameDb2.db";

    public static List<Game> getGamesFromDBPlayList() throws SQLException {
        List<Game> gamesPlaylist = new ArrayList<>();

        String sqlQuery = "SELECT * FROM GamePlaylist;";

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

                Game gamePlaylist = new Game(gameName, companyName, yearOfRelease, userRating, genres);
                gamesPlaylist.add(gamePlaylist);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return gamesPlaylist;
    }

    public static void saveGameInDBPlaylist(Game gamePlaylist) throws SQLException {

        Connection connection = DriverManager.getConnection(CONN);

        String insertSQL = "INSERT INTO GamePlaylist(gameName, companyName, yearOfRelease, userRating, genres) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
        try(connection; preparedStatement){
            preparedStatement.setString(1, gamePlaylist.getGameName());
            preparedStatement.setString(2, gamePlaylist.getCompanyName());
            preparedStatement.setInt(3, gamePlaylist.getYearOfRelease());
            preparedStatement.setDouble(4, gamePlaylist.getUserRating());
            preparedStatement.setString(5, Genre.asString(gamePlaylist.getGenres()));
            preparedStatement.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void removeGameFromDbPlaylist(Game gamePlaylist) throws SQLException {

        Connection connection = DriverManager.getConnection(CONN);

        String removeSQL = "DELETE FROM GamePlaylist WHERE gameName=?;";

        PreparedStatement preparedStatement = connection.prepareStatement(removeSQL);
        try(connection; preparedStatement){
            preparedStatement.setString(1, gamePlaylist.getGameName());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }

    }
}

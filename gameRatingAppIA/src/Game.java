package sample;
import java.sql.SQLException;
import java.util.*;

public class Game {
    private String gameName;
    private String companyName;
    private int yearOfRelease;
    private double userRating;
    private List<Genre> genres;

    public static List<Game> games;

    static {
        try {
            games = DBController.getGamesFromDB();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) throws SQLException {
        this.games = games;
    }

    public Game(String gameName, String companyName, int yearOfRelease, double userRating, List<Genre> genres) {
        this.gameName = gameName;
        this.companyName = companyName;
        this.yearOfRelease = yearOfRelease;
        this.userRating = userRating;
        this.genres = genres;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getYearOfRelease() {
        return yearOfRelease;
    }

    public void setYearOfRelease(int yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }

    public double getUserRating() {
        return userRating;
    }

    public void setUserRating(double userRating) {
        this.userRating = userRating;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }


    public static Genre getFavoriteGenre(List<Game> games) {
        int maxCount = 0;
        Genre favGenre = null;
        List<Genre> genreValues = Arrays.asList(Genre.values());

        for (int i = 0; i < genreValues.size(); i++) {
            int currentGenreCount = 0;
            Genre currentGenre = genreValues.get(i);
            for (int j = 0; j < games.size(); j++) {
                for (int k = 0; k < games.get(j).getGenres().size(); k++) {
                    if (currentGenre == games.get(j).getGenres().get(k)) {
                        currentGenreCount++;
                    }
                }
            }
            if (currentGenreCount > maxCount) {
                maxCount = currentGenreCount;
                favGenre = currentGenre;
            }
        }

        return favGenre;

    }

    public static int[] getFrequency(List<Game> games){
        Genre[] genres = Genre.values();
        int[] frequency = new int[genres.length];

        for (int i = 0; i < games.size(); i++) {
            for (int j = 0; j < games.get(i).getGenres().size(); j++) {
                for (int k = 0; k < genres.length; k++) {
                    if (genres[k] == (games.get(i).getGenres().get(j))){
                        frequency[k]++;
                    }
                }
            }
        }
        return frequency;
    }

    public static Genre getFrequentGenres(){
        Genre[] genres = Genre.values();
        int[] frequency = getFrequency(games);

        int sum = 0;
        for (int i = 0; i < frequency.length; i++) {
            sum += frequency[i];
        }


        int random = (int) (Math.random() * sum);
        sum = 0;

        for (int i = 0; i < frequency.length; i++) {
                sum += frequency[i];
                if (sum > random) {
                    return genres[i];

            }
        }

        return genres[genres.length - 1];
    }


    public static Game getNextGameToPlay(Genre genre, List<Game> gamesPlaylist) {
        List<Game> gamesWithFrequentGenre = new ArrayList<>();

        for (int i = 0; i < gamesPlaylist.size(); i++) {
                if (gamesPlaylist.get(i).getGenres().contains(genre)){
                    gamesWithFrequentGenre.add(gamesPlaylist.get(i));
            }
        }

        int randomIndex = (int) (Math.random() * gamesWithFrequentGenre.size());
        return gamesWithFrequentGenre.get(randomIndex);
    }

    public static double getAverageRating(List<Game> games) {
        double sum = 0;
        double avgRating = 0;

        for (int i = 0; i < games.size(); i++) {
            double rating = games.get(i).getUserRating();

            sum += rating;

        }
        avgRating = sum / games.size();

        avgRating = (Math.floor(avgRating * 100)) / (100);

        return avgRating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return gameName.equals(game.gameName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameName);
    }
}

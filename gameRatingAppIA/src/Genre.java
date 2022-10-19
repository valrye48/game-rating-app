package sample;

import java.util.ArrayList;
import java.util.List;

public enum Genre {
    HORROR, SCIFI, ADVENTURE, ACTION, RPG, SIMULATION, STRATEGY, SPORTS, SANDBOX, PUZZLE;

    public static List<Genre> asList(String genres) {
        String[] genreArray = genres.split(";");
        List<Genre> genreList = new ArrayList<>(genreArray.length);

        for (int i = 0; i < genreArray.length; i++) {
            genreList.add(Genre.valueOf(genreArray[i]));
        }

        return genreList;
    }

    public static String asString(List<Genre> genres) {
        String genreString = "";

        for (int i = 0; i < genres.size() - 1; i++) {
            String genre = String.valueOf(genres.get(i));

            genreString += genre + ";";
        }

        genreString += String.valueOf(genres.get(genres.size() - 1));

        return  genreString;
    }
}

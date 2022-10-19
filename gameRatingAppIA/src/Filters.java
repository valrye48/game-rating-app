package sample;

import java.util.ArrayList;
import java.util.List;

public class Filters {

    public static List<Game> getFilteredGames(List<Game> allGames, String searched){
        List <Game> filtered = new ArrayList<>();

        for (int i = 0; i < allGames.size(); i++) {
            Game game = allGames.get(i);
            if (game.getGameName().toLowerCase().contains(searched.toLowerCase())){
                filtered.add(game);
            }
            if (game.getCompanyName().toLowerCase().contains(searched.toLowerCase())) {
                filtered.add(game);
                continue;
            }

            if ( ( "" + game.getYearOfRelease()).equalsIgnoreCase(searched) ){
                filtered.add(game);
                continue;
            }

            if ( ("" + game.getUserRating()).contains(searched)) {
                filtered.add(game);
                continue;
            }

            for (int j = 0; j < game.getGenres().size(); j++) {
                if (game.getGenres().get(j).toString().equalsIgnoreCase(searched)){
                    filtered.add(game);
                    break;
                }
            }
        }


        return filtered;
    }

}

package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.sqlite.core.DB;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class Main extends Application {

    Stage stage;
    GridPane root;
    static final int WIDTH = 1200;
    static final int HEIGHT = 800;


    List<Game> games;
    List<Game> gamesPlaylist;
    List<Game> resultGame;
    List<Game> resultGamePl;
    List<Genre> genresMain;
    Game game1;

    String [] companyNames;
    String [] genresNames;

    @Override
    public void start(Stage primaryStage) throws Exception{
        games = DBController.getGamesFromDB();
        gamesPlaylist = DBController2.getGamesFromDBPlayList();
        companyNames = new String[] {"Rockstar Games", "Valve", "Electronic Arts", "Nintendo", "Blizzard", "Naughty Dog", "Ubisoft", "BioWare", "Square Enix", "Capcom", "Epic games", "Bethesda", "2K"};
        genresNames = new String[] {"Horror", "Scifi", "Adventure", "Action", "Rpg", "Simulation", "Strategy", "Sports", "Sandbox", "Puzzle"};

        for (int i = 0; i < games.size(); i++) {
            game1 = games.get(i);

            genresMain = game1.getGenres();
        }

        stage = primaryStage;

        root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(50));
        root.setHgap(100);
        root.setVgap(50);
        root.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        Text welcomeTextStart = new Text("GameBox");
        welcomeTextStart.setFont(Font.loadFont("file:font/RetroGaming.ttf", 120));
        welcomeTextStart.setFill(Color.WHITE);
        Text welcomeTextStartBelow = new Text("Welcome to GameBox!");
        welcomeTextStartBelow.setFont(Font.loadFont("file:font/Cantarell-Regular.otf", 20));
        welcomeTextStartBelow.setFill(Color.WHITE);
        Button enterButtonStart = new Button("Enter");
        enterButtonStart.setFont(Font.loadFont("file:font/Cantarell-Bold.otf", 20));
        enterButtonStart.setOnAction(event -> {
            mainWindow();
        });

        root.add(welcomeTextStart, 1, 0);
        root.add(welcomeTextStartBelow, 1,1);
        root.add(enterButtonStart, 1, 2);

        Scene scene = new Scene(root, WIDTH, HEIGHT);
        stage.setScene(scene);
        stage.show();
    }

    public void mainWindow() {
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(50));
        root.setSpacing(50);
        root.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        Text upperTextGB = new Text("GameBox");
        upperTextGB.setFont(Font.loadFont("file:font/RetroGaming.ttf", 80));
        upperTextGB.setFill(Color.WHITE);

        Button database = new Button("Your games");
        database.setFont(Font.loadFont("file:font/Cantarell-Bold.otf", 20));
        database.setMinWidth(250);
        database.setOnAction(event -> {
            gameDbWindow();
        });
        Button playList = new Button("Play list");
        playList.setFont (Font.loadFont("file:font/Cantarell-Bold.otf", 20));
        playList.setMinWidth(250);
        playList.setOnAction(event -> {
            playListWindow();
        });
        Button stats = new Button("Your statistics");
        stats.setMinWidth(250);
        stats.setFont(Font.loadFont("file:font/Cantarell-Bold.otf", 20));
        stats.setOnAction(event -> {
            statsWindow();
        });

        root.getChildren().add(upperTextGB);

        root.getChildren().add(database);
        root.getChildren().add(playList);
        root.getChildren().add(stats);

        Scene scene = new Scene(root, WIDTH, HEIGHT);
        stage.setScene(scene);
        stage.show();
    }

    public void gameDbWindow() {
        root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(50));
        root.setHgap(100);
        root.setVgap(50);
        root.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        Text addGameToDbText = new Text("Add a game to your list:");
        addGameToDbText.setFont(Font.loadFont("file:font/Cantarell-Regular.otf", 20));
        addGameToDbText.setFill(Color.WHITE);
        TextField titleTextField = new TextField();
        titleTextField.setPromptText("Game title");

        ComboBox companyNameComboBox = new ComboBox();
        companyNameComboBox.getItems().addAll(companyNames);
        companyNameComboBox.setPromptText("Company name");

        TextField yearTextField = new TextField();
        yearTextField.setPromptText("Year of release");
        TextField ratingTextField = new TextField();
        ratingTextField.setPromptText("Game rating");

        ComboBox genreComboBox = new ComboBox();
        genreComboBox.getItems().addAll(genresNames);
        genreComboBox.setPromptText("Genre");

        ComboBox genreComboBox2 = new ComboBox();
        genreComboBox2.getItems().addAll(genresNames);
        genreComboBox2.setPromptText("Second genre");

        Button addGameButton = new Button("Add");
        addGameButton.setFont(Font.loadFont("file:font/Cantarell-Bold.otf", 20));
        addGameButton.setOnAction(event -> {
            Game game = null;
            Game game2 = null;
            try {
                if (genreComboBox2.getValue() != null) {
                    game2 = new Game(titleTextField.getText(), companyNameComboBox.getValue().toString(), parseInt(yearTextField.getText()), parseDouble(ratingTextField.getText()),
                            Genre.asList(genreComboBox.getValue().toString().toUpperCase() + ";" + genreComboBox2.getValue().toString().toUpperCase()));
                    games.add(game2);
                } else {
                    game = new Game(titleTextField.getText(), companyNameComboBox.getValue().toString(), parseInt(yearTextField.getText()), parseDouble(ratingTextField.getText()),
                            Genre.asList(genreComboBox.getValue().toString().toUpperCase()));
                    games.add(game);
                }
            } catch (NullPointerException e) {
                System.out.println("NullPointerException thrown!");
            }

            try {
                if (genreComboBox2.getValue() != null){
                    assert game2 != null;
                    DBController.saveGameInDB(game2);
                } else {
                    assert game != null;
                    DBController.saveGameInDB(game);
                }
                titleTextField.setText("");
                yearTextField.setText("");
                ratingTextField.setText("");
                JOptionPane.showMessageDialog(null, "Game added!");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                JOptionPane.showMessageDialog(null, "Failed to add game");
            }
        });

        Button backButton = new Button("Go back");
        backButton.setFont(Font.loadFont("file:font/Cantarell-Bold.otf", 20));
        backButton.setOnAction(event -> {
            mainWindow();
        });

        Button openListButton = new Button("Open list");
        openListButton.setFont((Font.loadFont("file:font/Cantarell-Bold.otf", 20)));
        openListButton.setOnAction(event -> {
            try {
                listGameWindow();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        root.add(addGameToDbText, 0, 1);
        root.add(titleTextField, 1, 1);
        root.add(companyNameComboBox, 2, 1);
        root.add(yearTextField, 1, 2);
        root.add(ratingTextField, 2, 2);
        root.add(genreComboBox, 1, 3);
        root.add(genreComboBox2, 2, 3);
        root.add(addGameButton, 3,3);
        root.add(openListButton, 1, 6);
        root.add(backButton, 1, 7);

        Scene scene = new Scene(root, WIDTH, HEIGHT);
        stage.setScene(scene);
        stage.show();

    }

    public void searchWindow() {
        root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(50));
        root.setHgap(100);
        root.setVgap(50);
        root.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        Text searchResultsText = new Text("Search results:");
        searchResultsText.setFont(Font.loadFont("file:font/Cantarell-Bold.otf", 20));
        searchResultsText.setFill(Color.WHITE);


        TableView <Game> searchTableView = new TableView();
        searchTableView.setMinSize(400, 200);

        TableColumn<Game, String> column1 = new TableColumn <>("Game Name");
        TableColumn<Game, String> column2 = new TableColumn <>("Company Name");
        TableColumn<Game, Integer> column3 = new TableColumn <>("Year Of Release");
        TableColumn<Game, Double> column4 = new TableColumn <>("User rating");
        TableColumn<Game, List<Genre>> column5 = new TableColumn<>("Genre");
        column1.setCellValueFactory(new PropertyValueFactory <>("gameName"));
        column2.setCellValueFactory(new PropertyValueFactory <>("companyName"));
        column3.setCellValueFactory(new PropertyValueFactory <>("yearOfRelease"));
        column4.setCellValueFactory(new PropertyValueFactory <>("userRating"));
        column5.setCellValueFactory(new PropertyValueFactory<>("genres"));

        searchTableView.getColumns().addAll(column1, column2, column3, column4, column5);

        searchTableView.getItems().addAll(resultGame);

        Button backButton = new Button("Go back");
        backButton.setFont(Font.loadFont("file:font/Cantarell-Bold.otf", 20));
        backButton.setOnAction(event -> {
            try {
                listGameWindow();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        Text removeGameFromDbText = new Text("Remove a game from your list:");
        removeGameFromDbText.setFont(Font.loadFont("file:font/Cantarell-Regular.otf", 20));
        removeGameFromDbText.setFill(Color.WHITE);
        Button removeGameButton = new Button("Remove");
        removeGameButton.setFont(Font.loadFont("file:font/Cantarell-Bold.otf", 20));

        TextField title1TextField = new TextField();
        title1TextField.setPromptText("Game title");


        removeGameButton.setOnAction(event -> {
            Game gameRemove = new Game(title1TextField.getText(), null, 0, 0, null);
            games.remove(gameRemove);
            searchTableView.getItems().remove(gameRemove);

            try {
                DBController.removeGameFromDb(gameRemove);
                title1TextField.setText("");
                searchTableView.getItems().remove(gameRemove);
                JOptionPane.showMessageDialog(null, "Game removed!");

            } catch (SQLException throwables) {
                throwables.printStackTrace();
                JOptionPane.showMessageDialog(null, "Failed to remove game");
            }

        });


        root.add(searchTableView, 1,2, 2, 1);
        root.add(searchResultsText, 1, 1);
        root.add(removeGameFromDbText, 1, 3);
        root.add(title1TextField, 2, 3);
        root.add(removeGameButton, 3, 3);
        root.add(backButton, 1, 4);

        Scene scene = new Scene(root, WIDTH, HEIGHT);
        stage.setScene(scene);
        stage.show();
    }

    public void playListWindow() {
        root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(50));
        root.setHgap(100);
        root.setVgap(50);
        root.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        Text addGameToPlText = new Text("Add a game to your play list:");
        addGameToPlText.setFont((Font.loadFont("file:font/Cantarell-Regular.otf", 20)));
        addGameToPlText.setFill(Color.WHITE);
        Button addGameButton = new Button("Add");
        addGameButton.setFont((Font.loadFont("file:font/Cantarell-Bold.otf", 20)));

        TextField titleTextField = new TextField();
        titleTextField.setPromptText("Game title");

        ComboBox companyNameComboBox = new ComboBox();
        companyNameComboBox.getItems().addAll(companyNames);
        companyNameComboBox.setPromptText("Company name");

        TextField yearTextField = new TextField();
        yearTextField.setPromptText("Year of release");
        TextField ratingTextField = new TextField();
        ratingTextField.setPromptText("Game rating");

        ComboBox genreComboBox = new ComboBox();
        genreComboBox.getItems().addAll(genresNames);
        genreComboBox.setPromptText("Genre");

        ComboBox genreComboBox2 = new ComboBox();
        genreComboBox2.getItems().addAll(genresNames);
        genreComboBox2.setPromptText("Second genre");


        addGameButton.setOnAction(event -> {
            Game gamePlaylist = null;
            Game gamePlaylist2 = null;

            if (genreComboBox2.getValue() != null) {
                gamePlaylist2 = new Game(titleTextField.getText(), companyNameComboBox.getValue().toString(), parseInt(yearTextField.getText()), parseDouble(ratingTextField.getText()),
                        Genre.asList(genreComboBox.getValue().toString().toUpperCase() + ";" + genreComboBox2.getValue().toString().toUpperCase()));

                gamesPlaylist.add(gamePlaylist2);

            } else {
                gamePlaylist = new Game(titleTextField.getText(), companyNameComboBox.getValue().toString(), parseInt(yearTextField.getText()), parseDouble(ratingTextField.getText()),
                        Genre.asList(genreComboBox.getValue().toString().toUpperCase()));
                gamesPlaylist.add(gamePlaylist);

            }

            try {
                if (genreComboBox2.getValue() != null){
                    assert gamePlaylist2 != null;
                    DBController2.saveGameInDBPlaylist(gamePlaylist2);
                } else {
                    assert gamePlaylist != null;
                    DBController2.saveGameInDBPlaylist(gamePlaylist);
                }
                titleTextField.setText("");
                yearTextField.setText("");
                ratingTextField.setText("");
                JOptionPane.showMessageDialog(null, "Game added!");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                JOptionPane.showMessageDialog(null, "Failed to add game");
            }
        });


        Button backButton = new Button("Go back");
        backButton.setFont((Font.loadFont("file:font/Cantarell-Bold.otf", 20)));
        backButton.setOnAction(event -> {
            mainWindow();
        });

        Button openListButton2 = new Button("Open list");
        openListButton2.setFont((Font.loadFont("file:font/Cantarell-Bold.otf", 20)));
        openListButton2.setOnAction(event -> {
            try {
                playListGameWindow();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        root.add(addGameToPlText, 0, 0);
        root.add(titleTextField, 1, 0);
        root.add(companyNameComboBox, 2,0);
        root.add(yearTextField, 1,1);
        root.add(ratingTextField, 2,1);
        root.add(genreComboBox, 1, 2);
        root.add(genreComboBox2, 2, 2);
        root.add(addGameButton, 3,1);
        root.add(openListButton2, 1, 4);
        root.add(backButton, 1, 5);

        Scene scene = new Scene(root, WIDTH, HEIGHT);
        stage.setScene(scene);
        stage.show();
    }

    public void statsWindow() {
        root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(50));
        root.setHgap(100);
        root.setVgap(50);
        root.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        Text whatFavGame = new Text("What is your favorite genre?");
        whatFavGame.setFont((Font.loadFont("file:font/Cantarell-Regular.otf", 20)));
        whatFavGame.setFill(Color.WHITE);
        Button findOut = new Button("Find out");
        findOut.setFont((Font.loadFont("file:font/Cantarell-Bold.otf", 20)));
        findOut.setOnAction(event -> {
            favGenreWindow();
                    });

        Text whatAvgRating = new Text("What is your average rating?");
        whatAvgRating.setFont((Font.loadFont("file:font/Cantarell-Regular.otf", 20)));
        whatAvgRating.setFill(Color.WHITE);
        Button findOut2 = new Button("Find out");
        findOut2.setFont((Font.loadFont("file:font/Cantarell-Bold.otf", 20)));
        findOut2.setOnAction(event -> {
            avgRatingWindow();
        });

        Text whatPlayNext = new Text("What should you play next?");
        whatPlayNext.setFont((Font.loadFont("file:font/Cantarell-Regular.otf", 20)));
        whatPlayNext.setFill(Color.WHITE);
        Button findOut3 = new Button("Find out");
        findOut3.setFont((Font.loadFont("file:font/Cantarell-Bold.otf", 20)));
        findOut3.setOnAction(event -> {
            whatPlayNextWindow();
        });

        Button backButton = new Button("Go back");
        backButton.setFont((Font.loadFont("file:font/Cantarell-Bold.otf", 20)));
        backButton.setOnAction(event -> {
            mainWindow();
        });

        root.add(whatFavGame, 1,0);
        root.add(findOut, 2, 0);
        root.add(whatAvgRating, 1, 1);
        root.add(findOut2, 2,1);
        root.add(whatPlayNext, 1, 2);
        root.add(findOut3, 2,2);
        root.add(backButton, 1, 4);


        Scene scene = new Scene(root, WIDTH, HEIGHT);
        stage.setScene(scene);
        stage.show();
    }

    public void listGameWindow() throws SQLException {
        root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(50));
        root.setHgap(100);
        root.setVgap(50);
        root.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        Text listOfYourGamesText = new Text("List of your games:");
        listOfYourGamesText.setFont((Font.loadFont("Cantarell-Bold.otf", 20)));
        listOfYourGamesText.setFill(Color.WHITE);

        Text searchDbText = new Text("Search your games:");
        searchDbText.setFont(Font.loadFont("file:font/Cantarell-Regular.otf", 20));
        searchDbText.setFill(Color.WHITE);
        TextField searchDbTextField = new TextField();
        Button searchGameButton = new Button("Search");
        searchGameButton.setFont(Font.loadFont("file:font/Cantarell-Bold.otf", 20));

        searchGameButton.setOnAction(event -> {
            String searchedGame = (searchDbTextField.getText());
            resultGame = Filters.getFilteredGames(games, searchedGame);
            searchDbTextField.setText("");
            searchWindow();
        });

        TableView <Game> tableView = new TableView();
        tableView.setMinSize(400, 200);

        TableColumn<Game, String> column1 = new TableColumn <>("Game Name");
        column1.setMinWidth(300);
        TableColumn<Game, String> column2 = new TableColumn <>("Company Name");
        TableColumn<Game, Integer> column3 = new TableColumn <>("Year Of Release");
        TableColumn<Game, Double> column4 = new TableColumn <>("User rating");
        TableColumn<Game, List<Genre>> column5 = new TableColumn <>("Genre");
        column1.setCellValueFactory(new PropertyValueFactory <>("gameName"));
        column2.setCellValueFactory(new PropertyValueFactory <>("companyName"));
        column3.setCellValueFactory(new PropertyValueFactory <>("yearOfRelease"));
        column4.setCellValueFactory(new PropertyValueFactory <>("userRating"));
        column5.setCellValueFactory(new PropertyValueFactory <>("genres"));

        tableView.getColumns().addAll(column1, column2, column3, column4, column5);

        tableView.getItems().addAll(games);

        Text removeGameFromDbText = new Text("Remove a game from your list:");
        removeGameFromDbText.setFont(Font.loadFont("file:font/Cantarell-Regular.otf", 20));
        removeGameFromDbText.setFill(Color.WHITE);
        Button removeGameButton = new Button("Remove");
        removeGameButton.setFont(Font.loadFont("file:font/Cantarell-Bold.otf", 20));

        TextField title1TextField = new TextField();
        title1TextField.setPromptText("Game title");


        removeGameButton.setOnAction(event -> {
            Game gameRemove = new Game(title1TextField.getText(), null, 0, 0, null);
            games.remove(gameRemove);
            tableView.getItems().remove(gameRemove);

            try {
                DBController.removeGameFromDb(gameRemove);
                title1TextField.setText("");
                JOptionPane.showMessageDialog(null, "Game removed!");
                tableView.refresh();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                JOptionPane.showMessageDialog(null, "Failed to remove game");
            }



        });




        root.add(tableView, 1,1, 3, 1);

        listOfYourGamesText.setFont((Font.loadFont("file:font/Cantarell-Regular.otf", 20)));
        listOfYourGamesText.setFill(Color.WHITE);

        Button backButton = new Button("Go back");
        backButton.setFont((Font.loadFont("file:font/Cantarell-Bold.otf", 20)));
        backButton.setOnAction(event -> {
            gameDbWindow();
        });

        root.add(listOfYourGamesText, 1, 0);
        root.add(searchDbText, 1, 3);
        root.add(searchDbTextField, 2, 3);
        root.add(searchGameButton, 3, 3);
        root.add(removeGameFromDbText, 1, 4);
        root.add(title1TextField, 2, 4);
        root.add(removeGameButton, 3, 4);
        root.add(backButton, 1, 5);


        Scene scene = new Scene(root, WIDTH, HEIGHT);
        stage.setScene(scene);
        stage.show();
    }

    public void playListGameWindow() throws SQLException {
        root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(50));
        root.setHgap(100);
        root.setVgap(50);
        root.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        Text listOfYourGamesText2 = new Text("List of games on your play list:");
        listOfYourGamesText2.setFont((Font.loadFont("file:font/Cantarell-Bold.otf", 20)));
        listOfYourGamesText2.setFill(Color.WHITE);

        TableView <Game> tableView = new TableView();
        tableView.setMinSize(400, 200);

        TableColumn<Game, String> column1 = new TableColumn <>("Game Name");
        column1.setMinWidth(300);
        TableColumn<Game, String> column2 = new TableColumn <>("Company Name");
        TableColumn<Game, Integer> column3 = new TableColumn <>("Year Of Release");
        TableColumn<Game, Double> column4 = new TableColumn <>("User rating");
        TableColumn<Game, List<Genre>> column5 = new TableColumn <>("Genre");
        column1.setCellValueFactory(new PropertyValueFactory <>("gameName"));
        column2.setCellValueFactory(new PropertyValueFactory <>("companyName"));
        column3.setCellValueFactory(new PropertyValueFactory <>("yearOfRelease"));
        column4.setCellValueFactory(new PropertyValueFactory <>("userRating"));
        column5.setCellValueFactory(new PropertyValueFactory <>("genres"));

        tableView.getColumns().addAll(column1, column2, column3, column4, column5);

        tableView.getItems().addAll(gamesPlaylist);

        root.add(tableView, 1,1, 3, 1);

        Text searchPlText = new Text("Search your play list:");
        searchPlText.setFont(Font.loadFont("file:font/Cantarell-Regular.otf", 20));
        searchPlText.setFill(Color.WHITE);
        TextField searchDbTextField = new TextField();
        Button searchGameButton = new Button("Search");
        searchGameButton.setFont(Font.loadFont("file:font/Cantarell-Bold.otf", 20));

        searchGameButton.setOnAction(event -> {
            String searchedGame = (searchDbTextField.getText());
            resultGamePl = Filters.getFilteredGames(gamesPlaylist, searchedGame);
            searchDbTextField.setText("");
            searchWindowPl();
        });

        Text removeGameFromPlText = new Text("Remove a game from your play list:");
        removeGameFromPlText.setFont((Font.loadFont("file:font/Cantarell-Regular.otf", 20)));
        removeGameFromPlText.setFill(Color.WHITE);
        Button removeGameButton = new Button("Remove");
        removeGameButton.setFont((Font.loadFont("file:font/Cantarell-Bold.otf", 20)));

        TextField title1TextField = new TextField();
        title1TextField.setPromptText("Game title");


        removeGameButton.setOnAction(event -> {
            Game gameRemove = new Game(title1TextField.getText(), null, 0, 0, null);
            gamesPlaylist.remove(gameRemove);
            tableView.getItems().remove(gameRemove);

            try {
                DBController2.removeGameFromDbPlaylist(gameRemove);
                title1TextField.setText("");
                JOptionPane.showMessageDialog(null, "Game removed!");
                tableView.refresh();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                JOptionPane.showMessageDialog(null, "Failed to remove game");
            }

        });

        Button backButton = new Button("Go back");
        backButton.setFont((Font.loadFont("file:font/Cantarell-Bold.otf", 20)));
        backButton.setOnAction(event -> {
            playListWindow();
        });

        root.add(listOfYourGamesText2, 1, 0);
        root.add(searchPlText, 1, 3);
        root.add(searchDbTextField, 2, 3);
        root.add(searchGameButton, 3, 3);
        root.add(removeGameFromPlText, 1, 4);
        root.add(title1TextField, 2, 4);
        root.add(removeGameButton, 3, 4);
        root.add(backButton, 1, 5);

        Scene scene = new Scene(root, WIDTH, HEIGHT);
        stage.setScene(scene);
        stage.show();
    }

    private void searchWindowPl() {
        root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(50));
        root.setHgap(100);
        root.setVgap(50);
        root.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        Text searchResultsText = new Text("Search results:");
        searchResultsText.setFont(Font.loadFont("file:font/Cantarell-Bold.otf", 20));
        searchResultsText.setFill(Color.WHITE);


        TableView <Game> searchTableView = new TableView();
        searchTableView.setMinSize(400, 200);

        TableColumn<Game, String> column1 = new TableColumn <>("Game Name");
        TableColumn<Game, String> column2 = new TableColumn <>("Company Name");
        TableColumn<Game, Integer> column3 = new TableColumn <>("Year Of Release");
        TableColumn<Game, Double> column4 = new TableColumn <>("User rating");
        TableColumn<Game, List<Genre>> column5 = new TableColumn <>("Genre");
        column1.setCellValueFactory(new PropertyValueFactory <>("gameName"));
        column2.setCellValueFactory(new PropertyValueFactory <>("companyName"));
        column3.setCellValueFactory(new PropertyValueFactory <>("yearOfRelease"));
        column4.setCellValueFactory(new PropertyValueFactory <>("userRating"));
        column5.setCellValueFactory(new PropertyValueFactory <>("genres"));

        searchTableView.getColumns().addAll(column1, column2, column3, column4, column5);

        searchTableView.getItems().addAll(resultGamePl);

        Button backButton = new Button("Go back");
        backButton.setFont(Font.loadFont("file:font/Cantarell-Bold.otf", 20));
        backButton.setOnAction(event -> {
            try {
                playListGameWindow();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        Text removeGameFromDbText = new Text("Remove a game from your list:");
        removeGameFromDbText.setFont(Font.loadFont("file:font/Cantarell-Regular.otf", 20));
        removeGameFromDbText.setFill(Color.WHITE);
        Button removeGameButton = new Button("Remove");
        removeGameButton.setFont(Font.loadFont("file:font/Cantarell-Bold.otf", 20));

        TextField title1TextField = new TextField();
        title1TextField.setPromptText("Game title");


        removeGameButton.setOnAction(event -> {
            Game gameRemove = new Game(title1TextField.getText(), null, 0, 0, null);
            gamesPlaylist.remove(gameRemove);
            searchTableView.getItems().remove(gameRemove);

            try {
                DBController.removeGameFromDb(gameRemove);
                title1TextField.setText("");
                searchTableView.refresh();
                JOptionPane.showMessageDialog(null, "Game removed!");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                JOptionPane.showMessageDialog(null, "Failed to remove game");
            }

        });


        root.add(searchTableView, 1,2, 2, 1);
        root.add(searchResultsText, 1, 1);
        root.add(removeGameFromDbText, 1, 3);
        root.add(title1TextField, 2, 3);
        root.add(removeGameButton, 3, 3);
        root.add(backButton, 1, 4);

        Scene scene = new Scene(root, WIDTH, HEIGHT);
        stage.setScene(scene);
        stage.show();
    }

    public void favGenreWindow() {
        root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(50));
        root.setHgap(100);
        root.setVgap(50);
        root.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        Text yourFavGenreText = new Text("Your fav genre is:");
        yourFavGenreText.setFont((Font.loadFont("file:font/Cantarell-Regular.otf", 20)));
        yourFavGenreText.setFill(Color.WHITE);

        Text favGenreText = new Text();
        favGenreText.setFont((Font.loadFont("file:font/Cantarell-Bold.otf", 20)));
        favGenreText.setFill(Color.WHITE);
        favGenreText.setText(String.valueOf(Game.getFavoriteGenre(games)));

        Button backButton = new Button("Go back");
        backButton.setFont((Font.loadFont("file:font/Cantarell-Bold.otf", 20)));
        backButton.setOnAction(event -> {
            statsWindow();
        });

        root.add(yourFavGenreText, 1, 0);
        root.add(favGenreText, 1, 1);
        root.add(backButton, 1, 4);

        Scene scene = new Scene(root, WIDTH, HEIGHT);
        stage.setScene(scene);
        stage.show();
    }

    public void avgRatingWindow(){
        root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(50));
        root.setHgap(100);
        root.setVgap(50);
        root.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        Text yourAvgRatingText = new Text("Your average rating is:");
        yourAvgRatingText.setFont((Font.loadFont("file:font/Cantarell-Regular.otf", 20)));
        yourAvgRatingText.setFill(Color.WHITE);

        Text avgRatingText = new Text();
        avgRatingText.setText(String.valueOf(Game.getAverageRating(games)));
        avgRatingText.setFont((Font.loadFont("file:font/Cantarell-Bold.otf", 20)));
        avgRatingText.setFill(Color.WHITE);

        Button backButton = new Button("Go back");
        backButton.setFont((Font.loadFont("file:font/Cantarell-Bold.otf", 20)));
        backButton.setOnAction(event -> {
            statsWindow();
        });

        root.add(yourAvgRatingText, 1, 0);
        root.add(avgRatingText, 1, 1);
        root.add(backButton, 1, 4);


        Scene scene = new Scene(root, WIDTH, HEIGHT);
        stage.setScene(scene);
        stage.show();
    }

    public void whatPlayNextWindow() {
        root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(50));
        root.setHgap(100);
        root.setVgap(50);
        root.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        Text whatPlayNextText = new Text("You should play ");
        whatPlayNextText.setFont((Font.loadFont("file:font/Cantarell-Regular.otf", 20)));
        whatPlayNextText.setFill(Color.WHITE);

        Text playNextGameText = new Text();
        playNextGameText.setText(Game.getNextGameToPlay(Game.getFrequentGenres(), gamesPlaylist).getGameName());
        playNextGameText.setFont((Font.loadFont("file:font/Cantarell-Bold.otf", 23)));
        playNextGameText.setFill(Color.WHITE);

        Text whatPlayNextText2 = new Text("next!");
        whatPlayNextText2.setFont((Font.loadFont("file:font/Cantarell-Bold.otf", 20)));
        whatPlayNextText2.setFill(Color.WHITE);

        Button backButton = new Button("Go back");
        backButton.setFont((Font.loadFont("file:font/Cantarell-Bold.otf", 20)));
        backButton.setOnAction(event -> {
            statsWindow();
        });

        root.add(whatPlayNextText, 1, 0);
        root.add(playNextGameText, 1, 1);
        root.add(whatPlayNextText2, 1, 2);
        root.add(backButton, 1, 4);

        Scene scene = new Scene(root, WIDTH, HEIGHT);
        stage.setScene(scene);
        stage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }
}

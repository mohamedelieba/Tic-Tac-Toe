
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author elieba
 */
public class DataBase {

    Connection connection = null;
    private final String url = "jdbc:postgresql://localhost/tictactoe";
    private final String user = "elieba";
    private final String psqlPassword = "13058";

    /**
     * Connect to the PostgreSQL database
     *
     * @return a Connection object
     */
    public void insertPlayer(TextField textField, PasswordField passwordField) {

        try {
            //connection = DriverManager.getConnection(url, user, psqlPassword);
            //System.out.println("Connected to the PostgreSQL server successfully.");
            String userNameValue = textField.getText();
            String passwordValue = passwordField.getText();
            String query = "INSERT INTO player(name, password) VALUES(?, ?)";

            try (Connection con = DriverManager.getConnection(url, user, psqlPassword);
                    PreparedStatement pst = con.prepareStatement(query)) {

                pst.setString(1, userNameValue);
                pst.setString(2, passwordValue);

                if (textField.getText().isEmpty() || passwordField.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("you should complete your account first!");
                    //System.out.println("this user name is alredy taken");
                    alert.showAndWait();
                } else {
                    pst.executeUpdate();

                }
                pst.close();
                con.close();

            } catch (SQLException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("This user name is already taken");
                alert.showAndWait();
            }
        } catch (Exception e) {
            //System.out.println(e.getMessage());
        }
    }

    /**
     * @param playerName
     * @param passwordField
     * @throws SQLException
     */
    public void login(TextField playerName, PasswordField passwordField) throws SQLException {
        String userNameValue = playerName.getText();
        String passwordValue = passwordField.getText();

        connection = DriverManager.getConnection(url, user, psqlPassword);
        PreparedStatement pst;
        pst = connection.prepareStatement("select * from player where name like ? and password = ? ;");

        pst.setString(1, userNameValue);
        pst.setString(2, passwordValue);

        try (ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {

                if (AppManager.isSingleModeOn) {
                    playerName.clear();
                    passwordField.clear();
                    AppManager.viewPane(AppManager.gamePane);
                } else {
                    AppManager.viewPane(AppManager.multiplier);
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Wrong user name or password");
                alert.showAndWait();

            }
            pst.close();
            connection.close();
        }
    }

    public void addWin(Label text) {
        try {

            String userNameValue = text.getText();
            String query = "UPDATE player set win = win+1 where name like ?";

            try (Connection con = DriverManager.getConnection(url, user, psqlPassword);
                    PreparedStatement pst = con.prepareStatement(query)) {

                pst.setString(1, userNameValue);
                pst.executeUpdate();

                pst.close();
                con.close();

            }

        } catch (SQLException ex) {

        }
    }

    public void addLoss(Label text) {
        Connection connection = null;
        try {
            String userNameValue = text.getText();
            String query = "UPDATE player set lose = lose+1 where name like ?";

            try (Connection con = DriverManager.getConnection(url, user, psqlPassword);
                    PreparedStatement pst = con.prepareStatement(query)) {

                pst.setString(1, userNameValue);
                pst.executeUpdate();

                pst.close();
                con.close();

            }

        } catch (SQLException ex) {

        }
    }

    public void addDraw(Label text) {
        Connection connection = null;
        try {
            String userNameValue = text.getText();
            String query = "UPDATE player set draw = draw+1 where name like ?";

            try (Connection con = DriverManager.getConnection(url, user, psqlPassword);
                    PreparedStatement pst = con.prepareStatement(query)) {

                pst.setString(1, userNameValue);
                pst.executeUpdate();

                pst.close();
                con.close();

            }

        } catch (SQLException ex) {

        }

    }

    public void addGame(Label firstPlayerName, Label secondPlayerName, String winnerPlayerName) {
        Connection connection = null;
        try {
            String first = firstPlayerName.getText();
            String second = secondPlayerName.getText();
            String winner = winnerPlayerName;
            String query = "INSERT INTO game(first_player_name, second_player_name, winner_player_name) VALUES(?, ?, ?)";
            try (Connection con = DriverManager.getConnection(url, user, psqlPassword);
                    PreparedStatement pst = con.prepareStatement(query)) {

                pst.setString(1, first);
                pst.setString(2, second);
                pst.setString(3, winner);
                pst.executeUpdate();
                pst.close();
                con.close();

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertRecord(int moves) {
        Connection connection = null;
        try {
            String query1 = "SELECT MAX (id) FROM game";
            String query2 = "INSERT INTO move (game_id, position) VALUES(?, ?)";

            int gameId = 0;

            try (Connection con = DriverManager.getConnection(url, user, psqlPassword);
                    PreparedStatement pst1 = con.prepareStatement(query1);
                    PreparedStatement pst2 = con.prepareStatement(query2)) {
                ResultSet rs = pst1.executeQuery();
                if (rs.next()) {
                    gameId = rs.getInt(1);
                }

                pst2.setInt(1, gameId);
                pst2.setInt(2, moves);
                pst2.executeUpdate();
                pst2.close();
                con.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Game> retreiveGame() throws SQLException {
        ObservableList<Game> games = FXCollections.observableArrayList();

        connection = DriverManager.getConnection(url, user, psqlPassword);
        String query = "SELECT * FROM game";

        PreparedStatement pst;
        pst = connection.prepareStatement(query);

        ResultSet resultSet = pst.executeQuery();
        while (resultSet.next()) {
            games.add(
                    new Game(
                            resultSet.getInt("id"),
                            resultSet.getString("first_player_name"),
                            resultSet.getString("second_player_name"),
                            resultSet.getString("winner_player_name"))
            );
        }
        return games;
    }

    public ObservableList<Player> retreivePlayer() throws SQLException {
        ObservableList<Player> players = FXCollections.observableArrayList();

        connection = DriverManager.getConnection(url, user, psqlPassword);
        String query = "SELECT * FROM player where name not in ('computer','Computer') ";

        PreparedStatement pst;
        pst = connection.prepareStatement(query);

        ResultSet resultSet = pst.executeQuery();
        while (resultSet.next()) {
            players.add(
                    new Player(
                            resultSet.getString("name"),
                            resultSet.getInt("win"),
                            resultSet.getInt("lose"),
                            resultSet.getInt("draw"))
            );
        }
        return players;
    }

    public ArrayList retreiveRecord(int id) throws SQLException {

        ArrayList<Integer> moves = new ArrayList<Integer>();
        connection = DriverManager.getConnection(url, user, psqlPassword);
        String query = "SELECT position FROM move where game_id  = ?";

        PreparedStatement pst;
        pst = connection.prepareStatement(query);

        pst.setInt(1, id);

        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            moves.add(rs.getInt("position"));
        }

        return moves;
    }

}

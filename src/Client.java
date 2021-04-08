
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author elieba
 */
public class Client implements Runnable {

    Socket s;
    DataInputStream dis;
    static PrintStream ps;
    Thread thread;

    public Client() {
        try {
            s = new Socket(InetAddress.getLocalHost(), 10000);
            dis = new DataInputStream(s.getInputStream());
            ps = new PrintStream(s.getOutputStream());

        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("there is no available server");
            System.exit(0);
        }

        thread = new Thread(this);
        thread.start();

    }

    @Override
    public void run() {
        while (true) {

            try {
                String msg = dis.readLine();
                System.out.println(msg);
                if (msg.contains("X") && AppManager.multiplier.isFirstPlayerTurn) {
                    Platform.runLater(() -> {
                        try {
                            AppManager.multiplier.showOthersSymbols(msg);
                        } catch (SQLException ex) {
                            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        AppManager.multiplier.isFirstPlayerTurn = false;
                    });
                } else if (msg.contains("O")) {
                    Platform.runLater(() -> {
                        try {
                            AppManager.multiplier.showOthersSymbols(msg);
                        } catch (SQLException ex) {
                            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        AppManager.multiplier.isFirstPlayerTurn = true;
                    });
                } else if (msg.contains("name")) {
                    if (AppManager.multiplier.firstPlayer.getText().isEmpty()) {
                        Platform.runLater(() -> {
                            MultiPlayerPane.setFirstPlayerName(msg);

                        });
                    } else {
                        Platform.runLater(() -> {
                            MultiPlayerPane.setSecondPlayerName(msg);
                        });
                    }


                }
//                else if (msg.contains("clearBoard")) {
//
//                    Platform.runLater(() -> {
//                        AppManager.multiplier.clearBoard();
//                    });
//                }
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

}

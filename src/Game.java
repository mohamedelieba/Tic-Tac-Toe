/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author elieba
 */
public class Game {
            
        private String first;
        private String second;
        private String winner;
        private int id ;
        public Game(int id, String first, String second, String winner) {
            this.first = first;
            this.second = second;
            this.winner = winner;
            this.id = id;
        }

    public void setFirst(String first) {
        this.first = first;
    }

    public void setSecond(String second) {
        this.second = second;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst() {
        return first;
    }

    public String getSecond() {
        return second;
    }

    public String getWinner() {
        return winner;
    }

    public int getId() {
        return id;
    }
        
    
}

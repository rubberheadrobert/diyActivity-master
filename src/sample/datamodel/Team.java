package sample.datamodel;

import java.util.ArrayList;
import java.util.List;

public class Team {

    private List<Player> players;
    private String name;
    private int order;
    private int playerPlace;

    public Team(String name, int order) {
        this.name = name;
        this.players = new ArrayList<>();
        this.order = order;
        this.playerPlace = 0;
    }

    public void addPlayer(Player player){
        players.add(player);
    }

    public int getTeamPoints(){
        int totalPoints = 0;
        for (Player p : players) {
            totalPoints += p.getPoints();
        }
        return totalPoints;
    }

    public Player getPlayer(int id){
        return players.get(id);
    }

    public List<Player> getPlayers() {
        return players;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getPlayerPlace() {
        return playerPlace;
    }

    public void setPlayerPlace(int playerPlace) {
        this.playerPlace = playerPlace;
    }

    public void incrementPlayerPlace(){
        if((playerPlace + 1) < players.size()){
            playerPlace++;
        } else{
            playerPlace = 0;
        }
    }

    public Player getNextPlayer(){
        if((playerPlace + 1) < players.size()){
            playerPlace++;
        } else{
            playerPlace = 0;
        }
        return players.get(playerPlace);
    }


    @Override
    public String toString() {
        String s = "";
        s += "Team : " + name + "\n";
        s += "Players : ";
        for (Player p : players) {
            s += "\n" +  p.getName();
            s += "\n" + p.getPlace();
        }
        return s;
    }
}

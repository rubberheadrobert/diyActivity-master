package sample.datamodel;

import java.util.List;

public class Game {

    private List<Team> teams;
    private List<String> words;
    private List<String> usedWords;
    private int roundLength;
    private int teamPlace;

    public Game(List<Team> teams, List<String> words, int roundLength) {
        this.teams = teams;
        this.words = words;
        this.roundLength = roundLength;
        teamPlace = 0;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public List<String> getWords() {
        return words;
    }

    public void setWords(List<String> words) {
        this.words = words;
    }

    public List<String> getUsedWords() {
        return usedWords;
    }

    public void setUsedWords(List<String> usedWords) {
        this.usedWords = usedWords;
    }

    public int getRoundLength() {
        return roundLength;
    }

    public void setRoundLength(int roundLength) {
        this.roundLength = roundLength;
    }

    public int getTeamPlace() {
        return teamPlace;
    }

    public void incrementTeamPlace(){
        teamPlace++;
    }

    public boolean allTeamsHavePlayed(){
        if((teamPlace + 1) < teams.size()){
            return false;
        } else {
            return true;
        }
    }


    public void setTeamPlace(int teamPlace) {
        this.teamPlace = teamPlace;
    }

    @Override
    public String toString() {
        String s = "";
        s += "Game: ";
        for (Team team : teams ) {
            team.toString();
        }
        s += roundLength + " seconds";
        return s;
    }
}

package sample.datamodel;

public class Player {
    private String name;
    private int points;
    private int place;

    public Player(String name, int place) {
        this.name = name;
        this.points = 0;
        this.place = place;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void addPoint(){
        this.points += 1;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", points=" + points +
                ", place=" + place +
                '}';
    }
}

package nmct.jaspernielsmichielhein.watchfriends.model;

public enum Status {
    BRONZE ("Bronze"),
    SILVER ("Silver"),
    GOLD   ("Gold");

    private final String name;
    private String color;

    Status(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }
}

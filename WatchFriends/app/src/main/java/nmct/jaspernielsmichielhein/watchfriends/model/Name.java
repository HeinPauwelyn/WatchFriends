package nmct.jaspernielsmichielhein.watchfriends.model;


public class Name {
    private String givenName;
    private String middleName;
    private String lastName;

    public Name(String givenName, String middleName, String lastName) {
        this.givenName = givenName;
        this.middleName = middleName;
        this.lastName = lastName;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}

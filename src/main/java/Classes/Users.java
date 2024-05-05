package Classes;

public class Users {

    // Attributes
    private String username;
    private String name;
    private String surname;
    private String type;
    private String password;
    private static int usersCounter = 0;

    // Getters and Setters

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Default Constructor
    public Users(){

    }

    // Constructor
    public Users(String username, String name, String surname, String type) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.type = type;
        usersCounter++;
    }

    // Methods
    public void register(){
        System.out.println("register called from " + this.getUsername());
    }
    public void logIn(){
        System.out.println("logIn called from " + this.getUsername());
    }
    public void logOut(){
        System.out.println("logOut called from " + this.getUsername());
    }

}

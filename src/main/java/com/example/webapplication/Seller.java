package mainpackage;

public class Seller extends Users{

    // Methods
    public void newClient(){System.out.println("newClient called from " + this.getUsername()); }

    public void newBill(){
        System.out.println("newBill called from " + this.getUsername());
    }

    public void changeProgram(){
        System.out.println("changeProgram called from " + this.getUsername());
    }

    // Constructor
    public Seller(String username, String name, String surname, String type){
        super(username, name, surname, type);
    }
}

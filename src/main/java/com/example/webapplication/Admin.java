package mainpackage;

public class Admin extends Users{

    // Methods
    public void createSeller(){
        System.out.println("createSeller called from " + this.getUsername());
    }

    public void deleteSeller(){
        System.out.println("deleteSeller called from " + this.getUsername());
    }

    public void createClient(){
        System.out.println("createClient called from " + this.getUsername());
    }

    public void deleteClient(){
        System.out.println("deleteClient called from " + this.getUsername());
    }

    public void createProgram(){
        System.out.println("createProgram called from " + this.getUsername());
    }

    public void deleteProgram(){
        System.out.println("deleteProgram called from " + this.getUsername());
    }

    // Constructor
    public Admin(String username, String name, String surname, String type){
        super(username, name, surname, type);
    }
}

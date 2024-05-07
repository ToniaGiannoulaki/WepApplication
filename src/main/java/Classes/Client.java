package Classes;

public class Client extends Users{

    // Attributes
    private String address;
    private final String AFM;
    private String phoneNumber;
    private String programName;

    // Getters and Setters
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAFM() {
        return AFM;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    //Constructor
    public Client(String username, String name, String surname, String type, String AFM, String address, String phoneNumber) {
        super(username, name, surname, type);
        this.AFM = AFM;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    // Methods
    public void viewBill(){
        System.out.println("viewBill called from " + this.getUsername());
    }

    public void viewHistory(){
        System.out.println("viewHistory called from " + this.getUsername());
    }

    public void payBill(){
        System.out.println("payBill called from " + this.getUsername());
    }
}

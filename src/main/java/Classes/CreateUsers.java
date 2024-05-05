package Classes;

import java.util.Scanner;

public class CreateUsers {
    public static void main(String[] args) {

        // Create instance of a Client
        Scanner scanner = new Scanner(System.in);

        // Variables to hold user input
        String username = "";
        String firstName = "";
        String lastName = "";
        String address = "";
        String type = "Client";
        String AFM = "";
        String phoneNumber = "";

        System.out.println("Enter username:");
        username = scanner.nextLine();

        // Validate first name
        while (true) {
            System.out.println("Enter first name (no digits or symbols):");
            firstName = scanner.nextLine();
            if (!firstName.matches("^[A-Za-z]+$")) { // Only letters are allowed
                System.out.println("Invalid first name. Please use only letters.");
            } else {
                break; // Valid input
            }
        }

        // Validate last name
        while (true) {
            System.out.println("Enter last name (no digits or symbols):");
            lastName = scanner.nextLine();
            if (!lastName.matches("^[A-Za-z]+$")) { // Only letters are allowed
                System.out.println("Invalid last name. Please use only letters.");
            } else {
                break; // Valid input
            }
        }

        System.out.println("Enter address:");
        address = scanner.nextLine();

        // Validate ID (assuming an 8-digit number is required)
        while (true) {
            System.out.println("Enter AFM (9-digit number):");
            AFM = scanner.nextLine();
            if (!AFM.matches("\\d{9}")) {
                System.out.println("Invalid AFM. AFM must be an 9-digit number.");
            } else {
                break; // Valid input
            }
        }

        // Validate phone number (assuming a 10-digit number is required)
        while (true) {
            System.out.println("Enter phone number (10-digit number):");
            phoneNumber = scanner.nextLine();
            if (!phoneNumber.matches("\\d{10}")) {
                System.out.println("Invalid phone number. Phone number must be a 10-digit number.");
            } else {
                break; // Valid input
            }
        }

        // Create Client object with validated user input
        Client client1 = new Client(username, firstName, lastName, address, type, AFM, phoneNumber);

        client1.viewBill();
        client1.viewHistory();
        client1.payBill();
        client1.register();
        client1.logIn();
        client1.logOut();
        System.out.println("End client");

        // Create instance of a Seller
        scanner = new Scanner(System.in);

        // Variables to hold user input
        username = "";
        firstName = "";
        lastName = "";
        address = "";
        type = "Seller";

        // Validate first name
        while (true) {
            System.out.println("Enter first name (no digits or symbols):");
            firstName = scanner.nextLine();
            if (!firstName.matches("^[A-Za-z]+$")) { // Only letters are allowed
                System.out.println("Invalid first name. Please use only letters.");
            } else {
                break; // Valid input
            }
        }

        // Validate last name
        while (true) {
            System.out.println("Enter last name (no digits or symbols):");
            lastName = scanner.nextLine();
            if (!lastName.matches("^[A-Za-z]+$")) { // Only letters are allowed
                System.out.println("Invalid last name. Please use only letters.");
            } else {
                break; // Valid input
            }
        }

        Seller seller1 = new Seller(username, firstName, lastName, type);

        System.out.println("\nSeller created:\n" + "Username: " + seller1.getUsername()
                + "\nName: " + seller1.getName() + "\nSurname: " + seller1.getSurname());
        seller1.changeProgram();
        seller1.newBill();
        seller1.newClient();
        System.out.println("End seller");

        // Create instance of an Admin
        Admin admin1 = new Admin("ToniaG", "Tonia", "Giannoulaki" , "Administrator");

        System.out.println("\nAdmin created:\n" + "Username: " + admin1.getUsername()
                + "\nName: " + admin1.getName() + "\nSurname: " + admin1.getSurname());
        admin1.createProgram();
        admin1.deleteProgram();
        admin1.createClient();
        admin1.createSeller();
        admin1.deleteClient();
        admin1.deleteSeller();
        System.out.println("End admin");
    }
}

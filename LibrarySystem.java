import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LibrarySystem {

    static class Logger {
        public static void logInfo(String message) {
            System.out.println("[INFO] " + message);
        }
        public static void logError(String message) {
            System.out.println("[ERROR] " + message);
        }
        public static void logSuccess(String message) {
            System.out.println("[SUCCESS] " + message);
        }
    }

    static abstract class LibraryItem {
        private int id;
        private String title;
        private boolean isBorrowed;
        private Patron currentHolder;

        public LibraryItem(int id, String title) {
            this.id = id;
            this.title = title;
            this.isBorrowed = false;
            this.currentHolder = null;
        }

        public int getId() { return id; }
        public String getTitle() { return title; }
        public boolean isBorrowed() { return isBorrowed; }
        public Patron getCurrentHolder() { return currentHolder; }

        public void checkOut(Patron p) {
            this.isBorrowed = true;
            this.currentHolder = p;
        }

        public void returnItem() {
            this.isBorrowed = false;
            this.currentHolder = null;
        }

        public abstract String getItemType();

        @Override
        public String toString() {
            String holderName = (currentHolder != null) ? " (Held by: " + currentHolder.getName() + ")" : "";
            return String.format("ID: %d | Type: %-4s | Title: %-15s | Status: %s%s", 
                id, getItemType(), title, (isBorrowed ? "OUT" : "IN"), holderName);
        }
    }

    static class Book extends LibraryItem {
        private String author;

        public Book(int id, String title, String author) {
            super(id, title);
            this.author = author;
        }

        @Override
        public String getItemType() { return "BOOK"; }

        @Override
        public String toString() {
            return super.toString() + " | Author: " + author;
        }
    }

    static class DVD extends LibraryItem {
        private int durationMinutes;

        public DVD(int id, String title, int durationMinutes) {
            super(id, title);
            this.durationMinutes = durationMinutes;
        }

        @Override
        public String getItemType() { return "DVD"; }

        @Override
        public String toString() {
            return super.toString() + " | Runtime: " + durationMinutes + " mins";
        }
    }

    static class Patron {
        private int patronId;
        private String name;

        public Patron(int patronId, String name) {
            this.patronId = patronId;
            this.name = name;
        }

        public int getPatronId() { return patronId; }
        public String getName() { return name; }
    }

    private static List<LibraryItem> inventory = new ArrayList<>();
    private static List<Patron> patrons = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static int itemIdCounter = 1;
    private static int patronIdCounter = 1;

    public static void main(String[] args) {
        seedData();
        
        System.out.println("=== Advanced Library System ===");
        boolean running = true;

        while (running) {
            printMenu();
            int choice = getValidIntInput();

            switch (choice) {
                case 1:
                    listAllItems();
                    break;
                case 2:
                    listAllPatrons();
                    break;
                case 3:
                    addItem();
                    break;
                case 4:
                    registerPatron();
                    break;
                case 5:
                    performTransaction(true);
                    break;
                case 6:
                    performTransaction(false);
                    break;
                case 7:
                    running = false;
                    Logger.logInfo("System shutting down...");
                    break;
                default:
                    Logger.logError("Invalid selection.");
            }
        }
    }

    private static void seedData() {
        inventory.add(new Book(itemIdCounter++, "The Hobbit", "Tolkien"));
        inventory.add(new DVD(itemIdCounter++, "Inception", 148));
        patrons.add(new Patron(patronIdCounter++, "Alice Smith"));
    }

    private static void printMenu() {
        System.out.println("\n--- MENU ---");
        System.out.println("1. List Inventory");
        System.out.println("2. List Patrons");
        System.out.println("3. Add Item (Book/DVD)");
        System.out.println("4. Register Patron");
        System.out.println("5. Checkout Item");
        System.out.println("6. Return Item");
        System.out.println("7. Exit");
        System.out.print("Choice: ");
    }

    private static void listAllItems() {
        if (inventory.isEmpty()) Logger.logInfo("Inventory is empty.");
        else for (LibraryItem i : inventory) System.out.println(i);
    }

    private static void listAllPatrons() {
        if (patrons.isEmpty()) Logger.logInfo("No registered patrons.");
        else for (Patron p : patrons) System.out.println("ID: " + p.getPatronId() + " | Name: " + p.getName());
    }

    private static void addItem() {
        System.out.print("Enter type (1 for Book, 2 for DVD): ");
        int type = getValidIntInput();
        
        System.out.print("Enter Title: ");
        String title = scanner.next();

        if (type == 1) {
            System.out.print("Enter Author: ");
            String author = scanner.next();
            inventory.add(new Book(itemIdCounter++, title, author));
            Logger.logSuccess("Book added.");
        } else if (type == 2) {
            System.out.print("Enter Duration (mins): ");
            int duration = getValidIntInput();
            inventory.add(new DVD(itemIdCounter++, title, duration));
            Logger.logSuccess("DVD added.");
        } else {
            Logger.logError("Invalid Type.");
        }
    }

    private static void registerPatron() {
        System.out.print("Enter Patron Name: ");
        String name = scanner.next();
        patrons.add(new Patron(patronIdCounter++, name));
        Logger.logSuccess("Patron registered: " + name);
    }

    private static void performTransaction(boolean isBorrowing) {
        System.out.print("Enter Item ID: ");
        int itemId = getValidIntInput();
        LibraryItem item = findItem(itemId);

        if (item == null) {
            Logger.logError("Item not found.");
            return;
        }

        if (isBorrowing) {
            if (item.isBorrowed()) {
                Logger.logError("Item is already checked out.");
                return;
            }
            System.out.print("Enter Patron ID: ");
            int patronId = getValidIntInput();
            Patron p = findPatron(patronId);
            
            if (p != null) {
                item.checkOut(p);
                Logger.logSuccess(p.getName() + " borrowed " + item.getTitle());
            } else {
                Logger.logError("Patron not found.");
            }
        } else {
            if (!item.isBorrowed()) {
                Logger.logError("Item is not currently borrowed.");
            } else {
                item.returnItem();
                Logger.logSuccess("Item returned.");
            }
        }
    }

    private static LibraryItem findItem(int id) {
        return inventory.stream().filter(i -> i.getId() == id).findFirst().orElse(null);
    }

    private static Patron findPatron(int id) {
        return patrons.stream().filter(p -> p.getPatronId() == id).findFirst().orElse(null);
    }

    private static int getValidIntInput() {
        while (!scanner.hasNextInt()) {
            scanner.next(); 
            Logger.logError("Please enter a number.");
        }
        return scanner.nextInt();
    }
}

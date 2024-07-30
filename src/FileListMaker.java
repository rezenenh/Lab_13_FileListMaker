import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FileListMaker {
    private ArrayList<String> list;
    private boolean needsToBeSaved;
    private String currentFileName;

    public FileListMaker() {
        this.list = new ArrayList<>();
        this.needsToBeSaved = false;
        this.currentFileName = null;
    }

    public void addItem(String item) {
        list.add(item);
        needsToBeSaved = true;
    }

    public void deleteItem(int index) {
        if (index >= 0 && index < list.size()) {
            list.remove(index);
            needsToBeSaved = true;
        } else {
            System.out.println("Invalid index, please try again.");
        }
    }

    public void insertItem(int index, String item) {
        if (index >= 0 && index <= list.size()) {
            list.add(index, item);
            needsToBeSaved = true;
        } else {
            System.out.println("Invalid index, please try again.");
        }
    }

    public void moveItem(int fromIndex, int toIndex) {
        if (fromIndex >= 0 && fromIndex < list.size() && toIndex >= 0 && toIndex <= list.size()) {
            String item = list.remove(fromIndex);
            list.add(toIndex, item);
            needsToBeSaved = true;
        } else {
            System.out.println("Invalid index, please try again.");
        }
    }

    public void clearList() {
        list.clear();
        needsToBeSaved = true;
    }

    public void viewList() {
        if (list.isEmpty()) {
            System.out.println("The list is empty.");
        } else {
            for (int i = 0; i < list.size(); i++) {
                System.out.println(i + ": " + list.get(i));
            }
        }
    }

    public void saveList() {
        if (currentFileName == null) {
            System.out.println("No file loaded. Input 'S' to save with a new name.");
            return;
        }
        saveListAs(currentFileName);
    }

    public void saveListAs(String fileName) {
        String baseName = fileName + ".txt";
        try (PrintWriter writer = new PrintWriter(new FileWriter(baseName))) {
            for (String item : list) {
                writer.println(item);
            }
            needsToBeSaved = false;
            currentFileName = fileName;
            System.out.println("List has been saved to " + fileName);
        } catch (IOException e) {
            System.out.println("An error occurred while saving: " + e.getMessage());
        }
    }

    public void loadList(String fileName) {
        if (needsToBeSaved) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("You still have unsaved changes. Would you like to save them? (Y/N): ");
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("Y")) {
                saveList();
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            list.clear();
            String line;
            while ((line = reader.readLine()) != null) {
                list.add(line);
            }
            needsToBeSaved = false;
            currentFileName = fileName;
            System.out.println("List loaded from " + fileName);
        } catch (IOException e) {
            System.out.println("An error occurred while loading the list: " + e.getMessage());
        }
    }

    public boolean needsToBeSaved() {
        return needsToBeSaved;
    }

    public static void main(String[] args) {
        FileListMaker manager = new FileListMaker();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Options: A (Add), D (Delete), I (Insert), M (Move), C (Clear), V (View), O (Open), S (Save), Q (Quit)");
            System.out.print("Enter letter corresponding to an option: ");
            String option = scanner.nextLine().toUpperCase();

            switch (option) {
                case "A":
                    System.out.print("Please enter an item to add: ");
                    String addItem = scanner.nextLine();
                    manager.addItem(addItem);
                    break;
                case "D":
                    System.out.print("Please enter index to delete: ");
                    int deleteIndex = scanner.nextInt();
                    scanner.nextLine();
                    manager.deleteItem(deleteIndex);
                    break;
                case "I":
                    System.out.print("Please enter index to insert at: ");
                    int insertIndex = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Please enter item to insert: ");
                    String insertItem = scanner.nextLine();
                    manager.insertItem(insertIndex, insertItem);
                    break;
                case "M":
                    System.out.print("Please enter index to move from: ");
                    int fromIndex = scanner.nextInt();
                    System.out.print("Please enter index to move to: ");
                    int toIndex = scanner.nextInt();
                    scanner.nextLine();
                    manager.moveItem(fromIndex, toIndex);
                    break;
                case "C":
                    manager.clearList();
                    break;
                case "V":
                    manager.viewList();
                    break;
                case "O":
                    System.out.print("Please enter file name to open: ");
                    String openFileName = scanner.nextLine();
                    manager.loadList(openFileName);
                    break;
                case "S":
                    if (manager.currentFileName == null) {
                        System.out.print("Please enter file name to save as: ");
                        String saveFileName = scanner.nextLine();
                        manager.saveListAs(saveFileName);
                    } else {
                        manager.saveList();
                    }
                    break;
                case "Q":
                    if (manager.needsToBeSaved()) {
                        System.out.print("You still have unsaved changes. Would you like to save them before quitting? (Y/N): ");
                        String response = scanner.nextLine();
                        if (response.equalsIgnoreCase("Y")) {
                            if (manager.currentFileName == null) {
                                System.out.print("Please enter a file name to save as: ");
                                String quitSaveFileName = scanner.nextLine();
                                manager.saveListAs(quitSaveFileName);
                            } else {
                                manager.saveList();
                            }
                        }
                    }
                    System.out.println("Exiting program.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}
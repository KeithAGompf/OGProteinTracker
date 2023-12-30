package proteintracker;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import javax.swing.*;

public class tracker implements Meat_Interface {
    static String fileName = "protein.txt";
    static int weight= 180;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ProteinTrackerGUI();
            }
        });
    }

    // Loads file or creates if it's a brand new file
 // Loads file or creates if it's a brand new file
    private static void load_file(String filename, String date, double proteinIntake) {
        try {
            File myfile = new File(filename);
            if (myfile.createNewFile()) {
                System.out.println("File created: " + myfile.getName());
            }

            // Use FileWriter in append mode (second argument is true)
            FileWriter file = new FileWriter(filename, true);

            if (!check_Date(filename, date)) {
                System.out.println("Updating date");
                // If dates are not the same, add a new date
                file.append(date + "\n");
            }
            // Add protein after date has been checked
            file.append(Double.toString(proteinIntake) + "\n");
            file.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the file: " + e.getMessage());
            e.printStackTrace();
        }
    }


    // Check if the given date exists, otherwise load_file will create a new date
    private static boolean check_Date(String filename, String targetDate) {
        try {
            List<String> lines = Files.readAllLines(Path.of(filename));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");

            for (String line : lines) {
                LocalDate entryDate;
                try {
                    entryDate = LocalDate.parse(line, formatter);
                } catch (DateTimeParseException e) {
                    // Skip lines that do not have valid dates in the expected format
                    continue;
                }

                if (entryDate.format(formatter).equals(targetDate)) {
                    return true; // Return true if a matching date is found
                }
            }
        } catch (IOException e) {
            System.err.println("An error occurred while reading the file: " + e.getMessage());
        }

        return false; // Return false if the date is not found in the file or there's an error
    }

    // Gets the current date in the format MM-dd-yyyy
    private static String get_Date() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        return currentDateTime.format(formatter);
    }
    private static double days_protein(String filename, String startDate) {
	    double protein = 0.0;
	    boolean withinDateRange = false;

	    try {
	        List<String> lines = Files.readAllLines(Path.of(filename));

	        // Define a date format to match the format in the file
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");

	        for (String line : lines) {
	            if (!withinDateRange) {
	                // Try to parse the line as a date
	                LocalDate entryDate;
	                try {
	                    entryDate = LocalDate.parse(line, formatter);
	                    if (entryDate.format(formatter).equals(startDate)) {
	                        withinDateRange = true;
	                    }
	                } catch (DateTimeParseException e) {
	                    // Continue to the next line if the current line is not a valid date
	                    continue;
	                }
	            } else {
	                // Check if the current date is within the specified date range
	                LocalDate currentDate;
	                try {
	                    currentDate = LocalDate.parse(line, formatter);
	                    if (currentDate.isAfter(LocalDate.parse(startDate, formatter))) {
	                        break; // Exit the loop if we're past the end date
	                    }
	                } catch (DateTimeParseException e) {
	                    // Try to parse as a protein value
	                    try {
	                        double proteinValue = Double.parseDouble(line);
	                        protein += proteinValue;
	                    } catch (NumberFormatException ex) {
	                        // Skip lines that do not have valid protein values
	                        continue;
	                    }
	                }
	            }
	        }
	    } catch (IOException e) {
	        System.err.println("An error occurred while reading the file: " + e.getMessage());
	    }

	    return protein;
	}


    // Calculate protein intake for a meal with arbitrary ingredients
    public static double meal(List<Ingredient> ingredients) {
        double meal_protein = 0;

        for (Ingredient ingredient : ingredients) {
            double ingredientProtein = getIngredientProtein(ingredient);
            System.out.println(getIngredientProtein(ingredient));
            meal_protein += ingredientProtein;
        }

        return meal_protein;
    }

    // Define a class to represent an ingredient with its name and quantity
    public static class Ingredient {
        private String name;
        private double quantity;

        public Ingredient(String name, double quantity) {
            this.name = name;
            this.quantity = quantity;
        }

        public String getName() {
            return name;
        }

        public double getQuantity() {
            return quantity;
        }
    }

    // Calculate protein intake for different ingredients
    private static double getIngredientProtein(Ingredient ingredient) {
        String name = ingredient.getName();
        double quantity = ingredient.getQuantity();
        double proteinIntake = 0;
        System.out.println("name is "+name);
        double meat_protein;
        Beef cow = new Beef();
        Chicken chick = new Chicken();
        proteinIntake += chick.meat_protein();
        
        // Add more ingredient handling as needed

        return proteinIntake;
    }
}


package proteintracker;
import javax.swing.*;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
public class ProteinTrackerGUI {
    private JFrame frame;
    private JTextField dateField;
    private JComboBox<String> meatDropdown;
    private JComboBox<String> breadDropdown;
    private JComboBox<String> cheeseDropdown;
    private JComboBox<String> riceDropdown;
    private JButton addButton;
    private String font_Type = "Arial";

    public ProteinTrackerGUI() {
        frame = new JFrame("Protein Tracker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1600, 1000);  // Increase the frame size
        frame.setLayout(null);

        JLabel dateLabel = new JLabel("Date (MM-dd-yyyy):");
        dateLabel.setBounds(80, 80, 600, 80);  // Adjust bounds for labels
        dateLabel.setFont(new Font(font_Type, Font.PLAIN, 24));  // Set font size
        frame.add(dateLabel);

        dateField = new JTextField();
        dateField.setBounds(600, 80, 600, 80);  // Adjust bounds for text field
        dateField.setFont(new Font("Arial", Font.PLAIN, 24));  // Set font size for the text field

        frame.add(dateField);

        JLabel meatLabel = new JLabel("Meat:");
        meatLabel.setBounds(80, 160, 600, 80);
        meatLabel.setFont(new Font(font_Type, Font.PLAIN, 24));
        frame.add(meatLabel);

        String[] meatOptions = {"None", "Chicken", "Ground Beef(lbs)", "Salmon(filets)", "Pork(chops)", "Bacon(strips)"};
        meatDropdown = new JComboBox<>(meatOptions);
        meatDropdown.setBounds(600, 160, 600, 80);
        frame.add(meatDropdown);

        JLabel breadLabel = new JLabel("Bread:");
        breadLabel.setBounds(80, 240, 600, 80);
        breadLabel.setFont(new Font(font_Type, Font.PLAIN, 24));
        frame.add(breadLabel);

        String[] breadOptions = {"None", "White", "Whole Wheat"};
        breadDropdown = new JComboBox<>(breadOptions);
        breadDropdown.setBounds(600, 240, 600, 80);
        frame.add(breadDropdown);

        JLabel cheeseLabel = new JLabel("Cheese:");
        cheeseLabel.setBounds(80, 320, 600, 80);
        cheeseLabel.setFont(new Font(font_Type, Font.PLAIN, 24));
        frame.add(cheeseLabel);

        String[] cheeseOptions = {"None", "American", "Cheddar", "Gouda", "Mozzarella", "Swiss"};
        cheeseDropdown = new JComboBox<>(cheeseOptions);
        cheeseDropdown.setBounds(600, 320, 600, 80);
        frame.add(cheeseDropdown);

        JLabel riceLabel = new JLabel("Rice:");
        riceLabel.setBounds(80, 400, 600, 80);
        riceLabel.setFont(new Font(font_Type, Font.PLAIN, 24));
        frame.add(riceLabel);

        String[] riceOptions = {"None", "Jasmine Rice", "Spanish Rice", "Sushi Rice"};
        riceDropdown = new JComboBox<>(riceOptions);
        riceDropdown.setBounds(600, 400, 600, 80);
        frame.add(riceDropdown);

        addButton = new JButton("Add Protein");
        addButton.setBounds(80, 500, 600, 120);  // Adjust bounds for buttons
        frame.add(addButton);

        JButton getDayProteinButton = new JButton("Get Day's Protein");
        getDayProteinButton.setBounds(700, 500, 600, 120);  // Adjust bounds for buttons
        frame.add(getDayProteinButton);

        getDayProteinButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String date = tracker.get_Date(); // Get the date from the text field
                double totalProtein = tracker.days_protein(tracker.fileName, date);
                int proteinLeft = (int) (tracker.weight - totalProtein);
                if(proteinLeft > 0 ) {
                    JOptionPane.showMessageDialog(frame, "Total protein intake for " + date + ": " + totalProtein + " grams" + "You have " + proteinLeft + " grams left for the day", "Day's Protein", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "Total protein intake for " + date + ": " + totalProtein + " grams" + " You have met your goal", "Day's Protein", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String date = dateField.getText();
                String selectedMeat = meatDropdown.getSelectedItem().toString();
                String selectedBread = breadDropdown.getSelectedItem().toString();
                String selectedCheese = cheeseDropdown.getSelectedItem().toString();
                String selectedRice = riceDropdown.getSelectedItem().toString();
                List<tracker.Ingredient> ingredients = new ArrayList<>();
                ingredients.add(new tracker.Ingredient(selectedMeat, 1.0));
                ingredients.add(new tracker.Ingredient(selectedBread, 1.0));
                ingredients.add(new tracker.Ingredient(selectedCheese, 1.0));
                ingredients.add(new tracker.Ingredient(selectedRice, 1.0));
                double proteinIntake = tracker.meal(ingredients);
                tracker.load_file(tracker.fileName, date, proteinIntake);
                dateField.setText("");
            }
        });

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ProteinTrackerGUI();
            }
        });
    }
}

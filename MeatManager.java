package proteintracker;

import java.util.ArrayList;
import java.util.List;

public class MeatManager {
    public static List<Meat_Interface> getAllMeatTypes() {
        List<Meat_Interface> meatTypes = new ArrayList<>();

        // Get the package of the MeatManager class
        String packageName = MeatManager.class.getPackage().getName();

        // Get all classes in the specified package
        @SuppressWarnings("deprecation")
		Package pkg = Package.getPackage(packageName);
        for (String className : pkg.getImplementationVendor().split("\\s+")) {
            try {
                Class<?> clazz = Class.forName(className);

                // Check if the class implements MeatInterface
                if (Meat_Interface.class.isAssignableFrom(clazz) && !clazz.isInterface()) {
                    try {
                        // Instantiate the class and add it to the list
                        Meat_Interface meat = (Meat_Interface) clazz.getDeclaredConstructor().newInstance();
                        meatTypes.add(meat);
                    } catch (Exception e) {
                        // Handle instantiation exception
                        e.printStackTrace();
                    }
                }
            } catch (ClassNotFoundException e) {
                // Handle ClassNotFoundException
                e.printStackTrace();
            }
        }

        return meatTypes;
    }

    public static void main(String[] args) {
        List<Meat_Interface> allMeatTypes = getAllMeatTypes();

        // Now 'allMeatTypes' contains instances of all classes that implement MeatInterface
    }
}

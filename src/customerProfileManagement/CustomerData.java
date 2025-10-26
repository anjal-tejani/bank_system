package customerProfileManagement;

import java.io.*;
import java.util.*;

public class CustomerData {

    // ✅ Save CSV file in project root folder (not inside src)
    private static final String FILE_PATH = System.getProperty("user.dir") + File.separator + "customers.csv";

    // ✅ ADD new customer
    public static void addCustomer(Customer customer) {
        try (FileWriter fw = new FileWriter(FILE_PATH, true)) {
            fw.write(customer.toString() + "\n");
            System.out.println("✅ Added customer to: " + new File(FILE_PATH).getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ✅ GET all customers
    public static ArrayList<Customer> getAllCustomers() {
        ArrayList<Customer> list = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            System.out.println("⚠️ File not found: " + file.getAbsolutePath());
            return list;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",", -1);
                if (data.length == 10) {
                    list.add(new Customer(
                            data[0], data[1], data[2], data[3], data[4],
                            data[5], data[6], data[7], data[8], data[9]
                    ));
                }
            }
            System.out.println("📄 Customers loaded: " + list.size());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ✅ SEARCH by ID
    public static Customer getCustomerById(String id) {
        for (Customer c : getAllCustomers()) {
            if (c.getId().equals(id)) {
                return c;
            }
        }
        return null;
    }

    // ✅ DELETE by ID
    public static boolean deleteCustomer(String id) {
        ArrayList<Customer> list = getAllCustomers();
        boolean found = list.removeIf(c -> c.getId().equals(id));

        if (found) saveAllCustomers(list);
        return found;
    }

    // ✅ UPDATE existing customer
    public static boolean updateCustomer(Customer updatedCustomer) {
        ArrayList<Customer> list = getAllCustomers();
        boolean found = false;

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().equals(updatedCustomer.getId())) {
                list.set(i, updatedCustomer);
                found = true;
                break;
            }
        }

        if (found) saveAllCustomers(list);
        return found;
    }

    // ✅ Helper: Save all to file
    private static void saveAllCustomers(ArrayList<Customer> list) {
        try (FileWriter fw = new FileWriter(FILE_PATH)) {
            for (Customer c : list) {
                fw.write(c.toString() + "\n");
            }
            System.out.println("💾 Saved all customers to file.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

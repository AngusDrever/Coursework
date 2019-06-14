import org.sqlite.SQLiteConfig;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Main {

    public static Connection db = null;

    private static void listAllUsers() {
        try {

            PreparedStatement ps = db.prepareStatement("Select UserID, UserName, Password from UserAccount");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                int UserID = results.getInt(1);
                String UserName = results.getString(2);
                String Password = results.getString(3);
                System.out.println("UserID: " + UserID + " " + "UserName: " + UserName + " " + "Password: " + Password);
            }
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
        }
    }

    private static void addNew() {
        try{

            PreparedStatement ps = db.prepareStatement("INSERT INTO UserAccount (UserID, UserName, Password) VALUES (?, ?, ?)");

            ps.setInt(1, 88096);
            ps.setString(2, "Rick");
            ps.setString(3, "Pineapple6");

            ps.executeUpdate();

        } catch (Exception exception){
            System.out.println("Database error: " + exception.getMessage());
        }

    }
    public static void main(String[] args) {
        openDatabase("projectdatabase.db");

        listAllUsers();
        addNew();

        closeDatabase();

    }



    private static void openDatabase(String dbFile) {
        try  {
            Class.forName("org.sqlite.JDBC");
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            db = DriverManager.getConnection("jdbc:sqlite:resources/" + dbFile, config.toProperties());
            System.out.println("***Database connection successfully established***");
        } catch (Exception exception) {
            System.out.println("Database connection error: " + exception.getMessage());
        }

    }

    private static void closeDatabase(){
        try {
            db.close();
            System.out.println("***Disconnected from database***");
        } catch (Exception exception) {
            System.out.println("Database disconnection error: " + exception.getMessage());
        }
    }

}

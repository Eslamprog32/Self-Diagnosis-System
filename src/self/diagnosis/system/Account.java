
package self.diagnosis.system;

import java.util.Scanner;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author eslam
 */
public abstract class Account {

    String username;
    String password;

    public abstract void Register();

    public boolean Login() {
        Scanner input = new Scanner(System.in);
        boolean ca = false;
        
        try {
            File User = new File(username + ".txt");
            if (!User.createNewFile()) {
                System.out.println("Enter Password");
                password = input.next();
                Scanner Reader = new Scanner(User);
                int x = 0;
                while (Reader.hasNextLine()) {  
                    String data = Reader.nextLine();
                    if (x == 2) {
                        if (data.equals(password)) {
                            ca = true;
                            System.out.println("Welcome To Your Account");
                            break;
                        }
                        else{
                            ca = false;
                            System.out.println("Wrong Password");
                            break;
                        }
                    }
                    x++;
                }
                Reader.close();
            } else {
                User.delete();
                ca = false;
                System.out.println("Name Not Found");
            }
        } catch (IOException ex) {
            System.out.println("Data not found");
        }
        return ca;
    }

    public static boolean checkDoctorOrNot(String userName) {
        File user = new File(userName + ".txt");
        boolean ca = false;
        try {
            if (!user.createNewFile()) {
                Scanner Reader = new Scanner(user);
                int x = 0;
                while (Reader.hasNextLine()) {
                    String doctor = Reader.nextLine();
                    if(x==0){
                    if (doctor.equals("Doctor")) {
                        ca = true;
                    }
                    break;
                    }
                    x++;
                }
                Reader.close();
            }
            else{
                user.delete();
            }
        } catch (IOException ex) {
            System.out.println("Error");
        }
        return ca;
    }
}

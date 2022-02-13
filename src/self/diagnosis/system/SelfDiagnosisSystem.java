
package self.diagnosis.system;

import java.util.Scanner;
/**
 *
 * @author eslam
 */
public class SelfDiagnosisSystem {

    public static void main(String[] args) {
       RegisterOrLogin();
    }
    
    
    public static void RegisterOrLogin(){
        Scanner input = new Scanner(System.in);
        System.out.println("Press 1 to Register Or 2 to Login or 3 to leave Program");
        String num = input.nextLine();
        if(num.equals("1")){
            register();
       }
        else if(num.equals("2")){
            login();
        }
    }
    public static void register(){
        Scanner input  = new Scanner(System.in);
        System.out.println("Press 1 To Doctor Or 2 to Patient");
            String num = input.next();
            if(num.equals("1")){
                Doctor doctor = new Doctor();
                doctor.Register();
                System.out.println("Press 1 to exit the program or 2 to log in");
                String repeat = input.next();
                if(repeat.equals("2")){
                    login();
                }
                
            }
            else if(num.equals("2")){
               Patient patient = new Patient();
               patient.Register();
               System.out.println("Press 1 to exit the program or 2 to log in");
                String repeat = input.next();
                if(repeat.equals("2")){
                    login();
                }
            }
            else {
                System.out.println("Enter Correct Value");
                 register();
            }
    }
    
    public static void login(){
        Scanner input = new Scanner(System.in);
        System.out.println("Enter User Name");
        String Name = input.nextLine();
        if(Account.checkDoctorOrNot(Name)){
            Doctor doctor = new Doctor();
            doctor.username = Name;
            if(doctor.Login()){
                functionOfDoctor(doctor);
            }
            else{
                System.out.println("Press 1 to re-login Or 2 To return to the home page");
                String repeat = input.nextLine();
                if(repeat.equals("1")){
                login();
                }
                else{
                    RegisterOrLogin();
                }
            }
        }
        else{
            Patient patient = new Patient();
            patient.username = Name;
            if(patient.Login()){
                functionOfPatient(patient);
            }
            else{
                System.out.println("Press 1 to re-login Or 2 To return to the home page");
                String repeat = input.nextLine();
                if(repeat.equals("1")){
                login();
                }
                else{
                    RegisterOrLogin();
                }
            }
        }
    }
    public static void functionOfDoctor(Doctor doctor){
        Scanner input = new Scanner(System.in);
        System.out.println("1-Add disease"
                + "\n2-Remove disease"
                + "\n3-Add symptom to an existing disease"
                + "\n4-Remove a symptom from an existing disease"
                + "\n5-Display All diseases"
                + "\n6-View Patients with a certain disease");
        String choose = input.nextLine();
        if(choose.equals("1")){
            doctor.addDisease();
        }
        else if(choose.equals("2")){
            System.out.println("Enter ID");
            doctor.removeDisease();
        }
        else if(choose.equals("3")){
            System.out.println("Enter ID");
            doctor.addSymptomToAnExistingDisease(input.nextLine());
        }
        else if(choose.equals("4")){
            System.out.println("Enter ID");
            doctor.RemoveSymptomFromAnExistingDisease(input.nextLine());
        }
        else if(choose.equals("5")){
            doctor.displayAllDiseases();
        }
        else if(choose.equals("6")){
            System.out.println("Enter ID");
            doctor.viewPatientsWithCertainDisease(input.nextLine());
        }
        System.out.println("press 1 to log out and return to the main page, or 2 to return to your personal account");
        String repeat = input.nextLine();
        if(repeat.equals("1")){
            doctor = null;
            RegisterOrLogin();
        }
        else{
            functionOfDoctor(doctor);
        }
    }
    public static void functionOfPatient(Patient patient){
        Scanner input = new Scanner(System.in);
        System.out.println("1-Find out your disease based on your symptoms"
                + "\n2-Display symptoms	for certain Disease"
                + "\n3-View diagnosis history"
                + "\n4-Clear diagnosis history");
        String choose = input.nextLine();
        if(choose.equals("1")){
            patient.findOutYourDiseaseBasedOnYourSymptoms();
        }
        else if(choose.equals("2")){
            System.out.println("Enter ID");
            String ID = input.nextLine();
            patient.displaySymptomsForCertainDisease(ID);
        }
        else if(choose.equals("3")){
            patient.viewDiagnosisHistory();
        }
        else if(choose.equals("4")){
            patient.ClearDiagnosisHistory();
        }
        System.out.println("press 1 to log out and return to the main page, or 2 to return to your personal account");
        String repeat = input.nextLine();
        if(repeat.equals("1")){
            RegisterOrLogin();
        }
        else{
            functionOfPatient(patient);
        }
    }
}

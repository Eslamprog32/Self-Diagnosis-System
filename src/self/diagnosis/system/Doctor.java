
package self.diagnosis.system;

import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

/**
 *
 * @author eslam
 */
public class Doctor extends Account {

    String fullname;
    ArrayList<String> diseases_added;

    @Override
    public void Register() {
        Scanner input = new Scanner(System.in);
        try {
            System.out.println("Enter User Name");
            username = input.nextLine();
            File doctorFile = new File(username + ".txt");
            if (!doctorFile.createNewFile()) {
                System.out.println("The User Name Already exist");
                Register();
            } else {
                System.out.println("Enter Password");
                password = input.nextLine();
                System.out.println("Enter Full Name");
                fullname = input.nextLine();
                PrintWriter writer = new PrintWriter(doctorFile);
                writer.write("Doctor\n");
                writer.write(username+"\n");
                writer.write(password+"\n");
                writer.write(fullname+"\n");
                writer.close();
            }
        } catch (IOException ex) {
            System.out.println("Error While you write");
        }
    }

    public void viewPatientsWithCertainDisease(String ID) {
        ArrayList<String> nameOfPatients = new ArrayList<>();
         Disease.readAllDisease(ID + "Patients",nameOfPatients);
        if(!nameOfPatients.isEmpty()){
            System.out.println(nameOfPatients);
        }
        else 
            System.out.println("There are no patients with this disease");
    }

    public void addSymptomToAnExistingDisease(String ID) {
        Scanner input = new Scanner(System.in);
        Disease disease = new Disease();
        disease.readDataFromFile(ID);
        if (disease.nameOfDoctor.equals(username)){
            disease.symptoms.clear();
            while (true) {
                System.out.println("Enter Symptoms :");
                disease.symptoms.add(input.nextLine());
                System.out.println("press 1 to write more press 2 to stop");
                String x = input.nextLine();
                if (!x.equals("1")) {
                    break;
                }
            }
            disease.writeDataOFDisease();
            System.out.println("Successful process");
        } else {
            System.out.println("You are not allowed to modify this disease because another doctor added it");
        }
    }

    public void RemoveSymptomFromAnExistingDisease(String ID) {
        Disease disease = new Disease();
        disease.readDataFromFile(ID);
        if (disease.equals(username)) {
            disease.symptoms.clear();
            System.out.println("Successful process");
        } else {
            System.out.println("You are not allowed to modify this disease because another doctor added it");
        }
    }

    public void displayAllDiseases() {
        Disease.allDisease = new ArrayList<>();
        Disease.readAllDisease("AllDisease",Disease.allDisease);
        if(!Disease.allDisease.isEmpty()){
        for (int i = 0; i < Disease.allDisease.size(); i++) {
            Disease disease = new Disease();
            disease.ID = Disease.allDisease.get(i);
            disease.readDataFromFile(disease.ID);
            System.out.println(disease.title);
        }
        }else{
            System.out.println("There are no diseases");
        }
    }

    public void removeDisease() {
        Disease disease = new Disease();
        Scanner input = new Scanner(System.in);
        disease.ID = input.nextLine();
        File diseaseFile = new File(disease.ID + ".txt");
        diseaseFile.delete();
        File PatientsFile = new File(disease.ID+"Patients.txt");
        PatientsFile.delete();
        Disease.allDisease = new ArrayList<>();
        Disease.readAllDisease("AllDisease",Disease.allDisease);
        int x = Collections.binarySearch(Disease.allDisease, disease.ID);
        if (x >= 0) {
            Disease.allDisease.remove(x);
            Disease.writeAllDisease("AllDisease",Disease.allDisease);
        }
        System.out.println("Successful deleted for " + disease.ID);
    }

    public void addDisease() {
        Disease disease = new Disease();
        Scanner input = new Scanner(System.in);
        try {
            System.out.println("Enter ID");
            disease.ID = input.nextLine();
            File diseaseFile = new File(disease.ID + ".txt");
            if (!diseaseFile.createNewFile()) {
                System.out.println("The ID Already Exist");
                addDisease();
            } else {
                File AllDisease = new File("AllDisease.txt");
                Disease.allDisease = new ArrayList<>();
                if(!AllDisease.createNewFile()){
                Disease.readAllDisease("AllDisease",Disease.allDisease);
                }
                Disease.allDisease.add(disease.ID);
                Disease.writeAllDisease("AllDisease",Disease.allDisease);
                disease.nameOfDoctor = username;
                boolean ca ;
                do{
                 ca = false;
                System.out.println("Enter title");
                disease.title = input.nextLine();
                Disease.allDiseaseTitle = new ArrayList<>();
                Disease.readAllDisease("AllDiseaseTitle",Disease.allDiseaseTitle );
                Collections.sort(Disease.allDiseaseTitle);
                int x = Collections.binarySearch(Disease.allDiseaseTitle,disease.title);
                if(x>=0){
                    System.out.println("The Title Already Exist");
                    ca = true;
                }
                }while(ca);
                Disease.allDiseaseTitle.add(disease.title);
                Disease.writeAllDisease("AllDiseaseTitle",Disease.allDiseaseTitle);
                String loop = "1";
                disease.symptoms = new ArrayList<>();
                while (loop.equals("1")){
                    System.out.println("write symptoms");
                    disease.symptoms.add(input.nextLine());
                    System.out.println("Enter 1 to write more Or 2 to stop");
                    loop = input.nextLine();
                }
                disease.writeDataOFDisease();
            }
        } catch (IOException ex) {
            System.out.println("Error");
        }
    }

}

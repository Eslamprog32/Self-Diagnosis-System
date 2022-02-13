/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package self.diagnosis.system;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
/**
 *
 * @author eslam
 */
public class Patient extends Account {
    String fullname;
    private int Age;
    private String Gender;
    ArrayList<String> patientSymptoms;
    ArrayList<String> disease_History;
    
    public void setGender(String Gender){
        if(("Male".equals(Gender)||"Female".equals(Gender))){
           this.Gender = Gender;
        }
        else{
            System.out.println("Please Enter Male Or Female");
            Scanner input = new Scanner(System.in);
            Gender = input.nextLine();
            setGender(Gender);
        }
    }

    public void setAge(){
        Scanner input = new Scanner(System.in);
        Age = input.nextInt();
        input.nextLine();
        if(Age<0){
            try{
            System.out.println("There is no negative age");
            setAge();
            }catch(InputMismatchException ex){
                input.next();
                System.out.println("Please Enter Correct Age");
                setAge();
            }
        }
    } 
    @Override
    public  void Register(){   
        Scanner input = new Scanner(System.in);
        try{
        System.out.println("Enter User Name");
        username = input.nextLine();
        File patientFile = new File(username+".txt");
        if(!patientFile.createNewFile()){
            System.out.println("The User Name Already exist");
            Register();
        }
        else{
            System.out.println("Enter Password");
            password = input.nextLine();
            System.out.println("Enter Full Name");
            fullname = input.nextLine();
            System.out.println("Enter Age");
             ArrayList<String> numofpatients = new ArrayList<>();
              Disease.readAllDisease("NumberOFPatients", numofpatients);
              if(numofpatients.isEmpty()){
                  numofpatients.add("0");
              }
             int nop = Integer.parseInt(numofpatients.get(0))+1;
             Disease.numberOfPatient = String.valueOf(nop);
             numofpatients.clear();
             numofpatients.add(Disease.numberOfPatient);
             Disease.writeAllDisease("NumberOFPatients", numofpatients);
            setAge();
            System.out.println("Enter Gender");
            setGender(input.nextLine());
            PrintWriter writer = new PrintWriter(patientFile);
            writer.write("Patient\n");
            writer.write(username+"\n");
            writer.write(password+"\n");
            writer.write(fullname+"\n");
            writer.write(Age+"\n");
            writer.write(Gender+"\n");
            writer.close();
        }
       }catch(IOException ex){
            System.out.println("Error");
       }
    }
    public void displaySymptomsForCertainDisease(String ID){
        Disease disease = new Disease();
        disease.readDataFromFile(ID);
        if(!disease.symptoms.isEmpty()){
        System.out.println(disease.symptoms);
        }
        else{
            System.out.println("There is no disease with this ID");
        }
    }
    public void findOutYourDiseaseBasedOnYourSymptoms(){
        patientSymptoms = new ArrayList<>();
        boolean Case = false;
        Scanner input = new Scanner(System.in);
        String loop = "1";
        while(loop.equals("1")){
            System.out.println("Enter your Symptoms:");
            patientSymptoms.add(input.nextLine());
            System.out.println("press 1 to write more press 2 to stop");
            loop = input.nextLine();
            if(!loop.equals("1"))
                break;
        }
        Disease.allDisease= new ArrayList<>();
        Disease.readAllDisease("AllDisease",Disease.allDisease);
        Disease disease = new Disease();
        for(int y = 0;y<Disease.allDisease.size();y++){
            disease.readDataFromFile(Disease.allDisease.get(y));
            int theNumberOfIdenticalSymptoms=0;
        for(int i =0;i<patientSymptoms.size();i++){
            for(int j = 0;j<disease.symptoms.size();j++){
                if(patientSymptoms.get(i).equalsIgnoreCase(disease.symptoms.get(j))){
                    theNumberOfIdenticalSymptoms++;
                    break;
                }
            }
            int precent =  (patientSymptoms.size()-i+theNumberOfIdenticalSymptoms)*100/disease.symptoms.size();
            if(precent<80){
                break;
            }
          }
          int finalPrecent = theNumberOfIdenticalSymptoms*100/disease.symptoms.size();
          if(finalPrecent>=80){
              disease_History = new ArrayList<>();
              Disease.readAllDisease(username+"DiseaseHistory", disease_History);
              int x = Collections.binarySearch(disease_History,disease.ID);
              if(x>=0){
                  Case = false;
                  System.out.println("You have already been tested for this disease");
                  break;
              }
              else {Case = true;
              break;}
          }
        }
        if(Case){
            System.out.println("You are sick with "+disease.title);
            ArrayList<String> numofpatients = new ArrayList<>();
            Disease.readAllDisease("NumberOFPatients", numofpatients);
            if(numofpatients.isEmpty()){
                numofpatients.add("0");
            }
            disease_History = new ArrayList<>();
            Disease.readAllDisease(username+"DiseaseHistory", disease_History);
            if(disease_History.isEmpty()){
                int x = Integer.parseInt(numofpatients.get(0))+1;
                numofpatients.clear();
                numofpatients.add(String.valueOf(x));
            }
            disease_History.add(disease.ID);
            Collections.sort(disease_History);
            Disease.writeAllDisease(username+"DiseaseHistory", disease_History); 
            Disease.numberOfPatient=numofpatients.get(0);
            int nop=Integer.parseInt(disease.numberOfPatientsWithThisDiseasem)+1;
            disease.numberOfPatientsWithThisDiseasem = String.valueOf(nop);
            Disease.writeAllDisease("NumberOFPatients", numofpatients);
            disease.writeDataOFDisease();
            double percentage = (Double.parseDouble(disease.numberOfPatientsWithThisDiseasem)*100)/Double.parseDouble(Disease.numberOfPatient);
            System.out.printf("Percentage of patients with this disease = %.1f\n",percentage);
            disease.patientWithCertainDisease = new ArrayList<>();
            Disease.readAllDisease(disease.ID + "Patients",disease.patientWithCertainDisease);
            disease.patientWithCertainDisease.add(username);
            Collections.sort(disease.patientWithCertainDisease);
            Disease.writeAllDisease(disease.ID + "Patients",disease.patientWithCertainDisease);
        }
        else{
            System.out.println("no exact match found");
        }
    }
    public void viewDiagnosisHistory(){
        disease_History = new ArrayList<>();
        Disease.readAllDisease(username+"DiseaseHistory", disease_History);
        if(!disease_History.isEmpty()){
        System.out.println("Your disease history : ");
        System.out.println(disease_History);
        }
        else{
            System.out.println("No history of disease");
        }
    }
    public void ClearDiagnosisHistory(){
        disease_History = new ArrayList<>();
        Disease.readAllDisease(username+"DiseaseHistory",disease_History);
        Disease disease = new Disease();
        for(int i =0;i<disease_History.size();i++){
            disease.ID = disease_History.get(i);
            disease.patientWithCertainDisease = new ArrayList<>();
          Disease.readAllDisease(disease.ID + "Patients", disease.patientWithCertainDisease);
          disease.readDataFromFile(disease.ID);
          disease.numberOfPatientsWithThisDiseasem = String.valueOf(Integer.parseInt(disease.numberOfPatientsWithThisDiseasem)-1);
          disease.writeDataOFDisease();
          int x = Collections.binarySearch(disease.patientWithCertainDisease,username);
          if(x>=0){
              disease.patientWithCertainDisease.remove(x);
              Disease.writeAllDisease(disease.ID + "Patients", disease.patientWithCertainDisease);
          }
        }
        ArrayList<String> nop = new ArrayList<>();
        Disease.readAllDisease("NumberOFPatients", nop);
        int x = Integer.parseInt(nop.get(0))-1;
        nop.clear();
        nop.add(String.valueOf(x));
        Disease.writeAllDisease("NumberOFPatients", nop);
        File diseaseHistory = new File(username+"DiseaseHistory.txt");
        diseaseHistory.delete();
        System.out.println("Successful Process");
        disease_History.clear();
    }
}

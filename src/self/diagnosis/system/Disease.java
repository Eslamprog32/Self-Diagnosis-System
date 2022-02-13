/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package self.diagnosis.system;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
/**
 *
 * @author eslam
 */
public class Disease {

    String title;
    String nameOfDoctor;
    String ID;
    ArrayList<String> symptoms;
    String numberOfPatientsWithThisDiseasem = "0";
    ArrayList<String> patientWithCertainDisease ;
    static String numberOfPatient= "0" ;
    static ArrayList<String> allDisease;
    static ArrayList<String> allDiseaseTitle;
    boolean no_patients_diagnosed = "0".equals(numberOfPatientsWithThisDiseasem);
    public void writeDataOFDisease() {
        File diseaseFile = new File(ID+".txt");
        try {
            PrintWriter writer = new PrintWriter(diseaseFile);
            writer.write(nameOfDoctor+"\n");
            writer.write(ID+"\n");
            writer.write(title+"\n");
            writer.write(numberOfPatientsWithThisDiseasem+"\n");
            writer.write(numberOfPatient+"\n");
            for (int i = 0; i < symptoms.size(); i++) {
                writer.write(symptoms.get(i)+"\n");
            }
            writer.close();
        } catch (IOException ex) {
            System.out.println("Error");
        }
    }

    public void readDataFromFile(String ID) {
        File diseaseFile = new File(ID+".txt");
        
        try {
            Scanner Reader = new Scanner(diseaseFile);
            symptoms = new ArrayList<>();
            int x = 0;
            while (Reader.hasNextLine()) {
                switch (x) {
                    case 0-> 
                        nameOfDoctor = Reader.nextLine();
                    case 1 ->
                        this.ID = Reader.nextLine();
                    case 2 ->
                        title = Reader.nextLine();
                    case 3 ->
                        numberOfPatientsWithThisDiseasem = Reader.nextLine();
                    case 4 ->
                        numberOfPatient = Reader.nextLine();
                    default ->
                        symptoms.add(Reader.nextLine());
                }
                x++;
            }
            Reader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Data Not Found");
        }
    }
    public static ArrayList<String> readAllDisease(String NameFile,ArrayList<String> data) {
        File AllDisease = new File(NameFile+".txt");
        try{
        Scanner Reader = new Scanner(AllDisease);
        if(!AllDisease.createNewFile()){
        while (Reader.hasNextLine()){
            data.add(Reader.nextLine());
        }
        Reader.close();
        }
        }catch(IOException ex){
        }
       return data;        
    }
    public static void writeAllDisease(String NameFile,ArrayList<String> data){
        File AllDisease = new File(NameFile+".txt");
        try{
        PrintWriter writer = new PrintWriter(AllDisease);
        for(int i =0;i<data.size();i++){
            writer.write(data.get(i)+"\n");
        }
        writer.close();
        }catch(IOException ex){
            System.out.println("Error");
        }
    }
}

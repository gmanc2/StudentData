package Assignment3;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.io.BufferedReader;
import java.io.FileReader;
import javax.swing.*;

class Assignment3 {
    public static void main(String[] args) throws IOException {
            StudentParse student = new StudentParse();
            student.read("students.csv");
            student.calculateData();
            student.printData();
        String recall = JOptionPane.showInputDialog("Would you like to rerun the program with new data? Enter Yes or No. \n");
        while (recall.equalsIgnoreCase("yes"))  {
            student.read("students.csv");
            student.calculateData();
            student.printData();
            recall = JOptionPane.showInputDialog("Would you like to rerun the program with new data? Enter Yes or No. \n");
        }
    }
}


class StudentParse {
    StudentData[] students = new StudentData[5];
    String University;
    int HonorsList;
    double TotaledTuition;
   double AvgGPA;

    public void read(String file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        final int NumStudents = 5;
        String line = br.readLine();
        if (line == null) {
            throw new FileNotFoundException("File is empty or only contains the university name");
        }
        University = line;

        for (int i = 0; i < NumStudents; i++) {
            line = br.readLine();
            if (line == null) {
                throw new NumberFormatException("File does not contain enough data for " + NumStudents + " students");
            }
            String[] studentData = line.split(",");
            if (studentData.length != 4) {
                throw new NumberFormatException("Line does not contain enough data for one student: " + line);
            }
            int studentIdNum = Integer.parseInt(studentData[0]);
            String name = studentData[1];
            int credits = Integer.parseInt(studentData[2]);
            double gpa = Double.parseDouble(studentData[3]);
            students[i] = new StudentData(studentIdNum, name, credits, gpa);
        }
    }

    public void calculateData() {
        HonorsList = 0;
        TotaledTuition = 0;
        double TotaledGPA = 0;

        for (StudentData student : students) {
            TotaledTuition += student.getTuition();
            TotaledGPA += student.getGpa();
            if (student.getGpa() >= 3.5) {
                HonorsList++;
            }
        }
        AvgGPA = TotaledGPA / students.length;
    }

    public void printData() {
        DecimalFormat df = new DecimalFormat("0.00");
        System.out.println("University Name: " + University);
        System.out.println("Student ID Number | Name | Total Credits | GPA | Tuition\n");
        for (StudentData student : students) {
            System.out.printf("| %d | %s | %d | %s | %s\n" , student.getStudentIDNum(),
                    student.getStudentName(), student.getCredits(), df.format(student.getGpa()),
                    df.format(student.getTuition()));
        }
        System.out.println("\n| University Statistics |");
        System.out.println("Total Tuition Paid: " + df.format(TotaledTuition));
        System.out.println("Number of Honor Students: " + HonorsList);
        System.out.print("Average GPA " + df.format(AvgGPA) + "\n");
    }
}
class StudentData {
     int StudentIDNum;
     String Name;
     int Credits;
     double GPA;
     double Tuition;

    public StudentData(int StudentIDNum, String Name, int Credits, double GPA) {
        this.StudentIDNum = StudentIDNum;
        this.Name = Name;
        this.Credits = Credits;
        this.GPA = GPA;
    }

    public int getStudentIDNum() {
        return StudentIDNum;
    }

    public String getStudentName() {
        return Name;
    }

    public int getCredits() {
        return Credits;
    }

    public double getGpa() {
        return GPA;
    }

    public double getTuition() {
        Tuition = Credits * 850;
        return Tuition;
    }
}



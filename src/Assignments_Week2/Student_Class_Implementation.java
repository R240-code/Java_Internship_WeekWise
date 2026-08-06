package Assignments_Week2;
import java.util.Scanner;

//Student Class Implementation:Create a class with attributes and methods to display student info
class Student{
    String name;
    int Roll_no;
    String Branch;
    int ID;

    Student(String name, int Roll_no, String Branch, int ID) {
        this.name = name;
        this.Roll_no = Roll_no;
        this.Branch = Branch;
        this.ID = ID;
    }

    void displayStudentInfo() {
        System.out.println("Student Name: " + name);
        System.out.println("Roll Number: " + Roll_no);
        System.out.println("Branch: " + Branch);
        System.out.println("ID: " + ID);
    }
}

public class Student_Class_Implementation {
    public static void main(String[] args) {

        Student S=new Student("Rohit Vishwanath Patil" , 239, "Computer Science And Engineering" , 239);
        S.displayStudentInfo();



    }
}

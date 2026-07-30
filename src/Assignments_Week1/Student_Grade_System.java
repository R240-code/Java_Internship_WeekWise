package Assignments_Week1;
import java.util.Scanner;


//ASSIGNMENT NO 2: STUDENT GRADE SYSTEM(Assignment No 2: Take student marks and print Grade (A/B/C/Fail) using conditional logic)
public class Student_Grade_System {
    public static void main(String[] args) {

        Scanner sc=new Scanner(System.in);
        System.out.println("WELCOME TO STUDENT GRADE SYSTEM");
        System.out.println("Enter your marks:");
        int marks=sc.nextInt();

        if(marks>=75 && marks<=100){
        System.out.println("CONGARTULATIONS! You have secured Grade A and your are passed in the 'DISTINCTION' category");
        }
        else if(marks>=60 && marks<75){
            System.out.println("CONGRATULATIONS! You have secured Grade B and your are passed in the 'FIRST CLASS' category");
        }
        else if(marks>=45 && marks<60){
            System.out.println("CONGRATULATIONS! You have secured Grade C and your are passed in the 'SECOND CLASS' category");
        }
        else if(marks<35 && marks>=0){
            System.out.println("SORRY! You have Failed the Exam");
        }
        else{
            System.out.println("Invalid Marks Entered. Please enter marks between 0 and 100.");
        }

    }
}

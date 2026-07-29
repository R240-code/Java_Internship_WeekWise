package Assignments_Week1;
import java.util.Scanner;

//ASSIGNMENT NO 1;:BASIC CALCULATOR PROGRAMME
public class Basic_Calculator_Programme {
    public static void main(String[] args) {

        Scanner sc=new Scanner(System.in);
        System.out.println("Enter first number:");
        double num1=sc.nextDouble();
        System.out.println("Enter second number:");
        double num2=sc.nextDouble();

        System.out.println("choose the operations Number you want to perform");
        System.out.println("1.Addition");
        System.out.println("2.Subtraction");
        System.out.println("3.Multiplication");
        System.out.println("4.Division");
        int choice=sc.nextInt();

        switch (choice){
            case 1:
                System.out.println("Addition "+(num1+num2));
                break;
            case 2:
                System.out.println("Subtraction "+(num1-num2));

            case 3:
                System.out.println("Multiplication "+(num1*num2));
                break;
            case 4:
                if(num2==0){
                    System.out.println("Division by zero is not allowed");
                }
                else {
                    System.out.println("Division " + (num1 / num2));
                }
                break;

            default:
                System.out.println("Invalid operation is done please check your info");

        }


    }
}

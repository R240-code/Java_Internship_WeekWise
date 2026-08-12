package Assignments_Week3;


//Student Marks Record Program
// Store data in an ArrayList and calculate highest & lowest marks

import java.util.*;


public class Students_Marks_Record_Programme {
    public static void main(String[] args) {

        ArrayList<Integer> marks = new ArrayList<>();
        marks.add(85);
        marks.add(92);
        marks.add(78);
        marks.add(55);
        marks.add(88);
        marks.add(95);

        int highest=marks.get(0);
        int lowest=marks.get(0);

        for(int mark:marks){
            if (mark>highest){
                highest=mark;
            }

            if (mark<lowest){
                lowest=mark;
            }
        }

        System.out.println("Student Marks Record: " + marks);
        System.out.println("Highest Marks: " + highest);
        System.out.println("Lowest Marks: " + lowest);






    }
}

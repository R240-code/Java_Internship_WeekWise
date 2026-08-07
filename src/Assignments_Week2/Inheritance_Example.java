package Assignments_Week2;

//Vehicle Inheritance Example:
//Base class: Vehicle → Subclasses: Car, Bike (override methods).

class Vehicle {

void Start(){
    System.out.println("Vehicle is starting");
}


void run(){
    System.out.println("Vehicle is running");
}


void stop(){
    System.out.println("Vehicle is stopping");
}
}

class Car extends Vehicle{


    void start(){
        System.out.println("Car takes 2.3 seconds to start");
    }

    void run(){
        System.out.println("Car is running at maximum speed of 100 km/h");
    }
}

class Bike extends Vehicle{


    void start(){
        System.out.println("Bike takes 1.5 seconds to start");
    }

    void run(){
        System.out.println("Bike is running at maximum speed of 80 km/h");
    }
}




public class Inheritance_Example {
    public static void main(String[] args) {

        // This is The implementation of the parent class


        Vehicle v = new Vehicle();
        v.Start();
        v.run();
        v.stop();

        Car c = new Car();
        c.start();
        c.run();
        c.stop();

        Bike b = new Bike();
        b.start();
        b.run();
        b.stop();

    }
}

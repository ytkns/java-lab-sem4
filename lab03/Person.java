import java.util.Scanner;

public class Person{

    private MyString name;
    private MyString street;
    private int number;

    public Person(String n, String s, int nr){
        name = new MyString(n);
        street = new MyString(s);
        number = nr;
    }

    public Person (Person other){
        this.name = other.name;
        this.street = other.street;
        number = other.number;
    }

    public Person (Person other, boolean deep){
        if (deep) {
            this.name = new MyString ( other.name.text );
            this.street = new MyString ( other.street.text );
        }
        else {
            this.name = other.name;
            this.street = other.street;
        }

        number = other.number;
    }

    public String getName(){
        return name.text;
    }

    public String getStreet(){
        return street.text;
    }

    public int getNumber(){
        return number;
    }

    public void setName(String n){
        this.name.text = n;
    }

    public void setStreet(String str){
        this.street.text = str;
    }

    public void setNumber(int number){
        this.number = number;
    }

    public void printDetails(){
        System.out.println();
        System.out.println("Imie osoby: " + name.text);
        System.out.println("Ulica: " + street.text);
        System.out.println("numer: " + number);
        System.out.println();
    }

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ile osob chcesz podac? : ");
        int n = scanner.nextInt();
        scanner.nextLine();

        Person[] people = new Person[n];
        
        for ( int i=0; i<n; i++ ){
            System.out.println("Podaj imie " + (i+1) + " osoby:");
            String na = scanner.nextLine();
            System.out.println("Podaj ulice " + (i+1) + " osoby:");
            String str = scanner.nextLine();
            System.out.println("Podaj numer " + (i+1) + " osoby:");
            int nr = scanner.nextInt();
            people[i] = new Person(na, str, nr);   
            scanner.nextLine();
        }

        Person original = people[0];
        Person shallowCopy = new Person(original);
        Person deepCopy = new Person(original, true);

        shallowCopy.setName("newName");
        shallowCopy.setStreet("newStreet");

        System.out.println("Oryginal:");
        original.printDetails();
        System.out.println("Plytka kopia po zmianie imienia i ulicy:");
        shallowCopy.printDetails();

        deepCopy.setName("otherName");
        deepCopy.setStreet("otherStreet");

        System.out.println("Oryginal:");
        original.printDetails();
        System.out.println("Gleboka kopia po zmianie imienia i ulicy:");
        deepCopy.printDetails();

    } 

}

//klasa pomocnicza do kopii glebokiej
class MyString{
    public String text;

    MyString(String t){
        text = t;
    }
}
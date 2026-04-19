public class RegularClient extends Client{
    
    private double discountPercentage;

    public RegularClient(String name, double disc){
        super(name);
        this.discountPercentage = disc;
    }

    public static void main(String[] args){
        RegularClient regular = new RegularClient("regular", 7.2);
        regular.print();
    }

};
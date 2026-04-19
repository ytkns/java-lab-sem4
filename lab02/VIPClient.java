public class VIPClient extends Client{
    
    private int creditLimit;

    public VIPClient(String name, int cr){
        super(name);
        this.creditLimit = cr;
    }

    public static void main(String[] args){
        VIPClient vip = new VIPClient("vip", 10);
        vip.print();
    }
    
};
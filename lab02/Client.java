public class Client{

    private int clientID;
    private String clientName;
    private static int count;

    {
        count += 1;
        clientID = count;
        System.out.println("Wywolano blok inicjalizacyjny");
    }

    public Client(){
        clientName = "empty";
        System.out.println("Wywolano konstruktor bezargumentowy");
    }

    public Client(String name){
        clientName = name;
        System.out.println("Wywolano konstruktor z parametrem");
    }

    public String getName(){
        return clientName;
    }

    public int getID(){
        return clientID;
    }

    public void print(){
        System.out.println("Client ID: " + clientID + ", client name: " + clientName);
    }


    static int countClients(){
        return count;
    }

    public static void main(String[] args){
        System.out.println("Wywolano maina");
        Client a = new Client("KlientA");
        Client b = new Client();

        a.print();
        b.print();
    }

};



import java.util.ArrayList;

public class Store{
    
    private ArrayList<Client> clients;

    public Store(){
        clients = new ArrayList<Client>();
    }

    public void add(Client aClient){
        clients.add(aClient);
    }

    public void print(){
        for(Client c : clients){
            c.print();
        }
    }

    Client find(int ID){
        for(Client c: clients){
            if(c.getID() == ID)
                return c;
        }
        return null;
    }

    public static void main(String[] args){
        Store store = new Store();
        Client a = new Client();
        Client b = new Client("KlientB");

        store.add(a);
        store.add(b);

        store.print();

        Client found = store.find(2);
        if(found != null){
            System.out.print("Znaleziono kleinta: ");
            found.print();
        }
        else
            System.out.println("Nie znaleziono kleinta");
    }

};
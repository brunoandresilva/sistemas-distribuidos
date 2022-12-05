import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> lista = new ArrayList();
        lista.add("andrebruno12001@gmail.com");
        Contact c = new Contact("Bruno", 21, 933590837, "uminho", lista);
        System.out.println("Nome: " + c.name());
        System.out.println("Idade: " + c.age());
        System.out.println("Nr Telemovel: " + c.phoneNumber());
        System.out.println("Empresa: " + c.company());
        System.out.println("Emails: ");
        for(String s: c.emails()){
            System.out.println(s);
        }


    }
}

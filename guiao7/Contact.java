import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.*;

class Contact {
    private String name;
    private int age;
    private long phoneNumber;
    private String company;     // Pode ser null
    private ArrayList<String> emails;

    public Contact (String name, int age, long phoneNumber, String company, List<String> emails) {
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.company = company;
        this.emails = new ArrayList<>(emails);
    }

    public String name() { return name; }
    public int age() { return age; }
    public long phoneNumber() { return phoneNumber; }
    public String company() { return company; }
    public List<String> emails() { return new ArrayList(emails); }

    // @TODO
    public void serialize (DataOutputStream out) throws IOException { 
        out.writeUTF(this.name);
        out.writeInt(this.age);
        out.writeLong(this.phoneNumber);
        out.writeUTF(this.company);
        for(String s: this.emails){
            out.writeUTF(s);
        }
    }

    // @TODO
    public static Contact deserialize (DataInputStream in) throws IOException { 
        String name_read = in.readUTF();
        int age_read = in.readInt();
        long phone_nr_read = in.readLong();
        String company_read = in.readUTF();
        ArrayList<String> emails_read = new ArrayList<>();
        while(in.read() != -1){
            emails_read.add(in.readUTF());
        }
        return new Contact(name_read, age_read, phone_nr_read, company_read, emails_read);
    }

    public String toString () {
        StringBuilder builder = new StringBuilder();
        builder.append(this.name).append(";");
        builder.append(this.age).append(";");
        builder.append(this.phoneNumber).append(";");
        builder.append(this.company).append(";");
        builder.append(this.emails.toString());
        builder.append("}");
        return builder.toString();
    }

}

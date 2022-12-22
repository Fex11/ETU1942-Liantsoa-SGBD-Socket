import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client {
  public static void main(String[] args) throws Exception {
    Socket s=new Socket("localhost",1942);
    PrintWriter pr= new PrintWriter(s.getOutputStream());
    ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
    InputStreamReader in=new InputStreamReader(s.getInputStream());
    BufferedReader bf=new BufferedReader(in);
    while(true){
      System.out.print("Liantsoa SQL >");
      Scanner scan = new Scanner(System.in);
      String req=scan.nextLine();
      pr.println(req);
      pr.flush();
      String exe=bf.readLine();
      if(exe.hashCode()=="mety".hashCode()){
         Relation re=(Relation) ois.readObject();
        System.out.println("");
        re.afficher();
        System.out.println("");
      }else{
        System.out.println(exe);
        System.out.println("");
      }      
    }
  }
}
import java.net.*;
import java.io.*;
public class Serveur extends Thread {
  private int client;
  public static void main(String[] args) throws Exception {
      System.out.println("En attente.....");
      new Serveur().start();
  }

  public void run(){
    try{
      ServerSocket ss= new ServerSocket(1942);
      while(true) {
        Socket s=ss.accept();
        client++;
        System.out.println("=> "+client+" client connecte");
        new Conversation(s).start();
      }
    }catch (IOException e){
      e.printStackTrace();
    }
  }
  class Conversation extends Thread{
    private Socket socket;
    public Conversation(Socket s){
      this.socket=s;
    }
    public void run(){
      try{   
          InputStreamReader in=new InputStreamReader(socket.getInputStream());
          BufferedReader bf=new BufferedReader(in);
          OutputStream ot=socket.getOutputStream();
          ObjectOutputStream oot=new ObjectOutputStream(ot);
          PrintWriter pr= new PrintWriter(socket.getOutputStream());       
          while(true){
              String req=bf.readLine();
              Relation r=new Relation();
              try{
                Relation re=r.requette(req);
                pr.println("mety");
                pr.flush();
                oot.writeObject(re);
              }catch (Exception e){
                pr.println(e.getMessage());
                pr.flush();
              }
          }
      }catch (IOException ex){
        ex.printStackTrace();
      }
  }
  }
}
/*while(true) {
      ServerSocket ss= new ServerSocket(1942);
      Socket s=ss.accept();
      InputStreamReader in=new InputStreamReader(s.getInputStream());
      BufferedReader bf=new BufferedReader(in);
      String req=bf.readLine();
      Relation re=new Relation();
      try {
        Relation r=new Relation();
        re=r.requette(req);
      }catch (Exception e) {
        PrintWriter pr= new PrintWriter(s.getOutputStream());
        pr.println(e.getMessage());
        pr.flush();
      }
      OutputStream ot=s.getOutputStream();
      ObjectOutputStream oot=new ObjectOutputStream(ot);
      oot.writeObject(re);
      ss.close();
    }*/
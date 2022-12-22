import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Vector;
import java.io.Serializable;

public class Relation implements Serializable{
	String nom;
	Vector<String> colonne;
	Vector<Vector<String>> donnee;
	
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public Vector<String> getColonne() {
		return colonne;
	}
	public void setColonne(Vector<String> colonne) {
		this.colonne = colonne;
	}
	public Vector<Vector<String>> getDonnee() {
		return donnee;
	}
	public void setDonnee(Vector<Vector<String>> donnee) {
		this.donnee = donnee;
	}
	public Relation() {}
	public Relation(String nom,Vector<String> c,Vector<Vector<String>> v) {
		setNom(nom);
		setColonne(c);
		setDonnee(v);
	}
	 public Relation(String nom,Vector<String> c) {
		 setNom(nom);
		 setColonne(c);
	 }

	 public boolean compare_ligne(Vector<String> a,Vector<String> b) {
			int count=0;
			if(a.size()!=b.size()) { return false; }
			for(int i=0;i<a.size();i++) { if(a.get(i).hashCode()==b.get(i).hashCode()) { count++; } }
			if(count==a.size()) { return true; }
			else { return false; }
		}
		
		public Vector<String> addVectString(Vector<String> a,Vector<String> b){
			Vector<String> rep=new Vector<String>();
			for(int i=0;i<a.size();i++) {
				rep.addElement(a.get(i));
			}
			for(int i=0;i<b.size();i++) {
				rep.addElement(b.get(i));
			}
			return rep;
		}

		public void change(String name,Vector<Vector<String>> donnee)throws Exception {
			String tables=getAllTable();
			if(tables.contains(";"+name+";")) {
					File table=new File("Data/"+name+".txt");
					FileOutputStream outTab =new FileOutputStream(table,false);        
						PrintWriter pTab=new PrintWriter(outTab);
						for(int j=0;j<donnee.size();j++){
							for(int i=0;i<donnee.get(j).size();i++) {
							 pTab.print(donnee.get(j).get(i)+";;");
							}
							pTab.print("//");
						}
						pTab.flush(); pTab.close();
			}else { throw new Exception("ERREUR: table tsy mi existe");}
		}

		public void insertTemp(Relation temp) throws Exception {
			File att=new File("Data/tempatt.txt");
			File table=new File("Data/temp.txt");
					FileOutputStream outAtt=new FileOutputStream(att,false);   
					FileOutputStream outTab =new FileOutputStream(table,false);   
					PrintWriter pAtt=new PrintWriter(outAtt);
					PrintWriter pTab=new PrintWriter(outTab);
					pTab.print(""); pTab.flush(); pTab.close();
					for(int i=0;i<temp.getColonne().size();i++) {
						pAtt.print(temp.getColonne().get(i)+";;");
					}
					for(int i=0;i<temp.getDonnee().size();i++) {
						insertData("temp",temp.getDonnee().get(i),"ss");
					}
					pAtt.flush(); pAtt.close();
		}

		public void insertData(String name,Vector<String> donnee,String s)throws Exception {
			File table=new File("Data/"+name+".txt");
			 FileOutputStream outTab =new FileOutputStream(table,true);        
				 PrintWriter pTab=new PrintWriter(outTab);
				 for(int i=0;i<donnee.size();i++) {
					 pTab.print(donnee.get(i)+";;");
				 }
				 pTab.print("//");
				 pTab.flush(); pTab.close();
		}

		public void insertData(String name,Vector<String> donnee)throws Exception {
			String tables=getAllTable();
			if(tables.contains(";"+name+";")) {
				Vector<String> att=getAttribut(name);
				if(att.size()==donnee.size()) {
					File table=new File("Data/"+name+".txt");
					FileOutputStream outTab =new FileOutputStream(table,true);        
						PrintWriter pTab=new PrintWriter(outTab);
						for(int i=0;i<donnee.size();i++) {
							 pTab.print(donnee.get(i)+";;");
						}
						pTab.print("//");
						pTab.flush(); pTab.close();
				}
				else if(att.size()<donnee.size()) {throw new Exception("ERREUR: miotra le donnee"); }
				else { throw new Exception("ERREUR: tsy ampy le donnee");}
			}else { throw new Exception("ERREUR: table tsy mi existe");}
		}

		public Vector<String> getAttribut(String name)throws Exception {
			File fichier=new File("Data/"+name+"att.txt");
			FileReader file = new FileReader(fichier);
					BufferedReader buf = new BufferedReader(file);
					String[] att = buf.readLine().split(";;");
					Vector<String> rep=new Vector<String>();
					for(int i=0;i<att.length;i++) {
						rep.addElement(att[i]);
					}
			return rep;
		}

		public Relation getAllData(String name)throws Exception{
			File fichier=new File("Data/"+name+".txt");
			FileReader file = new FileReader(fichier);
			BufferedReader buf = new BufferedReader(file);
			Vector<Vector<String>> data=new Vector<Vector<String>>();
			String[] ligne =buf.readLine().split("//");
			for(int i=0;i<ligne.length;i++) {
				String[] datas=ligne[i].split(";;");
				Vector<String> temp=new Vector<String>();
				for(int j=0;j<datas.length;j++) {
					temp.addElement(datas[j]);
				}
				data.addElement(temp);
			}
			Vector<String> att=getAttribut(name);
			Relation rep=new Relation(name,att,data);
			return rep;
			
		}

		public String getAllTable() throws Exception {
			File listTable=new File("Data/Liste_table.txt");
			FileReader file = new FileReader(listTable);
			BufferedReader buf = new BufferedReader(file);
			String rep=buf.readLine();
			return rep;
		}

		public void afficher() {
			for(int j=0;j<this.getColonne().size();j++) {
				System.out.print(this.getColonne().get(j)+" | ");
			}
			System.out.println();
			for(int i=0;i<this.getDonnee().size();i++) {
				for(int j=0;j<this.getDonnee().get(i).size();j++) {
					System.out.print(this.getDonnee().get(i).get(j)+" | ");
				}
				System.out.println();
			}
		}


	//CREATION TABLE
	public void createTable(String name,Vector<String> attribut) throws Exception {
		File table=new File("Data/"+name+".txt");
		File att=new File("Data/"+name+"att.txt");
		File listTable=new File("Data/Liste_table.txt");
		FileOutputStream outTab =new FileOutputStream(table,true);        
		PrintWriter pTab=new PrintWriter(outTab);
		FileOutputStream outAtt=new FileOutputStream(att,true);        
		PrintWriter pAtt=new PrintWriter(outAtt);
		FileOutputStream outList=new FileOutputStream(listTable,true);        
		PrintWriter pList=new PrintWriter(outList);
		pList.print(name+";;");
		pTab.print("");
		for(int i=0;i<attribut.size();i++) {
			pAtt.print(attribut.get(i)+";;");
		}
		pTab.flush(); pTab.close(); pAtt.flush(); pAtt.close(); pList.flush(); pList.close();
	}

	public boolean traitementCreate(String req)throws Exception{
		File listTable=new File("Data/Liste_table.txt");
		if(listTable.exists()==false){
			FileOutputStream outList=new FileOutputStream(listTable,true);        
      PrintWriter pList=new PrintWriter(outList);
      pList.print(";;temp;;");
			pList.flush();
			pList.close();
		}
		String tables=getAllTable();
		String[] reqs=req.split(" ");
		if(reqs.length==5){
			if(tables.contains(";"+reqs[2]+";")==false){
				if(reqs[3].hashCode()=="column".hashCode()){
					return true;
				}else{ throw new Exception("ERREUR: tokony 'column' le "+reqs[3]);}
			}else{ throw new Exception("ERREUR: table efa mi existe");}
		}else{ throw new Exception("ERREUR: diso le requette");}
	}

	public void create(String name,String donnee)throws Exception{
		String[] d=donnee.split(",");
		Vector<String> v=new Vector<String>();
		for(int i=0;i<d.length;i++){
			v.add(d[i]);
		}
		createTable(name,v);
	}


	//INSERTION DE DONNEE
	public boolean traitementInsert(String req)throws Exception{
		String tables=getAllTable();
		String[] reqs=req.split(" ");
		if(reqs.length==5){
			if(tables.contains(";"+reqs[2]+";")){
				if(reqs[1].hashCode()=="into".hashCode()){
					if(reqs[3].hashCode()=="values".hashCode()){
							return true;
					}else{ throw new Exception("ERREUR: tokony 'values' le "+reqs[3]);}
				}else{ throw new Exception("ERREUR: tokony 'into' le "+reqs[1]);}
			}else{ throw new Exception("ERREUR: table tsy mi existe");}
		}else{ throw new Exception("ERREUR: diso le requette");}
	}

	public void insert(String name,String donnee)throws Exception{
		String[] d=donnee.split(",");
		Vector<String> v=new Vector<String>();
		for(int i=0;i<d.length;i++){
			v.add(d[i]);
		}
		insertData(name,v);
	}


	//UPDATE DE DONNEE
	public void update(String name,String modification,String condition) throws Exception{
		Relation all=getAllData(name);
		String[] cond=condition.split("=");
		String[] modif=modification.split("=");
		int indicec=-1;
		int indicem=-1;
		Vector<Vector<String>> v=new Vector<Vector<String>>();
		for(int i=0;i<all.getColonne().size();i++){
			if(all.getColonne().get(i).hashCode()==cond[0].hashCode()){
				indicec=i;
			}
			if(all.getColonne().get(i).hashCode()==modif[0].hashCode()){
				indicem=i;
			}
		}
		if(indicec<0 || indicem<0){ throw new Exception("ERREUR: diso le colonne");}
		int marque=0;
		for(int i=0;i<all.getDonnee().size();i++){
			if(cond[1].hashCode()==all.getDonnee().get(i).get(indicec).hashCode()){
				marque=1;
				Vector<String> temp=new Vector<String>();
				for(int j=0;j<all.getDonnee().get(i).size();j++){
					if(j==indicem){
						temp.add(modif[1]);
					}else{
						temp.add(all.getDonnee().get(i).get(j));
					}
				}
					v.addElement(temp);
			}else{
					v.addElement(all.getDonnee().get(i));
			}
		}
		if(marque==1){
			change(name,v);
		}else{
			throw new Exception("ERREUR: aucune ligne modifie");
		}
	}

	public boolean traitementUpdate(String req)throws Exception{
		String[] reqs=req.split(" ");
		String tables=getAllTable();
		if(command(req).hashCode()=="update ".hashCode()) {
			if(reqs.length==6) {
				if(tables.contains(";"+reqs[1]+";")) {
					if(reqs[4].hashCode()=="where".hashCode()){
							if(reqs[2].hashCode()=="set".hashCode()){
								return true;
							}else { throw new Exception("tokony 'set' le "+reqs[2]); }
					}else { throw new Exception("tokony 'where' le "+reqs[4]); }
				}else { throw new Exception("diso ny nom de table"); }
			}else { throw new Exception("verifieo tsara le requette"); }
		}else { throw new Exception("verifieo tsara le requette"); }
	}


	//DELETE
	public boolean traitementDelete(String req)throws Exception{
		String[] reqs=req.split(" ");
		String tables=getAllTable();
		if(command(req).hashCode()=="delete ".hashCode()) {
			if(reqs.length==5) {
				if(tables.contains(";"+reqs[2]+";")) {
					if(reqs[3].hashCode()=="where".hashCode()){
							if(reqs[1].hashCode()=="from".hashCode()){
								return true;
							}else { throw new Exception("ERREUR: tokony 'from' le "+reqs[1]); }
					}else { throw new Exception("ERREUR: tokony 'where' le "+reqs[3]); }
				}else { throw new Exception("ERREUR: diso ny nom de table"); }
			}else { throw new Exception("ERREUR: verifieo tsara le requette"); }
		}else { throw new Exception("ERREUR: verifieo tsara le requette"); }
	}

	public void delete(String name,String condition) throws Exception{
		Relation all=getAllData(name);
		String[] cond=condition.split("=");
		int indice=-1;
		Vector<Vector<String>> v=new Vector<Vector<String>>();
		for(int i=0;i<all.getColonne().size();i++){
			if(all.getColonne().get(i).hashCode()==cond[0].hashCode()){
				indice=i;
			}
		}
		if(indice<0){ throw new Exception("ERREUR: diso le colonne");}
		int marque=0;
		for(int i=0;i<all.getDonnee().size();i++){
			if(cond[1].hashCode()==all.getDonnee().get(i).get(indice).hashCode()){
				marque=1;
			}else{
				v.addElement(all.getDonnee().get(i));
			}
		}
		if(marque==1){
			change(name,v);
		}else{
			throw new Exception("ERREUR: aucune ligne supprime");
		}
	}


	//JONTURE
	public Relation join(Relation r1,Relation r2,String condition)throws Exception {
		Vector<Vector<String>> datarep=new Vector<Vector<String>>();
		int ind1=-1;
		int ind2=-1;
		String[] cols=condition.split("=");
		if(cols.length==2) {
			for(int i=0;i<r1.getColonne().size();i++)
			{
				if(cols[0].hashCode()==r1.getColonne().get(i).hashCode()) { ind1=i; break; }
			}	
			for(int i=0;i<r2.getColonne().size();i++)
			{
				if(cols[1].hashCode()==r2.getColonne().get(i).hashCode()) { ind2=i; }
			}
			if(ind1!=-1 && ind2!=-1) {
				for(int i=0;i<r1.getDonnee().size();i++) {
					for(int j=0;j<r2.getDonnee().size();j++) {
						Vector<String> temp=addVectString(r1.getDonnee().get(i),r2.getDonnee().get(j));
						if(r1.getDonnee().get(i).get(ind1).hashCode()==r2.getDonnee().get(j).get(ind2).hashCode()) {
							datarep.addElement(temp);
						}
					}
				}
				Vector<String> colrep=addVectString(r1.getColonne(), r2.getColonne());
				Relation rep=new Relation("",colrep,datarep);
				return rep;
			}else { throw new Exception("ERREUR: verifieo le condition"); }
		}else { throw new Exception("ERREUR: verifieo le condition"); }
	}

	public boolean traitementJoin(String req)throws Exception {
		String[] reqs=req.split(" ");
		if(command(req).hashCode()=="select ".hashCode()) {
			if(reqs.length==8) {
				if(reqs[6].hashCode()=="on".hashCode()) {
					return true;
				}else { throw new Exception("ERREUR: tokony 'on' le "+reqs[6]); }
			}else { throw new Exception("ERREUR: verifieo tsara le requette"); }
		}else { throw new Exception("ERREUR: 'select' ny syntaxe apesaina refa iselect"); }
	}


	//SELECT
	public Relation select(String req)throws Exception{
		if(command(req).hashCode()=="select ".hashCode()) {
			String[] reqs=req.split(" ");
			if(reqs.length==4) {
				if(traitementSelect(reqs).hashCode()=="rehetra".hashCode()) {
					Relation rep=getAllData(reqs[3]);
					return rep;
				}else { 
					Relation rep=projection(reqs[3],reqs[1]);
					return rep;
				}
			}else { throw new Exception("ERREUR: verifieo tsara le requette"); }
		}else { throw new Exception("ERREUR: 'select' ny syntaxe apesaina refa iselect"); }
	}

	public String traitementSelect(String[]reqs)throws Exception {
		String tables=getAllTable();
		if(tables.contains(";"+reqs[3]+";")) {
			if(reqs[2].hashCode()=="from".hashCode()) {
				if(reqs[1].hashCode()=="*".hashCode()) {
					return "rehetra";
				}else { return reqs[1]; }
			}else { throw new Exception("ERREUR: tokony 'from' le "+reqs[2]); }
		}else { throw new Exception("ERREUR: diso ny nom de table"); }
	}

	public Relation projection(String name,String colonne)throws Exception {
		Relation all=getAllData(name);
		Vector<String> att=getAttribut(name);
		String[] cols=colonne.split(",");
		Vector<Integer> indice=new Vector<Integer>();
		for(int i=0;i<cols.length;i++) {
			int marque=0;
			for(int j=0;j<att.size();j++) {
				if(cols[i].hashCode()==att.get(j).hashCode()) {
					marque=1;
					indice.add(j);
					break;
				}
			}
			if(marque==0) {
				throw new Exception("ERREUR: tsisy colonne "+cols[i]+" ao am table "+name);
			}
		}
		Vector<String> colrep=new Vector<String>();
		Vector<Vector<String>> datarep=new Vector<Vector<String>>();
		for(int i=0;i<cols.length;i++) {
			colrep.addElement(all.getColonne().get(indice.get(i)));
		}
		for(int i=0;i<all.getDonnee().size();i++) {
			Vector<String> temp=new Vector<String>();
			for(int j=0;j<cols.length;j++) {
				temp.addElement(all.getDonnee().get(i).get(indice.get(j)));
			}
			datarep.addElement(temp);
		}
		Relation rep=new Relation("",colrep,datarep);
		return rep;
	}


	//TRAITEMENT
	public String traitement(String req) {
		Vector<String> operation=new Vector<String>();
		operation.addElement(" join ");
		for(int i=0;i<operation.size();i++) {
			if(req.contains(operation.get(i))) {
				return operation.get(i);
			}
		}
		return "tsisy";
	}

	public Relation requette(String req)throws Exception {
		String trait=traitement(req);
		if(trait.hashCode()!="tsisy".hashCode()) {
			if(trait.hashCode()==" join ".hashCode()) {
				if(traitementJoin(req)) {
					String[] reqs=req.split(" ");
					Relation r1=getAllData(reqs[3]);
					Relation r2=getAllData(reqs[5]);
					Relation r=join(r1,r2,reqs[7]);
					insertTemp(r);
					Relation rep=select(reqs[0]+" "+reqs[1]+" "+reqs[2]+" temp");
					return rep;
				}else { throw new Exception("ERREUR: verifieo tsara le requette"); }
			} else { throw new Exception("ERREUR: verifieo tsara le requette"); }
		}else{
				String r=command(req);
				if(r.hashCode()=="select ".hashCode()){
					Relation rep=select(req);
					return rep;
				}
				else if(r.hashCode()=="insert ".hashCode()){
					if(traitementInsert(req)){
							String[] reqs=req.split(" ");
							insert(reqs[2],reqs[4]);
							throw new Exception("=> Insertion effectue avec succes");
					}
				}
				else if(r.hashCode()=="create table ".hashCode()){
					if(traitementCreate(req)){
							String[] reqs=req.split(" ");
							create(reqs[2],reqs[4]);
							throw new Exception("=> Table "+reqs[2]+" cree");
					}
				}
				else if(r.hashCode()=="delete ".hashCode()){
					if(traitementDelete(req)){
						String[] reqs=req.split(" ");
						delete(reqs[2],reqs[4]);
						throw new Exception("=> Suppression effectue avec succes");
					}
				}
				else if(r.hashCode()=="update ".hashCode()){
					if(traitementUpdate(req)){
						String[] reqs=req.split(" ");
						update(reqs[1],reqs[3],reqs[5]);
						throw new Exception("=> Update effectue avec succes");
					}
				}
				else{
						throw new Exception("ERREUR: Verifieo tsara le requette");
				}
		}
		return null;
	}

	public String command(String sql){
		String[] req = new String[5];
		req[0]="select ";
		req[1]="insert ";
		req[2]="create table ";
		req[3]="delete ";
		req[4]="update ";
		for(int j=0;j<req.length;j++) {
			int count =0;
			for(int i=0;i<req[j].length();i++){
						if(req[j].charAt(i)==sql.charAt(i)){
								count++;
						}
				}
			if(count==req[j].length()) {
				return req[j];
			}
		}
		return "tsisy";		
	}
}

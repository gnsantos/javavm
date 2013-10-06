import java.io.*;
import java.util.*;
import java.lang.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class VirtualMachine{
 
 Queue programArray = new LinkedList<String>(); /*Lista ligada (com Random Acess) que guarda instru��es. Vetor-programa.*/
 String filePath;

 void interpretSource(BufferedReader file) throws IOException{ /*L� o c�digo e constroi o vetor de instru��es.*/
   String line;
   Pattern comline = Pattern.compile("(\b[a-zA-Z]*\b:\\s*)?(\b[a-zA-Z]{2,4}\b[^:]?)(\\w*)\\s*[\n\f#]*");
   while((line = file.readLine()) != null){
     Matcher m = comline.matcher(line);
     if(m.find()){
       System.out.println("Found value: " + m.group(0) );
       System.out.println("Found value: " + m.group(1) );
       System.out.println("Found value: " + m.group(2) );
     }
   }
 }
 
 public static void main(String[] argv) throws IOException{
  BufferedReader file; /*Objeto que permite leitura do arquivo contendo o c�digo que a m�quina deve executar*/
  VirtualMachine vm = new VirtualMachine();
  Scanner sc = new Scanner(System.in);
  System.out.print("Entre o nome do codigo-fonte: ");
  String name = sc.nextLine();
  file = new BufferedReader(new FileReader(name));
  vm.interpretSource(file);
  
 }
 
}
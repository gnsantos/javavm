import java.io.*;
import java.util.*;
import java.lang.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class VirtualMachine{
  
  LinkedList<String[]> programArray = new LinkedList<String[]>(); /*Lista ligada (com Random Acess) que guarda instruções. Vetor-programa.*/
  Hashtable<String, Integer> labelsHash = new Hashtable<String, Integer>();
  
//   Pattern comline = Pattern.compile("(\\b[a-zA-Z]*\\b:\\s*)?(\\b[a-zA-Z]{2,4}\\b[^:]?)(\\w*)\\s*[\n\f#]*");
  
  void interpretSource(BufferedReader file) throws IOException{ /*Lê o código e constroi o vetor de instruções.*/
    String line;
    int pc = 0;
    Pattern comline = Pattern.compile("(\\b[a-zA-Z]*\\b:\\s*)?(\\b[a-zA-Z]{2,4}\\b[^:]?)(\\w*)\\s*[\n\f#]*");
    Pattern labeline = Pattern.compile("(\\b[a-zA-Z]*\\b:\\s*)[\n\f#]*$");
    Pattern other = Pattern.compile("(^#.*[\n\f]*)");
    
    while((line = file.readLine()) != null && !line.equals("\n") && !line.equals("")){
      Matcher matchLabel = labeline.matcher(line);
      Matcher matchComLine = comline.matcher(line);
      Matcher matchOther = other.matcher(line);
      String label;
      
      if(matchOther.find()){
        System.out.println("foo");
      }
      else if(matchLabel.find()){
        label = matchLabel.group(1).substring(0, matchLabel.group(1).length()-1);
        labelsHash.put(label, pc);
        pc++;
        System.out.println(label);
      }
      
      else if(matchComLine.find()){
        String opcode = "", arg="";
        if(matchComLine.group(1) != null){
          label = matchComLine.group(1).substring(0, matchComLine.group(1).length()-1);
          labelsHash.put(label, pc);
        }
        if(matchComLine.group(2) != null){
          opcode =  matchComLine.group(2);
          System.out.println(opcode);
        }
        if(matchComLine.group(3) != null && !matchComLine.group(3).equals("")){
          arg = matchComLine.group(3);
        }
        pc++;
        String [] comando = {opcode,arg};
        programArray.add(comando);
      }
      
      else{System.out.print(line+": ");System.out.println("Syntax Error.");}
      
    }
  }
  
  void showProgram(){
   for(int i = 0; i < programArray.size(); i++)
     System.out.println(programArray.get(i)[0]);
  }
  
  public static void main(String[] argv) throws IOException{
    BufferedReader file; /*Objeto que permite leitura do arquivo contendo o código que a máquina deve executar*/
    VirtualMachine vm = new VirtualMachine(); /*inicializa a VM*/
    Scanner sc = new Scanner(System.in); 
    System.out.print("Entre o nome do codigo-fonte: ");
    String name = sc.nextLine();
    file = new BufferedReader(new FileReader(name));
    vm.interpretSource(file);
    vm.showProgram();
  }
  
}
import java.io.*;
import java.util.*;
import java.lang.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class VirtualMachine{
  
  private LinkedList<String[]> programArray = new LinkedList<String[]>(); /*Lista ligada (com Random Acess) que guarda instrues. Vetor-programa.*/
  private Hashtable<String, Integer> labelsHash = new Hashtable<String, Integer>();
  private static Parser filter;
  private StackElement myStack = new StackElement();
  private int pc;
  private enum Set{
    ADD, SUB, MUL, DIV, EQ, GT, GE, NE, LT, LE, JIT, JIF, RCL, STO, PUSH, POP, DUP, PRN, END
  }
//   Pattern comline = Pattern.compile("(\\b[a-zA-Z]*\\b:\\s*)?(\\b[a-zA-Z]{2,4}\\b[^:]?)(\\w*)\\s*[\n\f#]*");
  
  private LinkedList<String[]> program(){
    return this.programArray;
  }

  private Hashtable<String, Integer> hashlables(){
    return this.labelsHash;
  }

  void showProgram(){
   for(int i = 0; i < programArray.size(); i++)
     System.out.println(programArray.get(i)[0]+" "+programArray.get(i)[1]);
  }

  void showHash(){
    Enumeration<String> enumKey = labelsHash.keys();
    while(enumKey.hasMoreElements()) {
      String key = enumKey.nextElement();
      Integer val = labelsHash.get(key);
      System.out.println(key+" "+val);
    }
  }

  private void makeOperation(int index){
    String opCode = programArray.get(index)[0];
    Set myOperation = Set.valueOf(opCode);
   // System.out.println("Valor e : " + myOperation);

    switch (myOperation) {
        case ADD:
          System.out.println("Isso e um ADD");
          break;
        case SUB:
          System.out.println("Isso e um SUB");
          break;
        case MUL:
          System.out.println("Isso e um MUL");
          break;
        case DIV:
          System.out.println("Isso e um ADD");
          break;
        case EQ:
          System.out.println("Isso e um SUB");
          break;
        case GT:
          System.out.println("Isso e um MUL");
          break;
        case GE:
          System.out.println("Isso e um ADD");
          break;
        case NE:
          System.out.println("Isso e um SUB");
          break;
        case LT:
          System.out.println("Isso e um MUL");
          break;
        case LE:
          System.out.println("Isso e um ADD");
          break;
        case JIT:
          System.out.println("Isso e um SUB");
          break;
        case JIF:
          System.out.println("Isso e um MUL");
          break;
        case RCL:
          System.out.println("Isso e um ADD");
          break;
        case STO:
          System.out.println("Isso e um SUB");
          break;
        case PUSH:
          System.out.println("Isso e um MUL");
          break;
        case POP:
          System.out.println("Isso e um ADD");
          break;
        case DUP:
          System.out.println("Isso e um SUB");
          break;
        case PRN:
          System.out.println("Isso e um MUL");
          break;
        case END:
          System.out.println("Isso e um ADD");
          break;
        case SYSCALL1:
          System.out.println("Isso e um SUB");
          break;
        case SYSCALL2:
          System.out.println("Isso e um MUL");
          break;
        default:
          System.out.println("QUI BURRO! DA ZERO PRA ELE!");
          break;

    }
  }

  private void setPC(int value){
    this.pc = value;
  }
  private int getPC(){
    return this.pc;
  }

  void runCode(){
    for(setPC(0); getPC() < programArray.size(); setPC(getPC() +1 )){
      makeOperation(getPC());
    }
  }

  public static void main(String[] argv) throws IOException{
    VirtualMachine vm = new VirtualMachine(); /*inicializa a VM*/
    String name = argv[0];
    filter.parseToMe(vm.program(), vm.hashlables(), name);
    vm.showProgram();
    vm.showHash();
    vm.runCode();
  }
  
}
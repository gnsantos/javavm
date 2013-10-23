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
    PUSH, 
    POP, 
    DUP, 
    PRN,
    SHW,
    RCL,
    STO,
    END,
    ADD,
    SUB,
    MUL,
    DIV,
    EQ,
    GT,
    GE,
    LT,
    LE,
    NE,
    JIT,
    JIF,
    JMP
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
    boolean decision;
    String opCode = programArray.get(index)[0];
    //System.out.println("Command : " + opCode);
    Set myOperation = Set.valueOf(opCode);
   // System.out.println("Valor e : " + myOperation);

    switch (myOperation) {
        case PUSH:
          try{
            myStack.pile(Double.parseDouble(programArray.get(index)[1]));

          }catch(NumberFormatException e1){
            myStack.pile(programArray.get(index)[1]);
          }
          break;
        case POP:
          //System.out.println("Isso e um POP");
          myStack.discartTop();
          break;
        case DUP:
          //System.out.println("Isso e um DUP");
          myStack.dupTop();
          break;
        case PRN:
          //System.out.println("Isso e um PRN");
          myStack.printTop();
          break;
        case SHW:
          myStack.printStack();
          break;
        case RCL:
        try{
          myStack.retriveMem(Integer.parseInt(programArray.get(index)[1]));
        }catch(NumberFormatException e1){
          System.out.println("Ocorreu um Erro na RCL Operation");
        }
          break;
        case STO:
          try{
            myStack.salveMem(Integer.parseInt(programArray.get(index)[1]));
          }catch(NumberFormatException e1){
            System.out.println("Ocorreu um Erro na STO Operation");
          }
          break;
        case END:
          this.pc = programArray.size() +1;
          break;
        case ADD:
          myStack.operation(0);
          break;
        case SUB:
          myStack.operation(1);
          break;
        case MUL:
          myStack.operation(2);
          break;
        case DIV:
          myStack.operation(3);
          break;
        case EQ:
          myStack.operation(4);
          break;
        case GT:
          myStack.operation(5);
          break;
        case GE:
          myStack.operation(6);
          break;
        case LT:
          myStack.operation(7);
          break;
        case LE:
          myStack.operation(8);
          break;
        case NE:
          myStack.operation(9);
          break;
        case JIT:
          myStack.operation(10);
          decision = myStack.jumpTrue();
          if(decision){
            jumpPC(index);
          }
          break;
        case JIF:
          myStack.operation(11);
          decision = myStack.jumpFalse();
          if(!decision){
            jumpPC(index);
          }
          break;
        case JMP:
          jumpPC(index);
          break;
        default:
          System.out.println("XX");
          break;
    }
  }

  private void jumpPC(int position){
    try{
      this.pc = Integer.parseInt(programArray.get(position)[1]);
      //jump to a exactly vector instruction position. 
    }catch(NumberFormatException e1){
      this.pc = labelsHash.get(programArray.get(position)[1]);
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
    //vm.showProgram();
    vm.showHash();
    vm.runCode();
  }
  
}
import java.lang.*;
import java.util.*;

public class StackElement{
	public ArrayList<StackableInterface> stackTeste = new ArrayList<StackableInterface>();
	
	public void push(int valor){
		Stackable_Number val = new Stackable_Number();
		stackTeste.add(val);
	}
	public void push(String texto){
		Stackable_String val = new Stackable_String();
		stackTeste.add(val);
	}

	public void printStack(){
		// System.out.println("Valor do bags"+stackTeste.get(0));
		stackTeste.get(0).teste1();
	}

	public void clearStack(){
		stackTeste.clear();
	}

	public void sizeStack(){
		int x = stackTeste.size();
		System.out.println("qnt de elementos ; "+x);
	}

	public static void main (String [] args){
		// System.out.println("Teste ok!");
		// StackFrame tmp = new StackFrame("Fellipe");
		// tmp.push(10);
		// tmp.push(13);
		// tmp.push(66);
		// tmp.sizeStack();
		// tmp.containStack(14);

		// Stackable_String tmp = new Stackable_String();
		// tmp.teste1();
		// Stackable_Number tmp2 = new Stackable_Number();
		// tmp2.teste1();

		StackElement myStack = new StackElement();
		myStack.push("teste");
		myStack.sizeStack();
		myStack.printStack();
	}
}
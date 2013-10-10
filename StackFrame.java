import java.lang.*;
import java.util.*;

public class StackFrame{
	public String myName;
	public ArrayList<Double> doubleStack = new ArrayList<Double>();

	public StackFrame(String name){
		myName = name;
	}

	public void push(double val){
		doubleStack.add(val);
	}
	public void printStack(){
		System.out.println("Valor do bags"+doubleStack.get(0));
	}

	public void clearStack(){
		doubleStack.clear();
	}

	public void sizeStack(){
		int x = doubleStack.size();
		System.out.println("qnt de elementos ; "+x);
	}

	public void containStack(double val){
		if (doubleStack.contains(val)) {
			System.out.println("Esta presente : " + val);
		}
		else {
			System.out.println("Nao esta presente : " + val);	
		}
	}
	// public static void main(String[] args){


	// 	// tmp.clearStack();
	// 	// tmp.sizeStack();
	// }
}

// public class StackFrame{
// 	public String myName;
// 	public StackFrame(String name){
// 		myName = name;
// 	}

// 	public void printName(){
// 		System.out.println("My name is:");
// 		System.out.println(myName);
// 	}
// 	public static void main(String[] args){
// 		System.out.println("Teste ok!");
// 		StackFrame tmp = new StackFrame("Fellipe");
// 		tmp.printName();

// 		StackFrame tmp2 = new StackFrame("Fellipexxx");
// 		tmp2.printName();
// 	}
// }
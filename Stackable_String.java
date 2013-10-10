import java.lang.*;
import java.util.*;

public class Stackable_String implements StackableInterface{
	public String myValue;

	public Stackable_String(String value){
		myValue = value;
	}
	public void teste1(){
		System.out.println("Teste da classe Stackable_Striing::teste1" +"\n" + "Valor : " + myValue);
	}
	public void waka(){
		System.out.println("Teste da classe Stackable_String::waka");
	}

	public void printYourself(){
		System.out.println("My Value is : " + myValue);
	}

	public double getValue(){
		return myValue.length();
	}

}
import java.io.*;
import java.util.*;
import java.lang.*;

public class SystemRequest{
	public static void makeRequest(String opCode, String arg){
		System.out.println("Commando : " +opCode +"\nArgument : " +arg);
		foo();
	}

	private static void foo(){
		System.out.println("Enjoy the foo");
	}
}
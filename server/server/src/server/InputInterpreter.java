package server;

public class InputInterpreter {

	public InputInterpreter(String input) {
	}
	
	public static String interpretString(String input) {
		String interpret = " * THIS STRING IS MODIFIED *";
		input = input + interpret;
		return input;
	}
}

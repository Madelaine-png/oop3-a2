package appDomain;


import java.util.EmptyStackException;
import exceptions.EmptyQueueException;

import implementations.MyStack;
import implementations.MyQueue;

public class Driver {

	public static void main(String[] args) throws EmptyStackException, EmptyQueueException {
		MyStack stack = new MyStack();
		MyQueue errorQ = new MyQueue();
		args = String.join("", args).split("");
		
		for( int i = 0; i < args.length; i++ ) {
			if(args[i].equals("(") || args[i].equals("{") || args[i].equals("[") || args[i].equals("<")) {
				stack.push(args[i]);
			} else if(args[i].equals(")") || args[i].equals("}") || args[i].equals("]") || args[i].equals(">")) {
				if(stack.isEmpty()) {
					errorQ.enqueue(args[i]);
				} else if(stack.peek() == "(" && args[i].equals(")") || stack.peek() == "{" && args[i].equals("}") || stack.peek() == "[" && args[i].equals("]") || stack.peek() == "<" && args[i].equals(">")) {
					stack.pop();
				} else if(errorQ.isEmpty() == false) {
					if(errorQ.peek() == "(" && args[i] == ")" || errorQ.peek() == "{" && args[i] == "}" || errorQ.peek() == "[" && args[i] == "]" || errorQ.peek() == "<" && args[i] == ">") {
						errorQ.dequeue();
					}
				} else {
					//person 2
				}
			}
		}
		//person 3
		
	}

}
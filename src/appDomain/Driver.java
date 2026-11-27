package appDomain;


import java.util.EmptyStackException;
import exceptions.EmptyQueueException;

import implementations.MyStack;
import implementations.MyQueue;
import utilities.Iterator;

public class Driver {

	public static void main(String[] args) throws EmptyStackException, EmptyQueueException {
		MyStack stack = new MyStack();
		MyQueue errorQ = new MyQueue();
		MyQueue extrasQ = new MyQueue();
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
					// person 2
					// search the stack for a matching opening bracket
					boolean found = false;
					Iterator it = stack.iterator();
					while(it.hasNext() && !found) {
						String open = it.next();
						if(open.equals("(") && args[i].equals(")") || open.equals("{") && args[i].equals("}") || open.equals("[") && args[i].equals("]") || open.equals("<") && args[i].equals(">")) {
							found = true;
						}
					}

					if(found) {
						// pop until the match; everything above it was never closed, so it goes to errorQ
						boolean matched = false;
						while(!matched) {
							String popped = stack.pop();
							if(popped.equals("(") && args[i].equals(")") || popped.equals("{") && args[i].equals("}") || popped.equals("[") && args[i].equals("]") || popped.equals("<") && args[i].equals(">")) {
								matched = true; // the match itself closes properly
							} else {
								errorQ.enqueue(popped);
							}
						}
					} else {
						// no match anywhere on the stack
						extrasQ.enqueue(args[i]);
					}
				}
			}
		}
		while(!stack.isEmpty()) {
			errorQ.enqueue(stack.pop());
		}

		if(errorQ.isEmpty() && extrasQ.isEmpty()) {
			System.out.println("No syntax errors found.");
		} else {
			if(!errorQ.isEmpty()) {
				System.out.println("Unmatched / out-of-place brackets (errorQ):");
				while(!errorQ.isEmpty()) {
					System.out.println("  " + errorQ.dequeue());
				}
			}
			if(!extrasQ.isEmpty()) {
				System.out.println("Extra closing brackets (extrasQ):");
				while(!extrasQ.isEmpty()) {
					System.out.println("  " + extrasQ.dequeue());
				}
			}
		}
		
	}

}

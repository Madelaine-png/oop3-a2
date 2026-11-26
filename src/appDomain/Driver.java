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
					handleUnmatchedClose(args[i], stack, errorQ, extrasQ);
				}
			}
		}
		//person 3
		
	}

	/**
	 * @param element   the closing bracket being handled
	 * @param stack   the opening brackets not yet closed
	 * @param errorQ  brackets found to be out of place
	 * @param extrasQ closing brackets with no matching opening bracket
	 */
	public static void handleUnmatchedClose(String element, MyStack stack, MyQueue errorQ, MyQueue extrasQ) {
		String close = bracketOf(element);

		// look through the stack for a matching opening bracket
		boolean found = false;
		Iterator it = stack.iterator();
		while(it.hasNext() && !found) {
			if(isMatchingPair(bracketOf(it.next()), close)) {
				found = true;
			}
		}

		if(found) {
			// everything above the match was never closed: report it
			while(!isMatchingPair(bracketOf(stack.peek()), close)) {
				errorQ.enqueue(stack.pop());
			}
			stack.pop(); // the match itself closes properly
		} else {
			extrasQ.enqueue(element);
		}
	}

	/**
	 * @param open  the opening bracket, e.g. "("
	 * @param close the closing bracket, e.g. ")"
	 * @return true if they are a matching pair
	 */
	public static boolean isMatchingPair(String open, String close) {
		return (open.equals("(") && close.equals(")"))
			|| (open.equals("{") && close.equals("}"))
			|| (open.equals("[") && close.equals("]"))
			|| (open.equals("<") && close.equals(">"));
	}

	/**
	 * @param element the element, e.g. "(" or "12:("
	 * @return the bracket, e.g. "("
	 */
	public static String bracketOf(String element) {
		return element.substring(element.indexOf(':') + 1);
	}

}

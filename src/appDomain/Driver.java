package appDomain;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.EmptyStackException;
import exceptions.EmptyQueueException;

import implementations.MyStack;
import implementations.MyQueue;
import utilities.Iterator;

public class Driver {

	/**
	 * Reads the file named on the command line one line at a time, checks
	 * every bracket using a stack and two queues, and print any errors.
	 * The line number of each bracket is kept in a matching stack or queue that is always changed together with the bracket's own stack or queue.
	 *
	 * @param args args[0] is the path of the file to parse
	 * @throws EmptyStackException if the stack is read while empty
	 * @throws EmptyQueueException if a queue is read while empty
	 * @throws IOException if the file cannot be read
	 */
	public static void main(String[] args) throws EmptyStackException, EmptyQueueException, IOException {
		MyStack stack = new MyStack();
		MyQueue errorQ = new MyQueue();
		MyQueue extrasQ = new MyQueue();
		MyStack lineStack = new MyStack();
		MyQueue errorLines = new MyQueue();
		MyQueue extrasLines = new MyQueue();

		if(args.length < 1) {
			System.out.println("Please give the file to parse, e.g. java -jar Parser.jar test.txt");
			return;
		}
		String fileName = args[0];
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(fileName));
		} catch(FileNotFoundException e) {
			System.out.println("File not found: " + fileName);
			return;
		}

		String line;
		int lineNum = 0;
		while((line = reader.readLine()) != null) {
			lineNum++;
			args = line.split("");

			for( int i = 0; i < args.length; i++ ) {
				String lineText = "" + lineNum;
				if(args[i].equals("(") || args[i].equals("{") || args[i].equals("[") || args[i].equals("<")) {
					stack.push(args[i]);
					lineStack.push(lineText);
				} else if(args[i].equals(")") || args[i].equals("}") || args[i].equals("]") || args[i].equals(">")) {
					if(stack.isEmpty()) {
						errorQ.enqueue(args[i]);
						errorLines.enqueue(lineText);
					} else if(stack.peek().equals("(") && args[i].equals(")") || stack.peek().equals("{") && args[i].equals("}") || stack.peek().equals("[") && args[i].equals("]") || stack.peek().equals("<") && args[i].equals(">")) {
						stack.pop();
						lineStack.pop();
					} else if(errorQ.isEmpty() == false && (errorQ.peek().equals("(") && args[i].equals(")") || errorQ.peek().equals("{") && args[i].equals("}") || errorQ.peek().equals("[") && args[i].equals("]") || errorQ.peek().equals("<") && args[i].equals(">"))) {
						errorQ.dequeue();
						errorLines.dequeue();
					} else {
						
						boolean found = false;
						Iterator it = stack.iterator();
						while(it.hasNext() && !found) {
							String open = it.next();
							if(open.equals("(") && args[i].equals(")") || open.equals("{") && args[i].equals("}") || open.equals("[") && args[i].equals("]") || open.equals("<") && args[i].equals(">")) {
								found = true;
							}
						}

						if(found) {
							boolean matched = false;
							while(!matched) {
								String popped = stack.pop();
								String poppedLine = lineStack.pop();
								if(popped.equals("(") && args[i].equals(")") || popped.equals("{") && args[i].equals("}") || popped.equals("[") && args[i].equals("]") || popped.equals("<") && args[i].equals(">")) {
									matched = true;
								} else {
									errorQ.enqueue(popped);
									errorLines.enqueue(poppedLine);
								}
							}
						} else {
							// no match anywhere on the stack
							extrasQ.enqueue(args[i]);
							extrasLines.enqueue(lineText);
						}
					}
				}
			}
		}
		reader.close();

		while(!stack.isEmpty()) {
			errorQ.enqueue(stack.pop());
			errorLines.enqueue(lineStack.pop());
		}

		MyQueue realErrors = new MyQueue();
		MyQueue realErrorLines = new MyQueue();
		while(!errorQ.isEmpty() && !extrasQ.isEmpty()) {
			String open = errorQ.peek();
			String close = extrasQ.peek();
			if(open.equals("(") && close.equals(")") || open.equals("{") && close.equals("}") || open.equals("[") && close.equals("]") || open.equals("<") && close.equals(">")) {
				errorQ.dequeue();
				errorLines.dequeue();
				extrasQ.dequeue();
				extrasLines.dequeue();
			} else {
				realErrors.enqueue(errorQ.dequeue());
				realErrorLines.enqueue(errorLines.dequeue());
			}
		}
		while(!errorQ.isEmpty()) {
			realErrors.enqueue(errorQ.dequeue());
			realErrorLines.enqueue(errorLines.dequeue());
		}
		errorQ = realErrors;
		errorLines = realErrorLines;

		if(errorQ.isEmpty() && extrasQ.isEmpty()) {
			System.out.println("No syntax errors found.");
		} else {
			if(!errorQ.isEmpty()) {
				System.out.println("Unmatched / out-of-place brackets (errorQ):");
				while(!errorQ.isEmpty()) {
					System.out.println("  Line " + errorLines.dequeue() + ": \"" + errorQ.dequeue() + "\"");
				}
			}
			if(!extrasQ.isEmpty()) {
				System.out.println("Extra closing brackets (extrasQ):");
				while(!extrasQ.isEmpty()) {
					System.out.println("  Line " + extrasLines.dequeue() + ": \"" + extrasQ.dequeue() + "\"");
				}
			}
		}

	}

}

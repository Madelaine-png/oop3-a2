package implementations;

import java.util.EmptyStackException;
import utilities.Iterator;
import utilities.StackADT;

/**
 * An array-backed implementation of the {@link StackADT} interface,
 * for use in CPRG 304 at SAIT.
 * <p>
 * This class delegates all storage to an internal {@link MyArrayList},
 * treating the end of the list as the top of the stack. {@link #push(String)}
 * appends to the end; {@link #pop()} and {@link #peek()} access the last
 * element. A stack follows a <strong>Last-In, First-Out (LIFO)</strong>
 * ordering policy: the last element pushed is the first one returned.
 * </p>
 *
 * <p><strong>Design Note – String-Specific Implementation:</strong><br>
 * This class is intentionally designed to store {@link String} elements only.
 * A fully reusable, type-safe design would use Java generics (e.g.,
 * {@code MyStack<E>}), allowing any object type to be stored in the stack.
 * Generic programming is outside the scope of CPRG 304 and has therefore
 * been omitted. All element fields, parameters, and return types are fixed
 * as {@link String} throughout this class and its implementations.
 * </p>
 */
public class MyStack implements StackADT
{
	private MyArrayList stack = new MyArrayList();

	@Override
	public void push(String toAdd) throws NullPointerException {
		stack.add(toAdd);
	}

	@Override
	public String pop() throws EmptyStackException {
		if(stack.isEmpty() == true) {
			throw new EmptyStackException();
		}
		
		String popped = stack.get(this.size()-1);
		
		stack.remove(this.size()-1);
		
		return popped;
	}

	@Override
	public String peek() throws EmptyStackException {
		if(stack.isEmpty() == true) {
			throw new EmptyStackException();
		}
		
		return stack.get(this.size()-1);
	}

	@Override
	public void clear() {
		stack.clear();
		
	}

	@Override
	public boolean isEmpty() {
		return stack.isEmpty();
	}

	@Override
	public String[] toArray(String[] holder) throws NullPointerException {
		return stack.toArray(holder);
	}

	@Override
	public boolean contains(String toFind) throws NullPointerException {
		return stack.contains(toFind);
	}

	@Override
	public int search(String toFind) {
		if(this.contains(toFind) == true) {
			for (int i = this.size() - 1; i > 0; i--) {
				if(stack.get(i) == toFind) {
					return this.size() - i;
				}
			}
			return this.size() - stack.indexOf(toFind);
		}
		return -1;
	}

	@Override
	public Iterator iterator() {
		return stack.iterator();
	}

	@Override
	public boolean equals(StackADT that) {
		if(this.size() == that.size()) {
			Iterator it1 = this.iterator();
			Iterator it2 = that.iterator();
			while (it1.hasNext()){
				if(it1.next() != it2.next()) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public int size() {
		return stack.size();
	}
  
}
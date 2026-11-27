package implementations;

import java.util.NoSuchElementException;
import java.util.Arrays;
import utilities.Iterator;
import utilities.ListADT;

/**
 * A dynamic array-based implementation of the {@link ListADT} interface,
 * for use in CPRG 304 at SAIT.
 * <p>
 * This class stores {@link String} elements in an internally managed array
 * that grows automatically when capacity is reached. The growth amount is
 * determined by a configurable growth rate expressed as a decimal percentage
 * of the current array length (e.g., {@code 0.25} means 25% growth).
 * All index parameters are zero-based unless otherwise noted.
 * </p>
 *
 * <p><strong>Design Note – String-Specific Implementation:</strong><br>
 * This class is intentionally designed to store {@link String} elements only.
 * A fully reusable, type-safe design would use Java generics (e.g.,
 * {@code MyArrayList<E>}), allowing any object type to be stored in the list.
 * Generic programming is outside the scope of CPRG 304 and has therefore
 * been omitted. All element fields, parameters, and return types are fixed
 * as {@link String} throughout this class and its implementations.
 * </p>
 */
public class MyArrayList implements ListADT
{

	private int size;
	private String[] list = new String[0];
	
	@Override
	public int size() {
		return this.size;
	}

	@Override
	public void clear() {
		list = new String[0];
		size = 0;
	}

	@Override
	public boolean add(int index, String toAdd) throws NullPointerException, IndexOutOfBoundsException {
		if(toAdd == null) {
			throw new NullPointerException();
		}
		
		String[] newList = new String[this.size + 1];
		System.arraycopy(list, 0, newList, 0, index);
		newList[index] = toAdd;
		System.arraycopy(list, index, newList, index + 1, this.size-index);
		list = newList;
		
		size++;
		return true;
	}

	@Override
	public boolean add(String toAdd) throws NullPointerException {
		if(toAdd == null) {
			throw new NullPointerException();
		}
		
		String[] newList = Arrays.copyOf(list, this.size+1);
		
		newList[this.size] = toAdd;
		list = newList;
		
		size++;
		return true;
	}

	@Override
	public boolean addAll(ListADT toAdd) throws NullPointerException {
		
		String[] newList = Arrays.copyOf(list, this.size + toAdd.size());
		
		int place = 0;
		for (int i = this.size; i < this.size + toAdd.size(); i++) {
			newList[i] = toAdd.get(place);
			place++;
		}
		
		list = newList;
		
		size += toAdd.size();
		return true;
	}

	@Override
	public String get(int index) throws IndexOutOfBoundsException {
		return list[index];
	}

	@Override
	public String remove(int index) throws IndexOutOfBoundsException {
		String removed = list[index];
		
		String[] newList = new String[this.size - 1];
		System.arraycopy(list, 0, newList, 0, index);
		System.arraycopy(list, index+1, newList, index, this.size-index-1);
		list = newList;
		
		size--;
		
		return removed;
	}

	@Override
	public String remove(String toRemove) throws NullPointerException {
		if(toRemove == null) {
			throw new NullPointerException();
		}
		
		for (int i = 0; i < this.size; i++) {
			if(list[i] == toRemove) {
				String[] newList = new String[this.size - 1];
				System.arraycopy(list, 0, newList, 0, i);
				System.arraycopy(list, i+1, newList, i, this.size-i-1);
				list = newList;
				
				size--;
				return toRemove;
			}
		}
		return null;
	}

	@Override
	public String set(int index, String toChange) throws NullPointerException, IndexOutOfBoundsException {
		if(toChange == null) {
			throw new NullPointerException();
		}
		
		String old = list[index];
		list[index] = toChange;
		
		return old;
	}

	@Override
	public boolean isEmpty() {
		if(list.length == 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean contains(String toFind) throws NullPointerException {
		if(toFind == null) {
			throw new NullPointerException();
		}
		
		for (int i = 0; i < this.size; i++) {
			if(list[i] == toFind) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int indexOf(String toFind) throws NullPointerException {
		if(toFind == null) {
			throw new NullPointerException();
		}
		
		for (int i = 0; i < this.size; i++) {
			if(list[i] == toFind) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public String[] toArray(String[] toHold) throws NullPointerException {
		if(toHold.length < this.size) {
			String[] newList = Arrays.copyOf(list, this.size);
			toHold = newList;
		} else {
			toHold = Arrays.copyOf(list, this.size);
		}
		return toHold;
	}

	@Override
	public Iterator iterator() {
		Iterator it = new ListIterator();
		return it;
	}

   
	class ListIterator implements Iterator{
		private int itIndex = 0;
		String[] newList = Arrays.copyOf(list, size);
		
		@Override
		public boolean hasNext() {
			if(size >=  itIndex + 1) {
				return true;
			}
			return false;
		}
		
		@Override
		public String next() throws NoSuchElementException {
			String output = newList[itIndex];
			
			itIndex++;
			return output;
		}
		
	}
}

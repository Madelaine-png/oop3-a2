package implementations;

import java.util.Arrays;
import java.util.NoSuchElementException;
import utilities.Iterator;
import utilities.ListADT;

/**
 * A doubly linked list (DLL) implementation of the {@link ListADT} interface,
 * for use in CPRG 304 at SAIT.
 * <p>
 * This class stores {@link String} elements in a chain of {@link MyDLLNode}
 * objects, each of which holds a reference to both its predecessor ({@code pred})
 * and its successor ({@code succ}). The list maintains explicit {@code head}
 * and {@code tail} references for O(1) access to both ends of the chain.
 * Insertions and removals at arbitrary positions require O(n) traversal.
 * All index parameters are zero-based unless otherwise noted.
 * </p>
 *
 * <p><strong>Design Note – String-Specific Implementation:</strong><br>
 * This class is intentionally designed to store {@link String} elements only.
 * A fully reusable, type-safe design would use Java generics (e.g.,
 * {@code MyDLL<E>}), allowing any object type to be stored in the list.
 * Generic programming is outside the scope of CPRG 304 and has therefore
 * been omitted. All element fields, parameters, and return types are fixed
 * as {@link String} throughout this class and its implementations.
 * </p>
 */
public class MyDLL implements ListADT
{

    /** First node in the list */
    private MyDLLNode head;

    /** Last node in the list */
    private MyDLLNode tail;

    /** Number of elements currently in the list. */
    private int size;

    /**
     * Creates an empty list.
     */
    public MyDLL()
    {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * @param index a valid index in [0, size - 1]
     * @return the node at that position
     */
    private MyDLLNode getNode( int index )
    {
        MyDLLNode current;
        if( index < size / 2 )
        {
            current = head;
            for( int i = 0; i < index; i++ )
            {
                current = current.getSucc();
            }
        }
        else
        {
            current = tail;
            for( int i = size - 1; i > index; i-- )
            {
                current = current.getPred();
            }
        }
        return current;
    }

    /**
     * @param index the index to check
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    private void checkIndex( int index ) throws IndexOutOfBoundsException
    {
        if( index < 0 || index >= size )
        {
            throw new IndexOutOfBoundsException( "Index: " + index + ", Size: " + size );
        }
    }

    /**
     * @param node a node currently in this list
     * @return the element that was stored in the node
     */
    private String unlink( MyDLLNode node )
    {
        MyDLLNode before = node.getPred();
        MyDLLNode after = node.getSucc();

        if( before == null )
        {
            head = after;           // removing the head
        }
        else
        {
            before.setSucc( after );
        }

        if( after == null )
        {
            tail = before;          // removing the tail
        }
        else
        {
            after.setPred( before );
        }

        node.setPred( null );
        node.setSucc( null );
        size--;
        return node.getElement();
    }

    @Override
    public int size()
    {
        return size;
    }

    @Override
    public void clear()
    {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public boolean add( int index, String toAdd ) throws NullPointerException, IndexOutOfBoundsException
    {
        if( toAdd == null )
        {
            throw new NullPointerException( "Cannot add a null element." );
        }
        if( index < 0 || index > size )
        {
            throw new IndexOutOfBoundsException( "Index: " + index + ", Size: " + size );
        }

        MyDLLNode newNode = new MyDLLNode( toAdd );

        if( size == 0 )
        {
            // empty list: new node is both head and tail
            head = newNode;
            tail = newNode;
        }
        else if( index == 0 )
        {
            // insert before the current head
            newNode.setSucc( head );
            head.setPred( newNode );
            head = newNode;
        }
        else if( index == size )
        {
            // insert after the current tail
            newNode.setPred( tail );
            tail.setSucc( newNode );
            tail = newNode;
        }
        else
        {
            // insert in the middle, just before the node currently at index
            MyDLLNode after = getNode( index );
            MyDLLNode before = after.getPred();
            newNode.setPred( before );
            newNode.setSucc( after );
            before.setSucc( newNode );
            after.setPred( newNode );
        }

        size++;
        return true;
    }

    @Override
    public boolean add( String toAdd ) throws NullPointerException
    {
        return add( size, toAdd );
    }

    @Override
    public boolean addAll( ListADT toAdd ) throws NullPointerException
    {
        if( toAdd == null )
        {
            throw new NullPointerException( "Cannot add from a null list." );
        }

        Iterator it = toAdd.iterator();
        boolean changed = false;
        while( it.hasNext() )
        {
            add( it.next() );
            changed = true;
        }
        return changed || toAdd.size() == 0;
    }

    @Override
    public String get( int index ) throws IndexOutOfBoundsException
    {
        checkIndex( index );
        return getNode( index ).getElement();
    }

    @Override
    public String remove( int index ) throws IndexOutOfBoundsException
    {
        checkIndex( index );
        return unlink( getNode( index ) );
    }

    @Override
    public String remove( String toRemove ) throws NullPointerException
    {
        if( toRemove == null )
        {
            throw new NullPointerException( "Cannot remove a null element." );
        }

        for( MyDLLNode current = head; current != null; current = current.getSucc() )
        {
            if( toRemove.equals( current.getElement() ) )
            {
                return unlink( current );
            }
        }
        return null;    // not found
    }

    @Override
    public String set( int index, String toChange ) throws NullPointerException, IndexOutOfBoundsException
    {
        if( toChange == null )
        {
            throw new NullPointerException( "Cannot set a null element." );
        }
        checkIndex( index );

        MyDLLNode node = getNode( index );
        String old = node.getElement();
        node.setElement( toChange );
        return old;
    }

    @Override
    public boolean isEmpty()
    {
        return size == 0;
    }

    @Override
    public boolean contains( String toFind ) throws NullPointerException
    {
        return indexOf( toFind ) != -1;
    }

    @Override
    public int indexOf( String toFind ) throws NullPointerException
    {
        if( toFind == null )
        {
            throw new NullPointerException( "Cannot search for a null element." );
        }

        int index = 0;
        for( MyDLLNode current = head; current != null; current = current.getSucc() )
        {
            if( toFind.equals( current.getElement() ) )
            {
                return index;
            }
            index++;
        }
        return -1;
    }

    @Override
    public String[] toArray( String[] toHold ) throws NullPointerException
    {
        if( toHold == null )
        {
            throw new NullPointerException( "Holder array cannot be null." );
        }

        String[] result = ( toHold.length >= size ) ? toHold : new String[ size ];

        int i = 0;
        for( MyDLLNode current = head; current != null; current = current.getSucc() )
        {
            result[ i++ ] = current.getElement();
        }
        if( result.length > size )
        {
            result[ size ] = null;
        }
        return result;
    }

    @Override
    public Iterator iterator()
    {
        return new DLLIterator();
    }


    private class DLLIterator implements Iterator
    {
        /** Private copy of the elements, in order. */
        private final String[] copy;

        /** Index of the next element to return. */
        private int position;

        private DLLIterator()
        {
            copy = new String[ size ];
            int i = 0;
            for( MyDLLNode current = head; current != null; current = current.getSucc() )
            {
                copy[ i++ ] = current.getElement();
            }
            position = 0;
        }

        @Override
        public boolean hasNext()
        {
            return position < copy.length;
        }

        @Override
        public String next() throws NoSuchElementException
        {
            if( !hasNext() )
            {
                throw new NoSuchElementException( "No more elements in the list." );
            }
            return copy[ position++ ];
        }
    }
}

package implementations;

/**
 * A single node in a doubly linked list (DLL), used as the building block
 * for {@link MyDLL} in CPRG 304 at SAIT.
 * <p>
 * Each node stores a {@link String} element and maintains two directional
 * references: a predecessor ({@code pred}) pointing to the node immediately
 * before it in the chain, and a successor ({@code succ}) pointing to the
 * node immediately after it. A {@code null} predecessor indicates the node
 * is the head of the list; a {@code null} successor indicates it is the tail.
 * </p>
 *
 * <p><strong>Design Note – String-Specific Implementation:</strong><br>
 * This class is intentionally designed to store {@link String} elements only.
 * A fully reusable, type-safe design would use Java generics (e.g.,
 * {@code DLLNode<E>}), allowing any object type to be stored in the node.
 * Generic programming is outside the scope of CPRG 304 and has therefore
 * been omitted. All element fields, parameters, and return types are fixed
 * as {@link String} throughout this class and its usages.
 * </p>
 */
public class MyDLLNode
{
    // Fields

    /** The String element stored in this node. */
    private String element;

    /** The node before this one */
    private MyDLLNode pred;

    /** The node after this one */
    private MyDLLNode succ;


    // Constructors

    /**
     * Creates a node holding {@code element} with no neighbours.
     *
     * @param element the String to store
     */
    public MyDLLNode( String element )
    {
        this( element, null, null );
    }

    /**
     * Creates a node holding an element, linked to the given neighbours.
     *
     * @param element the String to store
     * @param pred    the node before this one
     * @param succ    the node after this one 
     */
    public MyDLLNode( String element, MyDLLNode pred, MyDLLNode succ )
    {
        this.element = element;
        this.pred = pred;
        this.succ = succ;
    }


    // Getters and setters

    /** @return the element stored in this node */
    public String getElement()
    {
        return element;
    }

    /** @param element the new element to store in this node */
    public void setElement( String element )
    {
        this.element = element;
    }

    /** @return the previous node */
    public MyDLLNode getPred()
    {
        return pred;
    }

    /** @param pred the new previous node */
    public void setPred( MyDLLNode pred )
    {
        this.pred = pred;
    }

    /** @return the next node */
    public MyDLLNode getSucc()
    {
        return succ;
    }

    /** @param succ the new next node */
    public void setSucc( MyDLLNode succ )
    {
        this.succ = succ;
    }
}

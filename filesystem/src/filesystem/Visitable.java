package filesystem;

import commands.Visitor;

/**
 *
 */
public interface Visitable
{
    /**
     * Accepts the visitor to process this object.
     *
     * @param visitor the visitor object to visit this object.
     */
    boolean accept(Visitor visitor);

    /*
     * Accepts the visitor to process this object. Also
     * takes a value to match in order to find the element to accept
     * without looping to find it.
     *
     * @param visitor the visitor object to visit this object.
     * @param matchValue value to match for the visitable
     */
    boolean accept(Visitor visitor, String matchValue);
}

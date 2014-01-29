package org.eleusoft.jaxen.jaxp;

import java.util.Collections;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Helper DOM NodeList implementation.
 * @author Michele Vivoda
 */
class NodeListImpl implements NodeList
{
    public static final NodeList EMPTY = new NodeListImpl(Collections.EMPTY_LIST);
    private final List list;
    /**
     * Package-protected constructor
     * @param o a List containing Node instances.
     */
    NodeListImpl(Object o)
    {
        if (o instanceof List)
            this.list = (List)o;
        else throw new RuntimeException("Unsupported:" + o);
    }
    /*
     * (non-Javadoc)
     * @see org.w3c.dom.NodeList#getLength()
     */
    public int getLength()
    {
        return list.size();
    }
    /*
     * (non-Javadoc)
     * @see org.w3c.dom.NodeList#item(int)
     */
    public Node item(int index)
    {
        final Object obj = list.get(index);
        if (obj instanceof Node)
        {
            return (Node)obj;
        }
        else if (obj instanceof String)
        {
            throw new RuntimeException("Unexpected string : " + obj);
        }
        else throw new RuntimeException("Unknown object:" + obj);
    }
}
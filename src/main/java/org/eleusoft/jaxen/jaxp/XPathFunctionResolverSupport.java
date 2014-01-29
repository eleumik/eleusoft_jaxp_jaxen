/**
 *
 */
package org.eleusoft.jaxen.jaxp;

import java.util.HashMap;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPathFunction;
import javax.xml.xpath.XPathFunctionResolver;

/**
 * Support implementation of {@link XPathFunctionResolver}.
 * @author Michele Vivoda
 *
 */
public class XPathFunctionResolverSupport implements XPathFunctionResolver
{
    /**
     * The value to be passed as <code>arity</code>
     * to {@link #registerFunction(QName, int, XPathFunction)}
     * for functions with any arguments count (arity).
     */
    public static final int ARITY_ANY = -1;
    private final HashMap map = new HashMap();
    /*
     * (non-Javadoc)
     * @see javax.xml.xpath.XPathFunctionResolver#resolveFunction(javax.xml.namespace.QName, int)
     */
    public XPathFunction resolveFunction(QName qname, int arity)
    {
        XPathFunction func = (XPathFunction)map.get(qname);
        if (func!=null) return func;
        else
        {
            func = (XPathFunction)map.get(getKey(qname, arity));
            return func;
        }
    }
    /**
     * Registers a function with passed QName and arity.
     * @param qname the function qname
     * @param arity the function arity (number of arguments),
     *  use {@link #ARITY_ANY} to signal that the function
     *  accepts any argument.
     * @param func the function instance.
     * @throws IllegalArgumentException when passed QName or XPathFunction is null.
     */
    public void registerFunction(QName qname, int arity, XPathFunction func)
    {
        if (qname==null) throw new IllegalArgumentException("null qname");
        if (func==null) throw new IllegalArgumentException("null XPathFunction");

        Object key;
        if (arity==ARITY_ANY)
            key = qname;
        else key = getKey(qname,arity);
        map.put(key, func);

    }

    private String getKey(QName qname, int arity)
    {
        // Jaxen does not support function by args count
        // arity+
        return qname.toString();
    }

}

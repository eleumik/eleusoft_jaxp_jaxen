package org.eleusoft.jaxen.jaxp;

import java.util.Collection;
import java.util.Iterator;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Comment;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Helpers for converting values.
 *
 *
 */
public class XPathValues
{
    /**
     * Cache of NAN as java.lang.Double.
     */
    public static final Double NAN = new Double(Double.NaN);
    /**
     * Cache of zero as java.lang.Double.
     */
    public static final Double ZERO = new Double(0);
    /**
     * Cache of one as java.lang.Double.
     */
    public static final Double ONE = new Double(1);

    private XPathValues(){}
    /**
     * Convenience method, equivalent to
     * <code>convertValue(value, XPathConstants.STRING);</code>
     * @param value the value
     * @return the string value.
     */
    public static String convertToString(final Object value)
    {
        try
        {
            return (String)convertValue(value, XPathConstants.STRING);
        }
        catch (XPathExpressionException e) {
            throw new Error();
        }
    }

    /**
     * Converts the passed object to the
     * passed xpath type.
     * @param value the object
     * @param returnType the return type, see {@link XPathConstants}.
     * @return the converted value
     */
    public static Object convertValue(final Object value, final QName returnType)
    throws XPathExpressionException
    {
        if (XPathConstants.NUMBER.equals(returnType))
        {
            if (value instanceof Node)
            {
                try
                {
                    return new Double(getStringValueOfNode((Node)value));
                }
                catch(RuntimeException re)
                {
                    return NAN;
                }
            }
            else if (value instanceof NodeList)
            {
                try
                {
                    return new Double(getStringValueOfNodeList((NodeList)value));
                }
                catch(RuntimeException re)
                {
                    return NAN;
                }
            }
            else if (value instanceof String)
            {
                try
                {
                    return new Double((String)value);
                }
                catch(RuntimeException re)
                {
                    return NAN;
                }
            }
            else if (value instanceof Double)
            {
                return value;
            }
            else if (value instanceof Boolean)
            {
                return value.equals(Boolean.TRUE) ? ONE : ZERO;
            }
            else return NAN;
        }
        else if (XPathConstants.STRING.equals(returnType))
        {
            final Object actual;
            if (value==null)
                return "";
            else if (value instanceof String)
                return value;
            else if (value instanceof Collection)
            {
                // for saxon funcs
                final Iterator i = ((Collection)value).iterator();
                if (!i.hasNext()) return "";
                else actual = i.next();
            }
            else actual = value;
            // break if for first item of collection

            if (actual instanceof Node) // must stay before NodeList
                return getStringValueOfNode((Node)actual);
            else if (actual instanceof NodeList)
                return getStringValueOfNodeList((NodeList)actual);
            else if (actual instanceof Double)
                return getStringValueOfDouble((Double)actual);
            else return actual.toString();

        }
        else if (XPathConstants.BOOLEAN.equals(returnType))
        {
            if (value==null)
                return Boolean.FALSE;
            else if (value instanceof Boolean)
                return value;
            else if (value instanceof String)
                return ((String)value).length() == 0 ? Boolean.FALSE : Boolean.TRUE ;
            else if (value instanceof Collection)
                return ((Collection)value).isEmpty() ?
                    Boolean.FALSE : Boolean.TRUE;
            else if (value instanceof Node)
                return Boolean.TRUE;
            else if (value instanceof NodeList)
                return ((NodeList)value).getLength()==0 ?
                    Boolean.FALSE : Boolean.TRUE;
            else if (value instanceof Collection)
                return ((Collection)value).isEmpty() ?
                    Boolean.FALSE : Boolean.TRUE;
            else if (value instanceof Double)
                return value.equals(NAN) || ((Double)value).doubleValue()==0d ?
                    Boolean.FALSE : Boolean.TRUE;
            else return Boolean.TRUE; // here value is non-null
            // could throw ? exception in this last case?
        }
        else if(XPathConstants.NODE.equals(returnType))
        {
            if (value instanceof Node)
            {
                return value;
            }
            else if (value instanceof NodeList)
            {
                NodeList l = (NodeList)value;
                return l.getLength()==0 ? null : l.item(0);
            }
            else throw new XPathExpressionException("Cannot convert [" + value + "] to a NODE.");
        }
        else if(XPathConstants.NODESET.equals(returnType))
        {
            /*if (value instanceof Node)
            {
                return new NodeListImpl(Collections.singletonList(value));
            }
            else*/ if (value instanceof NodeList)
            {
                return value;
            }
            else throw new XPathExpressionException("Cannot convert [" + value + "] to a NODELIST.");
        }

        else throw new IllegalArgumentException("Unsupported returnType " + returnType);

    }

    /////////////////

    private static Object getStringValueOfDouble(Double dbl)
    {
        final long dblAsLong = dbl.longValue();
        if (new Long(dblAsLong).doubleValue()==dbl.doubleValue())
        {
            return Long.toString(dblAsLong);
        }
        else return dbl.toString();

    }

    /**
     * Returns the xpath <em>string-value</em>
     * of a NodeList.
     * @param n the required node.
     * @return the string value;
     */
    private static String getStringValueOfNodeList(final NodeList list)
    {
        if (list.getLength()==0) return "";
        else return getStringValueOfNode(list.item(0));
    }
    /**
     * Returns the xpath <em>string-value</em>
     * of a Node.
     * @param n the required node.
     * @return the string value;
     */
    private static String getStringValueOfNode(final Node n)
    {
        final StringBuffer buf = new StringBuffer();
        toStringAppend(n, buf);
        return buf.toString();
    }
    private static void toStringAppend(final Node n, final StringBuffer buf)
    {
        final String value = n.getNodeValue();
        if (value == null)
        {
            Node current= n.getFirstChild();
            while(current!=null)
            {
                toStringAppend(current, buf);
                current = current.getNextSibling();
            }
        }
        else if (!(n instanceof Comment)) buf.append(value);
    }

}

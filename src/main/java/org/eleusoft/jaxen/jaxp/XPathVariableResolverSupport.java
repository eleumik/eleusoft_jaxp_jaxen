/**
 *
 */
package org.eleusoft.jaxen.jaxp;

import java.util.HashMap;

import javax.xml.XMLConstants;
import javax.xml.namespace.QName;
import javax.xml.xpath.XPathVariableResolver;

/**
 * Support implementation of {@link XPathVariableResolver}
 * that allows to declare variables.
 * <p>Variables can be redeclared but they shouldn't during
 * the evaluation of an xpath expression that use them.
 *
 *
 * @author Michele Vivoda
 */
public class XPathVariableResolverSupport implements XPathVariableResolver
{
    final HashMap map = new HashMap();

    /* (non-Javadoc)
     * @see javax.xml.xpath.XPathVariableResolver#resolveVariable(javax.xml.namespace.QName)
     */
    public Object resolveVariable(final QName qname)
    {
        return map.get(qname);
    }

    /**
     * Declares a variable identified by the passed QName.
     * @param qname the QName that identifies the variable.
     * @param value a required value.
     * @throws IllegalArgumentException when the local
     * part of <code>qname</code> is an empty string
     * and/or when <code>value</code> is null.
     */
    public void declareVariable(final QName qname, Object value)
    {
        // Note: qname never returns null from getLocalPart()
        if (qname.getLocalPart().length()==0)
            throw new IllegalArgumentException("Local part of variable name cannot be empty string.");
        else if (value==null)
            throw new IllegalArgumentException("Value cannot be null.");
        else map.put(qname, value);
    }
    /**
     * Undeclares a variable identified by the passed QName.
     * Does not throw exception if the variable is not declared.
     * @param qname the QName that identifies the variable.
     * @throws IllegalArgumentException when the local
     * part of <code>qname</code> is an empty string.
     */
    public void undeclareVariable(final QName qname)
    {
        // Note: qname never returns null from getLocalPart()
        if (qname.getLocalPart().length()==0)
            throw new IllegalArgumentException("Local part of variable name cannot be empty string.");
        else map.remove(qname);
    }
    /**
     * Declares a variable passing local name and namespace name.
     * @param localPart the local part of the variable QName
     * @param namespace the namespace of the variable QName
     * @param value a required value.
     * @throws IllegalArgumentException when the <code>localPart</code>
     * is null or an empty string and/or when <code>value</code> is null.
     */
    public final void declareVariable(final String localPart, final String namespace, final Object value)
    {
        declareVariable(new QName(namespace, localPart), value);
    }
    /**
     * Undeclares a variable passing local name and namespace name.
     * Does not throw exception if the variable is not declared.
     * @param localPart the local part of the variable QName
     * @param namespace the namespace of the variable QName
     * @throws IllegalArgumentException when the <code>localPart</code>
     * is null or an empty string.
     */
    public final void undeclareVariable(final String localPart, final String namespace)
    {
        undeclareVariable(new QName(namespace, localPart));
    }
    /**
     * Declares a variable whose QName has no xml namespace.
     * @param name the local part of the variable QName
     * @param value a required value.
     * @throws IllegalArgumentException when <code>name</code>
     * is the empty string and/or when the <code>value</code> is null.
     */
    public final void declareVariable(final String name, final Object value)
    {
        declareVariable(new QName(XMLConstants.NULL_NS_URI, name), value);
    }

    /**
     * Undeclares a variable whose QName has no xml namespace.
     * Does not throw exception if the variable is not declared.
     * @param name the local part of the variable QName
     * @throws IllegalArgumentException when <code>name</code>
     * is the empty string.
     */
    public final void undeclareVariable(final String name)
    {
        undeclareVariable(new QName(XMLConstants.NULL_NS_URI, name));
    }


}

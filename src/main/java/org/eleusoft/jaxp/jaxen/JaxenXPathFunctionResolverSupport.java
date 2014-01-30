/**
 *
 */
package org.eleusoft.jaxp.jaxen;

import javax.xml.namespace.QName;

import org.eleusoft.jaxp.common.XPathFunctionResolverSupport;

/**
 * {@link XPathFunctionResolverSupport} that adapts for 
 * Jaxen behavior (no arguments count)
 * @author mik
 *
 */
public class JaxenXPathFunctionResolverSupport extends XPathFunctionResolverSupport
{
    
    protected String getKey(QName qname, int arity)
    {
        // Jaxen does not support function by args count
        return qname.toString();
    }

}

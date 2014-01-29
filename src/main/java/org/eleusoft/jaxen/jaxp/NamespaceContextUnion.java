package org.eleusoft.jaxen.jaxp;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;

import java.util.HashSet;
import java.util.Iterator;

/**
 * {@link NamespaceContext} implementation that
 * is composed by other two {@link NamespaceContext}
 * instances.
 * <p>This class queries the contained contexts.
 * The <code>primary</code> context is queried first,
 * the <code>secondary</code> is queried only when the
 * primary gives no result.
 * @author Michele Vivoda
 */
public class NamespaceContextUnion implements NamespaceContext
{
    private final NamespaceContext primaryContext;
    private final NamespaceContext secondaryContext;
    /**
     * Constructor for an instance configured with
     * two contexts.
     * @param primary the required primary context
     * @param secondary the required secondary context
     */
    public NamespaceContextUnion(final NamespaceContext primary, final NamespaceContext secondary) {
        if (primary==null) throw new IllegalArgumentException("Null primary context");
        else if (secondary==null) throw new IllegalArgumentException("Null secondary context");
        this.primaryContext = primary;
        this.secondaryContext = secondary;
    }
    public String getNamespaceURI(final String prefix) {
        Object test = primaryContext.getNamespaceURI(prefix);
        System.out.println("Union#getNamespaceURI(\"" + prefix + "\") returns " + test);
        if (test==null || XMLConstants.NULL_NS_URI.equals(test))
        {
            test = secondaryContext.getNamespaceURI(prefix);
        }
        System.out.println("Union#getNamespaceURI(\"" + prefix + "\") returns " + test);
        return test!=null ? (String)test : XMLConstants.NULL_NS_URI;
    }

    public String getPrefix(final String namespaceURI) {
        Object test = primaryContext.getPrefix(namespaceURI);
        if (test==null || XMLConstants.DEFAULT_NS_PREFIX.equals(test))
        {
            test = secondaryContext.getPrefix(namespaceURI);
        }
        System.out.println("Union#getPrefix(\"" + namespaceURI + "\") returns " + test);
        return test!=null ? (String)test : XMLConstants.DEFAULT_NS_PREFIX;
    }

    public Iterator getPrefixes(final String namespaceURI) {
        final HashSet set = new HashSet();
        for(final Iterator iter = primaryContext.getPrefixes(namespaceURI);iter.hasNext();)
            set.add(iter.next());
        for(final Iterator iter = secondaryContext.getPrefixes(namespaceURI);iter.hasNext();)
            set.add(iter.next());
        return set.iterator();
    }
}


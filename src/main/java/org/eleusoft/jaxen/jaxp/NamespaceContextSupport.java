package org.eleusoft.jaxen.jaxp;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;
import java.util.Iterator;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

/**
 * Support implementation of {@link NamespaceContext}.
 * @author Michele Vivoda
 */
public class NamespaceContextSupport implements NamespaceContext
{
    private final HashMap map;

    public NamespaceContextSupport() {
        map = new HashMap();
        map.put(XMLConstants.XML_NS_PREFIX, XMLConstants.XML_NS_URI);
        map.put(XMLConstants.XMLNS_ATTRIBUTE, XMLConstants.XMLNS_ATTRIBUTE_NS_URI );
        // This value stays until someone puts a new 'default' namespace,
        // with prefix DEFAULT_NS_PREFIX (empty string).
        map.put(XMLConstants.DEFAULT_NS_PREFIX, XMLConstants.NULL_NS_URI);
    }

    public void put(final String prefix, final String namespaceURI) {
        if (prefix==null) throw new IllegalArgumentException("Null prefix");
        if (namespaceURI==null) throw new IllegalArgumentException("Null uri");
        map.put(prefix, namespaceURI);
    }

    public String getNamespaceURI(final String prefix) {
        if (prefix==null) throw new IllegalArgumentException("Null prefix");
        final Object test = map.get(prefix);
        if (test==null)
        {
            // unbound prefix
            return XMLConstants.NULL_NS_URI;
        }
        else
        {
            // bound prefix, "xml", "xmlns", default prefix ("")
            return (String)test;
        }
    }

    public String getPrefix(final String namespaceURI) {
        if (namespaceURI==null) throw new IllegalArgumentException("Null uri");
        for (final Iterator iterator = map.entrySet().iterator(); iterator.hasNext();)
        {
            final Map.Entry entry = (Map.Entry)iterator.next();
            final String uri = (String) entry.getValue();
            if (namespaceURI.equals(uri)) return (String)entry.getKey();
        }
        return null;
    }

    public Iterator getPrefixes(final String namespaceURI) {
        if (namespaceURI==null) throw new IllegalArgumentException("Null uri");
        final ArrayList prefixes = new ArrayList();
        for (final Iterator iterator = map.entrySet().iterator(); iterator.hasNext();)
        {
            final Map.Entry entry = (Map.Entry)iterator.next();
            final String uri = (String) entry.getValue();
            if (uri.equals(namespaceURI)) prefixes.add(entry.getKey());
        }
        return prefixes.iterator();
    }
}


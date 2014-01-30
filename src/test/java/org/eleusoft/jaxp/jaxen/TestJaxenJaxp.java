package org.eleusoft.jaxp.jaxen;

import java.io.StringReader;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import junit.framework.TestCase;

import org.eleusoft.jaxp.jaxen.JaxenXPathFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

/**
 * Just a minimal test, for a bigger test
 * see the <code>eleusoft_xpath_test</code>
 * project.
 * 
 * @author mik
 *
 */
public class TestJaxenJaxp extends TestCase
{
    public void testAPI()
    {
        XPathFactory f = getXPathFactory();
        assertNotNull(f);
        assertTrue(f.isObjectModelSupported(JaxenXPathFactory.URI));
        System.out.println("Test API JAXEN OK");
    }
    public void testAPI_DOM()
    {
        XPathFactory f = getXPathFactory();
        assertNotNull(f);
        assertTrue(f.isObjectModelSupported(XPathConstants.DOM_OBJECT_MODEL));
    }
    public void testAPI_Unknown()
    {
        XPathFactory f = getXPathFactory();
        assertFalse(f.isObjectModelSupported("kjhkhskhdkjsd"));
    }
    
    
    public void testAPI_XPath() throws Exception
    {
        XPath xpath = getXPath();
        assertNotNull(xpath);
    }
    public void testAPI_XPath_Root() throws Exception
    {
        Object n = selectSingle("/*", "<xml/>");
        assertTrue(n instanceof Node);
        assertTrue(n instanceof Element);
        Node node = (Node) n;
        assertEquals("xml", node.getLocalName());
        
    }
    public void testAPI_XPath_Child() throws Exception
    {
        Object n = selectSingle("//c", "<xml><c/></xml>");
        assertTrue(n instanceof Node);
        assertTrue(n instanceof Element);
        Node node = (Node) n;
        assertEquals("c", node.getLocalName());
        
    }
    public void testAPI_XPath_ChildAttr() throws Exception
    {
        Object n = selectSingle("//c/@a", "<xml><c a='3'/></xml>");
        assertTrue(n instanceof Node);
        assertTrue(n instanceof Attr);
        Node node = (Node) n;
        assertEquals("a", node.getLocalName());
        assertEquals("3", node.getNodeValue());
        
        
    }
    

    private Object selectSingle(String expr, String xml) throws Exception
    {
        //Document doc = JAXP.getDocument();
        InputSource source = new InputSource(new StringReader(xml));
        XPath xp = getXPath();
        return xp.evaluate(expr, source, XPathConstants.NODE);
    }
    private XPath getXPath() throws Exception
    {
        XPathFactory xf = getXPathFactory();
        return xf.newXPath();
    }
    private XPathFactory getXPathFactory()
    {
        return new JaxenXPathFactory();
    }

}

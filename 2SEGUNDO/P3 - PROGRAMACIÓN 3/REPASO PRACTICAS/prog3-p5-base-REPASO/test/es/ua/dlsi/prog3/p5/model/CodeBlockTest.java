package es.ua.dlsi.prog3.p5.model;

import es.ua.dlsi.prog3.p5.export.html.HTMLExporter;
import es.ua.dlsi.prog3.p5.export.markdown.MarkdownExporter;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author David Rizo - drizo@dlsi.ua.es
 * @created 24/10/22
 */
public class CodeBlockTest {
    @Test
    public void testJavaCodeBlock() {
        String code = "int [] v = new int[25];";
        CodeBlock block = new CodeBlock(code, "java");
        assertEquals("```java\nint [] v = new int[25];\n```", block.export(new MarkdownExporter()));
        assertEquals("<pre>int [] v = new int[25];</pre>", block.export(new HTMLExporter()));
    }
    @Test
    public void testGenericCodeBlock() {
        String code = "var v = create array";
        CodeBlock block = new CodeBlock(code);
        assertEquals("```\nvar v = create array\n```", block.export(new MarkdownExporter()));
        assertEquals("<pre>var v = create array</pre>", block.export(new HTMLExporter()));
    }
}

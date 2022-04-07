package me.cormoran.asciidoc.confluence.extension.jira;

import org.asciidoctor.ast.ContentNode;
import org.asciidoctor.extension.InlineMacroProcessor;
import org.asciidoctor.extension.Name;

import java.util.Map;

@Name("jira")
public class JiraInlineMacroProcessor extends InlineMacroProcessor {
    @Override
    public Object process(ContentNode parent, String target, Map<String, Object> attrs) {
        System.out.println("Hello");
        final String issueKey = target;
        final String jiraMacro = new StringBuilder()
                .append("<ac:structured-macro")
                .append(" ac:name=\"jira\"")
                .append(" ac:schema-version=\"1\">")
                .append("<ac:parameter ac:name=\"key\">")
                .append(issueKey)
                .append("</ac:parameter>")
                .append("</ac:structured-macro>")
                .toString();
        return createPhraseNode(parent, "quoted", jiraMacro);
    }
}

package br.com.tommiranda.compiler;

import br.com.tommiranda.compiler.ast.Node;
import br.com.tommiranda.compiler.ast.NodeType;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.commons.text.TextStringBuilder;

public final class XmlBuilder {

    public static String create(Node node, int level) {
        String value = node.getValue();
        NodeType type = node.getType();

        TextStringBuilder builder = new TextStringBuilder();

        if (value != null) {
            if (type.equals(NodeType.STRING_CONSTANT)) {
                value = value.substring(1, value.length() - 1);
            }

            return String.format("%s<%s> %s </%s>", "  ".repeat(level), type, StringEscapeUtils.escapeXml11(value), type);
        }

        builder.appendln("%s<%s>", "  ".repeat(level), type);

        for (Node child : node.getChildren()) {
            builder.appendln(create(child, level + 1));
        }

        builder.append("%s</%s>", "  ".repeat(level), type);

        return builder.build();
    }
}

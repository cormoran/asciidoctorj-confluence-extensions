package me.cormoran.asciidoc.confluence.extension.task;

import org.asciidoctor.ast.*;
import org.asciidoctor.extension.Treeprocessor;

import java.util.concurrent.atomic.AtomicInteger;

public class TaskProcessor extends Treeprocessor {


    @Override
    public Document process(Document document) {
        AtomicInteger taskId = new AtomicInteger(1);
        visit(taskId, document);
        return document;
    }

    private void visit(AtomicInteger taskId, StructuralNode parent) {
        java.util.List<StructuralNode> blocks = parent.getBlocks();
        for (int i = 0; i < blocks.size(); i++) {
            StructuralNode block = blocks.get(i);
            if (block instanceof List) {
                List list = (List) block;
                if (canConvertToTask(list)) {
                    blocks.set(i, convertToTask(taskId, list));
                }
            } else if (block instanceof StructuralNode) {
                visit(taskId, block);
            }
        }
    }

    private Block convertToTask(AtomicInteger taskId, List list) {

        return createBlock((StructuralNode) list.getParent(),
                "pass",
                convertToTaskMacro(taskId, list));
    }

    private String convertToTaskMacro(AtomicInteger taskId, List list) {
        StringBuilder builder = new StringBuilder()
                .append("<ac:task-list>\n");
        for (StructuralNode item : list.getItems()) {
            builder.append("<ac:task>\n")
                    .append("<ac:task-id>")
                    .append(taskId.getAndIncrement())
                    .append("</ac:task-id>\n")
                    .append("<ac:task-status>")
                    .append(item.getAttributes().containsKey("checked") ? "complete" : "incomplete")
                    .append("</ac:task-status>\n")
                    .append("<ac:task-body><span class=\"placeholder-inline-tasks\">")
                    .append(((ListItem) item).getText())
                    .append("</span></ac:task-body>\n")
                    .append("</ac:task>\n");
            for (StructuralNode block : item.getBlocks()) {
                builder.append(convertToTaskMacro(taskId, (List) block));
            }
        }
        return builder.append("</ac:task-list>").toString();
    }

    private boolean canConvertToTask(List list) {
        for (StructuralNode item : list.getItems()) {
            if (!(item instanceof ListItem)) return false;
            if (!item.getAttributes().containsKey("checkbox")) return false;
            // if item has blocks, they need be task convertible list
            for (StructuralNode block : item.getBlocks()) {
                if (!(block instanceof List)) return false;
                if (!canConvertToTask((List) block)) return false;
            }
        }
        return list.hasItems();
    }
}

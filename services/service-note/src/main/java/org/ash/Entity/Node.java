package org.ash.Entity;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class Node {
    private String id;
    private String label;
    private List<Node> children;

    public Node(String nodeId, String nodeLabel) {
        this.id = nodeId;
        this.label = nodeLabel;
        this.children = new ArrayList<>();
    }

    public Node() {
    }
    public void addChild(Node child) {
        this.children.add(child);
    }
}

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
    //0表示未收藏，1表示已收藏
    private int isCollected;

    public Node(String id, String nodeLabel,int isCollected) {
        this.id = id;
        this.label = nodeLabel;
        this.children = new ArrayList<>();
        this.isCollected = isCollected;
    }

    public Node() {
    }
    public void addChild(Node child) {
        this.children.add(child);
    }
}

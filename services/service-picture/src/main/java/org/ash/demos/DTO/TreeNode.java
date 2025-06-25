package org.ash.demos.DTO;

import java.util.ArrayList;
import java.util.List;

// 定义 TreeNode 类
public class TreeNode {
    private String fileName;
    private String level;
    private List<TreeNode> children;

    // 构造函数
    public TreeNode() {
        this.children = new ArrayList<>();
    }

    // Getter 和 Setter 方法
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    // 添加子节点的方法
    public void addChild(TreeNode child) {
        this.children.add(child);
    }
}
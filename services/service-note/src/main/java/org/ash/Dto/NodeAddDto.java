package org.ash.Dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NodeAddDto {
    private long parentId;
    private int depth;
    private String nodeLabel;
    private int userAccount;
    private long nodeId;
    public NodeAddDto(long nodeId, int parentId, int depth, String nodeLabel, int userAccount) {
        this.parentId = parentId;
        this.depth = depth;
        this.nodeLabel = nodeLabel;
        this.userAccount = userAccount;
        this.nodeId = nodeId;
    }

    public NodeAddDto() {
    }
}

package org.ash.Dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NodeAddDto {
    private String parentId;
    private int depth;
    private String nodeLabel;
    private long userAccount;
    private String nodeId;

    public NodeAddDto(String parentId, int depth, String nodeLabel, long userAccount, String nodeId) {
        this.parentId = parentId;
        this.depth = depth;
        this.nodeLabel = nodeLabel;
        this.userAccount = userAccount;
        this.nodeId = nodeId;
    }

    public NodeAddDto() {
    }
}

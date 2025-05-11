package org.ash.Dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NodeAddDto {
    private int parentId;
    private int depth;
    private String nodeLabel;
    private int UserAccount;

    public NodeAddDto(int nodeId, int parentId, int depth, String nodeLabel, int userAccount) {
        this.parentId = parentId;
        this.depth = depth;
        this.nodeLabel = nodeLabel;
        UserAccount = userAccount;
    }

    public NodeAddDto() {
    }
}

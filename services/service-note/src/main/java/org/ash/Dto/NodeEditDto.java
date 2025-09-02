package org.ash.Dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NodeEditDto {
    String nodeLabel;
    String nodeId;

    public NodeEditDto(String nodeLabel, String nodeId) {
        this.nodeLabel = nodeLabel;
        this.nodeId = nodeId;
    }

    public NodeEditDto() {
    }
}

package org.ash.demos.DTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReNameDTO {
    private String OldName;
    private String NewName;
    private long account;

    public ReNameDTO(String oldName, String newName, long account) {
        OldName = oldName;
        NewName = newName;
        this.account = account;
    }

    public ReNameDTO() {
    }
}

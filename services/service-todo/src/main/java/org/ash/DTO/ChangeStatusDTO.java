package org.ash.DTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChangeStatusDTO {
    public long[] ids;
    public String status;

    public ChangeStatusDTO(long[] ids, String status) {
        this.ids = ids;
        this.status = status;
    }
    public ChangeStatusDTO() {
    }
}

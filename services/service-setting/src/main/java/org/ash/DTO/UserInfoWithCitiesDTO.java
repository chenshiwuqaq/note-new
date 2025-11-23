package org.ash.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ash.Entity.User;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoWithCitiesDTO {
    private User user;
    private List<CityDTO> cities;
}

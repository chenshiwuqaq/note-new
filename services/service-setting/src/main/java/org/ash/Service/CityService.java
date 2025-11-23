package org.ash.Service;

import org.ash.DTO.CityDTO;

import java.util.List;

public interface CityService {
    List<CityDTO> selectAllCities();
    // 添加城市搜索方法
    List<CityDTO> searchCities(String keyword);
}

package org.ash.Service;

import org.ash.DTO.CityDTO;
import org.ash.Mapper.CityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService{
    @Autowired
    private CityMapper cityMapper;

    @Override
    public List<CityDTO> selectAllCities() {
        return cityMapper.selectAllCities();
    }
    @Override
    public List<CityDTO> searchCities(String keyword) {
        return cityMapper.searchCities(keyword);
    }
}

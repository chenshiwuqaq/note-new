package org.ash.Mapper;

import org.apache.ibatis.annotations.*;
import org.ash.DTO.CityDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CityMapper {

    @Select("SELECT name, weather_code FROM city")
    @Results({
            @Result(property = "name", column = "name"),
            @Result(property = "weatherCode", column = "weather_code")
    })
    List<CityDTO> selectAllCities();
    // 添加城市搜索方法
    @Select("SELECT name, weather_code FROM city WHERE name LIKE CONCAT('%', #{keyword}, '%')")
    @Results({
            @Result(property = "name", column = "name"),
            @Result(property = "weatherCode", column = "weather_code")
    })
    List<CityDTO> searchCities(@Param("keyword") String keyword);
}
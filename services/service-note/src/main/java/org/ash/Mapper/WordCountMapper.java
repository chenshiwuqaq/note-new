package org.ash.Mapper;

import org.apache.ibatis.annotations.*;
import org.ash.Dto.DailyWordCountDto;
import org.ash.Dto.FiveDailyWordCount;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface WordCountMapper {
    @Select("SELECT word_count FROM daily_word_count WHERE account = #{account} AND DATE(time) = CURDATE()")
    Integer getTodayWordCount(@Param("account")long account);
    @Select("SELECT COUNT(*) > 0 FROM daily_word_count WHERE account = #{account} AND DATE(time) = CURDATE()")
    boolean isExist(@Param("account") long account);
    @Insert("INSERT INTO daily_word_count (account, word_count, time) VALUES (#{account}, #{wordCount}, NOW())")
    boolean addWordCount(DailyWordCountDto dailyWordInitDto);
    @Update("UPDATE daily_word_count SET word_count = #{wordCount} WHERE account = #{account} AND DATE(time) = CURDATE()")
    boolean updateWordCount(DailyWordCountDto dailyWordCountDto);
    @Select("SELECT SUM(word_count) FROM daily_word_count WHERE account = #{account}")
    int getTotalWords(@Param("account")long account);

    @Select("SELECT DATE(time) AS time, word_count " +
            "FROM daily_word_count WHERE account = #{account} " +
            "AND DATE(time) BETWEEN #{startDate} AND #{endDate}") //筛选日期范围
    List<FiveDailyWordCount> selectWordsByAccountAndDateRange(
            @Param("account") long account,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
}

package org.ash.Service;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.ash.Dto.DailyWordCountDto;

import java.time.LocalDate;
import java.util.Map;

public interface WordCountService {
    int getTodayWordCount(long account);
    boolean initWordCount(DailyWordCountDto dailyWordCountDto);
    boolean updateWordCount(DailyWordCountDto dailyWordCountDto);
    int getTotalWords(long account);
    Map<LocalDate, Integer>getRecentFiveDaysWords(long account);
}

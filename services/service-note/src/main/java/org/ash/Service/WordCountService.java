package org.ash.Service;

import org.ash.Dto.DailyWordCountDto;

public interface WordCountService {
    int getTodayWordCount(long account);
    boolean initWordCount(DailyWordCountDto dailyWordCountDto);
    boolean updateWordCount(DailyWordCountDto dailyWordCountDto);
    int getTotalWords(long account);
}

package org.ash.Service;

import org.ash.Dto.DailyWordCountDto;
import org.ash.Dto.FiveDailyWordCount;
import org.ash.Mapper.WordCountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WordCountServiceImpl implements WordCountService{
    @Autowired
    private WordCountMapper wordCountMapper;
    @Override
    public int getTodayWordCount(long account) {
        Integer count = wordCountMapper.getTodayWordCount(account);
        return count == null ? 0 : count;
    }

    @Override
    public boolean initWordCount(DailyWordCountDto dailyWordCountDto) {
        if (wordCountMapper.isExist(dailyWordCountDto.getAccount())){
            return true;
        }
        return wordCountMapper.addWordCount(dailyWordCountDto);
    }

    @Override
    public boolean updateWordCount(DailyWordCountDto dailyWordCountDto) {
        return wordCountMapper.updateWordCount(dailyWordCountDto);
    }

    @Override
    public int getTotalWords(long account) {
        return wordCountMapper.getTotalWords(account);
    }

    @Override
    public Map<LocalDate, Integer> getRecentFiveDaysWords(long account) {
        //确定最近五天的日期（今天及前四天）
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusDays(4);
        LocalDate endDate = today;

        Map<LocalDate, Integer> recentFiveDaysMap = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            LocalDate date = startDate.plusDays(i);
            recentFiveDaysMap.put(date, 0); // 默认所有日期的字数为 0
        }

        List<FiveDailyWordCount> records = wordCountMapper.selectWordsByAccountAndDateRange(
                account,
                startDate,
                endDate
        );

        //处理并构建返回的 Map 集合，覆盖实际字数
        for (FiveDailyWordCount record : records) {
            if (recentFiveDaysMap.containsKey(record.getTime())) {
                recentFiveDaysMap.put(record.getTime(), record.getWordCount());
            }
        }

        return recentFiveDaysMap;
    }
}

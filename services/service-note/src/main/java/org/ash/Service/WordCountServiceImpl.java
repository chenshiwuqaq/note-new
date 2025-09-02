package org.ash.Service;

import org.ash.Dto.DailyWordCountDto;
import org.ash.Mapper.WordCountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WordCountServiceImpl implements WordCountService{
    @Autowired
    private WordCountMapper wordCountMapper;
    @Override
    public int getTodayWordCount(long account) {
        return wordCountMapper.getTodayWordCount(account);
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
}

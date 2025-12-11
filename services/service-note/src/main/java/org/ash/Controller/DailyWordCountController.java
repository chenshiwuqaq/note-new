package org.ash.Controller;

import org.ash.Dto.DailyWordCountDto;
import org.ash.Service.WordCountService;
import org.com.Entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/WordCount")
public class DailyWordCountController {
    @Autowired
    private WordCountService wordCountService;

    @GetMapping("/get")
    public int getTodayWordCount(@RequestParam("account")long account){
        return wordCountService.getTodayWordCount(account);
    }
    @PostMapping("/init")
    public Result addWordCount(@RequestBody DailyWordCountDto dailyWordInitDto){
        return Result.success(wordCountService.initWordCount(dailyWordInitDto));
    }
    @PostMapping("/update")
    public Result updateWordCount(@RequestBody DailyWordCountDto dailyWordCountDto){
        return Result.success(wordCountService.updateWordCount(dailyWordCountDto));
    }
    @GetMapping("/getTotalWords")
    public int getTotalWords(@RequestParam("account")long account){
        return wordCountService.getTotalWords(account);
    }
    @GetMapping("/getRecentFiveDaysWords")
    public Result  getRecentFiveDaysWords(@RequestParam("account")long account){
        return Result.success(wordCountService.getRecentFiveDaysWords(account));
    }
}

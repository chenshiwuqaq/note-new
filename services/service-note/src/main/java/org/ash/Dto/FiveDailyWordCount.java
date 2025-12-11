package org.ash.Dto;

import java.time.LocalDate;

public class FiveDailyWordCount {
    private LocalDate time;
    private Integer wordCount;

    public LocalDate getTime() { return time; }
    public void setTime(LocalDate time) { this.time = time; }
    public Integer getWordCount() { return wordCount; }
    public void setWordCount(Integer wordCount) { this.wordCount = wordCount; }
}

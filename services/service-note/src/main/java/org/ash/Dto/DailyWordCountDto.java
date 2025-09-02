package org.ash.Dto;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class DailyWordCountDto {
    private long account;
    private int wordCount;
}

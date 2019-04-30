package com.utan.article.domain.DO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoveDO implements Serializable {
    private Integer assist;

    private Integer weight;
}

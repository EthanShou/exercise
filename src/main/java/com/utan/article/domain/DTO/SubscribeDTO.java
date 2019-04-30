package com.utan.article.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscribeDTO implements Serializable {
    private Integer userId;

    private Long lastId;

    private Integer size;
}

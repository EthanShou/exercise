package com.utan.article.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BrowseDTO implements Serializable {
    private Long userId;

    private String mac;

    private String tag;

    private Long firstId;

    private Long lastId;

    private Integer size;
}

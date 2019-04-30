package com.utan.article.domain.DO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDO implements Serializable {

    private String id;

    private String name;

    private String alias;

    private String showType;

    private String picture;

    private String prefix;
}


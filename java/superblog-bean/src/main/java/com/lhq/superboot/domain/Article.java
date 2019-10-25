package com.lhq.superboot.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    private Integer id;

    private Integer aid;

    private String name;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String context;

}
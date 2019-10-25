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
public class UserInfo {
    private String userInfoId;

    private String userRealName;

    private String idcard;

    private String birthday;

    private Integer sex;

    private Date createTime;

    private Date modifyTime;

}
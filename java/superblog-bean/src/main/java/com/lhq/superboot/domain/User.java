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
public class User {
    private String userId;

    private String channelId;

    private String userInfoId;

    private String userName;

    private String roleId;

    private String password;

    private String phone;

    private String nickName;

    private String email;

    private String icon;

    private String wechatId;

    private String alipayId;

    private Date createTime;

    private String createUser;

    private Date modifyTime;

    private String modifyUser;

    private Integer isEnabled;

    private Integer isDeleted;

}
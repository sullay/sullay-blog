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
public class Menu {
    private String menuId;

    private String channelId;

    private String menuName;

    private String menuPid;

    private Integer menuSort;

    private String menuUrl;

    private Integer menuLevel;

    private String menuIcon;

    private String remark;

    private Date createTime;

    private String createUser;

    private Date modifyTime;

    private String modifyUser;

    private Integer isEnabled;

    private Integer isDeleted;

}
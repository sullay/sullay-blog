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
public class RoleResource {
    private String roleResId;

    private String roleId;

    private String resId;

    private Date createTime;

    private Date modifyTime;

    private Integer isEnabled;

}
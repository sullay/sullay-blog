package com.lhq.superboot.mapper;

import com.lhq.superboot.domain.WhiteListUrl;
import com.lhq.superboot.domain.WhiteListUrlExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WhiteListUrlMapper {
    long countByExample(WhiteListUrlExample example);

    int deleteByExample(WhiteListUrlExample example);

    int deleteByPrimaryKey(Long whitelistId);

    int insert(WhiteListUrl record);

    int insertSelective(WhiteListUrl record);

    List<WhiteListUrl> selectByExample(WhiteListUrlExample example);

    WhiteListUrl selectByPrimaryKey(Long whitelistId);

    int updateByExampleSelective(@Param("record") WhiteListUrl record, @Param("example") WhiteListUrlExample example);

    int updateByExample(@Param("record") WhiteListUrl record, @Param("example") WhiteListUrlExample example);

    int updateByPrimaryKeySelective(WhiteListUrl record);

    int updateByPrimaryKey(WhiteListUrl record);
}
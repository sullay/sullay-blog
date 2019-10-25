package com.lhq.superboot.mapper;

import com.lhq.superboot.domain.Anthology;
import com.lhq.superboot.domain.AnthologyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AnthologyMapper {
    long countByExample(AnthologyExample example);

    int deleteByExample(AnthologyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Anthology record);

    int insertSelective(Anthology record);

    List<Anthology> selectByExample(AnthologyExample example);

    Anthology selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Anthology record, @Param("example") AnthologyExample example);

    int updateByExample(@Param("record") Anthology record, @Param("example") AnthologyExample example);

    int updateByPrimaryKeySelective(Anthology record);

    int updateByPrimaryKey(Anthology record);
}
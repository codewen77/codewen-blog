package com.codewen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.codewen.pojo.Tag;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author codewen77
 * @date 2022-08-25
 */
@Component
public interface TagMapper extends BaseMapper<Tag> {

    List<Tag> listTagByArticleId(Long id);

    List<Long> listHot(int limit_nums);

    List<Tag> listTagsByArticleIds(List<Long> tag_ids);
}

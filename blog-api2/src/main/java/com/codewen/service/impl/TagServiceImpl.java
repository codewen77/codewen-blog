package com.codewen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.codewen.pojo.Tag;
import com.codewen.mapper.TagMapper;
import com.codewen.service.TagService;
import com.codewen.vo.Result;
import com.codewen.vo.TagVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author codewen77
 * @date 2022-08-25
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public List<TagVo> listTagByArticleId(Long id) {
        List<Tag> tags = tagMapper.listTagByArticleId(id);
        return copyList(tags);
    }

    @Override
    public Result listHot(int limit_nums) {
        // 根据tag热度排序查询所有的id
        List<Long> tag_ids = tagMapper.listHot(limit_nums);

        // 根据id查询所有详细的信息
        List<Tag> tags = tagMapper.listTagsByArticleIds(tag_ids);
        return Result.success(copyList(tags));
    }

    @Override
    public Result listTags() {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Tag::getId, Tag::getTagName);
        List<Tag> tagList = tagMapper.selectList(queryWrapper);

        return Result.success(copyList(tagList));
    }

    @Override
    public Result listTagsDetail() {
        List<Tag> tagList = tagMapper.selectList(new LambdaQueryWrapper<>());

        return Result.success(copyList(tagList));
    }

    @Override
    public Result listTagsDetailById(Long id) {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Tag::getId, id);

        return Result.success(copy(tagMapper.selectOne(queryWrapper)));
    }

    private List<TagVo> copyList(List<Tag> records) {
        List<TagVo> tagVoList = new ArrayList<>();
        for (Tag record : records) {
            tagVoList.add(copy(record));
        }

        return tagVoList;
    }

    private TagVo copy(Tag tag) {
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tag, tagVo);
        return tagVo;
    }
}

package com.perfree.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.perfree.commons.EhCacheUtil;
import com.perfree.enums.EhCacheEnum;
import com.perfree.enums.OptionEnum;
import com.perfree.model.Option;
import com.perfree.mapper.OptionMapper;
import com.perfree.service.OptionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Perfree
 * @since 2022-03-19
 */
@Service
public class OptionServiceImpl extends ServiceImpl<OptionMapper, Option> implements OptionService {

    private final OptionMapper optionMapper;

    public OptionServiceImpl(OptionMapper optionMapper) {
        this.optionMapper = optionMapper;
    }

    @Override
    public Option getByKey(String value) {
        QueryWrapper<Option> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("`key`", OptionEnum.IS_INSTALLED.getValue());
        return optionMapper.selectOne(queryWrapper);
    }

    @Override
    public void initOptionCache() {
        List<Option> list = this.list();
        EhCacheUtil.removeAll(EhCacheEnum.EHCACHE_KEY_OPTION_DATA.getValue());
        list.forEach(item -> {EhCacheUtil.put(EhCacheEnum.EHCACHE_KEY_OPTION_DATA.getValue(), item.getKey(), item.getValue());});
    }
}

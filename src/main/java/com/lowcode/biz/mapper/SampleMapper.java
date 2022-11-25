package com.lowcode.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lowcode.flowable.common.annotation.DBType;
import com.lowcode.flowable.model.enums.DBTypeEnum;
import com.lowcode.biz.entity.Sample;

/**
 * <p>
 * Sample
 * </p>
 *
 * @author mayike
 */
@DBType(DBTypeEnum.BIZ)
public interface SampleMapper extends BaseMapper<Sample> {

}

package com.lhd.tams.module.color.model.convert;

import com.lhd.tams.module.color.model.data.ColorDO;
import com.lhd.tams.module.color.model.vo.ColorListVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author lhd
 */
@Mapper
public abstract class AbstractColorConverter {

    public static AbstractColorConverter INSTANCE = Mappers.getMapper(AbstractColorConverter.class);

    /**
     * doList2ListVoList
     * @param doList
     * @return
     */
    public abstract List<ColorListVO> doList2ListVoList(List<ColorDO> doList);
}

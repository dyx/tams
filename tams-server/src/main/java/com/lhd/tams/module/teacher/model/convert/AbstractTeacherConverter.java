package com.lhd.tams.module.teacher.model.convert;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lhd.tams.module.teacher.model.data.TeacherDO;
import com.lhd.tams.module.teacher.model.dto.TeacherSaveDTO;
import com.lhd.tams.module.teacher.model.vo.TeacherListVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author lhd
 */
@Mapper
public abstract class AbstractTeacherConverter {

    public static AbstractTeacherConverter INSTANCE = Mappers.getMapper(AbstractTeacherConverter.class);

    /**
     * doPage2ListVoPage
     * @param doPage
     * @return
     */
    public abstract Page<TeacherListVO> doPage2ListVoPage(IPage<TeacherDO> doPage);

    /**
     * doList2ListVoList
     * @param doList
     * @return
     */
    public abstract List<TeacherListVO> doList2ListVoList(List<TeacherDO> doList);

    /**
     * do2ListVO
     * @param dataObj
     * @return
     */
    public abstract TeacherListVO do2ListVO(TeacherDO dataObj);

    /**
     * saveDto2DO
     * @param saveDTO
     * @return
     */
    public abstract TeacherDO saveDto2DO(TeacherSaveDTO saveDTO);

}

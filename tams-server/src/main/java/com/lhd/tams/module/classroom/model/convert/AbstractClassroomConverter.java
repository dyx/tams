package com.lhd.tams.module.classroom.model.convert;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lhd.tams.module.classroom.model.data.ClassroomDO;
import com.lhd.tams.module.classroom.model.dto.ClassroomSaveDTO;
import com.lhd.tams.module.classroom.model.vo.ClassroomListVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public abstract class AbstractClassroomConverter {

    public static AbstractClassroomConverter INSTANCE = Mappers.getMapper(AbstractClassroomConverter.class);

    public abstract Page<ClassroomListVO> doPage2ListVoPage(IPage<ClassroomDO> doPage);

    public abstract List<ClassroomListVO> doList2ListVoList(List<ClassroomDO> doList);

    public abstract ClassroomListVO do2ListVO(ClassroomDO dataObj);

    public abstract ClassroomDO saveDto2DO(ClassroomSaveDTO saveDTO);

}

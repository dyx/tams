package com.lhd.tams.module.coursescheduling.model.convert;

import com.lhd.tams.module.coursescheduling.model.data.CourseSchedulingDO;
import com.lhd.tams.module.coursescheduling.model.dto.CourseSchedulingBatchSaveDTO;
import com.lhd.tams.module.coursescheduling.model.dto.CourseSchedulingSaveDTO;
import com.lhd.tams.module.coursescheduling.model.dto.CourseSchedulingTimeUpdateDTO;
import com.lhd.tams.module.coursescheduling.model.dto.CourseSchedulingUpdateDTO;
import com.lhd.tams.module.coursescheduling.model.vo.CourseSchedulingListVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public abstract class AbstractCourseSchedulingConverter {

    public static AbstractCourseSchedulingConverter INSTANCE = Mappers.getMapper(AbstractCourseSchedulingConverter.class);

    public abstract List<CourseSchedulingListVO> doList2ListVoList(List<CourseSchedulingDO> doList);

    public abstract CourseSchedulingListVO do2ListVO(CourseSchedulingDO dataObj);

    public abstract CourseSchedulingDO saveDto2DO(CourseSchedulingSaveDTO saveDTO);

    public abstract CourseSchedulingDO batchSaveDto2DO(CourseSchedulingBatchSaveDTO saveDTO);

    public abstract CourseSchedulingDO timeUpdateDto2DO(CourseSchedulingTimeUpdateDTO updateDTO);

    public abstract CourseSchedulingDO updateDto2DO(CourseSchedulingUpdateDTO updateDTO);
}

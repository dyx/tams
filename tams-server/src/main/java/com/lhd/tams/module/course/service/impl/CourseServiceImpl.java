package com.lhd.tams.module.course.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhd.tams.common.consts.EnableStateEnum;
import com.lhd.tams.module.course.dao.CourseMapper;
import com.lhd.tams.module.course.model.convert.AbstractCourseConverter;
import com.lhd.tams.module.course.model.data.CourseDO;
import com.lhd.tams.module.course.model.dto.CoursePageQuery;
import com.lhd.tams.module.course.model.dto.CourseSaveDTO;
import com.lhd.tams.module.course.model.vo.CourseListVO;
import com.lhd.tams.module.course.service.CourseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, CourseDO> implements CourseService {

    @Override
    public IPage<CourseListVO> pageCourse(CoursePageQuery pageQuery) {

        LambdaQueryWrapper<CourseDO> queryWrapper = Wrappers.<CourseDO>lambdaQuery()
                .eq(pageQuery.getEnableState() != null, CourseDO::getEnableState, pageQuery.getEnableState())
                .orderByAsc(CourseDO::getName);

        IPage<CourseDO> doPage = page(new Page<>(pageQuery.getCurrent(), pageQuery.getSize()), queryWrapper);

        IPage<CourseListVO> voPage = AbstractCourseConverter.INSTANCE.doPage2ListVoPage(doPage);

        return voPage;
    }

    @Override
    public List<CourseListVO> refList() {

        LambdaQueryWrapper<CourseDO> queryWrapper = Wrappers.<CourseDO>lambdaQuery()
                .eq(CourseDO::getEnableState, EnableStateEnum.ENABLED.getCode())
                .orderByAsc(CourseDO::getName);

        List<CourseDO> doList = list(queryWrapper);

        List<CourseListVO> voList = AbstractCourseConverter.INSTANCE.doList2ListVoList(doList);

        return voList;
    }

    @Override
    public CourseListVO getCourseById(Long id) {

        CourseDO dataObj = getById(id);

        CourseListVO vo = AbstractCourseConverter.INSTANCE.do2ListVO(dataObj);

        return vo;
    }

    @Override
    public boolean saveCourse(CourseSaveDTO saveDTO) {

        CourseDO dataObj = AbstractCourseConverter.INSTANCE.saveDto2DO(saveDTO);

        return save(dataObj);
    }

    @Override
    public boolean updateCourseById(Long id, CourseSaveDTO saveDTO) {

        CourseDO dataObj = AbstractCourseConverter.INSTANCE.saveDto2DO(saveDTO);
        dataObj.setId(id);

        return updateById(dataObj);
    }

    @Override
    public boolean updateCourseEnableStateById(Long id, Integer enableState) {

        CourseDO dataObj = new CourseDO();
        dataObj.setId(id);
        dataObj.setEnableState(enableState);

        return updateById(dataObj);
    }
}

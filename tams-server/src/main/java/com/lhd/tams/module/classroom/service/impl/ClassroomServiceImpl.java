package com.lhd.tams.module.classroom.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhd.tams.common.consts.EnableStateEnum;
import com.lhd.tams.module.classroom.dao.ClassroomMapper;
import com.lhd.tams.module.classroom.model.convert.AbstractClassroomConverter;
import com.lhd.tams.module.classroom.model.data.ClassroomDO;
import com.lhd.tams.module.classroom.model.dto.ClassroomPageQuery;
import com.lhd.tams.module.classroom.model.dto.ClassroomSaveDTO;
import com.lhd.tams.module.classroom.model.vo.ClassroomListVO;
import com.lhd.tams.module.classroom.service.ClassroomService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassroomServiceImpl extends ServiceImpl<ClassroomMapper, ClassroomDO> implements ClassroomService {

    @Override
    public IPage<ClassroomListVO> pageCourse(ClassroomPageQuery pageQuery) {

        LambdaQueryWrapper<ClassroomDO> queryWrapper = Wrappers.<ClassroomDO>lambdaQuery()
                .eq(pageQuery.getEnableState() != null, ClassroomDO::getEnableState, pageQuery.getEnableState())
                .orderByAsc(ClassroomDO::getName);

        IPage<ClassroomDO> doPage = page(new Page<>(pageQuery.getCurrent(), pageQuery.getSize()), queryWrapper);

        IPage<ClassroomListVO> voPage = AbstractClassroomConverter.INSTANCE.doPage2ListVoPage(doPage);

        return voPage;
    }

    @Override
    public List<ClassroomListVO> refList() {

        LambdaQueryWrapper<ClassroomDO> queryWrapper = Wrappers.<ClassroomDO>lambdaQuery()
                .eq(ClassroomDO::getEnableState, EnableStateEnum.ENABLED.getCode())
                .orderByAsc(ClassroomDO::getName);

        List<ClassroomDO> doList = list(queryWrapper);

        List<ClassroomListVO> voList = AbstractClassroomConverter.INSTANCE.doList2ListVoList(doList);

        return voList;
    }

    @Override
    public ClassroomListVO getCourseById(Long id) {

        ClassroomDO dataObj = getById(id);

        ClassroomListVO vo = AbstractClassroomConverter.INSTANCE.do2ListVO(dataObj);

        return vo;
    }

    @Override
    public boolean saveCourse(ClassroomSaveDTO saveDTO) {

        ClassroomDO dataObj = AbstractClassroomConverter.INSTANCE.saveDto2DO(saveDTO);

        return save(dataObj);
    }

    @Override
    public boolean updateCourseById(Long id, ClassroomSaveDTO saveDTO) {

        ClassroomDO dataObj = AbstractClassroomConverter.INSTANCE.saveDto2DO(saveDTO);
        dataObj.setId(id);

        return updateById(dataObj);
    }

    @Override
    public boolean updateCourseEnableStateById(Long id, Integer enableState) {

        ClassroomDO dataObj = new ClassroomDO();
        dataObj.setId(id);
        dataObj.setEnableState(enableState);

        return updateById(dataObj);
    }
}

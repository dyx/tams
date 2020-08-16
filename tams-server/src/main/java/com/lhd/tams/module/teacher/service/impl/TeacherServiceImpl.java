package com.lhd.tams.module.teacher.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhd.tams.common.consts.EnableStateEnum;
import com.lhd.tams.module.teacher.dao.TeacherMapper;
import com.lhd.tams.module.teacher.model.convert.AbstractTeacherConverter;
import com.lhd.tams.module.teacher.model.data.TeacherDO;
import com.lhd.tams.module.teacher.model.dto.TeacherPageQuery;
import com.lhd.tams.module.teacher.model.dto.TeacherSaveDTO;
import com.lhd.tams.module.teacher.model.vo.TeacherListVO;
import com.lhd.tams.module.teacher.service.TeacherService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, TeacherDO> implements TeacherService {

    @Override
    public IPage<TeacherListVO> pageTeacher(TeacherPageQuery pageQuery) {

        LambdaQueryWrapper<TeacherDO> queryWrapper = Wrappers.<TeacherDO>lambdaQuery()
                .eq(pageQuery.getEnableState() != null, TeacherDO::getEnableState, pageQuery.getEnableState())
                .orderByAsc(TeacherDO::getName);

        IPage<TeacherDO> doPage = page(new Page<>(pageQuery.getCurrent(), pageQuery.getSize()), queryWrapper);

        IPage<TeacherListVO> voPage = AbstractTeacherConverter.INSTANCE.doPage2ListVoPage(doPage);

        return voPage;
    }

    @Override
    public List<TeacherListVO> refList() {

        LambdaQueryWrapper<TeacherDO> queryWrapper = Wrappers.<TeacherDO>lambdaQuery()
                .eq(TeacherDO::getEnableState, EnableStateEnum.ENABLED.getCode())
                .orderByAsc(TeacherDO::getName);

        List<TeacherDO> doList = list(queryWrapper);

        List<TeacherListVO> voList = AbstractTeacherConverter.INSTANCE.doList2ListVoList(doList);

        return voList;
    }

    @Override
    public TeacherListVO getTeacherById(Long id) {

        TeacherDO dataObj = getById(id);

        TeacherListVO vo = AbstractTeacherConverter.INSTANCE.do2ListVO(dataObj);

        return vo;
    }

    @Override
    public boolean saveTeacher(TeacherSaveDTO saveDTO) {

        TeacherDO dataObj = AbstractTeacherConverter.INSTANCE.saveDto2DO(saveDTO);

        return save(dataObj);
    }

    @Override
    public boolean updateTeacherById(Long id, TeacherSaveDTO saveDTO) {

        TeacherDO dataObj = AbstractTeacherConverter.INSTANCE.saveDto2DO(saveDTO);
        dataObj.setId(id);

        return updateById(dataObj);
    }

    @Override
    public boolean updateTeacherEnableStateById(Long id, Integer enableState) {

        TeacherDO dataObj = new TeacherDO();
        dataObj.setId(id);
        dataObj.setEnableState(enableState);

        return updateById(dataObj);
    }
}

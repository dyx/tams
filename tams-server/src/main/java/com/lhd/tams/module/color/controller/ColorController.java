package com.lhd.tams.module.color.controller;

import com.lhd.tams.common.base.BaseController;
import com.lhd.tams.common.model.ApiResult;
import com.lhd.tams.module.color.service.ColorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lhd
 */
@Tag(name = "颜色")
@RequestMapping("color")
@RestController
public class ColorController extends BaseController {

    @Autowired
    private ColorService colorService;

    @Operation(summary = "有效颜色列表")
    @GetMapping("list/effective")
    public ResponseEntity<ApiResult<List<String>>> getEffectiveList() {

        return success(colorService.getEffectiveList());
    }
}

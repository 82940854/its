package com.controller;

import com.BaseEntity.Page;
import com.BaseEntity.ResultData;
import com.entity.Filter;
import com.service.FilterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * filter
 */
@RestController
@RequestMapping("/filter")
@Api(value = "filter",description = "FilterController")
public class FilterController {
    @Autowired
    private FilterService filterService;

    /**
     * 带参数分页查询
     * @param pageIndex 第几页-1,从0页开始
     * @param size      每页条数
     * @param filter  查询条件
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/find")
    @ApiOperation(value = "分页查询",notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageIndex",required = true,value = "第几页-1,从0页开始",paramType = "query",dataType = "int"),
            @ApiImplicitParam(name = "size",required = true,value = "每页条数",paramType = "query",dataType = "int")
    })
    public Page find(@RequestParam(value = "pageIndex", required = true) int pageIndex, @RequestParam(value = "size", required = true) int size, Filter filter, HttpServletRequest request) throws Exception {
        return new Page(filterService.findByPage(filter, --pageIndex, size, null, null));
    }

    /**
     * 查询
     *
     * @param id
     * @return
     * @throws Exception
     */
    @GetMapping("/get")
    @ApiOperation(value = "通过ID查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", required = true, value = "对象Id", paramType = "query", dataType = "String")
    })
    public ResultData get(@RequestParam(value = "id", required = true) String id) throws Exception {
        Filter filter = filterService.get(id);
        return ResultData.success(filter);
    }

    /**
     * 保存更新
     * @param filter
     * @return
     * @throws Exception
     */
    @PostMapping("/save")
    @ApiOperation(value = "保存更新")
    public ResultData save(Filter filter) throws Exception {
        try {
            Filter save = filterService.saveOrUpdate(filter);
            return ResultData.success(save);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.error("保存失败!");
        }
    }

    /**
     * 删除
     * @param id
     * @return
     * @throws Exception
     */
    @GetMapping("/delete")
    @ApiOperation(value = "删除",notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",required = true,value = "id",paramType = "query",dataType = "String"),
    })
    public ResultData delete(@RequestParam(value = "id", required = true) String id) {
        try {
            filterService.deleteByIds(id);
            return ResultData.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.error("删除失败!");
        }
    }




}

package ${packageName}.web.controller;

import com.cdkjframework.entity.PageEntity;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.CopyUtils;

import ${packageName}.vo.${className}Vo;
import ${packageName}.dto.${className}Dto;
import ${packageName}.service.${className}Service;
import com.cdkjframework.core.controller.WebUiController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ProjectName: ${projectName}
 * @Package: ${packageName}
 * @ClassName: ${className}
 * @Description: ${description}
 * @Author: ${author}
 * @Version: 1.0
 */

@RestController
@RequestMapping("/${classLowName}")
@RequiredArgsConstructor
@Api(tags="${description}")
public class ${className}Controller extends WebUiController{

    /**
     * 日志
     */
    private static LogUtils logUtil = LogUtils.getLogger(${className}Controller.class);

    /**
     * ${description} Service
     */
    private final ${className}Service ${classLowName}ServiceImpl;

    /**
     * 修改数据
     *
     * @param ${classLowName}Vo ${description} - 实体
     */
    @ResponseBody
    @ApiOperation(value = "修改数据")
    @PostMapping(value = "/modify${className}")
    public void modify${className}(@RequestBody ${className}Vo ${classLowName}Vo) {
        ${className}Dto ${classLowName} = CopyUtils.copyProperties(${classLowName}Vo, ${className}Dto.class);
        ${classLowName}ServiceImpl.modify${className}(${classLowName});
    }

    /**
     * 添加数据
     *
     * @param ${classLowName}Vo ${description} - 实体
     */
    @ResponseBody
    @ApiOperation(value = "添加数据")
    @PostMapping(value = "/add${className}")
    public void add${className}(@RequestBody ${className}Vo ${classLowName}Vo) {
        ${className}Dto ${classLowName} = CopyUtils.copyProperties(${classLowName}Vo, ${className}Dto.class);
        ${classLowName}ServiceImpl.add${className}(${classLowName});
    }
    /**
     * 删除数据
     *
     * @param ${classLowName}Vo ${description} - 实体
     */
    @ResponseBody
    @ApiOperation(value = "删除数据")
    @PostMapping(value = "/delete${className}")
    public void delete${className}(@RequestBody ${className}Vo ${classLowName}Vo) {
        ${className}Dto ${classLowName} = CopyUtils.copyProperties(${classLowName}Vo, ${className}Dto.class);
        ${classLowName}ServiceImpl.delete${className}(${classLowName});
    }

    /**
    * 查询数据
    *
    * @param ${classLowName}Vo ${description} - 实体
    * @return 返回结果
    */
    @ResponseBody
    @ApiOperation(value = "查询数据")
    @PostMapping(value = "/find${className}")
    public ${className}Vo find${className}(@RequestBody ${className}Vo ${classLowName}Vo){
        ${className}Dto ${classLowName}Dto = CopyUtils.copyProperties(${classLowName}Vo, ${className}Dto.class);
        ${classLowName}Dto = ${classLowName}ServiceImpl.find${className}(${classLowName}Dto);
        return CopyUtils.copyProperties(${classLowName}Dto, ${className}Vo.class);
    }

    /**
    * 查询指定数据
    *
    * @param id 查询条件
    * @return 返回结果
    */
    @ResponseBody
    @ApiOperation(value = "查询指定数据")
    @PostMapping(value = "/find${className}ById")
    public ${className}Vo find${className}ById(@RequestParam("id") String id){
        ${className}Dto ${classLowName}Dto = ${classLowName}ServiceImpl.find${className}ById(id);
        return CopyUtils.copyProperties(${classLowName}Dto, ${className}Vo.class);
    }

    /**
     * 查询分页数据
     *
     * @param ${classLowName}Vo 查询实体
     * @return 返回分页数据实体
     */
    @ResponseBody
    @ApiOperation(value = "查询分页数据")
    @PostMapping(value = "/list${className}Page")
    public PageEntity<${className}Vo> list${className}Page(@RequestBody ${className}Vo ${classLowName}Vo) {
        // 数据转换
        ${className}Dto ${classLowName}Dto = CopyUtils.copyProperties(${classLowName}Vo, ${className}Dto.class);
        // 查询数据
        PageEntity<${className}Dto> page = ${classLowName}ServiceImpl.list${className}Page(${classLowName}Dto);

        // 数据转换
        List<${className}Vo> dataVos = CopyUtils.copyProperties(page.getData(), ${className}Vo.class);
        // 返回结果
        return PageEntity.build(page.getPageIndex(), page.getTotal(), dataVos);
    }
}
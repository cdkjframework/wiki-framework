package com.cdkjframework.center.controller;

import com.cdkjframework.builder.ResponseBuilder;
import com.cdkjframework.center.service.GenerateService;
import com.cdkjframework.core.controller.WebUiController;
import com.cdkjframework.entity.generate.template.DatabaseEntity;
import com.cdkjframework.entity.generate.template.TableColumnEntity;
import com.cdkjframework.entity.generate.template.TableEntity;
import com.cdkjframework.entity.generate.template.TreeEntity;
import com.cdkjframework.exceptions.GlobalException;
import com.cdkjframework.util.log.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.core.controller.realization
 * @ClassName: GenerateController
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

@RestController
@RequestMapping("/code/generate")
public class GenerateController extends WebUiController {

    /**
     * 日志
     */
    private LogUtils logUtil = LogUtils.getLogger(GenerateController.class);

    /**
     * 生成服务
     */
    @Autowired
    private GenerateService generateServiceImpl;

    /**
     * 应用名称
     */
    @Value("${spring.application.name}")
    private String applicationName;

    /**
     * 首页
     *
     * @return
     */
    @GetMapping("/index")
    public ModelAndView index(ModelAndView view) {
        view.setViewName("index");
        view.addObject("applicationName", applicationName);
        //返回结果
        return view;
    }

    /**
     * 获取数据库
     *
     * @return 返回结果
     */
    @PostMapping(value = "/getDatabase")
    @ResponseBody
    public DatabaseEntity findDatabase() {
        //返回结果
        return generateServiceImpl.findDatabase();
    }

    /**
     * 获取数据库表
     *
     * @param tableEntity 查询实体
     * @return 返回结果
     */
    @PostMapping(value = "/getDatabaseTableList")
    @ResponseBody
    public List<TreeEntity> findDatabaseTableList(@RequestBody TableEntity tableEntity) {
        //返回结果
        return generateServiceImpl.findDatabaseTableList(tableEntity);
    }

    /**
     * 获取数据库表
     *
     * @param columnEntity 查询实体
     * @return 返回结果
     */
    @PostMapping(value = "/getTableColumnList")
    @ResponseBody
    public List<TableColumnEntity> findTableColumnList(@RequestBody TableColumnEntity columnEntity) {
        //返回结果
        return generateServiceImpl.findTableColumnList(columnEntity);
    }

    /**
     * 获取数据库表
     *
     * @param entityList 查询实体
     * @param dataBase   数据库
     * @return 返回结果
     */
    @PostMapping(value = "/generate")
    @ResponseBody
    public void generate(@RequestBody List<TreeEntity> entityList, String dataBase) throws GlobalException {
        boolean isGenerate = generateServiceImpl.generateCode(entityList, dataBase);
        if (!isGenerate) {
            throw new GlobalException("生成失败！");
        }
    }
}

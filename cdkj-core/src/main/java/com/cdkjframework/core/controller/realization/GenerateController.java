package com.cdkjframework.core.controller.realization;

import com.cdkjframework.builder.ResponseBuilder;
import com.cdkjframework.core.business.service.GenerateService;
import com.cdkjframework.core.controller.WebUiController;
import com.cdkjframework.entity.generate.template.DatabaseEntity;
import com.cdkjframework.entity.generate.template.TableColumnEntity;
import com.cdkjframework.entity.generate.template.TableEntity;
import com.cdkjframework.entity.generate.template.TreeEntity;
import com.cdkjframework.util.log.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
        view.setViewName("/index");
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
    public ResponseBuilder findDatabase() {
        ResponseBuilder builder;
        try {
            DatabaseEntity databaseEntity = generateServiceImpl.findDatabase();
            builder = ResponseBuilder.successBuilder(databaseEntity);
        } catch (Exception ex) {
            logUtil.error(ex.getCause(), ex.getMessage());
            builder = new ResponseBuilder(ex.getMessage());
        }

        //返回结果
        return builder;
    }

    /**
     * 获取数据库表
     *
     * @param tableEntity 查询实体
     * @return 返回结果
     */
    @PostMapping(value = "/getDatabaseTableList")
    @ResponseBody
    public ResponseBuilder findDatabaseTableList(@RequestBody TableEntity tableEntity) {
        ResponseBuilder builder;
        try {
            List<TreeEntity> tableList = generateServiceImpl.findDatabaseTableList(tableEntity);
            builder = ResponseBuilder.successBuilder(tableList);
        } catch (Exception ex) {
            logUtil.error(ex.getCause(), ex.getMessage());
            builder = new ResponseBuilder(ex.getMessage());
        }

        //返回结果
        return builder;
    }

    /**
     * 获取数据库表
     *
     * @param columnEntity 查询实体
     * @return 返回结果
     */
    @PostMapping(value = "/getTableColumnList")
    @ResponseBody
    public ResponseBuilder findTableColumnList(@RequestBody TableColumnEntity columnEntity) {
        ResponseBuilder builder;
        try {
            List<TableColumnEntity> columnList = generateServiceImpl.findTableColumnList(columnEntity);
            builder = ResponseBuilder.successBuilder(columnList);
        } catch (Exception ex) {
            logUtil.error(ex.getCause(), ex.getMessage());
            builder = new ResponseBuilder(ex.getMessage());
        }

        //返回结果
        return builder;
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
    public ResponseBuilder generate(@RequestBody List<TreeEntity> entityList, String dataBase) {
        ResponseBuilder builder;
        try {
            boolean isGenerate = generateServiceImpl.generateCode(entityList, dataBase);
            if (isGenerate) {
                builder = ResponseBuilder.successBuilder("生成成功！");
            } else {
                builder = ResponseBuilder.failBuilder("生成失败！");
            }
        } catch (Exception ex) {
            logUtil.error(ex.getCause(), ex.getMessage());
            builder = ResponseBuilder.failBuilder(ex.getMessage());
        }

        //返回结果
        return builder;
    }
}
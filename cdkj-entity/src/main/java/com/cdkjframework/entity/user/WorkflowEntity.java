package com.cdkjframework.entity.user;

import com.cdkjframework.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.entity.user
 * @ClassName: WorkflowEntity
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "bms_workflow", catalog = "")
public class WorkflowEntity extends BaseEntity {

    private static final long serialVersionUID = -1;

    /**
     * 工作流编码
     */
    @Column(name = "workflow_code")
    private String workflowCode;
    /**
     * 工作流名称
     */
    @Column(name = "workflow_name")
    private String workflowName;
    /**
     * 节点分组
     */
    @Column(name = "node_group")
    private String nodeGroup;
    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;
    /**
     * 状态
     */
    @Column(name = "status")
    private Integer status;

    public WorkflowEntity() {
    }

    public WorkflowEntity(String workflowCode, String workflowName, String nodeGroup, String remark, Integer status) {
        this.workflowCode = workflowCode;
        this.workflowName = workflowName;
        this.nodeGroup = nodeGroup;
        this.remark = remark;
        this.status = status;
    }
}

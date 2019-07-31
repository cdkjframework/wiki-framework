package com.cdkj.framework.enums;

/**
 * @ProjectName: HT-OMS-Project-OMS
 * @Package: com.cdkj.framework.core.enums
 * @ClassName: DictionaryTypeEnum
 * @Description: 数据字典枚举
 * @Author: xiaLin
 * @Version: 1.0
 */

public enum DictionaryTypeEnum {

    OmsOrderStatus {
        /**
         * 编码
         *
         * @return 返回编码
         */
        @Override
        public String getValue() {
            return "OmsOrderStatus";
        }

        /**
         * 名称
         *
         * @return 返回名称
         */
        @Override
        public String getName() {
            return "OMS订单状态";
        }
    },
    TmsOrderStatus {
        /**
         * 编码
         *
         * @return 返回编码
         */
        @Override
        public String getValue() {
            return "TmsOrderStatus";
        }

        /**
         * 名称
         *
         * @return 返回名称
         */
        @Override
        public String getName() {
            return "TMS订单状态";
        }
    },
    QuotationType {
        /**
         * 编码
         *
         * @return 返回编码
         */
        @Override
        public String getValue() {
            return "QuotationType";
        }

        /**
         * 名称
         *
         * @return 返回名称
         */
        @Override
        public String getName() {
            return "报价类型";
        }
    },
    OfferorType {
        /**
         * 编码
         *
         * @return 返回编码
         */
        @Override
        public String getValue() {
            return "OfferorType";
        }

        /**
         * 名称
         *
         * @return 返回名称
         */
        @Override
        public String getName() {
            return "报价人类型";
        }
    },
    TransactionType {
        /**
         * 编码
         *
         * @return 返回编码
         */
        @Override
        public String getValue() {
            return "TransactionType";
        }

        /**
         * 名称
         *
         * @return 返回名称
         */
        @Override
        public String getName() {
            return "成交方式";
        }
    },
    TransactionStatus {
        /**
         * 编码
         *
         * @return 返回编码
         */
        @Override
        public String getValue() {
            return "TransactionStatus";
        }

        /**
         * 名称
         *
         * @return 返回名称
         */
        @Override
        public String getName() {
            return "成交状态";
        }
    },
    CarriageType {
        /**
         * 编码
         *
         * @return 返回编码
         */
        @Override
        public String getValue() {
            return "CarriageType";
        }

        /**
         * 名称
         *
         * @return 返回名称
         */
        @Override
        public String getName() {
            return "承运方式";
        }
    },
    ImportType {
        /**
         * 编码
         *
         * @return 返回编码
         */
        @Override
        public String getValue() {
            return "ImportType";
        }

        /**
         * 名称
         *
         * @return 返回名称
         */
        @Override
        public String getName() {
            return "导入类型";
        }
    },
    OrderType {
        /**
         * 编码
         *
         * @return 返回编码
         */
        @Override
        public String getValue() {
            return "OrderType";
        }

        /**
         * 名称
         *
         * @return 返回名称
         */
        @Override
        public String getName() {
            return "出库订单类型";
        }
    },
    ProductionOrderType {
        /**
         * 编码
         *
         * @return 返回编码
         */
        @Override
        public String getValue() {
            return "ProductionOrderType";
        }

        /**
         * 名称
         *
         * @return 返回名称
         */
        @Override
        public String getName() {
            return "生产订单";
        }
    },
    PurchaseOrderType {
        /**
         * 编码
         *
         * @return 返回编码
         */
        @Override
        public String getValue() {
            return "PurchaseOrderType";
        }

        /**
         * 名称
         *
         * @return 返回名称
         */
        @Override
        public String getName() {
            return "采购订单";
        }
    },
    ReturnOrderType {
        /**
         * 编码
         *
         * @return 返回编码
         */
        @Override
        public String getValue() {
            return "ReturnOrderType";
        }

        /**
         * 名称
         *
         * @return 返回名称
         */
        @Override
        public String getName() {
            return "退换货订单";
        }
    },
    ReconciliationStatus {
        /**
         * 编码
         *
         * @return 返回编码
         */
        @Override
        public String getValue() {
            return "ReconciliationStatus";
        }

        /**
         * 名称
         *
         * @return 返回名称
         */
        @Override
        public String getName() {
            return "对账状态";
        }
    },
    ReleaseStatus {
        /**
         * 编码
         *
         * @return 返回编码
         */
        @Override
        public String getValue() {
            return "ReleaseStatus";
        }

        /**
         * 名称
         *
         * @return 返回名称
         */
        @Override
        public String getName() {
            return "发布状态";
        }
    },
    ReceiptStatus {
        /**
         * 编码
         *
         * @return 返回编码
         */
        @Override
        public String getValue() {
            return "ReceiptStatus";
        }

        /**
         * 名称
         *
         * @return 返回名称
         */
        @Override
        public String getName() {
            return "回单状态";
        }
    },
    SettlementType {
        /**
         * 编码
         *
         * @return 返回编码
         */
        @Override
        public String getValue() {
            return "SettlementType";
        }

        /**
         * 名称
         *
         * @return 返回名称
         */
        @Override
        public String getName() {
            return "结算方式";
        }
    },
    SettlementStatus {
        /**
         * 编码
         *
         * @return 返回编码
         */
        @Override
        public String getValue() {
            return "SettlementStatus";
        }

        /**
         * 名称
         *
         * @return 返回名称
         */
        @Override
        public String getName() {
            return "结算状态";
        }
    },
    BillingStatus {
        /**
         * 编码
         *
         * @return 返回编码
         */
        @Override
        public String getValue() {
            return "BillingStatus";
        }

        /**
         * 名称
         *
         * @return 返回名称
         */
        @Override
        public String getName() {
            return "开票状态";
        }
    },
    StowageStatus {
        /**
         * 编码
         *
         * @return 返回编码
         */
        @Override
        public String getValue() {
            return "StowageStatus";
        }

        /**
         * 名称
         *
         * @return 返回名称
         */
        @Override
        public String getName() {
            return "配载状态";
        }
    },
    SignStatus {
        /**
         * 编码
         *
         * @return 返回编码
         */
        @Override
        public String getValue() {
            return "SignStatus";
        }

        /**
         * 名称
         *
         * @return 返回名称
         */
        @Override
        public String getName() {
            return "签收状态";
        }
    },
    TaskType {
        /**
         * 编码
         *
         * @return 返回编码
         */
        @Override
        public String getValue() {
            return "TaskType";
        }

        /**
         * 名称
         *
         * @return 返回名称
         */
        @Override
        public String getName() {
            return "任务类型";
        }
    },
    DispatchStatus {
        /**
         * 编码
         *
         * @return 返回编码
         */
        @Override
        public String getValue() {
            return "DispatchStatus";
        }

        /**
         * 名称
         *
         * @return 返回名称
         */
        @Override
        public String getName() {
            return "调度状态";
        }
    },
    MessageType {
        /**
         * 编码
         *
         * @return 返回编码
         */
        @Override
        public String getValue() {
            return "MessageType";
        }

        /**
         * 名称
         *
         * @return 返回名称
         */
        @Override
        public String getName() {
            return "消息类型";
        }
    },
    ExceptionHandStatus {
        /**
         * 编码
         *
         * @return 返回编码
         */
        @Override
        public String getValue() {
            return "ExceptionHandStatus";
        }

        /**
         * 名称
         *
         * @return 返回名称
         */
        @Override
        public String getName() {
            return "异常处理状态";
        }
    },
    WaybillStatus {
        /**
         * 编码
         *
         * @return 返回编码
         */
        @Override
        public String getValue() {
            return "WaybillStatus";
        }

        /**
         * 名称
         *
         * @return 返回名称
         */
        @Override
        public String getName() {
            return "运单状态";
        }
    },
    TransportType {
        /**
         * 编码
         *
         * @return 返回编码
         */
        @Override
        public String getValue() {
            return "TransportType";
        }

        /**
         * 名称
         *
         * @return 返回名称
         */
        @Override
        public String getName() {
            return "运输方式（运输类型）";
        }
    },
    TransportStatus {
        /**
         * 编码
         *
         * @return 返回编码
         */
        @Override
        public String getValue() {
            return "TransportStatus";
        }

        /**
         * 名称
         *
         * @return 返回名称
         */
        @Override
        public String getName() {
            return "运输状态";
        }
    },
    OrderRecordType {
        /**
         * 编码
         *
         * @return 返回编码
         */
        @Override
        public String getValue() {
            return "OrderRecordType";
        }

        /**
         * 名称
         *
         * @return 返回名称
         */
        @Override
        public String getName() {
            return "订单日志记录类型";
        }
    },
    MaterielType {
        /**
         * 编码
         *
         * @return 返回编码
         */
        @Override
        public String getValue() {
            return "MaterielType";
        }

        /**
         * 名称
         *
         * @return 返回名称
         */
        @Override
        public String getName() {
            return "物料类型";
        }
    },
    PaymentType {
        /**
         * 编码
         *
         * @return 返回编码
         */
        @Override
        public String getValue() {
            return "PaymentType";
        }

        /**
         * 名称
         *
         * @return 返回名称
         */
        @Override
        public String getName() {
            return "收款方式";
        }
    },
    AttachmentType {
        /**
         * 编码
         *
         * @return 返回编码
         */
        @Override
        public String getValue() {
            return "AttachmentType";
        }

        /**
         * 名称
         *
         * @return 返回名称
         */
        @Override
        public String getName() {
            return "附件类型";
        }
    },
    FileType {
        /**
         * 编码
         *
         * @return 返回编码
         */
        @Override
        public String getValue() {
            return "FileType";
        }

        /**
         * 名称
         *
         * @return 返回名称
         */
        @Override
        public String getName() {
            return "文件类型";
        }
    },
    CustomerType {
        /**
         * 编码
         *
         * @return 返回编码
         */
        @Override
        public String getValue() {
            return "CustomerType";
        }

        /**
         * 名称
         *
         * @return 返回名称
         */
        @Override
        public String getName() {
            return "客户类型";
        }
    },
    ContractType {
        /**
         * 编码
         *
         * @return 返回编码
         */
        @Override
        public String getValue() {
            return "ContractType";
        }

        /**
         * 名称
         *
         * @return 返回名称
         */
        @Override
        public String getName() {
            return "合同类型";
        }
    },
    ContactType {
        /**
         * 编码
         *
         * @return 返回编码
         */
        @Override
        public String getValue() {
            return "ContactType";
        }

        /**
         * 名称
         *
         * @return 返回名称
         */
        @Override
        public String getName() {
            return "联系方式类型";
        }
    },
    OperationType {
        /**
         * 编码
         *
         * @return 返回编码
         */
        @Override
        public String getValue() {
            return "OperationType";
        }

        /**
         * 名称
         *
         * @return 返回名称
         */
        @Override
        public String getName() {
            return "订单操作类型";
        }
    },
    PacketWeight {
        /**
         * 编码
         *
         * @return 返回编码
         */
        @Override
        public String getValue() {
            return "PacketWeight";
        }

        /**
         * 名称
         *
         * @return 返回名称
         */
        @Override
        public String getName() {
            return "包重";
        }
    },
    DefaultUnit {
        /**
         * 编码
         *
         * @return 返回编码
         */
        @Override
        public String getValue() {
            return "DefaultUnit";
        }

        /**
         * 名称
         *
         * @return 返回名称
         */
        @Override
        public String getName() {
            return "默认单位";
        }
    },
    MenuButton {
        /**
         * 编码
         *
         * @return 返回编码
         */
        @Override
        public String getValue() {
            return "MenuButton";
        }

        /**
         * 名称
         *
         * @return 返回名称
         */
        @Override
        public String getName() {
            return "菜单按钮";
        }
    },
    DeliveryType {
        /**
         * 编码
         *
         * @return 返回编码
         */
        @Override
        public String getValue() {
            return "DeliveryType";
        }

        /**
         * 名称
         *
         * @return 返回名称
         */
        @Override
        public String getName() {
            return "交货方式";
        }
    },
    InStorage {
        /**
         * 编码
         *
         * @return 返回编码
         */
        @Override
        public String getValue() {
            return "InStorage";
        }

        /**
         * 名称
         *
         * @return 返回名称
         */
        @Override
        public String getName() {
            return "入库订单";
        }
    },
    OutStock {
        /**
         * 编码
         *
         * @return 返回编码
         */
        @Override
        public String getValue() {
            return "OutStock";
        }

        /**
         * 名称
         *
         * @return 返回名称
         */
        @Override
        public String getName() {
            return "出库订单";
        }
    },
    DealUnit {
        /**
         * 编码
         *
         * @return 返回编码
         */
        @Override
        public String getValue() {
            return "DealUnit";
        }

        /**
         * 名称
         *
         * @return 返回名称
         */
        @Override
        public String getName() {
            return "单位";
        }
    },
    VehicleType {
        /**
         * 编码
         *
         * @return 返回编码
         */
        @Override
        public String getValue() {
            return "VehicleType";
        }

        /**
         * 名称
         *
         * @return 返回名称
         */
        @Override
        public String getName() {
            return "车辆类型";
        }
    },
    VehicleLength {
        /**
         * 编码
         *
         * @return 返回编码
         */
        @Override
        public String getValue() {
            return "VehicleLength";
        }

        /**
         * 名称
         *
         * @return 返回名称
         */
        @Override
        public String getName() {
            return "车辆长度";
        }
    };

    /**
     * 编码
     *
     * @return 返回编码
     */
    public abstract String getValue();

    /**
     * 名称
     *
     * @return 返回名称
     */
    public abstract String getName();
}

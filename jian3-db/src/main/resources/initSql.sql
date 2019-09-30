CREATE TABLE `t_factor_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `org_code` varchar(64) NOT NULL DEFAULT '' COMMENT '编码',
  `name_en` varchar(128) NOT NULL DEFAULT '' COMMENT '英文名称',
  `name` varchar(128) NOT NULL DEFAULT '' COMMENT '名称',
  `descri` varchar(256) NOT NULL DEFAULT '' COMMENT '描述',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态 1 正常使用 -1 已删除',
  `creator` varchar(32) NOT NULL DEFAULT '0' COMMENT '创建者',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier` varchar(32) NOT NULL DEFAULT '0' COMMENT '最后更新人员',
  `last_modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='变量分组表';


CREATE TABLE `t_factor_group_rel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group_id` int(11) NOT NULL DEFAULT '0' COMMENT '组id',
  `factor_id` int(11) NOT NULL DEFAULT '0' COMMENT '变量id',
  `status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '状态： 1.正常 2.删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='变量分组关联表';

CREATE TABLE `t_factor` (
  `id` int(16) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL DEFAULT '' COMMENT '展示名称(无则直接取用factor_code)',
  `code` varchar(64) NOT NULL DEFAULT '' COMMENT '指标code，唯一',
  `data_from` tinyint(4) NOT NULL DEFAULT '0' COMMENT '数据来源，0:自有self、1:三方thirdpart',
  `type` varchar(32) NOT NULL DEFAULT '' COMMENT '模型:MODEL/普通:NORMAL/衍生变量:DERIVE/输出变量：OUTCOME_INDICATOR',
  `model_id` int(11) NOT NULL DEFAULT '0' COMMENT '模型ID，当type为model时，该值才有效',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态 1：正常 -1：删除',
  `derive_expression` varchar(4096) NOT NULL DEFAULT '' COMMENT '衍生变量表达式',
  `data_type` int(16) NOT NULL COMMENT '0： BOOLEAN   1：DATE   2：DOUBLE  3：INTEGER     4：STRING',
  `description` varchar(500) NOT NULL DEFAULT '' COMMENT '描述',
  `data_source_ids` varchar(255) NOT NULL DEFAULT '' COMMENT '大数据落库时候的数据源id集合',
  `bg_enable` tinyint(4) NOT NULL DEFAULT '1' COMMENT '0不使用大数据加工，1使用大数据加工',
  `bg_query_mode` tinyint(4) NOT NULL DEFAULT '1' COMMENT '1：根据pf查询   2根据orderNo查询',
  `bg_biz_code` varchar(64) NOT NULL DEFAULT '' COMMENT '大数据业务code',
  `bg_db_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '数据源ID',
  `bg_db_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '大数据落库时候的数据库名',
  `bg_db_table` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '大数据落库时候的数据表名',
  `bg_db_column` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '大数据落库时候的列名 若是 hbase 数据库则存储格式为FAMALIY:COLUMN',
  `expire_time` int(11) NOT NULL DEFAULT '7200' COMMENT '过期时间,单位/秒',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_user_id` int(11) NOT NULL DEFAULT '0' COMMENT '创建人',
  `last_modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `modify_user_id` int(11) NOT NULL DEFAULT '0' COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `bg_busi_code` (`bg_biz_code`) USING BTREE,
  KEY `code_UNIQUE` (`code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='变量表';
--实验环境相关
CREATE TABLE `t_risk_lab_offline_task`
(
  `id`                          int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `task_name`                   varchar(64)  NOT NULL DEFAULT '' COMMENT '任务名称',
  `should_new_flag`             bit(1)       NOT NULL DEFAULT b'0' COMMENT '是否只筛选新用户',
  `product_name`                varchar(64)  NOT NULL DEFAULT '' COMMENT '产品名称',
  `product_code`                varchar(64)  NOT NULL DEFAULT '' COMMENT '产品 code',
  `strategy_code`               varchar(64)  NOT NULL DEFAULT '' COMMENT '策略编码',
  `strategy_name`               varchar(64)  NOT NULL DEFAULT '' COMMENT '策略名称',
  `strategy_version`            varchar(64)  NOT NULL DEFAULT '' COMMENT '目标策略版本',
  `ref_data_source_code`        varchar(64)  NOT NULL DEFAULT '' COMMENT '目标策略所关联的数据源 code',
  `source_ref_data_source_code` varchar(64)  NOT NULL DEFAULT '' COMMENT '源策略所关联的数据源',
  `source_product_code`         varchar(64)  NOT NULL DEFAULT '' COMMENT '源产品编码',
  `source_product_name`         varchar(64)  NOT NULL DEFAULT '' COMMENT '源产品名称',
  `source_strategy_code`        varchar(64)  NOT NULL DEFAULT '' COMMENT '源策略编码',
  `source_strategy_name`        varchar(164) NOT NULL DEFAULT '' COMMENT '源策略名称',
  `source_strategy_version`     varchar(64)  NOT NULL DEFAULT '' COMMENT '源策略版本',
  `order_begin_time`            timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '实验需回溯的订单起始时间',
  `order_end_time`              timestamp    NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '实验需回溯的订单结束时间',
  `order_total_num`             bigint(20) NOT NULL DEFAULT '0' COMMENT '订单总量',
  `status`                      tinyint(2) NOT NULL DEFAULT '0' COMMENT '状态(0创建,1运行中,2,已完成,3中断)',
  `creator`                     varchar(64)  NOT NULL DEFAULT '' COMMENT '创建人',
  `created_time`                timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_modify_time`            timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY                           `offline_task_uk` (`product_code`,`strategy_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5000000 DEFAULT CHARSET=utf8 COMMENT='离线实验 task';






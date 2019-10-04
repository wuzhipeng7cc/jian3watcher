# 剑三金价实时监控以及实时告警分享
## 涉及到的技术框架
* spring全家桶
* grafana
* mysql
* xpath,css_selector
* docker
* linux
* java8
## 目前支持平台
* 百度贴吧
* dd373
* uu898
## 开发背景
* 上个月我560买了200r的金这俩周变成460/r了,金价波动太快,很难受
* 目前剑三工作室打击严重,金价持续下跌
## 需求描述
最快感知金价状态,遇到高比例的速拍

## 测试地址
* [测试dashboard](http://122.112.230.199:3000/d/DS-DI72Zz/jian3?orgId=1&from=now-1h&to=now)
* 账号 fg
* 密码 123456
* 服务器 *电信五区梦江南*
## git 地址
* https://github.com/wuzhipeng7cc/jian3watcher/tree/master
```sql
CREATE TABLE `t_gold_price_unit` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `platform` varchar(64) NOT NULL DEFAULT '' COMMENT '平台名称',
  `fetch_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `price` decimal(9,6) unsigned NOT NULL,
  `payment_url` varchar(512) NOT NULL DEFAULT '' COMMENT '支付链接',
  `inventory` bigint(20) unsigned NOT NULL COMMENT '库存',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `seller_name` varchar(128) DEFAULT '' COMMENT '卖家姓名',
  `add_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '上架时间',
  PRIMARY KEY (`id`),
  KEY `idx_price` (`price`) USING BTREE,
  KEY `idx_time` (`fetch_time`) USING BTREE,
  KEY `idx_url` (`payment_url`,`platform`,`price`,`fetch_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=352371 DEFAULT CHARSET=utf8mb4;
```
## 联系方式
* qq 762545960 
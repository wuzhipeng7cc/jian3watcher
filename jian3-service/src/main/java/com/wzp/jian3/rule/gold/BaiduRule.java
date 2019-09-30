package com.wzp.jian3.rule.gold;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author wuzhipeng
 * @date 2019/10/39:06 AM
 */
@Component
@ConfigurationProperties(prefix = "jian3.baidu.rule")
@Data
public class BaiduRule extends BaseGoldRule {

}

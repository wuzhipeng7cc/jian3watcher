package com.wzp.jian3.rule.gold;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author wuzhipeng
 * @date 2019-09-3014:22
 */
@Component
@ConfigurationProperties(prefix = "jian3.uu898.rule")
@Data
public class UU898Rule extends BaseGoldRule {

}

package com.wzp.jian3.rule.gold;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * author: wzp
 * date: 2019/10/1
 */
@Component
@ConfigurationProperties(prefix = "jian3.dd373.rule")
@Data
public class DD373Rule extends BaseGoldRule {
}

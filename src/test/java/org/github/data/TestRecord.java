package org.github.data;

import lombok.Data;

import java.io.Serializable;

/**
 * 测试业务记录类型
 */
@Data
public class TestRecord implements Serializable {
    private Long id;
    private String name;
    private Integer age;
}

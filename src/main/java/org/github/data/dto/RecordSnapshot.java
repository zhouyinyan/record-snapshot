package org.github.data.dto;

import cn.hutool.core.annotation.Alias;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

/**
 * 快照实体
 *  T 数据记录类型
 */
@Data
@Builder
public class RecordSnapshot<T> implements Serializable{
    public static final String TABLE_NAME = "record_snapshot";
    public static final String[] FIELD_NAMES = {"id","record_id","record_table","record_clazz",
            "record_data","record_version","create_by","create_time"};

    @Alias("id")
    private Long id;
    @Alias("record_id")
    private String recordId;
    @Alias("record_table")
    private String recordTable;
    @Alias("record_clazz")
    private String recordClazz;
    private T record;
    @Alias("record_data")
    private String recordData;
    @Alias("record_version")
    private Integer recordVersion;
    @Alias("create_by")
    private String createBy;
    @Alias("create_time")
    private DateTime createTime;

    public void convert2Json(){
        if(Objects.nonNull(record) && StrUtil.isBlank(recordData)){
            recordData = JSONUtil.toJsonStr(record);
        }
    }

    public void convert2Bean(){
        if(Objects.isNull(record) &&
                StrUtil.isNotBlank(recordData) &&
                StrUtil.isNotBlank(recordClazz) ){
            Class<? extends T> beanClass = ClassUtil.loadClass(recordClazz);
            record = JSONUtil.toBean(recordData, beanClass);
        }
    }

    public void setCreateTime2Now(){
        createTime = DateTime.now();
    }
}

package org.github.data;

import org.github.data.dto.RecordSnapshot;

import java.util.List;

/**
 * 数据库记录快照服务
 */
public interface IRecordSnapshotService {

    /**
     * 新增快照数据
     * @param snapshot
     * @return
     */
    Long addSnapshot(RecordSnapshot<?> snapshot);

    /**
     * 根据数据主键ID，查询快照列表
     * @param recordId
     * @return
     */
    List<RecordSnapshot> queryByRecordId(String recordId);

    /**
     * 根据数据主键ID，删除快照记录
     * @param recordId
     * @param remainVersion 保留版本号，小于该版本的数据被删除，默认当前最新版本
     * @return
     */
    Boolean removeByRecordId(String recordId, Integer remainVersion);
}

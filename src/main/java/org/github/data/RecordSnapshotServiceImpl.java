package org.github.data;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import org.github.data.dto.RecordSnapshot;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import static org.github.data.dto.RecordSnapshot.TABLE_NAME;

/**
 * 数据库记录快照服务
 */
public class RecordSnapshotServiceImpl implements IRecordSnapshotService{

    @Override
    public Long addSnapshot(RecordSnapshot<?> snapshot) {

        try {
            fillDataBeforeInsert(snapshot);

            return Db.use().insertForGeneratedKey(
                    Entity.create(TABLE_NAME)
                            .parseBean(snapshot)
                            .filter(RecordSnapshot.FIELD_NAMES)
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void fillDataBeforeInsert(RecordSnapshot<?> snapshot) throws SQLException {
        //转换json串
        snapshot.convert2Json();
        //设置version
        setNewVersion(snapshot);
        //设置创建时间
        snapshot.setCreateTime2Now();
    }

    private void setNewVersion(RecordSnapshot<?> snapshot) throws SQLException {
        int maxVersion = 0;
        Number number = Db.use()
                .queryNumber("SELECT max(record_version) FROM `record_snapshot` where record_id = ?", snapshot.getRecordId());
        if(Objects.nonNull(number)){
            maxVersion = number.intValue();
        }
        snapshot.setRecordVersion(maxVersion + 1);
    }

    @Override
    public List<RecordSnapshot> queryByRecordId(String recordId) {
        try {
            List<RecordSnapshot> snapshots = Db.use().findAll(
                    Entity.create(TABLE_NAME)
                            .set("record_id", recordId),
                    RecordSnapshot.class
            );
            fillDataAfterQuery(snapshots);
            return snapshots;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void fillDataAfterQuery(List<RecordSnapshot> snapshots) {
        if(CollUtil.isNotEmpty(snapshots)){
            for (RecordSnapshot snapshot : snapshots) {
                snapshot.convert2Bean();
            }
        }
    }

    @Override
    public Boolean removeByRecordId(String recordId, Integer remainVersion) {
        try {
            int del = Db.use().del(
                    Entity.create(TABLE_NAME)
                            .set("record_version", "< " + remainVersion)
            );

            return del > 0 ? Boolean.TRUE : Boolean.FALSE;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

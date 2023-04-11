package org.github.data;

import org.github.data.dto.RecordSnapshot;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class RecordSnapshotServiceTest {

    public static IRecordSnapshotService snapshotService;

    @BeforeClass
    public static void beforeClass() throws Exception {
        snapshotService = new RecordSnapshotServiceImpl();
    }

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testAddSnapshot() {
        TestRecord testRecord = new TestRecord();
        testRecord.setId(11111L);
        testRecord.setName("laozhou");
        testRecord.setAge(29);

        RecordSnapshot.RecordSnapshotBuilder<TestRecord> builder = RecordSnapshot.builder();
        RecordSnapshot<TestRecord> ss = builder.record(testRecord)
                .recordId(String.valueOf(testRecord.getId()))
                .recordClazz(testRecord.getClass().getName())
                .recordTable("user").build();

        snapshotService.addSnapshot(ss);
    }

    @Test
    public void testQueryByRecordId() {
        List<RecordSnapshot> recordSnapshots = snapshotService.queryByRecordId("11111");
        System.out.println(recordSnapshots);
    }

    @Test
    public void testRemoveByRecordId() {
        snapshotService.removeByRecordId("11111", 3);
    }
}
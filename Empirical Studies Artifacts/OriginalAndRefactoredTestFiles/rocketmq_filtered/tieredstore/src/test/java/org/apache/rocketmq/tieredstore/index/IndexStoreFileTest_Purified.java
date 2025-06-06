package org.apache.rocketmq.tieredstore.index;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.rocketmq.common.ThreadFactoryImpl;
import org.apache.rocketmq.tieredstore.MessageStoreConfig;
import org.apache.rocketmq.tieredstore.MessageStoreExecutor;
import org.apache.rocketmq.tieredstore.common.AppendResult;
import org.apache.rocketmq.tieredstore.common.FileSegmentType;
import org.apache.rocketmq.tieredstore.provider.FileSegment;
import org.apache.rocketmq.tieredstore.provider.PosixFileSegment;
import org.apache.rocketmq.tieredstore.util.MessageStoreUtilTest;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class IndexStoreFileTest_Purified {

    private static final String TOPIC_NAME = "TopicTest";

    private static final int TOPIC_ID = 123;

    private static final int QUEUE_ID = 2;

    private static final long MESSAGE_OFFSET = 666L;

    private static final int MESSAGE_SIZE = 1024;

    private static final String KEY = "MessageKey";

    private static final Set<String> KEY_SET = Collections.singleton(KEY);

    private String filePath;

    private MessageStoreConfig storeConfig;

    private IndexStoreFile indexStoreFile;

    @Before
    public void init() throws IOException {
        filePath = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        String directory = Paths.get(System.getProperty("user.home"), "store_test", filePath).toString();
        storeConfig = new MessageStoreConfig();
        storeConfig.setStorePathRootDir(directory);
        storeConfig.setTieredStoreFilePath(directory);
        storeConfig.setTieredStoreIndexFileMaxHashSlotNum(5);
        storeConfig.setTieredStoreIndexFileMaxIndexNum(20);
        storeConfig.setTieredBackendServiceProvider("org.apache.rocketmq.tieredstore.provider.PosixFileSegment");
        indexStoreFile = new IndexStoreFile(storeConfig, System.currentTimeMillis());
    }

    @After
    public void shutdown() {
        if (this.indexStoreFile != null) {
            this.indexStoreFile.shutdown();
            this.indexStoreFile.destroy();
        }
        MessageStoreUtilTest.deleteStoreDirectory(storeConfig.getTieredStoreFilePath());
    }

    @Test
    public void testIndexHeaderConstants_1() {
        Assert.assertEquals(0, IndexStoreFile.INDEX_MAGIC_CODE);
    }

    @Test
    public void testIndexHeaderConstants_2() {
        Assert.assertEquals(4, IndexStoreFile.INDEX_BEGIN_TIME_STAMP);
    }

    @Test
    public void testIndexHeaderConstants_3() {
        Assert.assertEquals(12, IndexStoreFile.INDEX_END_TIME_STAMP);
    }

    @Test
    public void testIndexHeaderConstants_4() {
        Assert.assertEquals(20, IndexStoreFile.INDEX_SLOT_COUNT);
    }

    @Test
    public void testIndexHeaderConstants_5() {
        Assert.assertEquals(24, IndexStoreFile.INDEX_ITEM_INDEX);
    }

    @Test
    public void testIndexHeaderConstants_6() {
        Assert.assertEquals(28, IndexStoreFile.INDEX_HEADER_SIZE);
    }

    @Test
    public void testIndexHeaderConstants_7() {
        Assert.assertEquals(0xCCDDEEFF ^ 1880681586 + 4, IndexStoreFile.BEGIN_MAGIC_CODE);
    }

    @Test
    public void testIndexHeaderConstants_8() {
        Assert.assertEquals(0xCCDDEEFF ^ 1880681586 + 8, IndexStoreFile.END_MAGIC_CODE);
    }

    @Test
    public void basicMethodTest_1_testMerged_1() throws IOException {
        long timestamp = System.currentTimeMillis();
        IndexStoreFile localFile = new IndexStoreFile(storeConfig, timestamp);
        Assert.assertEquals(timestamp, localFile.getTimestamp());
        Assert.assertEquals(IndexFile.IndexStatusEnum.UNSEALED, localFile.getFileStatus());
        localFile.doCompaction();
        Assert.assertEquals(IndexFile.IndexStatusEnum.SEALED, localFile.getFileStatus());
        Assert.assertEquals("TopicTest#MessageKey", localFile.buildKey(TOPIC_NAME, KEY));
        Assert.assertEquals(638347386, indexStoreFile.hashCode(localFile.buildKey(TOPIC_NAME, KEY)));
    }

    @Test
    public void basicMethodTest_6_testMerged_2() throws IOException {
        long headerSize = IndexStoreFile.INDEX_HEADER_SIZE;
        Assert.assertEquals(headerSize + Long.BYTES * 2, indexStoreFile.getSlotPosition(2));
        Assert.assertEquals(headerSize + Long.BYTES * 5, indexStoreFile.getSlotPosition(5));
        Assert.assertEquals(headerSize + Long.BYTES * 5 + IndexItem.INDEX_ITEM_SIZE * 2, indexStoreFile.getItemPosition(2));
        Assert.assertEquals(headerSize + Long.BYTES * 5 + IndexItem.INDEX_ITEM_SIZE * 5, indexStoreFile.getItemPosition(5));
    }
}

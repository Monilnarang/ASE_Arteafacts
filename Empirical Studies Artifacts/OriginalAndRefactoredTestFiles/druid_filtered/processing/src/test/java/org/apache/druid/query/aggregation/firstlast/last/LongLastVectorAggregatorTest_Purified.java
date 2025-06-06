package org.apache.druid.query.aggregation.firstlast.last;

import org.apache.druid.java.util.common.DateTimes;
import org.apache.druid.java.util.common.Pair;
import org.apache.druid.query.aggregation.SerializablePairLongLong;
import org.apache.druid.query.aggregation.VectorAggregator;
import org.apache.druid.query.aggregation.firstlast.FirstLastVectorAggregator;
import org.apache.druid.query.dimension.DimensionSpec;
import org.apache.druid.segment.column.ColumnCapabilities;
import org.apache.druid.segment.column.ColumnCapabilitiesImpl;
import org.apache.druid.segment.column.ColumnType;
import org.apache.druid.segment.vector.BaseLongVectorValueSelector;
import org.apache.druid.segment.vector.MultiValueDimensionVectorSelector;
import org.apache.druid.segment.vector.NoFilterVectorOffset;
import org.apache.druid.segment.vector.ReadableVectorInspector;
import org.apache.druid.segment.vector.SingleValueDimensionVectorSelector;
import org.apache.druid.segment.vector.VectorColumnSelectorFactory;
import org.apache.druid.segment.vector.VectorObjectSelector;
import org.apache.druid.segment.vector.VectorValueSelector;
import org.apache.druid.testing.InitializedNullHandlingTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import javax.annotation.Nullable;
import java.nio.ByteBuffer;
import java.util.concurrent.ThreadLocalRandom;

public class LongLastVectorAggregatorTest_Purified extends InitializedNullHandlingTest {

    private static final double EPSILON = 1e-5;

    private static final long[] VALUES = new long[] { 7, 15, 2, 150 };

    private static final long[] LONG_VALUES = new long[] { 1L, 2L, 3L, 4L };

    private static final float[] FLOAT_VALUES = new float[] { 1.0f, 2.0f, 3.0f, 4.0f };

    private static final double[] DOUBLE_VALUES = new double[] { 1.0, 2.0, 3.0, 4.0 };

    private static final boolean[] NULLS = new boolean[] { false, false, false, false };

    private static final String NAME = "NAME";

    private static final String FIELD_NAME = "FIELD_NAME";

    private static final String FIELD_NAME_LONG = "LONG_NAME";

    private static final String TIME_COL = "__time";

    private final long[] times = { 2345001L, 2345100L, 2345200L, 2345300L };

    private final SerializablePairLongLong[] pairs = { new SerializablePairLongLong(2345001L, 1L), new SerializablePairLongLong(2345100L, 2L), new SerializablePairLongLong(2345200L, 3L), new SerializablePairLongLong(2345300L, 4L) };

    private VectorObjectSelector selector;

    private BaseLongVectorValueSelector timeSelector;

    private ByteBuffer buf;

    private LongLastVectorAggregator target;

    private LongLastAggregatorFactory longLastAggregatorFactory;

    private VectorColumnSelectorFactory selectorFactory;

    private VectorValueSelector nonLongValueSelector;

    @Before
    public void setup() {
        byte[] randomBytes = new byte[1024];
        ThreadLocalRandom.current().nextBytes(randomBytes);
        buf = ByteBuffer.wrap(randomBytes);
        timeSelector = new BaseLongVectorValueSelector(new NoFilterVectorOffset(times.length, 0, times.length) {
        }) {

            @Override
            public long[] getLongVector() {
                return times;
            }

            @Nullable
            @Override
            public boolean[] getNullVector() {
                return null;
            }
        };
        selector = new VectorObjectSelector() {

            @Override
            public Object[] getObjectVector() {
                return pairs;
            }

            @Override
            public int getMaxVectorSize() {
                return 4;
            }

            @Override
            public int getCurrentVectorSize() {
                return 0;
            }
        };
        nonLongValueSelector = new BaseLongVectorValueSelector(new NoFilterVectorOffset(LONG_VALUES.length, 0, LONG_VALUES.length)) {

            @Override
            public long[] getLongVector() {
                return LONG_VALUES;
            }

            @Override
            public float[] getFloatVector() {
                return FLOAT_VALUES;
            }

            @Override
            public double[] getDoubleVector() {
                return DOUBLE_VALUES;
            }

            @Nullable
            @Override
            public boolean[] getNullVector() {
                return null;
            }

            @Override
            public int getMaxVectorSize() {
                return 4;
            }

            @Override
            public int getCurrentVectorSize() {
                return 4;
            }
        };
        selectorFactory = new VectorColumnSelectorFactory() {

            @Override
            public ReadableVectorInspector getReadableVectorInspector() {
                return new NoFilterVectorOffset(VALUES.length, 0, VALUES.length);
            }

            @Override
            public SingleValueDimensionVectorSelector makeSingleValueDimensionSelector(DimensionSpec dimensionSpec) {
                return null;
            }

            @Override
            public MultiValueDimensionVectorSelector makeMultiValueDimensionSelector(DimensionSpec dimensionSpec) {
                return null;
            }

            @Override
            public VectorValueSelector makeValueSelector(String column) {
                if (TIME_COL.equals(column)) {
                    return timeSelector;
                } else if (FIELD_NAME_LONG.equals(column) || FIELD_NAME.equals(column)) {
                    return nonLongValueSelector;
                }
                return null;
            }

            @Override
            public VectorObjectSelector makeObjectSelector(String column) {
                if (FIELD_NAME.equals(column)) {
                    return selector;
                } else {
                    return null;
                }
            }

            @Nullable
            @Override
            public ColumnCapabilities getColumnCapabilities(String column) {
                if (FIELD_NAME.equals(column)) {
                    return ColumnCapabilitiesImpl.createSimpleNumericColumnCapabilities(ColumnType.LONG);
                } else if (FIELD_NAME_LONG.equals(column)) {
                    return ColumnCapabilitiesImpl.createSimpleNumericColumnCapabilities(ColumnType.DOUBLE);
                }
                return null;
            }
        };
        target = new LongLastVectorAggregator(timeSelector, selector);
        clearBufferForPositions(0, 0);
        longLastAggregatorFactory = new LongLastAggregatorFactory(NAME, FIELD_NAME, TIME_COL);
    }

    private VectorValueSelector createLongSelector(Long[] times) {
        return new BaseLongVectorValueSelector(new NoFilterVectorOffset(times.length, 0, times.length)) {

            @Override
            public long[] getLongVector() {
                long[] timesCasted = new long[times.length];
                for (int i = 0; i < times.length; ++i) {
                    timesCasted[i] = times[i] == null ? 0 : times[i];
                }
                return timesCasted;
            }

            @Nullable
            @Override
            public boolean[] getNullVector() {
                boolean[] nulls = new boolean[times.length];
                for (int i = 0; i < times.length; ++i) {
                    nulls[i] = times[i] == null;
                }
                return nulls;
            }
        };
    }

    private void clearBufferForPositions(int offset, int... positions) {
        for (int position : positions) {
            target.init(buf, offset + position);
        }
    }

    @Test
    public void testFactory_1() {
        Assert.assertTrue(longLastAggregatorFactory.canVectorize(selectorFactory));
    }

    @Test
    public void testFactory_2_testMerged_2() {
        VectorAggregator vectorAggregator = longLastAggregatorFactory.factorizeVector(selectorFactory);
        Assert.assertNotNull(vectorAggregator);
        Assert.assertEquals(LongLastVectorAggregator.class, vectorAggregator.getClass());
    }
}

package org.apache.dubbo.common.config;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SystemConfigurationTest_Purified {

    private static SystemConfiguration sysConfig;

    private static final String MOCK_KEY = "mockKey";

    private static final String MOCK_STRING_VALUE = "mockValue";

    private static final Boolean MOCK_BOOL_VALUE = Boolean.FALSE;

    private static final Integer MOCK_INT_VALUE = Integer.MAX_VALUE;

    private static final Long MOCK_LONG_VALUE = Long.MIN_VALUE;

    private static final Short MOCK_SHORT_VALUE = Short.MIN_VALUE;

    private static final Float MOCK_FLOAT_VALUE = Float.MIN_VALUE;

    private static final Double MOCK_DOUBLE_VALUE = Double.MIN_VALUE;

    private static final Byte MOCK_BYTE_VALUE = Byte.MIN_VALUE;

    private static final String NOT_EXIST_KEY = "NOTEXIST";

    @BeforeEach
    public void init() {
        sysConfig = new SystemConfiguration();
    }

    @AfterEach
    public void clean() {
        if (null != System.getProperty(MOCK_KEY)) {
            System.clearProperty(MOCK_KEY);
        }
    }

    enum ConfigMock {

        MockOne, MockTwo
    }

    @Test
    void testGetSysProperty_1() {
        Assertions.assertNull(sysConfig.getInternalProperty(MOCK_KEY));
    }

    @Test
    void testGetSysProperty_2() {
        Assertions.assertFalse(sysConfig.containsKey(MOCK_KEY));
    }

    @Test
    void testGetSysProperty_3() {
        Assertions.assertNull(sysConfig.getString(MOCK_KEY));
    }

    @Test
    void testGetSysProperty_4() {
        Assertions.assertNull(sysConfig.getProperty(MOCK_KEY));
    }

    @Test
    void testGetSysProperty_5_testMerged_5() {
        System.setProperty(MOCK_KEY, MOCK_STRING_VALUE);
        Assertions.assertTrue(sysConfig.containsKey(MOCK_KEY));
        Assertions.assertEquals(MOCK_STRING_VALUE, sysConfig.getInternalProperty(MOCK_KEY));
        Assertions.assertEquals(MOCK_STRING_VALUE, sysConfig.getString(MOCK_KEY, MOCK_STRING_VALUE));
        Assertions.assertEquals(MOCK_STRING_VALUE, sysConfig.getProperty(MOCK_KEY, MOCK_STRING_VALUE));
    }

    @Test
    void testConvert_1() {
        Assertions.assertEquals(MOCK_STRING_VALUE, sysConfig.convert(String.class, NOT_EXIST_KEY, MOCK_STRING_VALUE));
    }

    @Test
    void testConvert_2_testMerged_2() {
        System.setProperty(MOCK_KEY, String.valueOf(MOCK_BOOL_VALUE));
        Assertions.assertEquals(MOCK_BOOL_VALUE, sysConfig.convert(Boolean.class, MOCK_KEY, null));
        System.setProperty(MOCK_KEY, String.valueOf(MOCK_STRING_VALUE));
        Assertions.assertEquals(MOCK_STRING_VALUE, sysConfig.convert(String.class, MOCK_KEY, null));
        System.setProperty(MOCK_KEY, String.valueOf(MOCK_INT_VALUE));
        Assertions.assertEquals(MOCK_INT_VALUE, sysConfig.convert(Integer.class, MOCK_KEY, null));
        System.setProperty(MOCK_KEY, String.valueOf(MOCK_LONG_VALUE));
        Assertions.assertEquals(MOCK_LONG_VALUE, sysConfig.convert(Long.class, MOCK_KEY, null));
        System.setProperty(MOCK_KEY, String.valueOf(MOCK_SHORT_VALUE));
        Assertions.assertEquals(MOCK_SHORT_VALUE, sysConfig.convert(Short.class, MOCK_KEY, null));
        System.setProperty(MOCK_KEY, String.valueOf(MOCK_FLOAT_VALUE));
        Assertions.assertEquals(MOCK_FLOAT_VALUE, sysConfig.convert(Float.class, MOCK_KEY, null));
        System.setProperty(MOCK_KEY, String.valueOf(MOCK_DOUBLE_VALUE));
        Assertions.assertEquals(MOCK_DOUBLE_VALUE, sysConfig.convert(Double.class, MOCK_KEY, null));
        System.setProperty(MOCK_KEY, String.valueOf(MOCK_BYTE_VALUE));
        Assertions.assertEquals(MOCK_BYTE_VALUE, sysConfig.convert(Byte.class, MOCK_KEY, null));
        System.setProperty(MOCK_KEY, String.valueOf(ConfigMock.MockOne));
        Assertions.assertEquals(ConfigMock.MockOne, sysConfig.convert(ConfigMock.class, MOCK_KEY, null));
    }
}

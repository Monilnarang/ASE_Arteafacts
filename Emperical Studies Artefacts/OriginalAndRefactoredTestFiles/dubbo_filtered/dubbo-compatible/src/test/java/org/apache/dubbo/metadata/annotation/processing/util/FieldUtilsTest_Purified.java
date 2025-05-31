package org.apache.dubbo.metadata.annotation.processing.util;

import org.apache.dubbo.metadata.annotation.processing.AbstractAnnotationProcessingTest;
import org.apache.dubbo.metadata.annotation.processing.model.Color;
import org.apache.dubbo.metadata.annotation.processing.model.Model;
import org.apache.dubbo.metadata.tools.TestServiceImpl;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PRIVATE;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;
import static org.apache.dubbo.metadata.annotation.processing.util.FieldUtils.findField;
import static org.apache.dubbo.metadata.annotation.processing.util.FieldUtils.getAllDeclaredFields;
import static org.apache.dubbo.metadata.annotation.processing.util.FieldUtils.getAllNonStaticFields;
import static org.apache.dubbo.metadata.annotation.processing.util.FieldUtils.getDeclaredField;
import static org.apache.dubbo.metadata.annotation.processing.util.FieldUtils.getDeclaredFields;
import static org.apache.dubbo.metadata.annotation.processing.util.FieldUtils.getNonStaticFields;
import static org.apache.dubbo.metadata.annotation.processing.util.FieldUtils.isEnumMemberField;
import static org.apache.dubbo.metadata.annotation.processing.util.FieldUtils.isField;
import static org.apache.dubbo.metadata.annotation.processing.util.FieldUtils.isNonStaticField;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FieldUtilsTest_Purified extends AbstractAnnotationProcessingTest {

    private TypeElement testType;

    @Override
    protected void addCompiledClasses(Set<Class<?>> classesToBeCompiled) {
    }

    @Override
    protected void beforeEach() {
        testType = getType(TestServiceImpl.class);
    }

    private void assertModelFields(List<VariableElement> fields) {
        assertEquals(6, fields.size());
        assertEquals("d", fields.get(1).getSimpleName().toString());
        assertEquals("tu", fields.get(2).getSimpleName().toString());
        assertEquals("str", fields.get(3).getSimpleName().toString());
        assertEquals("bi", fields.get(4).getSimpleName().toString());
        assertEquals("bd", fields.get(5).getSimpleName().toString());
    }

    private void assertModelAllFields(List<VariableElement> fields) {
        assertEquals(11, fields.size());
        assertEquals("f", fields.get(0).getSimpleName().toString());
        assertEquals("d", fields.get(1).getSimpleName().toString());
        assertEquals("tu", fields.get(2).getSimpleName().toString());
        assertEquals("str", fields.get(3).getSimpleName().toString());
        assertEquals("bi", fields.get(4).getSimpleName().toString());
        assertEquals("bd", fields.get(5).getSimpleName().toString());
        assertEquals("b", fields.get(6).getSimpleName().toString());
        assertEquals("s", fields.get(7).getSimpleName().toString());
        assertEquals("i", fields.get(8).getSimpleName().toString());
        assertEquals("l", fields.get(9).getSimpleName().toString());
        assertEquals("z", fields.get(10).getSimpleName().toString());
    }

    private void assertField(VariableElement field, String fieldName, Type fieldType) {
        assertEquals(fieldName, field.getSimpleName().toString());
        assertEquals(fieldType.getTypeName(), field.asType().toString());
    }

    @Test
    void testGetDeclaredField_1_testMerged_1() {
        TypeElement type = getType(Model.class);
        testGetDeclaredField(type, "f", float.class);
        testGetDeclaredField(type, "d", double.class);
        testGetDeclaredField(type, "tu", TimeUnit.class);
        testGetDeclaredField(type, "str", String.class);
        testGetDeclaredField(type, "bi", BigInteger.class);
        testGetDeclaredField(type, "bd", BigDecimal.class);
        assertNull(getDeclaredField(type, "b"));
        assertNull(getDeclaredField(type, "s"));
        assertNull(getDeclaredField(type, "i"));
        assertNull(getDeclaredField(type, "l"));
        assertNull(getDeclaredField(type, "z"));
    }

    @Test
    void testGetDeclaredField_6() {
        assertNull(getDeclaredField((Element) null, "z"));
    }

    @Test
    void testGetDeclaredField_7() {
        assertNull(getDeclaredField((TypeMirror) null, "z"));
    }

    @Test
    void testFindField_1() {
        assertNull(findField((Element) null, "f"));
    }

    @Test
    void testFindField_2() {
        assertNull(findField((Element) null, null));
    }

    @Test
    void testFindField_3() {
        assertNull(findField((TypeMirror) null, "f"));
    }

    @Test
    void testFindField_4() {
        assertNull(findField((TypeMirror) null, null));
    }

    @Test
    void testFindField_5_testMerged_5() {
        TypeElement type = getType(Model.class);
        testFindField(type, "f", float.class);
        testFindField(type, "d", double.class);
        testFindField(type, "tu", TimeUnit.class);
        testFindField(type, "str", String.class);
        testFindField(type, "bi", BigInteger.class);
        testFindField(type, "bd", BigDecimal.class);
        testFindField(type, "b", byte.class);
        testFindField(type, "s", short.class);
        testFindField(type, "i", int.class);
        testFindField(type, "l", long.class);
        testFindField(type, "z", boolean.class);
        assertNull(findField(type, null));
        assertNull(findField(type.asType(), null));
    }

    @Test
    void testIsEnumField_1_testMerged_1() {
        TypeElement type = getType(Color.class);
        VariableElement field = findField(type, "RED");
        assertTrue(isEnumMemberField(field));
        field = findField(type, "YELLOW");
        field = findField(type, "BLUE");
        type = getType(Model.class);
        field = findField(type, "f");
        assertFalse(isEnumMemberField(field));
    }

    @Test
    void testIsEnumField_5() {
        assertFalse(isEnumMemberField(null));
    }

    @Test
    void testIsField_1_testMerged_1() {
        TypeElement type = getType(Model.class);
        assertTrue(isField(findField(type, "f")));
        assertTrue(isField(findField(type, "f"), PRIVATE));
        type = getType(Color.class);
        assertTrue(isField(findField(type, "BLUE"), PUBLIC, STATIC, FINAL));
    }

    @Test
    void testIsField_4() {
        assertFalse(isField(null));
    }

    @Test
    void testIsField_5() {
        assertFalse(isField(null, PUBLIC, STATIC, FINAL));
    }
}

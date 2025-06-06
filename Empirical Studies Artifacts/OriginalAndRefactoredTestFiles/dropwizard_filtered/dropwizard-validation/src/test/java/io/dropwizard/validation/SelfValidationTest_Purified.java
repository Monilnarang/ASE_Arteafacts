package io.dropwizard.validation;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.AppenderBase;
import io.dropwizard.validation.selfvalidating.SelfValidating;
import io.dropwizard.validation.selfvalidating.SelfValidatingValidator;
import io.dropwizard.validation.selfvalidating.SelfValidation;
import io.dropwizard.validation.selfvalidating.ViolationCollector;
import jakarta.validation.Validator;
import org.hibernate.validator.messageinterpolation.AbstractMessageInterpolator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

@Execution(ExecutionMode.SAME_THREAD)
class SelfValidationTest_Purified {

    private static final String FAILED = "failed";

    private static final String FAILED_RESULT = " " + FAILED;

    private static class ListAppender extends AppenderBase<ILoggingEvent> {

        private final List<ILoggingEvent> events = new ArrayList<>();

        private final Level level = Level.INFO;

        public List<ILoggingEvent> getAllLoggingEvents() {
            return events;
        }

        public void clearAllLoggingEvents() {
            events.clear();
        }

        @Override
        protected void append(ILoggingEvent iLoggingEvent) {
            if (iLoggingEvent.getLevel().isGreaterOrEqual(level)) {
                events.add(iLoggingEvent);
            }
        }
    }

    private static final ListAppender listAppender = new ListAppender();

    private final Logger logger = (Logger) org.slf4j.LoggerFactory.getLogger(SelfValidationTest.class);

    @BeforeAll
    static void setUp() {
        Logger selfValidatingValidatorLogger = (Logger) LoggerFactory.getLogger(SelfValidatingValidator.class);
        selfValidatingValidatorLogger.setAdditive(false);
        selfValidatingValidatorLogger.setLevel(Level.INFO);
        listAppender.setContext(selfValidatingValidatorLogger.getLoggerContext());
        listAppender.start();
        selfValidatingValidatorLogger.addAppender(listAppender);
    }

    @AfterEach
    @BeforeEach
    public void clearAllLoggers() {
        listAppender.clearAllLoggingEvents();
    }

    @SelfValidating
    public static class FailingExample {

        @SuppressWarnings("unused")
        @SelfValidation
        public void validateFail(ViolationCollector col) {
            col.addViolation(FAILED);
        }
    }

    public static class SubclassExample extends FailingExample {

        @SuppressWarnings("unused")
        @SelfValidation
        public void subValidateFail(ViolationCollector col) {
            col.addViolation(FAILED + "subclass");
        }
    }

    @SelfValidating
    public static class AnnotatedSubclassExample extends FailingExample {

        @SuppressWarnings("unused")
        @SelfValidation
        public void subValidateFail(ViolationCollector col) {
            col.addViolation(FAILED + "subclass");
        }
    }

    public static class OverridingExample extends FailingExample {

        @Override
        public void validateFail(ViolationCollector col) {
        }
    }

    @SelfValidating
    public static class DirectContextExample {

        @SuppressWarnings("unused")
        @SelfValidation
        public void validateFail(ViolationCollector col) {
            col.getContext().buildConstraintViolationWithTemplate(FAILED).addConstraintViolation();
            col.setViolationOccurred(true);
        }
    }

    @SelfValidating
    public static class CorrectExample {

        @SuppressWarnings("unused")
        @SelfValidation
        public void validateCorrect(ViolationCollector col) {
        }
    }

    @SelfValidating
    public static class InvalidExample {

        @SuppressWarnings("unused")
        @SelfValidation
        public void validateCorrect(ViolationCollector col) {
        }

        @SuppressWarnings("unused")
        @SelfValidation
        public void validateFailAdditionalParameters(ViolationCollector col, int a) {
            col.addViolation(FAILED);
        }

        @SelfValidation
        public boolean validateFailReturn(ViolationCollector col) {
            col.addViolation(FAILED);
            return true;
        }

        @SelfValidation
        private void validateFailPrivate(ViolationCollector col) {
            col.addViolation(FAILED);
        }
    }

    @SelfValidating
    public static class ComplexExample {

        @SuppressWarnings("unused")
        @SelfValidation
        public void validateFail1(ViolationCollector col) {
            col.addViolation(FAILED + "1");
        }

        @SuppressWarnings("unused")
        @SelfValidation
        public void validateFail2(ViolationCollector col) {
            col.addViolation("p2", FAILED);
        }

        @SuppressWarnings("unused")
        @SelfValidation
        public void validateFail3(ViolationCollector col) {
            col.addViolation("p", 3, FAILED);
        }

        @SuppressWarnings("unused")
        @SelfValidation
        public void validateFail4(ViolationCollector col) {
            col.addViolation("p", "four", FAILED);
        }

        @SuppressWarnings("unused")
        @SelfValidation
        public void validateCorrect(ViolationCollector col) {
        }
    }

    @SelfValidating
    public static class NoValidations {
    }

    @SelfValidating
    public static class InjectionExample {

        @SuppressWarnings("unused")
        @SelfValidation
        public void validateFail(ViolationCollector col) {
            col.addViolation("${'value'}");
            col.addViolation("$\\A{1+1}");
            col.addViolation("{value}", Collections.singletonMap("value", "TEST"));
            col.addViolation("${'property'}", "${'value'}");
            col.addViolation("${'property'}", 1, "${'value'}");
            col.addViolation("${'property'}", "${'key'}", "${'value'}");
        }
    }

    @SelfValidating
    public static class MessageParametersExample {

        @SuppressWarnings("unused")
        @SelfValidation
        public void validateFail(ViolationCollector col) {
            col.addViolation("{1+1}");
            col.addViolation("{value}", Map.of("value", "VALUE"));
            col.addViolation("No parameter", Map.of("value", "VALUE"));
            col.addViolation("{value} {unsetParameter}", Map.of("value", "VALUE"));
            col.addViolation("{value", Map.of("value", "VALUE"));
            col.addViolation("value}", Map.of("value", "VALUE"));
            col.addViolation("{  value  }", Map.of("value", "VALUE"));
            col.addViolation("Mixed ${'value'} {value}", Map.of("value", "VALUE"));
            col.addViolation("Nested {value}", Map.of("value", "${'nested'}"));
            col.addViolation("{property}", "{value}", Map.of("property", "PROPERTY", "value", "VALUE"));
            col.addViolation("{property}", 1, "{value}", Map.of("property", "PROPERTY", "value", "VALUE"));
            col.addViolation("{property}", "{key}", "{value}", Map.of("property", "PROPERTY", "key", "KEY", "value", "VALUE"));
        }
    }

    private final Validator validator = BaseValidator.newValidator();

    @Test
    void failingExample_1() {
        assertThat(ConstraintViolations.format(validator.validate(new FailingExample()))).containsExactlyInAnyOrder(FAILED_RESULT);
    }

    @Test
    void failingExample_2() {
        assertThat(listAppender.getAllLoggingEvents()).isEmpty();
    }

    @Test
    void subClassExample_1() {
        assertThat(ConstraintViolations.format(validator.validate(new SubclassExample()))).containsExactlyInAnyOrder(FAILED_RESULT, FAILED_RESULT + "subclass");
    }

    @Test
    void subClassExample_2() {
        assertThat(listAppender.getAllLoggingEvents()).isEmpty();
    }

    @Test
    void annotatedSubClassExample_1() {
        assertThat(ConstraintViolations.format(validator.validate(new AnnotatedSubclassExample()))).containsExactlyInAnyOrder(FAILED_RESULT, FAILED_RESULT + "subclass");
    }

    @Test
    void annotatedSubClassExample_2() {
        assertThat(listAppender.getAllLoggingEvents()).isEmpty();
    }

    @Test
    void overridingSubClassExample_1() {
        assertThat(ConstraintViolations.format(validator.validate(new OverridingExample()))).isEmpty();
    }

    @Test
    void overridingSubClassExample_2() {
        assertThat(listAppender.getAllLoggingEvents()).isEmpty();
    }

    @Test
    void correctExample_1() {
        assertThat(ConstraintViolations.format(validator.validate(new CorrectExample()))).isEmpty();
    }

    @Test
    void correctExample_2() {
        assertThat(listAppender.getAllLoggingEvents()).isEmpty();
    }

    @Test
    void multipleTestingOfSameClass_1() {
        assertThat(ConstraintViolations.format(validator.validate(new CorrectExample()))).isEmpty();
    }

    @Test
    void multipleTestingOfSameClass_2() {
        assertThat(ConstraintViolations.format(validator.validate(new CorrectExample()))).isEmpty();
    }

    @Test
    void multipleTestingOfSameClass_3() {
        assertThat(listAppender.getAllLoggingEvents()).isEmpty();
    }

    @Test
    void testDirectContextUsage_1() {
        assertThat(ConstraintViolations.format(validator.validate(new DirectContextExample()))).containsExactlyInAnyOrder(FAILED_RESULT);
    }

    @Test
    void testDirectContextUsage_2() {
        assertThat(listAppender.getAllLoggingEvents()).isEmpty();
    }

    @Test
    void complexExample_1() {
        assertThat(ConstraintViolations.format(validator.validate(new ComplexExample()))).containsExactly(" failed1", "p2 failed", "p[3] failed", "p[four] failed");
    }

    @Test
    void complexExample_2() {
        assertThat(listAppender.getAllLoggingEvents()).isEmpty();
    }

    @Test
    void violationMessagesAreEscapedByDefault_1() {
        assertThat(ConstraintViolations.format(validator.validate(new InjectionExample()))).containsExactly(" $\\A{1+1}", " ${'value'}", " TEST", "${'property'} ${'value'}", "${'property'}[${'key'}] ${'value'}", "${'property'}[1] ${'value'}");
    }

    @Test
    void violationMessagesAreEscapedByDefault_2() {
        assertThat(listAppender.getAllLoggingEvents()).isEmpty();
    }
}

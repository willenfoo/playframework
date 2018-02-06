package org.apache.playframework.exception;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.playframework.enums.ErrorCode;
import org.springframework.util.ObjectUtils;

import java.util.Collection;
import java.util.Map;

/**
 * <p>
 * REST 业务断言<br>
 * 参考：org.junit.Assert
 * </p>
 *
 * @author hubin
 * @since 2017-02-17
 */
public class RestAssert {

    protected RestAssert() {
        // to do noting
    }

    /**
     * 大于O
     *
     * @param num
     * @param errorCode
     */
    public static void gtZero(Integer num, ErrorCode errorCode) {
        if (num == null || num <= 0) {
            fail(errorCode);
        }
    }

    /**
     * 大于等于O
     *
     * @param num
     * @param errorCode
     */
    public static void geZero(Integer num, ErrorCode errorCode) {
        if (num == null || num < 0) {
            fail(errorCode);
        }
    }

    /**
     * num1大于num2
     *
     * @param num1
     * @param num2
     * @param errorCode
     */
    public static void gt(Integer num1, Integer num2, ErrorCode errorCode) {
        if (num1 <= num2) {
            fail(errorCode);
        }
    }

    /**
     * num1大于等于num2
     *
     * @param num1
     * @param num2
     * @param errorCode
     */
    public static void ge(Integer num1, Integer num2, ErrorCode errorCode) {
        if (num1 < num2) {
            fail(errorCode);
        }
    }

    /**
     * obj1 eq obj2
     *
     * @param obj1
     * @param obj2
     * @param errorCode
     */
    public static void eq(Object obj1, Object obj2, ErrorCode errorCode) {
        if (!obj1.equals(obj2)) {
            fail(errorCode);
        }
    }

    public static void isTrue(boolean condition, ErrorCode errorCode) {
        if (!condition) {
            fail(errorCode);
        }
    }

    public static void isFalse(boolean condition, ErrorCode errorCode) {
        if (condition) {
            fail(errorCode);
        }
    }

    public static void isNull(ErrorCode errorCode, Object... conditions) {
        if (!ObjectUtils.isEmpty(conditions)) {
            fail(errorCode);
        }
    }

    public static void notNull(ErrorCode errorCode, Object... conditions) {
        if (ObjectUtils.isEmpty(conditions)) {
            fail(errorCode);
        }
    }

    /**
     * <p>
     * 失败结果
     * </p>
     *
     * @param errorCode 异常错误码
     * @return
     */
    public static void fail(ErrorCode errorCode) {
        throw new RestServiceException(errorCode);
    }

    public static void notEmpty(Object[] array, ErrorCode ErrorCode) {
        if (ObjectUtils.isEmpty(array)) {
            fail(ErrorCode);
        }
    }

    /**
     * Assert that an array has no null elements. Note: Does not complain if the
     * array is empty!
     * <p>
     * <pre class="code">
     * Assert.noNullElements(array, &quot;The array must have non-null elements&quot;);
     * </pre>
     *
     * @param array         the array to check
     * @param ErrorCode the exception message to use if the assertion fails
     * @throws RestServiceException if the object array contains a {@code null} element
     */
    public static void noNullElements(Object[] array, ErrorCode ErrorCode) {
        if (array != null) {
            for (Object element : array) {
                if (element == null) {
                    fail(ErrorCode);
                }
            }
        }
    }

    /**
     * Assert that a collection has elements; that is, it must not be
     * {@code null} and must have at least one element.
     * <p>
     * <pre class="code">
     * Assert.notEmpty(collection, &quot;Collection must have elements&quot;);
     * </pre>
     *
     * @param collection    the collection to check
     * @param ErrorCode the exception message to use if the assertion fails
     * @throws RestServiceException if the collection is {@code null} or has no elements
     */
    public static void notEmpty(Collection<?> collection, ErrorCode ErrorCode) {
        if (CollectionUtils.isNotEmpty(collection)) {
            fail(ErrorCode);
        }
    }

    /**
     * Assert that a Map has entries; that is, it must not be {@code null} and
     * must have at least one entry.
     * <p>
     * <pre class="code">
     * Assert.notEmpty(map, &quot;Map must have entries&quot;);
     * </pre>
     *
     * @param map           the map to check
     * @param ErrorCode the exception message to use if the assertion fails
     * @throws RestServiceException if the map is {@code null} or has no entries
     */
    public static void notEmpty(Map<?, ?> map, ErrorCode ErrorCode) {
        if (MapUtils.isEmpty(map)) {
            fail(ErrorCode);
        }
    }

    /**
     * Assert that the provided object is an instance of the provided class.
     * <p>
     * <pre class="code">
     * Assert.instanceOf(Foo.class, foo);
     * </pre>
     *
     * @param type          the type to check against
     * @param obj           the object to check
     * @param ErrorCode a message which will be prepended to the message produced by
     *                      the function itself, and which may be used to provide context.
     *                      It should normally end in ":" or "." so that the generated
     *                      message looks OK when appended to it.
     * @throws RestServiceException if the object is not an instance of clazz
     * @see Class#isInstance
     */
    public static void isInstanceOf(Class<?> type, Object obj, ErrorCode ErrorCode) {
        notNull(ErrorCode, type);
        if (!type.isInstance(obj)) {
            fail(ErrorCode);
        }
    }

    /**
     * Assert that {@code superType.isAssignableFrom(subType)} is {@code true}.
     * <p>
     * <pre class="code">
     * Assert.isAssignable(Number.class, myClass);
     * </pre>
     *
     * @param superType     the super type to check against
     * @param subType       the sub type to check
     * @param ErrorCode a message which will be prepended to the message produced by
     *                      the function itself, and which may be used to provide context.
     *                      It should normally end in ":" or "." so that the generated
     *                      message looks OK when appended to it.
     * @throws RestServiceException if the classes are not assignable
     */
    public static void isAssignable(Class<?> superType, Class<?> subType, ErrorCode ErrorCode) {
        notNull(ErrorCode, superType);
        if (subType == null || !superType.isAssignableFrom(subType)) {
            fail(ErrorCode);
        }
    }

    /**
     * Assert a boolean expression, throwing {@code IllegalStateException} if
     * the test result is {@code false}. Call isTrue if you wish to throw
     * AppIllegalArgumentException on an assertion failure.
     * <p>
     * <pre class="code">
     * Assert.state(id == null, &quot;The id property must not already be initialized&quot;);
     * </pre>
     *
     * @param expression    a boolean expression
     * @param ErrorCode the exception message to use if the assertion fails
     * @throws IllegalStateException if expression is {@code false}
     */
    public static void state(boolean expression, ErrorCode ErrorCode) {
        if (!expression) {
            fail(ErrorCode);
        }
    }

}
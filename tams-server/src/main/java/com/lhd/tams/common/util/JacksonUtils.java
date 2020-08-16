package com.lhd.tams.common.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.lhd.tams.common.consts.CommonConsts;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * json 工具
 *
 * @author lhd
 */
public class JacksonUtils {

    private static final Logger log = LoggerFactory.getLogger(JacksonUtils.class);

    public final static Map<Class, JsonSerializer> SERIALIZER_MAP;
    public final static Map<Class, JsonDeserializer> DESERIALIZER_MAP;
    static {
        SERIALIZER_MAP = new LinkedHashMap<>();
        SERIALIZER_MAP.put(Long.class, ToStringSerializer.instance);
        SERIALIZER_MAP.put(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(CommonConsts.TIME_FORMATTER)));
        SERIALIZER_MAP.put(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(CommonConsts.DATE_FORMATTER)));
        SERIALIZER_MAP.put(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(CommonConsts.DATETIME_FORMATTER)));

        DESERIALIZER_MAP = new LinkedHashMap<>();
        DESERIALIZER_MAP.put(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(CommonConsts.TIME_FORMATTER)));
        DESERIALIZER_MAP.put(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(CommonConsts.DATE_FORMATTER)));
        DESERIALIZER_MAP.put(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(CommonConsts.DATETIME_FORMATTER)));
    }

    private final static ObjectMapper OBJECT_MAPPER;
    static {
        OBJECT_MAPPER = new ObjectMapper();

        SimpleModule simpleModule = new SimpleModule();
        SERIALIZER_MAP.forEach(simpleModule::addSerializer);
        DESERIALIZER_MAP.forEach(simpleModule::addDeserializer);
        OBJECT_MAPPER.registerModule(simpleModule);
    }

    /**
     * 对象转换字符串
     * @param obj
     * @return
     */
    public static String toStr(Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug("转换为字符串出错", e);
            }
        }
        return "";
    }

    /**
     * 字符串转换对象
     * @param str
     * @return
     */
    public static Object toObj(String str) {
        return toObj(str, Object.class);
    }

    /**
     * 字符串转换指定类型
     * @param str
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T toObj(String str, Class<T> clazz) {

        if (StringUtils.isEmpty(str) || clazz == null) {
            return null;
        }

        try {
            return OBJECT_MAPPER.readValue(str, clazz);
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug("转换为指定类型出错，str: {} class: {}", str, clazz, e);
            }
        }
        return null;
    }

    public static <T> T toObj(String str, TypeReference<T> typeReference) {

        if (StringUtils.isEmpty(str) || typeReference == null) {
            return null;
        }

        try {
            return OBJECT_MAPPER.readValue(str, typeReference);
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug("转换为指定范型出错，str: {}", str, e);
            }
        }
        return null;
    }
}

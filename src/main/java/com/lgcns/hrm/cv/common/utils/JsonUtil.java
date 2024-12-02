package com.lgcns.hrm.cv.common.utils;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.type.CollectionLikeType;
import com.fasterxml.jackson.databind.type.MapType;
import com.lgcns.hrm.cv.common.function.CheckedConsumer;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.*;
public class JsonUtil {

    @Nullable
    public static String toJson(@Nullable Object object) {
        if (object == null) {
            return null;
        }
        try {
            return getInstance().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw Exceptions.unchecked(e);
        }
    }
    @Nullable
    public static String toJsonWithView(@Nullable Object object, Class<?> serializationView) {
        if (object == null) {
            return null;
        }
        try {
            return getInstance()
                    .writerWithView(serializationView)
                    .writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw Exceptions.unchecked(e);
        }
    }

    @Nullable
    public static String toPrettyJson(@Nullable Object object) {
        if (object == null) {
            return null;
        }
        try {
            return getInstance()
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw Exceptions.unchecked(e);
        }
    }

    public static byte[] toJsonAsBytes(@Nullable Object object) {
        if (object == null) {
            return new byte[0];
        }
        try {
            return getInstance().writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            throw Exceptions.unchecked(e);
        }
    }
    public static byte[] toJsonAsBytesWithView(@Nullable Object object, Class<?> serializationView) {
        if (object == null) {
            return new byte[0];
        }
        try {
            return getInstance()
                    .writerWithView(serializationView)
                    .writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            throw Exceptions.unchecked(e);
        }
    }

    public static JsonNode readTree(String jsonString) {
        try {
            return getInstance().readTree(Objects.requireNonNull(jsonString, "jsonString is null"));
        } catch (IOException e) {
            throw Exceptions.unchecked(e);
        }
    }

    public static JsonNode readTree(InputStream in) {
        try {
            return getInstance().readTree(Objects.requireNonNull(in, "InputStream in is null"));
        } catch (IOException e) {
            throw Exceptions.unchecked(e);
        }
    }

    public static JsonNode readTree(Reader reader) {
        try {
            return getInstance().readTree(Objects.requireNonNull(reader, "Reader in is null"));
        } catch (IOException e) {
            throw Exceptions.unchecked(e);
        }
    }

    public static JsonNode readTree(byte[] content) {
        try {
            return getInstance().readTree(Objects.requireNonNull(content, "byte[] content is null"));
        } catch (IOException e) {
            throw Exceptions.unchecked(e);
        }
    }

    public static JsonNode readTree(JsonParser jsonParser) {
        try {
            return getInstance().readTree(Objects.requireNonNull(jsonParser, "jsonParser is null"));
        } catch (IOException e) {
            throw Exceptions.unchecked(e);
        }
    }

    @Nullable
    public static <T> T readValue(@Nullable byte[] content, Class<T> valueType) {
        if (ObjectUtils.isEmpty(content)) {
            return null;
        }
        try {
            return getInstance().readValue(content, valueType);
        } catch (IOException e) {
            throw Exceptions.unchecked(e);
        }
    }

    @Nullable
    public static <T> T readValue(@Nullable String jsonString, Class<T> valueType) {
        if (StringUtil.isBlank(jsonString)) {
            return null;
        }
        try {
            return getInstance().readValue(jsonString, valueType);
        } catch (IOException e) {
            throw Exceptions.unchecked(e);
        }
    }

    @Nullable
    public static <T> T readValue(@Nullable InputStream in, Class<T> valueType) {
        if (in == null) {
            return null;
        }
        try {
            return getInstance().readValue(in, valueType);
        } catch (IOException e) {
            throw Exceptions.unchecked(e);
        }
    }

    @Nullable
    public static <T> T readValue(@Nullable Reader reader, Class<T> valueType) {
        if (reader == null) {
            return null;
        }
        try {
            return getInstance().readValue(reader, valueType);
        } catch (IOException e) {
            throw Exceptions.unchecked(e);
        }
    }

    @Nullable
    public static <T> T readValue(@Nullable byte[] content, TypeReference<T> typeReference) {
        if (ObjectUtils.isEmpty(content)) {
            return null;
        }
        try {
            return getInstance().readValue(content, typeReference);
        } catch (IOException e) {
            throw Exceptions.unchecked(e);
        }
    }

    @Nullable
    public static <T> T readValue(@Nullable String jsonString, TypeReference<T> typeReference) {
        if (StringUtil.isBlank(jsonString)) {
            return null;
        }
        try {
            return getInstance().readValue(jsonString, typeReference);
        } catch (IOException e) {
            throw Exceptions.unchecked(e);
        }
    }

    @Nullable
    public static <T> T readValue(@Nullable InputStream in, TypeReference<T> typeReference) {
        if (in == null) {
            return null;
        }
        try {
            return getInstance().readValue(in, typeReference);
        } catch (IOException e) {
            throw Exceptions.unchecked(e);
        }
    }

    @Nullable
    public static <T> T readValue(@Nullable Reader reader, TypeReference<T> typeReference) {
        if (reader == null) {
            return null;
        }
        try {
            return getInstance().readValue(reader, typeReference);
        } catch (IOException e) {
            throw Exceptions.unchecked(e);
        }
    }

    @Nullable
    public static <T> T readValue(@Nullable byte[] content, JavaType javaType) {
        if (ObjectUtils.isEmpty(content)) {
            return null;
        }
        try {
            return getInstance().readValue(content, javaType);
        } catch (IOException e) {
            throw Exceptions.unchecked(e);
        }
    }

    @Nullable
    public static <T> T readValue(@Nullable String jsonString, JavaType javaType) {
        if (StringUtil.isBlank(jsonString)) {
            return null;
        }
        try {
            return getInstance().readValue(jsonString, javaType);
        } catch (IOException e) {
            throw Exceptions.unchecked(e);
        }
    }

    @Nullable
    public static <T> T readValue(@Nullable InputStream in, JavaType javaType) {
        if (in == null) {
            return null;
        }
        try {
            return getInstance().readValue(in, javaType);
        } catch (IOException e) {
            throw Exceptions.unchecked(e);
        }
    }

    @Nullable
    public static <T> T readValue(@Nullable Reader reader, JavaType javaType) {
        if (reader == null) {
            return null;
        }
        try {
            return getInstance().readValue(reader, javaType);
        } catch (IOException e) {
            throw Exceptions.unchecked(e);
        }
    }

    public static JavaType getType(Class<?> clazz) {
        return getInstance().getTypeFactory().constructType(clazz);
    }

    public static MapType getMapType(Class<?> valueClass) {
        return getMapType(String.class, valueClass);
    }

    public static MapType getMapType(Class<?> keyClass, Class<?> valueClass) {
        return getInstance().getTypeFactory().constructMapType(Map.class, keyClass, valueClass);
    }

    public static CollectionLikeType getListType(Class<?> elementClass) {
        return getInstance().getTypeFactory().constructCollectionLikeType(List.class, elementClass);
    }

    public static JavaType getParametricType(Class<?> parametrized, Class<?>... parameterClasses) {
        return getInstance().getTypeFactory().constructParametricType(parametrized, parameterClasses);
    }

    public static JavaType getParametricType(Class<?> parametrized, JavaType... parameterTypes) {
        return getInstance().getTypeFactory().constructParametricType(parametrized, parameterTypes);
    }

    public static <T> List<T> readList(@Nullable byte[] content, Class<T> elementClass) {
        if (ObjectUtils.isEmpty(content)) {
            return Collections.emptyList();
        }
        try {
            return getInstance().readValue(content, getListType(elementClass));
        } catch (IOException e) {
            throw Exceptions.unchecked(e);
        }
    }

    public static <T> List<T> readList(@Nullable InputStream content, Class<T> elementClass) {
        if (content == null) {
            return Collections.emptyList();
        }
        try {
            return getInstance().readValue(content, getListType(elementClass));
        } catch (IOException e) {
            throw Exceptions.unchecked(e);
        }
    }

    public static <T> List<T> readList(@Nullable Reader reader, Class<T> elementClass) {
        if (reader == null) {
            return Collections.emptyList();
        }
        try {
            return getInstance().readValue(reader, getListType(elementClass));
        } catch (IOException e) {
            throw Exceptions.unchecked(e);
        }
    }

    public static <T> List<T> readList(@Nullable String content, Class<T> elementClass) {
        if (StringUtil.isBlank(content)) {
            return Collections.emptyList();
        }
        try {
            return getInstance().readValue(content, getListType(elementClass));
        } catch (IOException e) {
            throw Exceptions.unchecked(e);
        }
    }

    public static Map<String, Object> readMap(@Nullable byte[] content) {
        return readMap(content, Object.class);
    }

    public static Map<String, Object> readMap(@Nullable InputStream content) {
        return readMap(content, Object.class);
    }

    public static Map<String, Object> readMap(@Nullable Reader reader) {
        return readMap(reader, Object.class);
    }

    public static Map<String, Object> readMap(@Nullable String content) {
        return readMap(content, Object.class);
    }

    public static <V> Map<String, V> readMap(@Nullable byte[] content, Class<?> valueClass) {
        return readMap(content, String.class, valueClass);
    }

    public static <V> Map<String, V> readMap(@Nullable InputStream content, Class<?> valueClass) {
        return readMap(content, String.class, valueClass);
    }

    public static <V> Map<String, V> readMap(@Nullable Reader reader, Class<?> valueClass) {
        return readMap(reader, String.class, valueClass);
    }

    public static <V> Map<String, V> readMap(@Nullable String content, Class<?> valueClass) {
        return readMap(content, String.class, valueClass);
    }

    public static <K, V> Map<K, V> readMap(@Nullable byte[] content, Class<?> keyClass, Class<?> valueClass) {
        if (ObjectUtils.isEmpty(content)) {
            return Collections.emptyMap();
        }
        try {
            return getInstance().readValue(content, getMapType(keyClass, valueClass));
        } catch (IOException e) {
            throw Exceptions.unchecked(e);
        }
    }

    public static <K, V> Map<K, V> readMap(@Nullable InputStream content, Class<?> keyClass, Class<?> valueClass) {
        if (content == null) {
            return Collections.emptyMap();
        }
        try {
            return getInstance().readValue(content, getMapType(keyClass, valueClass));
        } catch (IOException e) {
            throw Exceptions.unchecked(e);
        }
    }

    public static <K, V> Map<K, V> readMap(@Nullable Reader reader, Class<?> keyClass, Class<?> valueClass) {
        if (reader == null) {
            return Collections.emptyMap();
        }
        try {
            return getInstance().readValue(reader, getMapType(keyClass, valueClass));
        } catch (IOException e) {
            throw Exceptions.unchecked(e);
        }
    }

    public static <K, V> Map<K, V> readMap(@Nullable String content, Class<?> keyClass, Class<?> valueClass) {
        if (StringUtil.isBlank(content)) {
            return Collections.emptyMap();
        }
        try {
            return getInstance().readValue(content, getMapType(keyClass, valueClass));
        } catch (IOException e) {
            throw Exceptions.unchecked(e);
        }
    }

    public static <T> T convertValue(Object fromValue, Class<T> toValueType) {
        return getInstance().convertValue(fromValue, toValueType);
    }

    public static <T> T convertValue(Object fromValue, JavaType toValueType) {
        return getInstance().convertValue(fromValue, toValueType);
    }

    public static <T> T convertValue(Object fromValue, TypeReference<T> toValueTypeRef) {
        return getInstance().convertValue(fromValue, toValueTypeRef);
    }

    public static <T> T treeToValue(TreeNode treeNode, Class<T> valueType) {
        try {
            return getInstance().treeToValue(treeNode, valueType);
        } catch (JsonProcessingException e) {
            throw Exceptions.unchecked(e);
        }
    }

    public static <T extends JsonNode> T valueToTree(@Nullable Object fromValue) {
        return getInstance().valueToTree(fromValue);
    }

    public static boolean canSerialize(@Nullable Object value) {
        if (value == null) {
            return true;
        }
        return getInstance().canSerialize(value.getClass());
    }

    public static boolean canDeserialize(JavaType type) {
        return getInstance().canDeserialize(type);
    }

    public static boolean isValidJson(String jsonString) {
        return isValidJson(mapper -> mapper.readTree(jsonString));
    }

    public static boolean isValidJson(byte[] content) {
        return isValidJson(mapper -> mapper.readTree(content));
    }

    public static boolean isValidJson(InputStream input) {
        return isValidJson(mapper -> mapper.readTree(input));
    }

    public static boolean isValidJson(Reader reader) {
        return isValidJson(mapper -> mapper.readTree(reader));
    }

    public static boolean isValidJson(JsonParser jsonParser) {
        return isValidJson(mapper -> mapper.readTree(jsonParser));
    }

    public static boolean isValidJson(CheckedConsumer<ObjectMapper> consumer) {
        ObjectMapper mapper = getInstance().copy();
        mapper.enable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS);
        mapper.enable(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY);
        try {
            consumer.accept(mapper);
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    public static ObjectNode createObjectNode() {
        return getInstance().createObjectNode();
    }

    public static ArrayNode createArrayNode() {
        return getInstance().createArrayNode();
    }

    public static ObjectMapper getInstance() {
        return JacksonHolder.INSTANCE;
    }

    private static class JacksonHolder {
        private static final ObjectMapper INSTANCE = new ObjectMapper(JsonFactory.builder()
                .configure(JsonReadFeature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true)

                .configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS, true)
                .build());

        static {
            INSTANCE.setLocale(new Locale("vn"));
            INSTANCE.setDateFormat(new SimpleDateFormat(DateUtil.PATTERN_DATETIME, Locale.CHINA));

            INSTANCE.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

            INSTANCE.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            INSTANCE.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            INSTANCE.setTimeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));
            INSTANCE.findAndRegisterModules();
        }
    }
}

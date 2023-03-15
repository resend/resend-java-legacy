package com.resend.sdk.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class QueryParameters {
    public static <T extends Object> List<NameValuePair> parseQueryParams(Class<T> type, T queryParams,
            Map<String, Map<String, Map<String, Object>>> globals) throws Exception {
        List<NameValuePair> allParams = new ArrayList<>();

        Field[] fields = type.getFields();

        for (Field field : fields) {
            Object value = queryParams != null ? field.get(queryParams) : null;
            value = Utils.popualteGlobal(value, field.getName(), "queryParam", globals);
            if (value == null) {
                continue;
            }

            QueryParamsMetadata queryParamsMetadata = QueryParamsMetadata.parse(field);
            if (queryParamsMetadata == null) {
                continue;
            }

            if (queryParamsMetadata.serialization != null && !queryParamsMetadata.serialization.isBlank()) {
                List<NameValuePair> params = parseSerializedParams(queryParamsMetadata, value);
                allParams.addAll(params);
            } else {
                switch (queryParamsMetadata.style) {
                    case "form":
                        List<NameValuePair> params = parseFormParams(queryParamsMetadata, value);
                        allParams.addAll(params);
                        break;
                    case "deepObject":
                        List<NameValuePair> deepObjectParams = parseDeepObjectParams(queryParamsMetadata, value);
                        allParams.addAll(deepObjectParams);
                        break;
                }
            }
        }

        return allParams;
    }

    private static List<NameValuePair> parseSerializedParams(QueryParamsMetadata queryParamsMetadata, Object value)
            throws JsonProcessingException {
        List<NameValuePair> params = new ArrayList<>();

        switch (queryParamsMetadata.serialization) {
            case "json":
                ObjectMapper mapper = JSON.getMapper();
                String json = mapper.writeValueAsString(value);

                params.add(new BasicNameValuePair(queryParamsMetadata.name, json));
                break;
        }

        return params;
    }

    private static List<NameValuePair> parseFormParams(QueryParamsMetadata queryParamsMetadata, Object value)
            throws IllegalArgumentException, IllegalAccessException {
        List<NameValuePair> params = new ArrayList<>();

        switch (Types.getType(value.getClass())) {
            case ARRAY: {
                Object[] array = (Object[]) value;

                List<String> values = new ArrayList<>();
                List<String> items = new ArrayList<>();

                for (Object v : array) {
                    if (queryParamsMetadata.explode) {
                        values.add(Utils.valToString(v));
                    } else {
                        items.add(Utils.valToString(v));
                    }
                }

                if (items.size() > 0) {
                    values.add(String.join(",", items));
                }

                params.addAll(values.stream().map(v -> new BasicNameValuePair(queryParamsMetadata.name, v))
                        .collect(Collectors.toList()));
                break;
            }
            case MAP: {
                Map<?, ?> map = (Map<?, ?>) value;

                List<String> items = new ArrayList<>();

                for (Map.Entry<?, ?> entry : map.entrySet()) {
                    String key = Utils.valToString(entry.getKey());
                    String val = Utils.valToString(entry.getValue());

                    if (queryParamsMetadata.explode) {
                        params.add(new BasicNameValuePair(key, val));
                    } else {
                        items.add(String.format("%s,%s", key, val));
                    }
                }

                if (items.size() > 0) {
                    params.add(new BasicNameValuePair(queryParamsMetadata.name, String.join(",", items)));
                }
                break;
            }
            case OBJECT: {
                Field[] fields = value.getClass().getFields();

                List<String> items = new ArrayList<>();

                for (Field field : fields) {
                    Object val = field.get(value);
                    if (val == null) {
                        continue;
                    }

                    QueryParamsMetadata metadata = QueryParamsMetadata.parse(field);
                    if (metadata == null) {
                        continue;
                    }

                    if (queryParamsMetadata.explode) {
                        params.add(new BasicNameValuePair(metadata.name, Utils.valToString(val)));
                    } else {
                        items.add(String.format("%s,%s", metadata.name, Utils.valToString(val)));
                    }
                }

                if (items.size() > 0) {
                    params.add(new BasicNameValuePair(queryParamsMetadata.name, String.join(",", items)));
                }
                break;
            }
            default:
                params.add(new BasicNameValuePair(queryParamsMetadata.name, Utils.valToString(value)));
                break;
        }

        return params;
    }

    private static List<NameValuePair> parseDeepObjectParams(QueryParamsMetadata queryParamsMetadata, Object value)
            throws Exception {
        List<NameValuePair> params = new ArrayList<>();

        switch (Types.getType(value.getClass())) {
            case MAP: {
                Map<?, ?> map = (Map<?, ?>) value;

                for (Map.Entry<?, ?> entry : map.entrySet()) {
                    String key = Utils.valToString(entry.getKey());
                    Object val = entry.getValue();

                    if (val.getClass().isArray()) {
                        for (Object v : (Object[]) val) {
                            params.add(new BasicNameValuePair(String.format("%s[%s]", queryParamsMetadata.name, key),
                                    Utils.valToString(v)));
                        }
                    } else {
                        params.add(new BasicNameValuePair(String.format("%s[%s]", queryParamsMetadata.name, key),
                                Utils.valToString(val)));
                    }
                }

                return params;
            }
            case OBJECT: {
                Field[] fields = value.getClass().getFields();

                for (Field field : fields) {
                    Object val = field.get(value);
                    if (val == null) {
                        continue;
                    }

                    QueryParamsMetadata metadata = QueryParamsMetadata.parse(field);
                    if (metadata == null) {
                        continue;
                    }

                    if (val.getClass().isArray()) {
                        for (Object v : (Object[]) val) {
                            params.add(new BasicNameValuePair(
                                    String.format("%s[%s]", queryParamsMetadata.name, metadata.name),
                                    Utils.valToString(v)));
                        }
                    } else {
                        params.add(
                                new BasicNameValuePair(String.format("%s[%s]", queryParamsMetadata.name, metadata.name),
                                        Utils.valToString(val)));
                    }
                }

                return params;
            }
            default:
                throw new Exception("DeepObject style only supports Map and Object types");
        }
    }
}

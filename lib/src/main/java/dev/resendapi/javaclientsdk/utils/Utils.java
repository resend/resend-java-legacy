package dev.resendapi.javaclientsdk.utils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;

public final class Utils {
    public static String generateURL(String baseURL, String path)
            throws IllegalArgumentException, IllegalAccessException {
        return generateURL(baseURL, path, null);
    }

    public static String generateURL(String baseURL, String path, Object params)
            throws IllegalArgumentException, IllegalAccessException {
        if (baseURL != null && baseURL.endsWith("/")) {
            baseURL = baseURL.split("/")[0];
        }

        Map<String, String> pathParams = new HashMap<>();

        if (params != null) {
            Field[] fields = params.getClass().getFields();

            for (Field field : fields) {
                PathParamsMetadata pathParamsMetadata = PathParamsMetadata.parse(field);
                if (pathParamsMetadata == null) {
                    continue;
                }

                Object value = Types.getValue(field.get(params));
                if (value == null) {
                    continue;
                }

                switch (pathParamsMetadata.style) {
                    case "simple":
                        switch (Types.getType(value.getClass())) {
                            case ARRAY:
                                Object[] array = (Object[]) value;
                                if (array.length == 0) {
                                    continue;
                                }

                                pathParams.put(pathParamsMetadata.name,
                                        String.join(",",
                                                Arrays.asList(array).stream().map(v -> String.valueOf(v)).toList()));
                                break;
                            case MAP:
                                Map<?, ?> map = (Map<?, ?>) value;
                                if (map.size() == 0) {
                                    continue;
                                }

                                pathParams.put(pathParamsMetadata.name,
                                        String.join(",", map.entrySet().stream().map(e -> {
                                            if (pathParamsMetadata.explode) {
                                                return String.format("%s=%s", String.valueOf(e.getKey()),
                                                        String.valueOf(e.getValue()));
                                            } else {
                                                return String.format("%s,%s", String.valueOf(e.getKey()),
                                                        String.valueOf(e.getValue()));
                                            }
                                        }).toList()));
                                break;
                            case PRIMITIVE:
                                pathParams.put(pathParamsMetadata.name, String.valueOf(value));
                                break;
                            case OBJECT:
                                List<String> values = new ArrayList<String>();

                                Field[] valueFields = value.getClass().getFields();
                                for (Field valueField : valueFields) {
                                    PathParamsMetadata valuePathParamsMetadata = PathParamsMetadata.parse(valueField);
                                    if (valuePathParamsMetadata == null) {
                                        continue;
                                    }

                                    Object val = Types.getValue(valueField.get(value));

                                    if (val == null) {
                                        continue;
                                    }

                                    if (pathParamsMetadata.explode) {
                                        values.add(String.format("%s=%s", valuePathParamsMetadata.name,
                                                String.valueOf(val)));
                                    } else {
                                        values.add(String.format("%s,%s", valuePathParamsMetadata.name,
                                                String.valueOf(val)));
                                    }
                                }

                                pathParams.put(pathParamsMetadata.name, String.join(",", values));
                                break;
                        }
                }
            }
        }

        return baseURL + replaceParameters(path, pathParams);
    }

    public static boolean matchContentType(String contentType, String pattern) {
        if (contentType == null || contentType.isBlank()) {
            return false;
        }

        if (contentType.equals(pattern) || pattern.equals("*") || pattern.equals("*/*")) {
            return true;
        }

        String[] contentTypeParts = contentType.split(";");
        if (contentTypeParts.length == 0) {
            return false;
        }
        String mediaType = contentTypeParts[0];

        if (mediaType.equals(pattern)) {
            return true;
        }

        String[] mediaTypeParts = mediaType.split("/");
        if (mediaTypeParts.length == 2) {
            if (String.format("%s/*", mediaTypeParts[0]).equals(pattern)
                    || String.format("*/%s", mediaTypeParts[1]).equals(pattern)) {
                return true;
            }
        }

        return false;
    }

    public static SerializedBody serializeRequestBody(Object request) throws NoSuchFieldException,
            IllegalArgumentException, IllegalAccessException, UnsupportedOperationException, IOException {
        return RequestBody.serialize(request);
    }

    public static List<NameValuePair> getQueryParams(Object params) throws Exception {
        return QueryParameters.parseQueryParams(params);
    }

    public static HTTPClient configureSecurityClient(HTTPClient client, Object security) throws Exception {
        return Security.createClient(client, security);
    }

    public static String replaceParameters(String url, Map<String, String> params) {
        StringBuilder sb = new StringBuilder();

        Pattern p = Pattern.compile("(\\{.*?\\})");
        Matcher m = p.matcher(url);

        while (m.find()) {
            String match = m.group(1);
            String key = match.substring(1, match.length() - 1);
            String value = params.get(key);
            if (value != null) {
                m.appendReplacement(sb, value);
            }
        }
        m.appendTail(sb);

        return sb.toString();
    }

    public static Map<String, List<String>> getHeaders(Object headers) throws Exception {
        if (headers == null) {
            return null;
        }

        Map<String, List<String>> result = new HashMap<>();

        Field[] fields = headers.getClass().getFields();

        for (Field field : fields) {
            HeaderMetadata headerMetadata = HeaderMetadata.parse(field);
            if (headerMetadata == null) {
                continue;
            }

            Object value = Types.getValue(field.get(headers));
            if (value == null) {
                continue;
            }

            switch (Types.getType(value.getClass())) {
                case OBJECT: {
                    List<String> items = new ArrayList<String>();

                    Field[] valueFields = value.getClass().getFields();
                    for (Field valueField : valueFields) {
                        HeaderMetadata valueHeaderMetadata = HeaderMetadata.parse(valueField);
                        if (valueHeaderMetadata == null || valueHeaderMetadata.name == null
                                || valueHeaderMetadata.name.isBlank()) {
                            continue;
                        }

                        Object valueFieldValue = Types.getValue(valueField.get(value));
                        if (valueFieldValue == null) {
                            continue;
                        }

                        if (headerMetadata.explode) {
                            items.add(
                                    String.format("%s=%s", valueHeaderMetadata.name, String.valueOf(valueFieldValue)));
                        } else {
                            items.add(valueHeaderMetadata.name);
                            items.add(String.valueOf(valueFieldValue));
                        }
                    }

                    if (!result.containsKey(headerMetadata.name)) {
                        result.put(headerMetadata.name, new ArrayList<String>());
                    }

                    List<String> values = result.get(headerMetadata.name);
                    values.add(String.join(",", items));

                    break;
                }
                case MAP: {
                    Map<?, ?> map = (Map<?, ?>) value;
                    if (map.size() == 0) {
                        continue;
                    }

                    List<String> items = new ArrayList<String>();

                    for (Map.Entry<?, ?> entry : map.entrySet()) {
                        if (headerMetadata.explode) {
                            items.add(String.format("%s=%s", String.valueOf(entry.getKey()),
                                    String.valueOf(entry.getValue())));
                        } else {
                            items.add(String.valueOf(entry.getKey()));
                            items.add(String.valueOf(entry.getValue()));
                        }
                    }

                    if (!result.containsKey(headerMetadata.name)) {
                        result.put(headerMetadata.name, new ArrayList<String>());
                    }

                    List<String> values = result.get(headerMetadata.name);
                    values.add(String.join(",", items));

                    break;
                }
                case ARRAY: {
                    Object[] array = (Object[]) value;

                    if (array.length == 0) {
                        continue;
                    }

                    List<String> items = new ArrayList<String>();

                    for (Object item : array) {
                        items.add(String.valueOf(item));
                    }

                    if (!result.containsKey(headerMetadata.name)) {
                        result.put(headerMetadata.name, new ArrayList<String>());
                    }

                    List<String> values = result.get(headerMetadata.name);
                    values.add(String.join(",", items));

                    break;
                }
                default:
                    if (!result.containsKey(headerMetadata.name)) {
                        result.put(headerMetadata.name, new ArrayList<String>());
                    }

                    List<String> values = result.get(headerMetadata.name);
                    values.add(String.valueOf(value));
            }
        }

        return result;
    }

    private Utils() {
    }
}
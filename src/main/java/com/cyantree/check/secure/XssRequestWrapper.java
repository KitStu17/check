package com.cyantree.check.secure;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

// Lucy Xss Servlet Filter 사용 불가에 따른 request wrapper 구현
// Form data 처리를 위해 사용 (해당 프로젝트에선 Form data X)
// 참조 : https://github.com/dlask913/vue3-spring-project/issues/51
public class XssRequestWrapper extends HttpServletRequestWrapper {

    public XssRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        return sanitize(value);
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (values == null)
            return null;

        String[] sanitizedValues = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            sanitizedValues[i] = sanitize(values[i]);
        }
        return sanitizedValues;
    }

    private String sanitize(String input) {
        if (input == null || input.isEmpty())
            return input;

        StringBuilder sb = new StringBuilder(input.length() + 20);

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            switch (c) {
                case '<':
                    sb.append("&lt;");
                    break;
                case '>':
                    sb.append("&gt;");
                    break;
                case '&':
                    sb.append("&amp;");
                    break;
                case '"':
                    sb.append("&quot;");
                    break;
                case '\'':
                    sb.append("&#x27;");
                    break;
                case '/':
                    sb.append("&#x2F;");
                    break;
                default:
                    sb.append(c);
                    break;
            }
        }

        return sb.toString();
    }
}

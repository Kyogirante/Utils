package com.wang.utils.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import android.text.TextUtils;

/**
 * 给url添加参数
 * <br/>
 * 不支持添加path
 *
 * @author KyoWang
 * @since 2018/10/15
 */
public class UrlBuilder {

    private static final String QUESTION_MARK = "?";
    private static final String AND_MARK = "&";
    private static final String EQUAL_MARK = "=";
    private String mUrl;
    private Map<String, String> mParams = new HashMap<>();

    public UrlBuilder(String url) {
      mUrl = url;
    }

    public UrlBuilder append(Map<String, String> params) {
        if (params == null || params.isEmpty()) {
          return this;
        }

        mParams.putAll(params);

        return this;
    }

    public UrlBuilder append(String key, String value) {
        if (TextUtils.isEmpty(key) || TextUtils.isEmpty(value)) {
          return this;
        }

        mParams.put(key, value);

        return this;
    }

    public String build() {
        if (mParams.size() != 0) {
          appendIntervalMark();

          Set<Map.Entry<String, String>> entrySet = mParams.entrySet();
          for (Map.Entry<String, String> entry : entrySet) {
            appendIntervalMark();
            appendParams(entry.getKey(), entry.getValue());
          }
        }
        return mUrl;
    }

    private void appendIntervalMark() {
        if (TextUtils.isEmpty(mUrl)) {
          return;
        }

        if (mUrl.endsWith(QUESTION_MARK) || mUrl.endsWith(AND_MARK)) {
          return;
        }

        if (!mUrl.contains(QUESTION_MARK)) {
          mUrl = mUrl.concat(QUESTION_MARK);
        } else {
          mUrl = mUrl.concat(AND_MARK);
        }
    }

    private void appendParams(String key, String value) {
        mUrl = mUrl.concat(key).concat(EQUAL_MARK).concat(value);
    }

}

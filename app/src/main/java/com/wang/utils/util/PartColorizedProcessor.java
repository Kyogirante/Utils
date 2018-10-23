package com.wang.utils.util;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;

/**
 * 高亮特殊字符包裹的特殊字符串
 *
 * @author KyoWang
 * @since 2018/10/15
 */
public class PartColorizedProcessor {

    private Context mContext;

    private String mTargetString = "";
    private String mPrefixMark = "";
    private String mSuffixMark = "";

    private int mColorResId = android.R.color.holo_blue_light;

    public PartColorizedProcessor(Context context) {
      mContext = context;
    }

    public PartColorizedProcessor(Context context, String target) {
        mContext = context;
        mTargetString = checkString(target);
    }

    public PartColorizedProcessor targetString(String target) {
        mTargetString = checkString(target);
        return this;
    }

    public PartColorizedProcessor prefixMark(String mark) {
        this.mPrefixMark = mark;
        return this;
    }

    public PartColorizedProcessor suffixMark(String mark) {
        this.mSuffixMark = mark;
        return this;
    }

    public PartColorizedProcessor color(int resId) {
        this.mColorResId = resId;
        return this;
    }

    public SpannableString obtain() {
        if (TextUtils.isEmpty(mTargetString)
            || TextUtils.isEmpty(mPrefixMark)
            || TextUtils.isEmpty(mSuffixMark)) {
          return new SpannableString(mTargetString);
        }

        List<Integer> prefixIndexs = new ArrayList<>();
        List<Integer> suffixIndexs = new ArrayList<>();

        String temp = mTargetString;

        while (true) {
          int begin = temp.indexOf(mPrefixMark);
          if (begin < 0) {
            break;
          }

          prefixIndexs.add(begin);
          temp = temp.replaceFirst(mPrefixMark, "");

          int end = temp.indexOf(mSuffixMark);
          if (end < 0) {
            break;
          }

          suffixIndexs.add(end);
          temp = temp.replaceFirst(mSuffixMark, "");
        }

        if (prefixIndexs.isEmpty() || suffixIndexs.isEmpty()) {
          return new SpannableString(temp);
        }

        SpannableString sb = new SpannableString(temp);

        int size = Math.min(prefixIndexs.size(), suffixIndexs.size());

        for (int i = 0; i < size; i++) {
          sb.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(mColorResId)),
              prefixIndexs.get(i), suffixIndexs.get(i),
              Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }

        return sb;
    }

    private String checkString(String string) {
        if (string == null) {
          return "";
        }

        return string;
    }
}

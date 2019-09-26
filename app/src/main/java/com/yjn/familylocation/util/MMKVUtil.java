package com.yjn.familylocation.util;

import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.tencent.mmkv.MMKV;

import java.util.Set;

/**
 * <pre>
 *     author: Bruce_Yang
 *     blog  : https://yangjianan.gitee.io
 *     time  : 2019/9/26
 *     desc  : 基于 mmap 的高性能通用 key-value 组件
 *     link  : https://github.com/Tencent/MMKV/blob/master/readme_cn.md
 *     msg   : MMKV.initialize(this) 应用启动时初始化
 * </pre>
 */
public final class MMKVUtil {
    private static MMKV kv = MMKV.defaultMMKV();

    public static void put(@NonNull final String key, final String value) {
        kv.encode(key, value);
    }

    public static void put(@NonNull final String key, final int value) {
        kv.encode(key, value);
    }

    public static void put(@NonNull final String key, final long value) {
        kv.encode(key, value);
    }

    public static void put(@NonNull final String key, final float value) {
        kv.encode(key, value);
    }

    public static void put(@NonNull final String key, final double value) {
        kv.encode(key, value);
    }

    public static void put(@NonNull final String key, final boolean value) {
        kv.encode(key, value);
    }

    public static void put(@NonNull final String key, final Set<String> value) {
        kv.encode(key, value);
    }

    public static void put(@NonNull final String key, final byte[] value) {
        kv.encode(key, value);
    }

    public static void put(@NonNull final String key, final Parcelable value) {
        kv.encode(key, value);
    }

    public static String getString(@NonNull final String key) {
        return kv.decodeString(key);
    }

    public static String getString(@NonNull final String key, final String defaultValue) {
        return kv.decodeString(key, defaultValue);
    }

    public static int getInt(@NonNull final String key) {
        return kv.decodeInt(key);
    }

    public static int getInt(@NonNull final String key, final int defaultValue) {
        return kv.decodeInt(key, defaultValue);
    }

    public static long getLong(@NonNull final String key) {
        return kv.decodeLong(key);
    }

    public static long getLong(@NonNull final String key, final long defaultValue) {
        return kv.decodeLong(key, defaultValue);
    }

    public static float getFloat(@NonNull final String key) {
        return kv.decodeFloat(key);
    }

    public static float getFloat(@NonNull final String key, final float defaultValue) {
        return kv.decodeFloat(key, defaultValue);
    }

    public static double getDouble(@NonNull final String key) {
        return kv.decodeDouble(key);
    }

    public static double getDouble(@NonNull final String key, final double defaultValue) {
        return kv.decodeDouble(key, defaultValue);
    }

    public static boolean getBoolean(@NonNull final String key) {
        return kv.decodeBool(key);
    }

    public static boolean getBoolean(@NonNull final String key, final boolean defaultValue) {
        return kv.decodeBool(key, defaultValue);
    }

    public static Set<String> getStringSet(@NonNull final String key) {
        return kv.decodeStringSet(key);
    }

    public static Set<String> getStringSet(@NonNull final String key, final Set<String> defaultValue) {
        return kv.decodeStringSet(key, defaultValue);
    }

    public static byte[] getBytes(@NonNull final String key) {
        return kv.decodeBytes(key);
    }

    public static byte[] getBytes(@NonNull final String key, final byte[] defaultValue) {
        return kv.decodeBytes(key, defaultValue);
    }

    public static <T extends Parcelable> T getBytes(@NonNull final String key, Class<T> tClass) {
        return kv.decodeParcelable(key, tClass);
    }

    public static <T extends Parcelable> T getBytes(@NonNull final String key, Class<T> tClass, final T defaultValue) {
        return kv.decodeParcelable(key, tClass, defaultValue);
    }

    public static boolean contains(@NonNull final String key) {
        return kv.contains(key);
    }

    public static void remove(@NonNull final String key) {
        kv.remove(key);
    }

    public static void clear() {
        kv.clear();
    }
}

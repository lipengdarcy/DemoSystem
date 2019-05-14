package cn.smarthse.framework.util.enumcommons;

/**
 * 枚举接口添加枚举的公用方法
 * @param <V>
 */
public interface EnumAble<V> {

    V getValueByName();

    String getNameByValue();
}

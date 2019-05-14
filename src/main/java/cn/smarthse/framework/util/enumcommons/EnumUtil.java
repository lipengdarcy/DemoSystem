package cn.smarthse.framework.util.enumcommons;

import java.util.EnumSet;

/**
 * 枚举工具包
 */
public class EnumUtil {

    /**
     * 通过value得到name
     * @param clazz
     * @param id
     * @param <T>
     * @param <V>
     * @return
     */
    public static <T extends Enum<T> & EnumAble<V>, V> T getByValue(Class<T> clazz, V id){
        EnumSet<T> set = EnumSet.allOf(clazz);
        for(T t : set){
            if(t.getValueByName().equals(id)){
                return t;
            }
        }
        return null;
    }

    /**
     * 通过name得到value
     * @param clazz
     * @param label
     * @param <T>
     * @param <V>
     * @return
     */
    public static <T extends Enum<T> & EnumAble<V>, V> T getByName(Class<T> clazz, String label){
        EnumSet<T> set = EnumSet.allOf(clazz);
        for(T t : set){
            if(t.getNameByValue().equals(label)){
                return t;
            }
        }
        return null;
    }
}

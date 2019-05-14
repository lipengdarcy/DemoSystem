package cn.smarthse.framework.enumtype;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public enum MouldType {
    type1("device001.ftl", (byte) 1), type2("device002.ftl", (byte) 2),
    type3("device003.ftl", (byte) 3);

    // 成员变量
    private String name;
    private byte value;
    //得到所有枚举集合且仅有只读权限
    public final static List<MouldType> MOULD_TYPE_LIST = Collections.unmodifiableList(MouldType.getList());


    MouldType(String name, byte value) {
        this.name = name;
        this.value = value;
    }

    // get set 方法
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte getValue() {
        return value;
    }

    public void setValue(byte index) {
        this.value = index;
    }

    // 返回全部枚举值
    private static List<MouldType> getList() {
        List<MouldType> list = new ArrayList<MouldType>();
        for (MouldType c : MouldType.values()) {
            list.add(c);
        }
        return list;
    }

    // 根据前台数据得到模板集合
    public static List<String> getList(List<Byte> list) {
        List<String> alist = new ArrayList<>();
        list.forEach(a -> MOULD_TYPE_LIST.forEach(b->{
            if(b.getValue()==a.byteValue())
                alist.add(b.getName());
        }));
        return alist;
    }
}

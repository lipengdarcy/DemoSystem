package cn.smarthse.business.enums.skill;

/**
 * @author zhoulj(周利军) [1217102780@qq.com]
 * @Module ID:   <(模块)类编号，可以引用系统设计中的类编号>
 * @Comments: <对此类的描述，可以引用系统设计中的描述>
 * @JDK version used:      <JDK1.8> 14
 * @since 2019/3/26-15:14
 */
public enum ReportRecordStateEnum {

        waitEditState("待编制", 1),
        editingState("编制中", 2),
        waitAuditState("审核中", 3),
        waitModifyState("待修改", 4),
        modifyingState("修改中", 5),
        auditPassState("审核通过", 6),
        waitUploadState("待上传", 7),
        uploadedState("已上传", 8),
        modifyApplyIng("修改申请中", 9),
        ;
        // 成员变量
        private String name;
        private Integer state;
        // 构造方法

        private ReportRecordStateEnum(String name, Integer state) {
            this.name = name;
            this.state = state;
        }

        // 普通方法
        public static String getName(Integer state) {
            if (state == null) {
                return null;
            }
            for (ReportRecordStateEnum c : values()) {
                if (c.getState().equals(state)) {
                    return c.name;
                }
            }
            return null;
        }
    public static ReportRecordStateEnum getState(Integer state) {
        if (state == null) {
            return null;
        }
        for (ReportRecordStateEnum c : values()) {
            if (c.getState().equals(state)) {
                return c;
            }
        }
        return null;
    }


        // get set 方法
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }

        public Integer getState() {
            return state;
        }

        public void setState(Integer state) {
            this.state = state;
        }

}
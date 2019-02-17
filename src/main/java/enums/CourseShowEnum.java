package enums;

public enum CourseShowEnum {
    NONE("",0),
    ID("a.id",1),
    C_NAME("c_name",2),
    C_DESC("c_desc",3),
    PIC("",4),
    TOTAL("total_need_stu_amount",5),
    Have("have_stu_amount",6),
    GRADE("grade",7),
    T0_CLASS("to_class",8),
    TERM("term",9);


    private String name;
    private int index;
    CourseShowEnum(String name, int index){
        this.name=name;
        this.index=index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }


        public static String getNameByIndex(int index) {
            for (CourseShowEnum c : CourseShowEnum.values()) {
                if (c.getIndex() == index) {
                    return c.name;
                }
            }
            return null;
        }

}

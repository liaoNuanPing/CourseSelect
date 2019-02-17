package enums;

public enum StuSelectEnum {
    NONE("",0),
    STUDENT_ID("student_card",1),
    STU_NAME("stu_name",2),
    C_NAME("c_name",3),
    GRADE("grade",4),
    CLASS_NOW("class_now",5),
    SELECT_TIME("select_time",6);

    private String name;
    private int index;
    StuSelectEnum(String name, int index){
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
        for (StuSelectEnum c : StuSelectEnum.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }

}

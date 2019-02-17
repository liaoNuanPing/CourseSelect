package enums;

public enum StudentEnum {
    NONE("",0),
    STUDENT_ID("id",1),
    STU_NAME("stu_name",2),
    GRADE("grade",3),
    CLASS_NOW("class_now",4),
    PARENT_NAME("parent_name",5),
    PARENT_PHONE("parent_phone",6),
    HEAD_IMG("head_img",7);

    private String name;
    private int index;
    StudentEnum(String name, int index){
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
        for (StudentEnum c : StudentEnum.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }
}

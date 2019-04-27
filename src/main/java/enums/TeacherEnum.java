package enums;

public enum TeacherEnum {
    NONE("",0),
    ID("id",1),
    TEACHER("teacher",2),
    TEACHER_ID("teacher_id",3),
    SEX("sex",4),
    TEACHER_PHONE("teacher_phone",5),
    TEACHER_MAIL("teacher_mail",6);

    private String name;
    private int index;
    TeacherEnum(String name, int index){
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
        for (TeacherEnum c : TeacherEnum.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }
}

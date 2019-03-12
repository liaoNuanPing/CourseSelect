package enums;

public enum CourseEnum {

    NONE("",0),
    ID("id",1),
    C_NAME("c_name",2),
    C_DESC("c_desc",3),
    PIC("",4);

    private String name;
    private int index;
    CourseEnum(String name, int index){
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
        for (CourseEnum c : CourseEnum.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }
}

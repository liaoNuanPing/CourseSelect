package enums;

public enum RegisterCodeEnum {

    NONE("",0),
    CODE("code",1),
    DISABLE("disable",2);

    private String name;
    private int index;
    RegisterCodeEnum(String name, int index){
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
        for (RegisterCodeEnum c : RegisterCodeEnum.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }
}

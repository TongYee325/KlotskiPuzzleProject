package controller;
public enum BlockType {
    EXIT(0),        // 空白
    GUAN_YU(2),     // 关羽
    ZHANG_FEI(3),   // 张飞
    ZHAO_YUN(4),    // 赵云
    MA_CHAO(5),     // 马超
    HUANG_ZHONG(6), // 黄忠
    CAO_CAO(7),    // 曹操
    SOLDIER(1),;    // 小兵1

    final int code;
    BlockType(int code) { this.code = code; }

    public static BlockType fromCode(int blockCode) {
        for (BlockType type : BlockType.values()) {
            if (type.code == blockCode) return type;
        }
        return null;
    }

    public int getWidth() {
        if(this.code == 2||this.code == 7){return 2;}//关羽曹操返回2
        else return 1;
    }

    public int getHeight() {
        if(this.code >=3&&this.code<=7){return 2;}
        else return 1;
    }

    public int getCode() {
        return code;
    }
}

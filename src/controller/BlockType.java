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
        return switch (this) {
            case GUAN_YU -> 2; // 实际应为 1x2 竖版
            case CAO_CAO -> 2;
            default -> 1;
        };
    }

    public int getHeight() {
        if(this.code >=3&&this.code<=7){return 2;}
        else return 1;
    }

    public int getCode() {
        return code;
    }

    public static String getName(int code) {
        switch (code) {
            case 7 -> {
                return"CAO_CAO";
            }
            case 2 -> {
                return "GUAN_YU";
            }case 3 -> {
                return "ZHANG_FEI";
            }case 4 -> {
                return "ZHAO_YUN";
            }case 5 -> {
                return "MA_CHAO";
            }case 6 -> {
                return "HUANG_ZHONG";
            }case 1 -> {
                return "SOLDIER";
            }case 0-> {
                return "EXIT";
            }default -> {
                return "EXIT";
            }
        }
    }
}

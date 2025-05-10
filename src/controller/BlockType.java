package controller;
public enum BlockType {
    EXIT(0),        // 空白
    GUAN_YU(2),     // 关羽
    ZHANG_FEI(3),   // 张飞
    ZHAO_YUN(4),    // 赵云
    MA_CHAO(5),     // 马超
    HUANG_ZHONG(6), // 黄忠
    CAO_CAO(7),    // 曹操
    SOLDIER_A(1),     // 小兵1
    SOLDIER_B(8),   //小兵2
    SOLDIER_C(9),   //小兵3
    SOLDIER_D(10);  //小兵4

    final int code;
    BlockType(int code) { this.code = code; }
}

public class Map {
    public static String[] grid1 = {
        "        vv                           >>>>>>>>>>>>>vv",
        "        vv       >>>>>>vv            >>>>>>>>>>>>>vv",
        "        vv       >>>>>>vv<<<<        ^^           vv",
        "        vv       ^^    vv<<<<        ^^           vv",
        "        vv             vv                         vv",
        "        vv             vv                         vv",
        ">>>>>>>>>>>>>vv<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<",
        ">>>>>>>>>>>>>vv<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<",
        "             vv         ^^              ^^          ",
        "             SS         ^^              ^^          ",
        "                        ^^              ^^<<<<<<    ",
        "                        ^^              ^^<<<<<<    ",
        "                   >>>>>^^<<<<<         ^^          ",
        "                   >>>>>^^<<<<<         ^^          ",
        "                                >>>>>>>>^^          ",
        "                                >>>>>>>>^^          "
    };

    public static int[][] sammelpunkte1 = {
        {9, 13},
        {9, 14}
    };

    public static String[] grid2 = {
        ">>>>>>vvv<<<<<<            vvvv          vvvv",
        ">>>>>>vvv<<<<<<            vvvv          vvvv",
        "      vvv                  vvvv          vvvv",
        "      vvv                  vvvv          vvvv",
        "      vvv                  vvvv          vvvv",
        "      vvv                  >>vv          vv<<",
        "      vvv                  >>vv          vv<<",
        "      vvv                    >>>>>>vv<<<<<<  ",
        "      vvv                    >>>>>>vv<<<<<<  ",
        "      v>>>>>>>>>>>vvvv             vv        ",
        "      >>>>>>>>>>>>vvvv             vv        ",
        "                  vvvv<<<<<<<<<<<<<<<        ",
        "                  vvvv<<<<<<<<<<<<<<<        ",
        "                  vvvv                       ",
        "                  vvvv                       ",
        "                  vvvv                       ",
        "                  >vv<                       ",
        "                  >vv<                       ",
        "                   SS                        ",
    };
    public static int[][] sammelpunkte2 = {
        {18, 19},
        {18, 20}
    };

    public static String[] getGrid(String name) {
        if(name.equals("grid1")) {
            return grid1;
        } else if(name.equals("grid2")) {
            return grid2;
        } else {
            return null;
        }
    }

    public static int[][] getSammelpunkte(String name) {
        if(name.equals("grid1")) {
            return sammelpunkte1;
        } else if(name.equals("grid2")) {
            return sammelpunkte2;
        } else {
            return null;
        }
    }

    //TODO 3tes Grid einfügen
}

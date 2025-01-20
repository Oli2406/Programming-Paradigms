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

    public static String[] grid3 =  {
        ">>>>>>vvv             >>>>>>vvv         ",
        "^^^^>>>>vvvv          ^^^^>>>>vvvv      ",
        "^^^^    >>>vv         ^^^^    >>>vv     ",
        "^^^^      >>>>>>>>>>>>^^^^      >>>>vv  ",
        "vvvv      >>>>>>>>>>>>vvvv      >>>>vv  ",
        "vvvv    >>>^^         vvvv    >>>^^ vv  ",
        "vvvv>>>>^^^^          vvvv>>>>^^^^  vv  ",
        ">>>>>>^^^             >>>>>>^^^     vv  ",
        "^^^^                  ^^^^          >>>S",
        "^^^^                  ^^^^          >>>S",
        "^^^^                  ^^^^              ",
        "^^^^                  ^^^^              ",
        "^^^^                  ^^^^              "
    };

    public static String[] getGrid(String name) {
        if(name.equals("grid1")) {
            return grid1;
        } else if(name.equals("grid2")) {
            return grid2;
        } else if(name.equals("grid3")) {
            return grid3;
        } else {
            return null;
        }
    }
}

package sumit.tictactoe;

import android.widget.ImageView;

class Logic {

    private static ImageView[] sBlocks;

    static String Winner;
    static int sSet;

    static final int CIRCLE=0, CROSS=1;

    static boolean isCompleted(int pos, ImageView[] blocks){

        Logic.sBlocks=blocks;
        boolean result = false;

        switch (pos){
            case 1:
                result  = sameset(1,2,3,1) || sameset(1,4,7,4) || sameset(1,5,9,7);
                break;
            case 2:
                result = sameset(1,2,3,1) || sameset(2,5,8,5);
                break;
            case 3:
                result = sameset(1,2,3,1) || sameset(3,6,9,6) || sameset(3,5,7,8);
                break;
            case 4:
                result = sameset(4, 5, 6, 2) || sameset(1, 4, 7, 4);
                break;
            case 5:
                result = sameset(4, 5, 6, 2) || sameset(2, 5, 8, 5) || sameset(1, 5, 9, 7) || sameset(3, 5, 7, 8);
                break;
            case 6:
                result = sameset(4, 5, 6, 2) || sameset(3, 6, 9, 6);
                break;
            case 7:
                result = sameset(7, 8, 9, 3) || sameset(1, 4, 7, 4) || sameset(3, 5, 7, 8);
                break;
            case 8:
                result = sameset(7, 8, 9, 3) || sameset(2, 5, 8, 5);
                break;
            case 9:
                result = sameset(7, 8, 9, 3) || sameset(3, 6, 9, 6) || sameset(1, 5, 9, 7);
                break;
        }
        return result;

    }

    private static boolean sameset(int x, int y, int z, int set){
        boolean value = sBlocks[x-1].getId() == sBlocks[y-1].getId() && sBlocks[y-1].getId() == sBlocks[z-1].getId();

        if(value){
            if(sBlocks[x-1].getId()==CIRCLE)
                Winner="CIRCLE";
            else
                Winner="CROSS";
            sSet=set;
        }
        return value;
    }



}

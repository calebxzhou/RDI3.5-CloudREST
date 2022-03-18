package calebzhou.rdicloudrest.utils;

import calebzhou.rdicloudrest.model.CoordLocation;

import java.util.Random;

public class RandomUtils {
    public static int generateRandomInt(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
    public static char generateRandomChar(){
                        //1234567890 长得不像
        String chars = "acdefhjkmnprtvwxy";
        Random rnd = new Random();
        return chars.charAt(rnd.nextInt(chars.length()));
    }
    public static boolean randomPercentage(double perc){
        int ranMax=10000;
        int ranPerc=(int)(ranMax*perc);
        int ran=RandomUtils.generateRandomInt(0,ranMax);
        return ran < ranPerc;
    }
    public static String getRandomId(){
        int digits=8;
        StringBuilder sb= new StringBuilder();
        for(int i=0;i<digits;i++){
            sb.append(generateRandomInt(0,9));
        }
        return sb.toString();
    }
    public static CoordLocation getRandomCoordinate(){
        int x=RandomUtils.generateRandomInt(-49999,49999);
        int z=RandomUtils.generateRandomInt(-49999,49999);
        return new CoordLocation(x,128,z);
    }
}

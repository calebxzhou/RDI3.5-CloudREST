package calebzhou.rdicloudrest.utils;

import java.util.SplittableRandom;

public class RandomUtils {
    public static final SplittableRandom random = new SplittableRandom();
    public static int generateRandomInt(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }
    public static boolean randomPercentage(double perc){
        int ranMax=10000;
        int ranPerc=(int)(ranMax*perc);
        int ran=RandomUtils.generateRandomInt(0,ranMax);
        return ran < ranPerc;
    }
    public static char generateRandomChar(){
        String chars = "qwertyuiopasdfghjklzxcvbnm1234567890QWERTYUIOPASDFGHJKLZXCVBNM";
        return chars.charAt(random.nextInt(chars.length()));
    }
    public static String getRandomString(int len){
        StringBuilder iid = new StringBuilder();
        for(int i=0;i<len;++i){
            iid.append(generateRandomChar());
        }
        return iid.toString();
    }
}

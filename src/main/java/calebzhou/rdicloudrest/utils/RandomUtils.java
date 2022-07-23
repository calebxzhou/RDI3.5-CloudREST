package calebzhou.rdicloudrest.utils;

import java.util.SplittableRandom;

public class RandomUtils {
    public static final SplittableRandom random = new SplittableRandom();
    public static int generateRandomInt(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }
    public static char generateRandomChar(){
                        //1234567890 长得不像
        String chars = "acdefhjkmnprtvwxy";
        return chars.charAt(random.nextInt(chars.length()));
    }
    public static boolean randomPercentage(double perc){
        int ranMax=10000;
        int ranPerc=(int)(ranMax*perc);
        int ran=RandomUtils.generateRandomInt(0,ranMax);
        return ran < ranPerc;
    }
    public static int getRandomIdV2(){
        return random.nextInt(10000,99999);
    }
    @Deprecated
    public static String getRandomId(){
        int digits=8;
        StringBuilder sb= new StringBuilder();
        for(int i=0;i<digits;i++){
            sb.append(generateRandomInt(0,9));
        }
        return sb.toString();
    }
}

package calebzhou.rdicloudrest.module.account;

import it.unimi.dsi.fastutil.chars.Char2CharMap;
import it.unimi.dsi.fastutil.chars.Char2CharOpenHashMap;

import java.util.stream.Collector;

public class AccountUtils {
    //0123456789 -> abcdefghkm, HEX abcdef-> zyxwtr
    public static final Char2CharMap charMap = new Char2CharOpenHashMap();
    //反向
    public static final Char2CharMap charMapRev = new Char2CharOpenHashMap();

    public static String qq2rdi(long qq){
        // eg 1037414277 -> 3DD5AF85 -> dwwfzrkf
        return hex2rdi(qq2hex(qq));
    }
    public static String qq2hex(long qq){
        // eg 1037414277 -> 3DD5AF85
        return Long.toHexString(qq);
    }
    public static String hex2rdi(String hex){
        // eg 3DD5AF85 -> dwwfzrkf
        return hex.chars()
                .mapToObj(intValue -> charMap.get((char) intValue))
                .collect(Collector.of(
                        StringBuilder::new,
                        StringBuilder::append,
                        StringBuilder::append,
                        StringBuilder::toString));
    }
    public static String rdi2hex(String rdi){
        return rdi.chars()
                .mapToObj(intVal -> charMapRev.get((char) intVal))
                .collect(Collector.of(
                        StringBuilder::new,
                        StringBuilder::append,
                        StringBuilder::append,
                        StringBuilder::toString));
    }
    public static long hex2qq(String hex){
        return Long.parseLong(hex,16);
    }
    public static long rdi2qq(String rdi){
        //eg dwwfzrkf -> 1037414277
        return hex2qq(rdi2hex(rdi));
    }

    static{
        charMap.put('0','a');
        charMap.put('1','b');
        charMap.put('2','c');
        charMap.put('3','d');
        charMap.put('4','e');
        charMap.put('5','f');
        charMap.put('6','g');
        charMap.put('7','h');
        charMap.put('8','k');
        charMap.put('9','m');
        charMap.put('a','z');
        charMap.put('b','y');
        charMap.put('c','x');
        charMap.put('d','w');
        charMap.put('e','t');
        charMap.put('f','r');
        charMap.char2CharEntrySet().forEach(entry -> charMapRev.put(entry.getCharValue(),entry.getCharKey()));
    }
}
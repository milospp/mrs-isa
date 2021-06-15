package isa9.Farmacy.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public class Utils {
    public final static Random rand = new SecureRandom();


    public static Random getRand() {
        return rand;
    }
}

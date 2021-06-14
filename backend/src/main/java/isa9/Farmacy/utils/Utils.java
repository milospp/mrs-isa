package isa9.Farmacy.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public class Utils {
    public static Random rand;


    public Utils() throws NoSuchAlgorithmException {
        this.rand = SecureRandom.getInstanceStrong();
    }

    public static Random getRand() {
        return rand;
    }
}

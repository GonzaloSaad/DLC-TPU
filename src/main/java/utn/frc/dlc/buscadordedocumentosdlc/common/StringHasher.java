package utn.frc.dlc.buscadordedocumentosdlc.common;

public class StringHasher {
    public static long longHash(String string) {
        long h = 98764321261L;
        int l = string.length();
        char[] chars = string.toCharArray();

        for (int i = 0; i < l; i++) {
            h = 31*h + chars[i];
        }
        return h;
    }
}

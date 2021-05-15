package net;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author qyyzxty@icloud.com
 * 2021/4/8
 **/
public class Charts {

    public static void main(String[] args) {

    }

    public static void generateCharacters(OutputStream out) throws IOException {
        int firstPrintableCharacter = 33;
        int numberOfPrintableCharacter = 94;
        int numberOfCharactersPerLine = 72;

        int start = firstPrintableCharacter;
        while (true) {
            for (int i = start; i < start + numberOfCharactersPerLine; i++) {
                out.write((
                        (i - firstPrintableCharacter) % numberOfPrintableCharacter)
                );
            }
            out.write('\r'); //回车
            out.write('\n'); //换行
            start = ((start + 1) - firstPrintableCharacter) % numberOfPrintableCharacter + firstPrintableCharacter;
        }
    }

}

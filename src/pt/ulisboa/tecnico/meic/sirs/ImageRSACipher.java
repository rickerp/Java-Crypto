package pt.ulisboa.tecnico.meic.sirs;

import javax.crypto.Cipher;
import java.io.IOException;

/**
 * Encrypts an image with the RSA algorithm in multiple modes, with a given, appropriate RSA key
 */
public class ImageRSACipher {

    public static void main(String[] args) throws IOException {

        if(args.length != 4) {
            System.err.println("This program encrypts an image file with RSA.");
            System.err.println("Usage: ImageRSACipher [inputFile.png] [RSAPubKeyFile] [RSAPrivKeyFile] [outputFile.png]");
            return;
        }

        final String inputFile = args[0];
        final String pubKeyFile = args[1];
        final String privKeyFile = args[2];
        final String outputFile = args[3];

        RSACipherByteArrayMixer cipher = new RSACipherByteArrayMixer(Cipher.ENCRYPT_MODE);
        cipher.setParameters(pubKeyFile, privKeyFile);
        ImageMixer.mix(inputFile, outputFile, cipher);
    }

}

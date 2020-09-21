package pt.ulisboa.tecnico.meic.sirs;

import javax.crypto.Cipher;
import java.io.IOException;

/**
 * Decrypts an image with the RSA algorithm in multiple modes, with a given, appropriate RSA key
 */
public class ImageRSADecipher {

    public static void main(String[] args) throws IOException {

        if(args.length != 4) {
            System.err.println("This program decrypts an image file with RSA.");
            System.err.println("Usage: ImageRSADecipher [inputFile.png] [RSAPubKeyFile] [RSAPrivKeyFile] [outputFile.png]");
            return;
        }

        final String inputFile = args[0];
        final String pubKeyFile = args[1];
        final String privKeyFile = args[2];
        final String outputFile = args[3];

        RSACipherByteArrayMixer cipher = new RSACipherByteArrayMixer(Cipher.DECRYPT_MODE);
        cipher.setParameters(pubKeyFile, privKeyFile);
        ImageMixer.mix(inputFile, outputFile, cipher);
    }

}

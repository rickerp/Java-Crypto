package pt.ulisboa.tecnico.meic.sirs;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.swing.text.StyledEditorKit;

import java.security.Key;
import java.security.KeyPair;

/**
 * Implementation of the RSA cipher as a ByteArrayMixer
 */
public class RSACipherByteArrayMixer implements ByteArrayMixer {

    private String pubKeyFile;
    private String privKeyFile;
    private int opmode;

    public void setParameters(String pubKeyFile, String privKeyFile) {
        this.pubKeyFile = pubKeyFile;
        this.privKeyFile = privKeyFile;
    }

    public RSACipherByteArrayMixer(int opmode) {
        this.opmode = opmode;
    }

    @Override
    public byte[] mix(byte[] byteArray, byte[] byteArray2) {

        try {
            final int MAXDECBYTES = 117;
            final int MAXENCBYTES = 128;

            KeyPair key = RSAKeyGenerator.read(pubKeyFile, privKeyFile);

            // get a DES cipher object and print the provider
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            System.out.println(cipher.getProvider().getInfo());

            if (this.opmode == Cipher.ENCRYPT_MODE) {
                System.out.println("Ciphering ...");
                cipher.init(this.opmode, key.getPublic());

                byte[] cipheredBytes = new byte[byteArray.length / MAXDECBYTES * MAXENCBYTES];
                for (int iByte = 0; iByte < byteArray.length / MAXDECBYTES; iByte++) {
                    byte[] cBytes = cipher.doFinal(
                                            byteArray, 
                                            iByte * MAXDECBYTES, 
                                            (MAXDECBYTES * (iByte + 1) > byteArray.length) ? byteArray.length - iByte * MAXDECBYTES : MAXDECBYTES
                                        );
                    System.arraycopy(cBytes, 0, cipheredBytes, iByte * MAXENCBYTES, cBytes.length);
                }
                return cipheredBytes; 
            } else {
                System.out.println("Deciphering ...");
                cipher.init(this.opmode, key.getPrivate());

                byte[] decipheredBytes = new byte[byteArray.length / MAXDECBYTES * MAXENCBYTES];
                for (int iByte = 0; iByte < byteArray.length / MAXENCBYTES; iByte++) {
                    byte[] dBytes = cipher.doFinal(
                                            byteArray, 
                                            iByte * MAXENCBYTES, 
                                            (MAXENCBYTES * (iByte + 1) > byteArray.length) ? byteArray.length - iByte * MAXENCBYTES : MAXENCBYTES
                                        );
                    System.arraycopy(dBytes, 0, decipheredBytes, iByte * MAXDECBYTES, dBytes.length);
                }
                return decipheredBytes; 
            }

        } catch (Exception e) {
            // Pokemon exception handling!
            e.printStackTrace();
        }

        return null;

    }
}

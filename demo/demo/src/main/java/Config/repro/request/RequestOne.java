package Config.repro.request;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class RequestOne {
    public static void main(String[] args) {
        try {
            // Đường dẫn đến tệp
            File file = new File("output.csv");

            // Tính toán checksum (MD5)
            String checksum = FileUtils.getMD5Checksum(file);
            System.out.println("MD5 Checksum (Base64): " + checksum);

            // Lấy kích thước của tệp
            long fileSize = FileUtils.getFileSize(file);
            System.out.println("File Size (bytes): " + fileSize);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getCheckSum() throws IOException, NoSuchAlgorithmException {
        File file = new File("output.csv");

        // Tính toán checksum (MD5)
        String checksum = FileUtils.getMD5Checksum(file);
        return checksum;
    }

    public static long getSize() {
        File file = new File("output.csv");
        long fileSize = FileUtils.getFileSize(file);
        return fileSize;
    }
}

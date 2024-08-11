package Config.repro;

import Config.repro.request.RequestOne;
import Config.repro.response.Respone;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.modelmapper.ModelMapper;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

class C{

    private static final String REPRO_URL = "https://api.reproio.com/v3/user_profiles/bulk_import";
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        writeCsv(genCSV(), "output.csv");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("checksum", RequestOne.getCheckSum());
        jsonObject.put("byte_size", RequestOne.getSize());


        System.out.println("CSV file was created successfully.");

        String result = sendRequestToUserProFileRepro(jsonObject.toJSONString(), REPRO_URL, "28ce6bcd-6073-4f4c-8284-c4b442171636");
        ObjectMapper objectMapper = new ObjectMapper();
        Respone respone = objectMapper.readValue(result, Respone.class);

        String url = respone.getDirectUpload().getUrl();
        String contentType = respone.getDirectUpload().getHeaders().getContentType();
        String contentMD5 = respone.getDirectUpload().getHeaders().getContentMd5();

        System.out.println("URL______________________________: " + url);
        System.out.println("contentType______________________________: " + contentType);
        System.out.println("contentMD5______________________________: " + contentMD5);

        String result1 = sendRequestReproNewVersions(url, contentType, contentMD5, "output.csv", "28ce6bcd-6073-4f4c-8284-c4b442171636");

        System.out.println(result1);
    }




    public static void writeCsv(List<List<String>> data, String filePath) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (List<String> row : data) {
                writer.println(String.join(",", row.toString()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<List<String>> genCSV() {
        return genParam();
    }

    public static String sendRequestToUserProFileRepro(String postData, String urlStr, String token) {
        String result = " ";
        try {

            StringBuilder postBuilder = new StringBuilder();

            URL u = new URL(urlStr);
            String host = u.getHost();
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();

            //post method
            byte[] postDataByte = null;
            if (postData != null) {

                conn.setRequestMethod("POST");
                //data to send
                postBuilder.append(postData);
                String encodedData = postBuilder.toString();
                // send data by byte

                conn.setRequestProperty("Content-Length", (Integer.valueOf(encodedData.length())).toString());
                conn.setRequestProperty("X-Repro-Token", token);
                conn.setRequestProperty("Host", host);
                conn.setRequestProperty("Content-Type", "application/json");
                postDataByte = postBuilder.toString().getBytes("UTF-8");
            }

            try {
                conn.setDoOutput(true);
                conn.setDoInput(true);
                OutputStream out = conn.getOutputStream();
                if (postDataByte != null) {
                    out.write(postDataByte);
                }
                out.close();
            } catch (IOException ex) {
                System.out.println(ex);
            }
            //get data from server
            InputStreamReader isr = new InputStreamReader(conn.getInputStream());
            BufferedReader buf = new BufferedReader(isr);

            //write
            result = buf.readLine();
            isr.close();
            buf.close();

        } catch (Exception ex) {
            System.out.println("Send Data Repro Error: " + ex);
            System.out.println(ex);
        }
        return result;
    }


    public static String sendRequestReproNewVersions(String urlStr, String contentType, String contentMD5, String filePath, String token) {
        StringBuilder result = new StringBuilder();
        try {
            URL u = new URL(urlStr);
            String host = u.getHost();
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();

            // Đặt phương thức PUT
            conn.setRequestMethod("PUT");

            // Thiết lập các thuộc tính của yêu cầu
            conn.setRequestProperty("Content-Type", contentType);
            conn.setRequestProperty("Content-MD5", contentMD5);
            conn.setRequestProperty("Host", host);

            // Thêm tiêu đề Authorization nếu cần
            // conn.setRequestProperty("Authorization", "Bearer yourToken");

            // Đọc dữ liệu từ tệp
            File file = new File(filePath);
            long fileLength = file.length();

            // Cấu hình kết nối để cho phép gửi dữ liệu
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Length", String.valueOf(fileLength));

            // Gửi tệp qua OutputStream
            try (FileInputStream fis = new FileInputStream(file);
                 OutputStream out = conn.getOutputStream()) {

                byte[] buffer = new byte[4096];
                int bytesRead;

                while ((bytesRead = fis.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            } catch (IOException ex) {
                System.out.println("Error sending file: " + ex);
                ex.printStackTrace();
            }

            // Nhận dữ liệu từ máy chủ
            int responseCode = conn.getResponseCode();
            result.append("Response Code: ").append(responseCode).append("\n");

            try (InputStreamReader isr = new InputStreamReader(conn.getInputStream());
                 BufferedReader buf = new BufferedReader(isr)) {

                String line;
                while ((line = buf.readLine()) != null) {
                    result.append(line).append("\n");
                }
            } catch (IOException ex) {
                // Đọc dữ liệu từ lỗi, nếu có
                try (InputStreamReader isr = new InputStreamReader(conn.getErrorStream());
                     BufferedReader buf = new BufferedReader(isr)) {

                    String line;
                    while ((line = buf.readLine()) != null) {
                        result.append(line).append("\n");
                    }
                } catch (IOException e) {
                    System.out.println("Error reading error stream: " + e);
                    e.printStackTrace();
                }
            }

            conn.disconnect();

        } catch (Exception ex) {
            System.out.println("Send Data Repro Error: " + ex);
            ex.printStackTrace();
        }
        return result.toString();

    }

    private static List<List<String>> genParam() {
        List<List<String>> listRowCSV = new ArrayList<>();

        List<String> title = new ArrayList<>();
        title.add(UserProfileRepro.USER_ID);
        title.add(UserProfileRepro.TOTAL_VIEW_PROFILE);
        title.add(UserProfileRepro.TOTAL_PURCHASE);
        title.add(UserProfileRepro.TOTAL_POINT_IN_APP);

        title.add(UserProfileRepro.USING_VOICE_CALL);
        title.add(UserProfileRepro.USING_VIDEO_CALL);

        listRowCSV.add(title);


        List<String> ids = new ArrayList<>();
        ids.add("66b08a738c5cec55abdf717a");
        ids.add("66b1ee1d8c5cec55abdf7a6b");
        for (String userId : ids) {
            List<String> list = new ArrayList<>();
            list.add(userId);
            list.add("1");
            list.add("1");
            list.add("1");

            list.add("有");
            list.add("有");

            listRowCSV.add(list);
        }

        return listRowCSV;
    }

}

class UserProfileRepro {
    public static final String USER_ID = "user_id";
    public static final String KEY = "key";
    public static final String TYPE = "type";
    public static final String VALUE = "value";
    public static final String USER_PROFILES = "user_profiles";
    public static final String INT = "int";
    public static final String STRING = "string";
    public static final String ON = "ON";
    public static final String OFF = "OFF";
    public static final String YES = "有";
    public static final String NO = "無";
    public static final String REJOIN = "再入会";
    public static final String NEW = "新規";

    public static final String TOTAL_VIEW_PROFILE = "あしとし人数";
    public static final String TOTAL_PURCHASE = "購入回数";
    public static final String TOTAL_POINT_IN_APP = "消費ポイント";
    public static final String TOTAL_SEND_GIFT = "累計ギフト送信数";
    public static final String TOTAL_QUEST_POINT = "累計クエストポイント獲得数";
    public static final String TOTAL_QUEST_POINT_NOT_TAKEN = "受け取り可能ポイント数";
    public static final String DATE_LAST_PURCHASE = "最後に課金した日";
    public static final String USED_POINT_IN_CHAT = "ポイント消費経験";
    public static final String STATUS_SETTING_MAIL = "メルマガ設定";
    public static final String STATUS_GET_FREE_POINT_SMS = "電話認証無料ポイント獲得権";
    public static final String DATE_LAST_CALL = "最後に通話した日";
    public static final String DATE_REGISTER = "登録時間";
    public static final String USING_CHAT = "チャット経験";
    public static final String USING_VOICE_CALL = "音声通話経験";
    public static final String USING_VIDEO_CALL = "ビデオ通話経験";
    public static final String USING_SEXY_ALBUM = "セクシーアルバム経験";
    public static final String USING_FAVORITE = "お気に入り経験";
    public static final String USING_SEARCH = "検索経験";
    public static final String GROUP = "ユーザーグループ";
    public static final String REJOINT_PARAM = "再入会ユーザー";
}
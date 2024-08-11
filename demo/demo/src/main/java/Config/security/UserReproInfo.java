package Config.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserReproInfo {
    private String userId;
    private Integer totalViewProfile;
    private Integer totalPurchase;
    private Integer totalPointInApp;
    private Integer totalSendGift;
    private Integer totalQuestPoint;
    private Integer totalQuestPointNotTaken;
    private String dateLastPurchase;
    private Boolean usedPointInChat;
    private Boolean statusSettingMail;
    private Boolean statusGetFreePointSMS;
    private String dateLastCall;
    private String dateRegister;
    private Boolean usingChat;
    private Boolean usingVoiceCall;
    private Boolean usingVideoCall;
    private Boolean usingSexyAlbum;
    private Boolean usingFavorite;
    private Boolean usingSearch;
    private String group;
    private Boolean isRejoin;
}

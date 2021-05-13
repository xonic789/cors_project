package ml.market.cors.domain.security.oauth.enums;

public enum eSocialType {
    NORMAL("normal"),
    GOOGLE("google"),
    KAKAO("kakao"),
    NAVER("naver");

    private final String ROLE_PREFIX = "ROLE_";
    private String name;

    eSocialType(String name) { this.name = name; }

    public String getRoleType() { return ROLE_PREFIX + name.toUpperCase(); }

    public String getValue() { return name; }

    public boolean isEquals(String authority) { return this.getRoleType().equals(authority);}
}

package top.whiteleaf03.api.util;

import top.whiteleaf03.api.modal.entity.User;

import java.util.Date;

public class Guest {
    public static final Long ID = -1L;
    public static final String UID = "-1";
    public static final String NICKNAME = "游客账号";
    public static final String ACCOUNT = "guest";
    public static final String AVATAR = null;
    public static final Integer GENDER = -1;
    public static final String ROLE = "guest";
    public static final String PASSWORD = null;
    public static final String EMAIL = "whiteleaf03@163.com";
    public static final String GUEST_ACCESS_KEY = "GUEST_ACCESS_KEY";
    public static final String GUEST_SECRET_KEY = "GUEST_SECRET_KEY";
    public static final User ENTITY = new User(ID, NICKNAME, ACCOUNT, AVATAR, GENDER, ROLE, PASSWORD, EMAIL, GUEST_ACCESS_KEY, GUEST_SECRET_KEY, new Date(), new Date(), 0);
}

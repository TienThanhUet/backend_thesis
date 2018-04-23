package com.stadio.common.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andy on 10/08/2017.
 */
public enum RoleType
{
    ROOT(0),
    ADMIN(1),
    CONTENT_MANAGER(2),
    User(3);

    private final int code;

    private static RoleType[] allValues = values();

    private RoleType(int code) {
        this.code = code;
    }

    public int toInt() {
        return code;
    }

    public static RoleType fromInt(Integer code) {
        return allValues[code];
    }

    public static List<Integer> allIntegerValue() {
        List<Integer> allCode = new ArrayList<>();
        for (RoleType roleType: allValues) {
            allCode.add(roleType.toInt());
        }
        return allCode;
    }

    public static List<Integer> allIntegerValueExceptUser() {
        List<Integer> codes = RoleType.allIntegerValue();
        codes.remove(RoleType.User.toInt());
        return codes;
    }

    public String toStringCode() {
        return String.valueOf(code);
    }

    public String toStringDefine() {
        switch (code) {
            case 0:
                return "ROOT";
            case 1:
                return "ADMIN";
            case 2:
                return "CONTENT_MANAGER";
            default:
                return "USER";
        }
    }


    public static Boolean isCanCreateNewUserRole(RoleType currentUserRole, String createUserRole)
    {
        if (RoleType.ROOT == currentUserRole)
        {
            return true;
        }

        if (RoleType.ADMIN == currentUserRole && (RoleType.CONTENT_MANAGER.toInt() == Integer.valueOf(createUserRole)))
        {
            return true;
        }

        return false;
    }

    public static Boolean isCanDeleteManager(RoleType currentUserRole)
    {
        if (RoleType.ROOT == currentUserRole)
        {
            return true;
        }

        return false;
    }
}

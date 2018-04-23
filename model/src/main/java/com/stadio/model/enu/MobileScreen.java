package com.stadio.model.enu;



public enum MobileScreen
{
    HOME,
    EXAM_LIST,
    PROFILE,
    MESSAGE,
    EXAM_DETAILS;

    public static boolean equals(String vk)
    {
        for (MobileScreen mk: values())
        {
            if (mk.name().equals(vk))
            {
                return true;
            }
        }
        return false;
    }

    public static MobileScreen find(String vk)
    {
        for (MobileScreen mk: values())
        {
            if (mk.name().equals(vk))
            {
                return mk;
            }
        }
        return null;
    }
}


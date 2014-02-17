package com.sfxcode.sapphire.core.cdi;

import javax.enterprise.inject.Instance;


public class CdiHelper {

    public static <T> Instance<T> selectByClass(Instance<T> instance, Class<?> param)  {
        return instance.select((Class<T>)param);
    }
}

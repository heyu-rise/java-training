package org.heyu.homework0709.database;

import org.heyu.homework0709.dao.IOrderMapper;

/**
 * @author heyu
 * @date 2021/8/10
 */
public class SqlTypeUtils {

    private static ThreadLocal<IOrderMapper> threadLocal = new ThreadLocal<>();

    public static void setValue(IOrderMapper iOrderMapper) {
        threadLocal.set(iOrderMapper);
    }

    public static IOrderMapper getValue() {
        return threadLocal.get();
    }

    public static void remove(){
        threadLocal.remove();
    }

}

package cn.iocoder.yudao.module.meals.dal.redis;

/**
 * @Title: RedisKeyConstants
 * @Author InsectMk
 * @Package cn.iocoder.yudao.module.meals.dal.redis
 * @Date 2025/4/29 11:07
 * @description: Redis缓存key
 */
public interface RedisKeyConstants {
    /**
     * 静态字典list的缓存
     * <p>
     * KEY 格式：static_dict_list:{type}
     * VALUE 数据类型：String 字典类型集合集合
     */
    String STATIC_DICT_LIST = "static_dict_list:%s";
}

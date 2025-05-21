package cn.iocoder.yudao.module.meals.dal.redis.staticdict;

import cn.iocoder.yudao.framework.common.biz.system.dict.dto.DictDataRespDTO;
import cn.iocoder.yudao.framework.common.util.collection.CollectionUtils;
import cn.iocoder.yudao.framework.common.util.json.JsonUtils;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

import static cn.iocoder.yudao.module.meals.dal.redis.RedisKeyConstants.STATIC_DICT_LIST;

/**
 * @Title: StaticDictDAO
 * @Author InsectMk
 * @Package cn.iocoder.yudao.module.meals.dal.redis.staticdict
 * @Date 2025/4/29 11:11
 * @description: 静态list缓存DAO
 */
@Repository
public class StaticDictDAO {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 根据字典名获取字典数据
     * @param dictType 字典名
     * @return 字典数据
     */
    public List<DictDataRespDTO> get(String dictType) {
        String redisKey = formatKey(dictType); // 格式化token
        // 解析字符串，转为字典数据列表
        return JsonUtils.parseArray(stringRedisTemplate.opsForValue().get(redisKey), DictDataRespDTO.class);
    }

    /**
     * 设置静态字典缓存
     * @param dictType 字典名
     * @param dictDataList 字典数据
     */
    public void set(String dictType, List<DictDataRespDTO> dictDataList) {
        String redisKey = formatKey(dictType); // 格式化字典缓存token
        // 设置缓存，永不过期
        stringRedisTemplate.opsForValue().set(redisKey, JsonUtils.toJsonString(dictDataList));
    }

    /**
     * 设置静态字典缓存
     * @param dictType 字典名
     * @param dictDataList 字典数据
     */
    public void set(String dictType, DictDataRespDTO[] dictDataList) {
        String redisKey = formatKey(dictType); // 格式化字典缓存token
        // 设置缓存，永不过期
        stringRedisTemplate.opsForValue().set(redisKey, JsonUtils.toJsonString(dictDataList));
    }

    /**
     * 删除静态字典缓存
     * @param dictType 字典名
     */
    public void delete(String dictType) {
        String redisKey = formatKey(dictType); // 获取缓存token
        stringRedisTemplate.delete(redisKey); // 删除
    }

    /**
     * 删除多个字段缓存
     * @param dictTypes 字典缓存名
     */
    public void deleteList(Collection<String> dictTypes) {
        List<String> redisKeys = CollectionUtils.convertList(dictTypes, StaticDictDAO::formatKey); // 格式化缓存token
        stringRedisTemplate.delete(redisKeys);
    }

    /**
     * 格式化Token
     * @param dictType 字典名
     * @return 格式化好的Token
     */
    private static String formatKey(String dictType) {
        return String.format(STATIC_DICT_LIST, dictType);
    }
}

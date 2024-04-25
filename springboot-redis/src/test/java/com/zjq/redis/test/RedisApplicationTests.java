package com.zjq.redis.test;

import com.alibaba.fastjson.JSON;
import com.zjq.redis.entity.Car;
import com.zjq.redis.entity.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis使用案例测试
 * @author zjq
 * @date 2022-10-12
 */
@SpringBootTest
public class RedisApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void redisConnectTest(){
        //心跳检测连接情况
        String pong = redisTemplate.getConnectionFactory().getConnection().ping();
        System.out.println("pong="+pong);
    }

    /**
     * Redis String类型测试
     * @throws InterruptedException
     */
    @Test
    public void redisStringGetSetTest() throws InterruptedException {
        // 设置值，默认不过期
        redisTemplate.opsForValue().set("userName","共饮一杯无");
        // 获取值
        String userName = (String) redisTemplate.opsForValue().get("userName");
        System.out.println("获取userName对应的值="+userName);

        // 设置值并且设置2秒过期时间，过期之后自动删除
        redisTemplate.opsForValue().set("verificationCode", "666888", 2, TimeUnit.SECONDS);
        Thread.sleep(1000);
        System.out.println("获取验证码过期时间（单位秒）：" + redisTemplate.getExpire("verificationCode"));
        System.out.println("获取验证码对应的值：" +  redisTemplate.opsForValue().get("verificationCode"));
        Thread.sleep(1000);
        System.out.println("获取验证码对应的值：" +  redisTemplate.opsForValue().get("verificationCode"));

        // 删除key
        Boolean result = redisTemplate.delete("userName");
        System.out.println("删除userName结果：" +  result);
    }


    /**
     * Redis Object类型测试
     * @throws Exception
     */
    @Test
    public void redisObjectTest() throws Exception {
        // 设置对象值，并且2秒自动过期
        Person person = new Person();
        person.setName("共饮一杯无");
        person.setId(1);
        person.setAge(18);
        savePerson(person);

        //获取对象值
        Person personResult = getPerson(1L);
        System.out.println(personResult.toString());
        System.out.println("获取person过期时间（单位秒）：" + redisTemplate.getExpire("person"));


        //删除key
        Boolean deleteValue = redisTemplate.delete("person");
        System.out.println("删除person结果：" +  deleteValue);
    }

    /**
     * 将Person对象存入Redis，键为person:{id}，过期时间为2秒
     *
     * @param person 待存储的Person对象
     */
    public void savePerson(Person person) {
        String key = "person:" + person.getId();
        redisTemplate.opsForValue().set(key, person, 2, TimeUnit.HOURS);
    }

    /**
     * 从Redis中获取Person对象，键为person:{id}
     *
     * @param id Person对象的唯一标识符
     * @return 如果存在则返回对应的Person对象，否则返回null
     */
    public Person getPerson(Long id) {
        String key = "person:" + id;
        return (Person) redisTemplate.opsForValue().get(key);
    }

    /**
     * Redis Hash类型测试
     */
    @Test
    public void redisHashTest() throws Exception {
        // 向hash中添加数据
        HashOperations<String, String, Integer> operations = redisTemplate.opsForHash();
        //Hash 中新增元素。
        operations.put("score", "张三", 2);
        operations.put("score", "李四", 1);
        operations.put("score", "王五", 3);
        operations.put("score", "赵六", 4);

        Boolean hasKey = operations.hasKey("score", "张三");
        System.out.println("检查是否存在【score】【张三】：" + hasKey);
        Integer value = operations.get("score", "张三");
        System.out.println("获取【score】【张三】的值：" + value);
        Set<String> keys = operations.keys("score");
        System.out.println("获取hash表【score】所有的key集合：" + JSON.toJSONString(keys));
        List<Integer> values = operations.values("score");
        System.out.println("获取hash表【score】所有的value集合：" + JSON.toJSONString(values));
        Map<String,Integer> map = operations.entries("score");
        System.out.println("获取hash表【score】下的map数据：" + JSON.toJSONString(map));
        Long delete = operations.delete("score", "李四");
        System.out.println("删除【score】中key为【李四】的数据：" + delete);
        Boolean result = redisTemplate.delete("score");
        System.out.println("删除整个key：" + result);

    }


    /**
     * Redis List类型测试
     */
    @Test
    public void redisListTest() {
        // 向列表中添加数据
        ListOperations<String, Person> operations = redisTemplate.opsForList();
        // 往List左侧插入一个元素
        operations.leftPush("userList", new Person(2,"共饮一杯无",19));
        operations.leftPush("userList", new Person(1,"共饮一杯无",18));
        //往 List 右侧插入一个元素
        operations.rightPush("userList", new Person(3,"共饮一杯无",20));
        operations.rightPush("userList", new Person(4,"共饮一杯无",21));
        // 获取List 大小
        Long size = operations.size("userList");
        System.out.println("获取列表总数：" + size);
        //遍历整个List
        List<Person> allPerson1 = operations.range("userList", 0, size);
        System.out.println("遍历列表所有数据：" + JSON.toJSONString(allPerson1));
        //遍历整个List，-1表示倒数第一个即最后一个
        List<Person> allPerson2 = operations.range("userList", 0, -1);
        System.out.println("遍历列表所有数据：" + JSON.toJSONString(allPerson2));
        //从 List 左侧取出第一个元素，并移除
        Object Person1 = operations.leftPop("userList", 200, TimeUnit.MILLISECONDS);
        System.out.println("从左侧取出第一个元素并移除：" + Person1.toString());
        //从 List 右侧取出第一个元素，并移除
        Object Person2 = operations.rightPop("userList", 200, TimeUnit.MILLISECONDS);
        System.out.println("从右侧取出第一个元素并移除：" + Person2.toString());

    }

    /**
     * Redis Set类型测试
     */
    @Test
    public void redisSetTest() throws Exception {
        // 向集合中添加数据
        SetOperations<String, String> operations = redisTemplate.opsForSet();
        //向集合中添加元素,set元素具有唯一性
        operations.add("city", "北京","上海", "广州", "深圳", "杭州");
        Long size = operations.size("city");
        System.out.println("获取集合总数：" + size);
        //判断是否是集合中的元素
        Boolean isMember = operations.isMember("city", "广州");
        System.out.println("检查集合中是否存在指定元素：" + isMember);
        Set<String> cityNames = operations.members("city");
        System.out.println("获取集合所有元素：" + JSON.toJSONString(cityNames));
        Long remove = operations.remove("city", "广州");
        System.out.println("删除指定元素结果：" + remove);
        //移除并返回集合中的一个随机元素
        String cityName = operations.pop("city");
        System.out.println("移除并返回集合中的一个随机元素：" + cityName);
    }
}

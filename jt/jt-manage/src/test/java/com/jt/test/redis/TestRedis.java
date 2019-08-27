package com.jt.test.redis;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class TestRedis {

	/**
	 * 测试String类型操作
	 * 1.防火墙关闭  2.IP绑定注释  3.保护模式关闭  
	 */
	@Test
	public void test01() {
		Jedis jedis = new Jedis("192.168.15.129", 6379);

		//1.设定key值
		//jedis.set("1904","1904班redis学习");
		String result = jedis.get("1904");

		//2.为key设定超时时间
		//jedis.expire("1904", 100);

		//3.set数据 同时为数据添加超时时间
		jedis.setex("1904", 100, "1904班redis学习");

		//4.需要将key中的值覆盖
		jedis.set("1904","1904班快毕业了");
		System.out.println(jedis.get("1904"));

		//5.删除redis数据
		jedis.del("1904");

		//6.如果当前key已经存在,则不能修改.
		jedis.setnx("1904", "年薪百万");
		System.out.println("获取修改之后的值:"+jedis.get("1904"));

		//7:1.添加超时时间   2.不允许重复操作
		jedis.set("1904A","今天中午吃什么??", "NX", "EX", 50);
		System.out.println(jedis.get("1904A"));
	}

	/**
	 * 操作Hash
	 */
	@Test
	public void testHash() {
		Jedis jedis = new Jedis("192.168.15.129", 6379);
		jedis.hset("person","id", "100");
		jedis.hset("person","name", "人");
		jedis.hset("person","age", "18");
		System.out.println(jedis.hgetAll("person"));
	}

	/**
	 * 测试List集合
	 * 问:该方法执行到第五次获取的结果是??
	 * 答案: 1
	 */
	@Test
	public void testList() {
		Jedis jedis = new Jedis("192.168.15.129", 6379);
		jedis.lpush("list", "1","2","3","4");
		System.out.println(jedis.rpop("list"));
	}

	@Test
	public void testTx() {
		Jedis jedis = new Jedis("192.168.15.129", 6379);
		//开启事物
		Transaction transaction = jedis.multi();
		try {
			//业务操作
			transaction.set("aa", "aaa");
			transaction.set("bb", "bbb");
			transaction.set("cc", "ccc");
			//int a = 1/0;
			//事物提交
			transaction.exec();
		} catch (Exception e) {
			//事物回滚
			transaction.discard();
		}
	}
}

package net.blogjava.welldoer.repository;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import net.blogjava.welldoer.entity.User;

@Component
public class UserRedis {
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	public void add(String key, Long time, User user) {
		Gson gson = new Gson();
		redisTemplate.opsForValue().set(key, gson.toJson(user), time, TimeUnit.MINUTES);
	}
	
	public void add(String key, Long time, List<User> users) {
		Gson gson = new Gson();
		redisTemplate.opsForValue().set(key, gson.toJson(users), time, TimeUnit.MINUTES);
	}
	
	public User get(String key) {
		Gson gson = new Gson();
		User user = null;
		String userJson = redisTemplate.opsForValue().get(key);
		if (!StringUtils.isEmpty(userJson))
			user = gson.fromJson(userJson, User.class);

		return user;
	}
	
	public List<User> getList(String key) {
		Gson gson = new Gson();
		List<User> users = null;
		String listJson = redisTemplate.opsForValue().get(key);
		if (!StringUtils.isEmpty(listJson))
			users = gson.fromJson(listJson, new TypeToken<List<User>>() {}.getType());
		
		return users;
	}
	
	public void delete(String key) {
		redisTemplate.opsForValue().getOperations().delete(key);
	}
}

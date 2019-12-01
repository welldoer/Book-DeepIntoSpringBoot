package net.blogjava.welldoer.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import net.blogjava.welldoer.entity.UserMongo;

public interface UserMongoRepository extends MongoRepository<UserMongo, String> {

}

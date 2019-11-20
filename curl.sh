curl https://start.spring.io/starter.tgz \
 -d type=gradle-project -d language=java \
 -d dependencies=web,data-jpa,data-redis,data-mongodb,data-neo4j,h2,actuator \
 -d groupId=net.blogjava.welldoer -d artifactId=Book-DeepIntoSpringBoot \
  | tar -xzvf -

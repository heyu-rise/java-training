# 第五周作业说明

## 作业2

> 写代码实现 Spring Bean 的装配

#### 作业地址

```http
https://github.com/heyu-rise/java-training/tree/main/week05/homework2
```

> 我写了三种方式

- `xml`配置方式

```xml
    <bean id="student1" class="org.heyu.spring.model.Student">
        <property name="id" value="123" />
        <property name="name" value="KK123" />
</bean>
```

- `component-scan`加注解方式

```xml
<context:component-scan base-package="org.heyu.spring" />
```

- `@Bean`注解方式

```java
@Configuration
public class Config {

    @Bean(name = "student2")
    public Student student2() {
        Student student = new Student();
        student.setId(2);
        student.setName("2");
        return student;
    }

}
```

## 作业8

> 给前面课程提供的 Student/Klass/School 实现自动配置和 Starter

#### starter地址

```java
https://github.com/heyu-rise/java-training/tree/main/week05/bean-starter
```

## 作业10

> JDBC 接口和数据库连接池

#### 作业地址

```http
https://github.com/heyu-rise/java-training/tree/main/week05/homework10
```

> 作业写在测试目录下

```http
https://github.com/heyu-rise/java-training/tree/main/week05/homework10/src/test/java/org/heyu/homework10
```


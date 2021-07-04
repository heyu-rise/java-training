# java-training
> Java训练营作业

## week01第一周作业

#### 1、使用 HttpClient 或 OkHttp访问 [http://127.0.0.1:8801](http://127.0.0.1:8801)

```http
https://github.com/heyu-rise/java-training/blob/main/week02gcnio/src/main/java/WithHttpClient.java
```

```http
https://github.com/heyu-rise/java-training/blob/main/week02gcnio/src/main/java/WithOkHttp.java
```

#### 2、不同 GC 和堆内存的总结

- 使用SerialGC进行垃圾回收，相比使用ParallelGC来说单次回收用时更长，在堆内存最大为1g的时候，单词mirrorGc耗时约为50ms, 是ParallelGC的3-4倍。在堆内存最大为4g的时候，垃圾回收次数会减少，但是单次mirrorGc耗时长达100ms是ParallelGC的一倍。由此可见SerialGC只适用于小型程序，在大堆内存的Java应用中使用SerialGC对应用性能会有较大影响。
- 使用ParallelGC和CMSGC时，他们在mirrorGC的时候，ParallelGC处理时间相对较短，但是在发生fullGC的时候，CMSGC处理时间相对较短。大小内存都是如此。
- 在使用G1GC的时候，mirrorGC和fullGC时间都相对较短，但是会处理多次。所以在大堆内存的情况下，为了单次的gc时间减少，可以配置G1GC。


import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author heyu
 * @date 2021/7/12
 */
public class Homework {


    public static void main(String[] args) throws ExecutionException, InterruptedException, BrokenBarrierException {
//        join();
//        futureTask();
//        synchronousQueue();
//        countDownLatch();
//        semaphore();
//        lockCondition();
//        synchronizedWait();
//        completableFuture();
//        forkJoinPool();
//        cyclicBarrier();
    }

    private static void cyclicBarrier() throws BrokenBarrierException, InterruptedException {
        AtomicInteger x = new AtomicInteger();
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
        new Thread(() -> {
            try {
                x.set(sum());
            } finally {
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        cyclicBarrier.await();
        System.out.println(x);
    }

    private static void forkJoinPool() throws InterruptedException, ExecutionException {
        ForkJoinPool pool = new ForkJoinPool(1);
        ForkJoinTask<Integer> task = pool.submit(new RecursiveTask<Integer>() {
            @Override
            protected Integer compute() {
                return sum();
            }
        });
        Integer x = task.get();
        pool.shutdown();
        System.out.println(x);
    }

    private static void completableFuture() {
        CompletableFuture<Integer> f0 = CompletableFuture.supplyAsync(Homework::sum);
        System.out.println(f0.join());
    }

    private static void synchronizedWait() throws InterruptedException {
        AtomicInteger x = new AtomicInteger();
        Object lock = new Object();
        new Thread(() -> {
            synchronized (lock) {
                try {
                    x.set(sum());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.notifyAll();
                }
            }
        }).start();
        synchronized (lock) {
            lock.wait();
        }
        System.out.println(x.get());
    }

    private static void lockCondition() throws InterruptedException {
        AtomicInteger x = new AtomicInteger();
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        new Thread(() -> {
            lock.lock();
            try {
                x.set(sum());
                condition.signalAll();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }).start();
        lock.lock();
        try {
            condition.await();
        } finally {
            lock.unlock();
        }
        System.out.println(x.get());
    }

    private static void semaphore() throws InterruptedException {
        AtomicInteger x = new AtomicInteger();
        Semaphore semaphore = new Semaphore(0);
        new Thread(() -> {
            try {
                x.set(sum());
            } finally {
                semaphore.release();
            }
        }).start();
        semaphore.acquire();
        System.out.println(x.get());
    }

    private static void countDownLatch() throws InterruptedException {
        AtomicInteger x = new AtomicInteger();
        CountDownLatch latch = new CountDownLatch(1);
        new Thread(() -> {
            try {
                x.set(sum());
            } finally {
                latch.countDown();
            }
        }).start();
        latch.await();
        System.out.println(x.get());
    }

    private static void synchronousQueue() throws InterruptedException {
        SynchronousQueue<Integer> synchronousQueue = new SynchronousQueue<>();
        new Thread(() -> {
            try {
                synchronousQueue.put(sum());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        int x = synchronousQueue.take();
        System.out.println(x);
    }

    private static void futureTask() throws ExecutionException, InterruptedException {
        FutureTask<Integer> futureTask = new FutureTask<>(Homework::sum);
        new Thread(futureTask).start();
        int x = futureTask.get();
        System.out.println(x);
    }

    private static void join() throws InterruptedException {
        AtomicInteger x = new AtomicInteger();
        Thread thread = new Thread(() -> {
            x.set(sum());
        });
        thread.start();
        thread.join();
        System.out.println(x.get());
    }

    private static int sum() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return fibo(36);
    }

    private static int fibo(int a) {
        if (a < 2) {
            return 1;
        }
        return fibo(a - 1) + fibo(a - 2);
    }

}

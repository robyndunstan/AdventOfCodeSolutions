package tools;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

/*
 * // Wait for all threads to finish
for (Thread thread : threads) {
    try {
        thread.join();
    } catch (InterruptedException e) {
        // Handle interruption if needed
        e.printStackTrace();
    }
}
 */

public class SimpleParallelQueue {
    // Mostly copied from https://math.hws.edu/javanotes/source/chapter12/MultiprocessingDemo2.java
    private WorkerThread[] workers; // the threads that do the tasks
    ConcurrentLinkedQueue<Runnable> taskQueue; // holds individual tasks
    private volatile int threadsRunning; // how many threads are still running?
    private volatile boolean running;  // used to signal the thread to abort
    private int threadCount;

    public SimpleParallelQueue(int threadCount) {
        running = false;
        taskQueue = new ConcurrentLinkedQueue<>();
        this.threadCount = threadCount;
    }

    public void cancel() {
        running = false;
    }

    public void start() {
        running = true;  // Set the signal variable before starting the threads!
        threadsRunning = threadCount;  // Records how many of the threads are still running
        workers = new WorkerThread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            workers[i] = new WorkerThread();
            try {
                workers[i].setPriority( Thread.currentThread().getPriority() - 1 );
            }
            catch (Exception e) {
            }
            workers[i].start();
        }
    }

    public void await() throws InterruptedException {
        if (running) {
            for (Thread t : workers) {
                t.join();
            }
        }
    }
    
    public void setTasks(ArrayList<Runnable> tasks) {
        taskQueue = new ConcurrentLinkedQueue<>();
        taskQueue.addAll(tasks);
    }

    public void addTask(Runnable task) {
        taskQueue.add(task);
    }

    /**
     * This class defines the worker threads that carry out the tasks. 
     * A WorkerThread runs in a loop in which it retrieves a task from the 
     * taskQueue and calls the run() method in that task.  The thread 
     * terminates when the queue is empty.  (Note that for this to work 
     * properly, all the tasks must be placed into the queue before the
     * thread is started.  If the queue is empty when the thread starts,
     * the thread will simply exit immediately.)  The thread also terminates
     * if the signal variable, running, is set to false.  Just before it
     * terminates, the thread calls the threadFinished() method.
     */
    private class WorkerThread extends Thread {
        public void run() {
            try {
                while (running) {
                    Runnable task = taskQueue.poll();
                    if (task == null)
                        break;
                    task.run();
                }
            }
            finally {
                threadFinished();
            }
        }
    }

    /**
     * This method is called by each thread when it terminates.  We keep track
     * of the number of threads that have terminated, so that when they have
     * all finished, we can put the program into the correct state.
     */
    synchronized private void threadFinished() {
        threadsRunning--;
        if (threadsRunning == 0) { // all threads have finished
            running = false; // Make sure running is false after the thread ends.
            workers = null;
        }
    }
}

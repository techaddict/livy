/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cloudera.livy;

import java.net.URI;
import java.util.concurrent.Future;

/**
 * A client for submitting Spark-based jobs to a Livy backend.
 */
public interface LivyClient {

  /**
   * Submits a job for asynchronous execution.
   *
   * @param job The job to execute.
   * @return A handle that be used to monitor the job.
   */
  <T> JobHandle<T> submit(Job<T> job);

  String clientId();

  /**
   * Asks the remote context to run a job immediately.
   * <p/>
   * Normally, the remote context will queue jobs and execute them based on how many worker
   * threads have been configured. This method will run the submitted job in the same thread
   * processing the RPC message, so that queueing does not apply.
   * <p/>
   * It's recommended that this method only be used to run code that finishes quickly. This
   * avoids interfering with the normal operation of the context.
   * <p/>
   * Note: the {@link JobContext#monitor()} functionality is not available when using this method.
   *
   * @param job The job to execute.
   * @return A future to monitor the result of the job.
   */
  <T> Future<T> run(Job<T> job);

  /**
   * Stops the remote context.
   *
   * Any pending jobs will be cancelled, and the remote context will be torn down.
   */
  void stop();

  /**
   * Adds a jar file to the running remote context.
   *
   * Note that the URL should be reachable by the Spark driver process. If running the driver
   * in cluster mode, it may reside on a different host, meaning "file:" URLs have to exist
   * on that node (and not on the client machine).
   *
   * @param uri The location of the jar file.
   * @return A future that can be used to monitor the operation.
   */
  Future<?> addJar(URI uri);

  /**
   * Adds a file to the running remote context.
   *
   * Note that the URL should be reachable by the Spark driver process. If running the driver
   * in cluster mode, it may reside on a different host, meaning "file:" URLs have to exist
   * on that node (and not on the client machine).
   *
   * @param uri The location of the file.
   * @return A future that can be used to monitor the operation.
   */
  Future<?> addFile(URI uri);

}

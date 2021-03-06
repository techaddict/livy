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
package com.cloudera.livy.spark.client

import java.net.URI

sealed trait ClientMessage

case class JobMessage(job: String) extends ClientMessage

case class CreateClientRequest(
  timeout: Long,
  sparkConf: Map[String, String] = Map()
) extends ClientMessage

case class SerializedJob(job: Array[Byte]) extends ClientMessage

case object Stop extends ClientMessage

case class AddJar(uri: URI) extends ClientMessage

case class AddFile(uri: URI) extends ClientMessage

case class JobSubmitted(id: Long) extends ClientMessage

case class JobStatus(id: Long) extends ClientMessage

case class JobCompleted(id: Long) extends ClientMessage

case class JobFailed(id: Long) extends ClientMessage

case class JobResult(id: Long, result: Any) extends ClientMessage

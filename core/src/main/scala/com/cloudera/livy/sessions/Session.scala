/*
 * Licensed to Cloudera, Inc. under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  Cloudera, Inc. licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cloudera.livy.sessions

import scala.concurrent.Future

trait Session {
  def id: Int

  def lastActivity: Option[Long] = None

  def stoppedTime: Option[Long] = {
    state match {
      case SessionState.Error(time) => Some(time)
      case SessionState.Dead(time) => Some(time)
      case SessionState.Success(time) => Some(time)
      case _ => None
    }
  }

  def state: SessionState

  def stop(): Future[Unit]

  def logLines(): IndexedSeq[String]
}

/*
 * Copyright Camunda Services GmbH and/or licensed to Camunda Services GmbH
 * under one or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information regarding copyright
 * ownership. Camunda licenses this file to you under the Apache License,
 * Version 2.0; you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zjq.bpm;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

/**
 * 此处拒绝只是通过 sysout 完成的。您可以在此处实现向作者发送邮件。
 * 为此使用您自己的邮件机制或使用您的应用程序服务器功能。
 * @author zjq
 */
@Service("emailAdapter")
public class RejectionNotificationDelegate implements JavaDelegate {

  @Override
  public void execute(DelegateExecution execution) throws Exception {
    String content = (String) execution.getVariable("content");
    String comments = (String) execution.getVariable("comments");

    System.out.println("你好!\n\n"
           + "抱歉，您的推文已被拒绝。\n\n"
           + "创作内容: " + content + "\n\n"
           + "审核结果: " + comments + "\n\n"
           + "请下次尝试更好的内容 :-)");
  }

}

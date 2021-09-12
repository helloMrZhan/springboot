/*
 * Copyright 2019-2029 geekidea(https://github.com/geekidea)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
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

package com.zjq.aop.bean;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>Filter请求详细信息</p>
 *
 * @author zjq
 * @date 2020/12/21
 */
@Data
@Accessors(chain = true)
public class RequestDetail implements Serializable {
    private static final long serialVersionUID = 2577641512850125440L;

    /**
     * 请求ip地址
     */
    private String ip;

    /**
     * 请求路径
     */
    private String path;

    /**
     * 请求参数
     */
    private String params;
}

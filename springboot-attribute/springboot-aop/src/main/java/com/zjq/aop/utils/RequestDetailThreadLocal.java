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

package com.zjq.aop.utils;

import com.zjq.aop.bean.RequestDetail;

/**
 * <p>记录请求详情信息到当前线程中，可在任何地方获取</p>
 *
 * @author zjq
 * @date 2021/8/21
 */
public class RequestDetailThreadLocal {

    private static final ThreadLocal<RequestDetail> THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 设置请求信息到当前线程中
     *
     * @param requestDetail
     */
    public static void setRequestDetail(RequestDetail requestDetail) {
        THREAD_LOCAL.set(requestDetail);
    }

    /**
     * 从当前线程中获取请求信息
     */
    public static RequestDetail getRequestDetail() {
        return THREAD_LOCAL.get();
    }

    /**
     * 销毁
     */
    public static void remove() {
        THREAD_LOCAL.remove();
    }

}

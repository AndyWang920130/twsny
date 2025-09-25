// src/utils/api.ts
import axios from "axios";
import qs from 'qs';
import type { AxiosInstance, AxiosRequestConfig, AxiosRequestHeaders, AxiosResponse } from 'axios';

/**
 * 通用 API 响应包装（根据你后端的返回结构调整）
 * 假设后端返回 { code: number, message: string, data: T }
 */
export interface ApiResponse<T = any> {
  code: number;
  message?: string;
  data?: T;
}

/** 请求选项（都可选） */
export interface RequestOptions {
  /** 是否返回整个 AxiosResponse 而不是 data */
  wholeResponse?: boolean;
  /** 额外 header */
  headers?: Record<string, string>;
  /** 超时时间（ms），覆盖默认 */
  timeout?: number;
  /** 自定义 AbortSignal 用于取消请求（优于 CancelToken） */
  signal?: AbortSignal;
  /** 是否抛出非 2xx 的错误（默认 true）*/
  throwOnError?: boolean;
}

/** 自定义错误类 */
export class ApiError extends Error {
  public status?: number;
  public code?: number;
  public data?: any;
  constructor(message: string, status?: number, code?: number, data?: any) {
    super(message);
    this.name = "ApiError";
    this.status = status;
    this.code = code;
    this.data = data;
  }
}

/** 创建 axios 实例的配置（可按环境注入） */
const DEFAULT_BASE_URL = import.meta.env.VITE_VUE_APP_API_URL || "/api";
const DEFAULT_TIMEOUT = 10_000; // ms

const axiosInstance: AxiosInstance = axios.create({
  baseURL: DEFAULT_BASE_URL,
  timeout: DEFAULT_TIMEOUT,
  // 可根据需要统一设置 withCredentials: true
  headers: {
    "Content-Type": "application/json",
  },
});

/** 存放 token（简单实现，可以和 Pinia/Vuex 联动） */
let accessToken: string | null = null;
export function setToken(token: string | null) {
  accessToken = token;
}

/** 请求拦截器：在 header 中设置 Authorization */
axiosInstance.interceptors.request.use(
  (config) => {
    if (!config.headers) config.headers = {} as AxiosRequestHeaders;
    if (accessToken) {
      config.headers["Authorization"] = `Bearer ${accessToken}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

/** 响应拦截器：统一处理后端返回结构/错误 */
axiosInstance.interceptors.response.use(
  (res: AxiosResponse) => {
    // 假设后端返回 ApiResponse<T>
    // 但也允许后端直接返回原始数据（不包裹）
    const data = res.data;
    if (data && typeof data === "object" && "code" in data) {
      const ar = data as ApiResponse;
      // 假设 code === 0 表示成功（请按你后端实际规则修改）
      if (ar.code === 0 || ar.code === 200) {
        return res;
      } else {
        // 将错误统一抛出
        throw new ApiError(ar.message || "请求返回错误", res.status, ar.code, ar);
      }
    }
    // 如果不是包装结构，直接返回
    return res;
  },
  (error) => {
    // 网络/超时/状态码错误等统一封装为 ApiError
    if (axios.isAxiosError(error)) {
      const status = error.response?.status;
      const msg =
        error.response?.data?.message ||
        error.message ||
        "网络或服务器错误";
      const code = error.response?.data?.code;
      return Promise.reject(new ApiError(msg, status, code, error.response?.data));
    }
    return Promise.reject(error);
  }
);

/** 内部通用请求函数 */
async function request<T = any>(
  config: AxiosRequestConfig,
  options?: RequestOptions
): Promise<T> {
  const { wholeResponse = false, headers, timeout, signal, throwOnError = true } = options || {};
  const cfg: AxiosRequestConfig = {
    ...config,
    headers: {
      ...(config.headers || {}),
      ...(headers || {}),
    },
    timeout: timeout ?? config.timeout ?? DEFAULT_TIMEOUT,
    signal, // axios v1 支持 AbortSignal
  };

  try {
    const res = await axiosInstance.request(cfg);
    if (wholeResponse) {
      return (res as unknown) as T;
    }

    // 如果后端用 ApiResponse 包装，则 unwrap data
    const body = res.data as ApiResponse<T> | T;
    if (body && typeof body === "object" && "data" in (body as any)) {
      return (body as any).data as T;
    }
    return body as T;
  } catch (err) {
    if (throwOnError) throw err;
    // 不抛出错误，则返回一个 rejected-like value or null
    return Promise.reject(err);
  }
}

/** 导出的便捷方法：GET/POST/PUT/DELETE/OPTIONS */
export const api = {
  get<T = any>(url: string, params?: any, options?: RequestOptions) {
    return request<T>(
      {
        url,
        method: "GET",
        params,
      },
      options
    );
  },

  post<T = any>(url: string, data?: any, params?: any, options?: RequestOptions) {
    // 自动处理 FormData 的 Content-Type
    const headers =
      data instanceof FormData ? { "Content-Type": "multipart/form-data" } : undefined;
    return request<T>(
      {
        url,
        method: "POST",
        data,
        params,
        headers,
      },
      options
    );
  },

  put<T = any>(url: string, data?: any, options?: RequestOptions) {
    const headers =
      data instanceof FormData ? { "Content-Type": "multipart/form-data" } : undefined;
    return request<T>(
      {
        url,
        method: "PUT",
        data,
        headers,
      },
      options
    );
  },

  delete<T = any>(url: string, params?: any, options?: RequestOptions) {
    return request<T>(
      {
        url,
        method: "DELETE",
        params,
      },
      options
    );
  },

  options<T = any>(url: string, params?: any, options?: RequestOptions) {
    return request<T>(
      {
        url,
        method: "OPTIONS",
        params,
      },
      options
    );
  },

  postForm<T = any>(url: string, data?: any, params?: any, options?: RequestOptions) {
    const headers = { 'Content-Type': 'application/x-www-form-urlencoded' };
    return request<T>(
      {
        url,
        method: 'POST',
        data: data ? qs.stringify(data) : undefined, // 序列化为 form-urlencoded
        params, // URL 参数
        headers
      },
      options
    );
  },

  /** 暴露底层 axios 实例以便高级定制（不建议随意修改拦截器） */
  instance: axiosInstance,
};

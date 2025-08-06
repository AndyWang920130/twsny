import dayjs, { Dayjs } from 'dayjs';

export interface UserInfoT {
    name: string
    nickName?: string
    avatar?: string
}

export interface UserProfileT {
  username: string
  nickname?: string
  avatar: string
  phone: string
  email?: string
  gender?: number
  birthday?: Dayjs
}

export interface UserProfileFormT {
  nickname: string
  avatar: string
  phone: string
  email?: string
  gender?: number
  birthday?: Dayjs
}

export interface PasswordChangeFormT {
  oldPassword: string;
  newPassword: string;
  confirmPassword: string;
}

export interface LoginFormT {
  username: string;
  password: string;
}


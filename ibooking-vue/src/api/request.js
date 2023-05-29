import {Service} from './Service'
export function login(data) {
  return Service({
    url: '/login',
    method: 'post',
    data
  })
}
export function register(data) {
  return Service({
    url: '/register',
    method: 'post',
    data: data
  })
}
// 用户获取
export function getStudent(data) {
  return Service({
    url: '/student',
    method: 'get',
    data: data
  })
}
// import axios from 'axios'
// 获取所有自习室信息
export function getStudyRoom(data) {
  // const baseURL = "http://localhost:8090/studyroom";
  // return axios.get(baseURL, data)
  return Service({
    url: '/studyroom',
    method: 'get',
    data: data
  })
}
// 更新自习室信息
export function renewStudyRoom(data) {
  return Service({
    url: '/studyroom',
    method: 'put',
    data: data
  })
}
export function addStudyRoom(data) {
  return Service({
    url: '/studyroom',
    method: 'post',
    data: data
  })
}
export function deleteStudyRoom(data) {
  return Service({
    url: '/studyroom/'+data,
    method: 'delete',
  })
}
// 用户删除
export function delStudent(data) {
  return Service({
    url: '/student/' + data,
    method: 'delete',
  })
}
// 进行预约
export function booking(data) {
  return Service({
    url: '/booking',
    method: 'post',
    data: data
  })
}
// 根据学号获取预约记录
export function bookingList(data) {
  return Service({
    url: '/booking/list/' + data,
    method: 'get',
  })
}
// 预约信息修改
export function bookingRenew(data) {
  return Service({
    url: '/booking/renew',
    method: 'put',
    data: data
  })
}
// 签到
export function bookingCheck(data) {
  return Service({
    url: '/booking/check/' + data,
    method: 'post',
  })
}
// 退预约
export function bookingDel(data) {
  return Service({
    url: '/booking/del/' + data,
    method: 'post',
  })
}
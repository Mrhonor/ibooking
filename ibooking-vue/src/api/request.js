import {Service} from './Service'
export function login(data) {
  return Service({
    url: '/login',
    method: 'post',
    data: data
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
// 获取开放中自习室信息
export function getStudyRoomOpen(data) {
  return Service({
    url: '/studyroom/opening',
    method: 'get',
    data: data
  })
}
// 获取自习室号对应的自习室信息
export function getStudyRoomId(data) {
  return Service({
    url: '/studyroom/'+data,
    method: 'get',
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
// 添加自习室
export function addStudyRoom(data) {
  return Service({
    url: '/studyroom',
    method: 'post',
    data: data
  })
}
// 删除自习室
export function deleteStudyRoom(data) {
  return Service({
    url: '/studyroom/'+data,
    method: 'delete',
  })
}
// 获取所有座位
export function getAllSeat() {
  return Service({
    url: '/seat',
    method: 'get',
  })
}
// 根据自习室id获取座位表
export function getSeat(data) {
  return Service({
    url: '/seat/'+data,
    method: 'get',
  })
}

// 根据座位id获取座位表
export function getSeatById(data) {
  return Service({
    url: '/seat/id/'+data,
    method: 'get',
  })
}

// 添加座位
export function addSeat(data) {
  return Service({
    url: '/seat',
    method: 'post',
    data:data,
  })
}
// 删除座位
export function deleteSeat(data) {
  return Service({
    url: '/seat',
    method: 'delete',
    data: data,
  })
}
// 根据座位id获取预约记录
export function getSeatBook(data) {
  return Service({
    url: '/booking/seat/'+data,
    method: 'get',
  })
}
// 获取所有预约记录
export function getAllBook() {
  return Service({
    url: '/booking',
    method: 'get',
  })
}

// 根据id获取预约记录
export function getBookById(data) {
  return Service({
    url: '/booking/'+data,
    method: 'get',
  })
}

// 根据学号查询预约记录
export function getStuBook(data) {
  return Service({
    url: '/booking/student/' + data,
    method: 'get',
    data:data
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
// 预约信息修改
export function bookingRenew(data) {
  return Service({
    url: '/booking',
    method: 'post',
    data: data
  })
}
// 签到
export function bookingCheck(data) {
  return Service({
    url: '/booking/attendance/' + data,
    method: 'Post',
  })
}
// 签退
export function bookingOutStu(data) {
  return Service({
    url: '/booking/signout/' + data,
    method: 'Post',
  })
}
// 退预约
export function bookingDel(data) {
  return Service({
    url: '/booking',
    method: 'delete',
    data: data,
  })
}
// 根据自习室ID和座位编号获取座位信息
export function getSeatByStudyRoomIDAndSeatNum(seatNum, studyRoomId) {
  return Service({
    url: '/seat/' + seatNum + '/' + studyRoomId,
    method: 'get',
  })
}
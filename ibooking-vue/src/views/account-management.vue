<template>
    <layout>
        <div>
            <el-button type="primary" @click="query">查询</el-button>
            <el-table :data="tableData" border style="width: 100%">
                <el-table-column type="index" label="序号" align="center" width="55px"/>
                <el-table-column prop="id" label="预约单号" align="center" />
                <el-table-column prop="buildingNum" label="自习室编号" align="center"/>
                <el-table-column prop="stuNum" label="学生学号" align="center"/>
                <el-table-column prop="day" type="date" label="预约日期" align="center"/>
                <el-table-column prop="start" type="datatime" label="预约开始时间" align="center"/>
                <el-table-column prop="end" type="datatime" label="预约结束时间" align="center"/>
                <el-table-column prop="status" label="签到状态" align="center"/>
                <el-table-column
                    fixed="right"
                    label="操作"
                    align="center"
                    width="100">
                    <template slot-scope="scope">
                        <el-button @click="bookStu(scope.row)" type="text" size="small" :disabled="timeToBook(scope.row)? true:false">签到</el-button>
                        <el-button @click="bookOutStu(scope.row)" type="text" size="small" :disabled="canBookOut(scope.row)? true:false">签退</el-button>
                        <el-button @click="unbookStu(scope.row)" type="text" size="small" :disabled="checkBookStatus(scope.row)? true:false" >退预约</el-button>
                        <el-button @click="rebook(scope.row)" type="text" size="small" :disabled="checkBookStatus(scope.row)? false:true" >再次预约</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <el-pagination
                @current-change="handleCurrentChange"
                :current-page="currentPage"
                :page-size="10"
                layout="total, prev, pager, next, jumper"
                :total="total">
            </el-pagination>
            <el-dialog title="重新预约自习室" :visible.sync="Visible" width="520px">
                <el-form :model="form">
                    <el-form-item label="预约时间" >
                      <el-date-picker
                        v-model="chooseDd"
                        type="date"
                        >
                        :default-value="form.returnTime"
                    </el-date-picker>
                    <el-time-select
                        placeholder="起始时间"
                        v-model="startTime"
                        :picker-options="{
                            start: this.roomSta,
                            step: '00:15',
                            end: this.roomEnd,
                        }"
                        @change="Limite(startTime)">
                    </el-time-select>
                    <span class="demonstration" style="margin-right:5px;margin-left:5px">至</span>
                    <el-time-select
                        placeholder="结束时间"
                        v-model="endTime"
                        :picker-options="{
                            start: this.roomSta,
                            step: '00:15',
                            end: this.roomEnd,
                            minTime: this.startTime,
                            maxTime: this.timeLimite
                        }">
                    </el-time-select>
                        <!-- <el-time-picker
                            v-model="form.time"
                            format="HH:mm:ss"
                            value-format="HH:mm:ss"
                            style="width: 380px"
                            is-range
                            range-separator="至"
                            start-placeholder="开始时间"
                            end-placeholder="结束时间"
                            placeholder="选择时间范围"
                        /> -->
                    </el-form-item>
                </el-form>
                <span slot="footer" class="dialog-footer">
                  <el-button type="primary" @click="sure">确 定</el-button>
                  <el-button @click="Visible = false">取 消</el-button>
                </span>
            </el-dialog>
        </div>
    </layout>
</template>
<script>
import layout from '@/components/layout-page.vue'
import {booking, getStuBook,bookingDel,bookingCheck,getAllBook,getBookById,getSeatById,getStudyRoomId,bookingOutStu } from '@/api/request'

export default ({
    components: {
        layout
    },
    data() {
      return {
        currentPage: 1,
        pagesize: 10,
        total: 10,
        
        Visible: false,
        chooseDd: '',
        start: '',
        end: '',
        startTime: '',
        endTime: '',
        tableData: [],
        seatId: null,
        studyRoomNum: null,
        buildingNumber : null,
        classRoomNumber : null,
        openStatus : null,
        roomSta : null,
        roomEnd : null,
        timeLimite : null,
        testdata:[
            {
            "id": 1,
            "seatId": 1,
            "startTime": "2023-05-29 10:00",
            "endTime": "2023-05-29 12:00",
            "isEnd": false,
            "isTimeout": false,
            "stuNum": "01010101",
            "name": "John Doe",
            "buildingNum": "Building1",
            "classRoomNum": "Class1"
            },
        ],
        form: {
            time: '',
            returnTime:[],
        },
      }
    },
    methods: {
        handleCurrentChange(page) {
            this.currentPage = page
            this.query()
        },
        Limite(startTime){
            let min = parseInt(startTime.split(":")[1]) + 1
            this.timeLimite = (parseInt(startTime)+4).toString().padStart(2,'0')+":"+min.toString().padStart(2,'0')
        },
        query() {
            const stu = localStorage.getItem('username');
            getStuBook(stu).then((res) => {
                
                if(res){
                    const oritableData = res
                    const statusMap = {
                        0: '未签到',
                        1: '已签到',
                        2: '违约',
                        4: '已签退'
                    };
                    // 在获取数据的地方，对 response.data 进行解析并赋值给 tableData
                    this.tableData = oritableData.map(item => ({
                    id: item.id,
                    seatId: item.seatId,
                    buildingNum: item.buildingNum,
                    stuNum: item.stuNum,
                    day: item.startTime.split(' ')[0], // 只获取日期部分
                    start: item.startTime.split(' ')[1], // 只获取时间部分
                    end: item.endTime.split(' ')[1], // 只获取时间部分
                    status: statusMap[item.status]
                    }));

                    for(let i = 0; i < this.tableData.length; i++){
                        getSeatById(this.tableData[i].seatId).then((res_seat)=>{
                            if(res_seat){
                                this.tableData[i].buildingNum = res_seat.studyRoomId

                            }
                        })

                    }
                }

                this.total = res.length 
            }
            ).catch((err) => {
                this.$message.success('没有预约记录')
                // console.log(err)
            })

        },
        sure() {
            if(!this.startTime || !this.endTime || !this.chooseDd ){
                this.$message.info('请选择开始时间和结束时间！')
                return
            }
            let day = this.chooseDd.getDate()
            let year = this.chooseDd.getFullYear()
            let mon = ("0" + (this.chooseDd.getMonth() + 1)).slice(-2);
            let date = year+"-"+mon+"-"+day

            let start = date + ' ' + this.startTime // + ':00'
            let end = date+ ' ' + this.endTime //+ ':00

            let data = {
                "seatId": this.seatId,
                "startTime": start,
                "endTime": end,
                "isEnd": false,
                "isTimeout": false,
                "stuNum": localStorage.getItem('username'),
                "name":"John Doe",
                "buildingNum": '',
                "classRoomNum": '',
                "status": 0,
            }
            console.log(data)
            booking(data).then((res) => {
                console.log(res)
                this.$message.success('预约成功')
                // this.$router.replace('/account/')
                // if (this.active++ > 3) this.active = 0;
                // next()
            }).catch(err=>{
                console.log(err)
                this.$message.success('预约失败')
            })
            // TODO
            this.query()
            this.Visible = false
        },
        checkBookStatus(val){

            if(val.status != "未签到"){
                return true
            }
            else{
                return false
            } 
        },
        timeToBook(val){

            if(val.status != "未签到"){
                return true
            }

            const currentDate = new Date();
            const currentTime = currentDate.getHours() + ':' + currentDate.getMinutes();

            // 获取当前日期
            const currentDay = currentDate.toISOString().split('T')[0];

            console.log(currentDay);
            console.log(val.day);
            // 判断日期和时间是否满足条件
            const isSameDay = val.day === currentDay;
            console.log(isSameDay)
            const isTimeInRange = currentTime >= val.start && currentTime <= val.end;
            // let now = new Date()
            if (isSameDay && isTimeInRange){
                return false
            }
            else{
                return true
            } 
        },
        canBookOut(val){

            if(val.status != "已签到"){
                return true
            }
            else{
                return false
            }

        },
        bookStu(val){
            bookingCheck(val.stuNum).then((res)=>{
                if(res){
                    this.query()
                    this.$message.success('签到成功')
                }
                else{
                    this.$message.success('已超过签到时间，签到失败！')
                }
            })
        },
        bookOutStu(val){
            bookingOutStu(val.stuNum).then((res)=>{
                if(res){
                    this.query()
                    this.$message.success('签退成功')
                }
                else{
                    this.$message.success('已超过签退时间，签退失败！')
                }
            })
        },
        unbookStu(val){
            this.$confirm('确定要退预约吗?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                let data={
                    id: val.id,
                    seatId: null,
                    startTime: null,
                    endTime: null,
                    isEnd: null,
                    isTimeout: null,
                    stuNum: null,
                    name: null,
                    buildingNum: null,
                    classRoomNum: null,
                }
                
                bookingDel(data).then((res) => {

                    this.query()
                    this.$message.success('退预约成功')
                })
            }).catch(() => {
                this.$message.info('已取消')
            })
        },
        rebook(val){
            this.$confirm('确定要重新预约吗?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                getBookById(val.id).then((res)=>{
                    if(res){
                        this.seatId = res.seatId
                        getSeatById(res.seatId).then((res_seat)=>{
                            if(res_seat){
                                this.studyRoomNum = res_seat.studyRoomId
                                getStudyRoomId(res_seat.studyRoomId).then((res_studyRoom)=>{
                                    if(res_studyRoom){
                                        this.buildingNumber = res_studyRoom.buildingNum
                                        this.classRoomNumber = res_studyRoom.classRoomNum
                                        this.openStatus = res_studyRoom.openStatus
                                        this.roomSta = res_studyRoom.startTime.split(':')[0] + ':' + res_studyRoom.startTime.split(':')[1]
                                        this.roomEnd = res_studyRoom.endTime.split(':')[0] + ':' + res_studyRoom.endTime.split(':')[1]
                                        if(this.openStatus == 1){
                                            this.Visible = true
                                        }
                                        else{
                                            this.$message.info('该自习室已关闭，预约失败！')
                                        }
                                    }
                                })
                            }
                        })

                    }
                })

                // let data={
                //     id: val.id,
                //     seatId: null,
                //     startTime: null,
                //     endTime: null,
                //     isEnd: null,
                //     isTimeout: null,
                //     stuNum: null,
                //     name: null,
                //     buildingNum: null,
                //     classRoomNum: null,
                // }
                
                // bookingDel(data).then((res) => {

                //     this.query()
                //     this.$message.success('退预约成功')
                // })
            }).catch(() => {
                this.$message.info('已取消')
            })
        },
    },
    mounted() {
        this.query()
    },
})
</script>
<style scoped>
.el-table {
    margin-top: 15px;
}
.upload-demo {
    display: inline-block;
    margin-top: 15px;
}
.el-pagination {
    margin-top: 15px;
    float: right;
}
.el-select {
    margin-left: 15px;
    margin-right: 15px;
}
</style>
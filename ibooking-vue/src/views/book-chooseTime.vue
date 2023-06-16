<template>
    <layout>
        <div>
            <el-steps :active="active" finish-status="success">
                <el-step title="选择自习室"></el-step>
                <el-step title="选择座位"></el-step>
                <el-step title="选择时间"></el-step>
                <el-step title="预约"></el-step>
            </el-steps>
            <div class="block" style="margin: 20px auto">
                <span class="demonstration">选择日期：  </span>
                <el-date-picker
                    v-model="value2"
                    align="right"
                    type="date"
                    placeholder="选择日期"
                    :picker-options="pickerOptions1"
                    @change="query">
                </el-date-picker>
            </div>
            <el-table :data="tableData" border style="width: 100%;">
                <el-table-column type="index" label="序号" align="center" width="55px" />
                <el-table-column prop="startTime" type="datatime" label="预约时间" align="center"/>
                <el-table-column prop="endTime" type="datatime" label="结束时间" align="center"/>
            </el-table>
            <el-pagination
                @current-change="handleCurrentChange"
                :current-page="currentPage"
                :page-size="10"
                layout="total, prev, pager, next, jumper"
                :total="total">
            </el-pagination>
            <div class="block" style="margin-top: 60px;margin-bottom: 20px;">
                <span class="demonstration">选择时间：  </span>
                <el-time-select
                    placeholder="起始时间"
                    v-model="startTime"
                    :picker-options="{
                        start: this.start,
                        step: '00:15',
                        end: this.end,
                    }"
                    @change="Limite(startTime)">
                </el-time-select>
                <span class="demonstration" style="margin-right:5px;margin-left:5px">至</span>
                <el-time-select
                    placeholder="结束时间"
                    v-model="endTime"
                    :picker-options="{
                        start: this.start,
                        step: '00:15',
                        end: this.end,
                        minTime: this.startTime,
                        maxTime: this.timeLimite
                    }">
                </el-time-select>
            </div>
            <el-button type="primary" @click="sure">确 定</el-button>
            <el-button @click="renew">重 置</el-button>
        </div>
    </layout>
</template>
<script>
import layout from '@/components/layout-page.vue'
import { getSeatBook, booking } from '@/api/request'
import BookChooseRoom from './book-chooseRoom.vue';

export default ({
    components: {
        layout,
    },
    data() {
      return {
        currentPage: 1,
        pagesize: 10,
        total: 10,
        tableData: [],
        active: 2,
        pickerOptions1: {
        //   disabledDate(time) {
        //     return time.getTime() < Date.now()-3600 * 1000 * 24;
        //   },
          shortcuts: [{
            text: '今天',
            onClick(picker) {
              picker.$emit('pick', new Date());
            }
          }, {
            text: '明天',
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() + 3600 * 1000 * 24);
              picker.$emit('pick', date);
            }
          }, {
            text: '后天',
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() + 3600 * 1000 * 24 * 2);
              picker.$emit('pick', date);
            }
          }
        ],
        },
        value2:'',
        chooseDd:'',
        seatNum:this.$route.query.seatnum,
        roomNum:this.$route.query.roomnum,
        start: this.$route.query.st,
        end: this.$route.query.et,
        startTime:'',
        endTime:'',
        timeLimite:'',
        
        form: {
            index: '',
            stuRoomNumber: '',
            buildingNumber: '',
            classRoomNumber: '',
            openStatus: '',
            time: '',
            returnTime:[],
        },
        open: true,

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
        next() {
            if (this.active++ > 3) this.active = 0;
        },
        query() {
            getSeatBook(this.seatNum).then((res) => {
                if(res) {
                    // console.log(typeof this.value2)
                    this.tableData = []
                    for (let i in res) {
                        let d = new Date(res[i].startTime)
                        let day = d.getDate()
                        let year = d.getFullYear()
                        let mon = d.getMonth() + 1
                        let dd = year+"-"+mon+"-"+day
                        
                        d = new Date(this.value2)
                        // console.log(d)
                        day = d.getDate()
                        year = this.value2.getFullYear()
                        mon = this.value2.getMonth() + 1
                        this.chooseDd = year+"-"+mon+"-"+day
                        if (dd==this.chooseDd){
                            this.tableData.push(res[i])
                        }
                    }
                    this.total = this.tableData.length
                } 
            })
        },
        sure(){
            if( !this.startTime || !this.endTime) {
                this.$message.info('请选择预约开始时间和结束时间！')
                return
            }
            let start = this.chooseDd + ' ' + this.startTime + ':00'
            let end = this.chooseDd+ ' ' + this.endTime + ':00'
            // console.log(typeof start, start)
            let data = {
                "seatId": this.seatNum,
                "startTime": start,
                "endTime": end,
                "isEnd": false,
                "isTimeout": false,
                "stuNum":"01010101",
                "name":"John Doe",
                "buildingNum":this.$route.query.bn,
                "classRoomNum":this.$route.query.crn,
            }
            console.log(data)
            booking(data).then((res) => {
                console.log(res)
                this.$message.success('预约成功')
                next()
            }).catch(err=>{
                console.log(err)
                this.$message.success('预约失败')
            })
        },
        renew(){
            this.value2=''
            query()
            this.startTime=''
            this.endTime=''
            this.timeLimite=''
            this.chooseDd=''
        }
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
.el-pagination {
    margin-top: 15px;
    float: right;
}
.item {
  width: 32px;
  height: 32px;
}
</style>

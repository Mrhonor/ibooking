<template>
    <layout>
        <div>
            <el-button type="primary" @click="query">查询</el-button>
            <el-table :data="tableData" border style="width: 100%">
                <el-table-column type="index" label="序号" align="center" width="55px"/>
                <el-table-column prop="id" label="学号" align="center" />
                <el-table-column prop="buildingNum" label="楼栋与教室" align="center"/>
                <el-table-column prop="stuNum" label="学生学号" align="center"/>
                <el-table-column prop="day" type="date" label="预约日期" align="center"/>
                <el-table-column prop="start" type="datatime" label="预约开始时间" align="center"/>
                <el-table-column prop="end" type="datatime" label="预约结束时间" align="center"/>
                <el-table-column
                    fixed="right"
                    label="操作"
                    align="center"
                    width="100">
                    <template slot-scope="scope">
                        <el-button @click="bookStu(scope.row)" type="text" size="small" :disabled="timeToBook(scope.row)? true:false">签到</el-button>
                        <el-button @click="unbookStu(scope.row)" type="text" size="small" >退预约</el-button>
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
        </div>
    </layout>
</template>
<script>
import layout from '@/components/layout-page.vue'
import { getStuBook,bookingDel,bookingCheck } from '@/api/request'

export default ({
    components: {
        layout
    },
    data() {
      return {
        currentPage: 1,
        pagesize: 10,
        total: 10,
        tableData: [],
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
        ]
      }
    },
    methods: {
        handleCurrentChange(page) {
            this.currentPage = page
            this.query()
        },
        query() {
            const stuNum = 1
            // getStuBook(stuNum).then((res) => {
            //     if(res) {
            let res = this.testdata
            // console.log(res)
            if(!res.length){
                this.tableData = [res]
            }else{
                this.tableData = res
            }
            for (let i in res){
                let st = new Date(this.tableData[i].startTime)
                
                let d = st.getDate()
                let y = st.getFullYear()
                let mon = st.getMonth()+1
                let h =  st.getHours()
                let m = new Date(st).getMinutes()
                let s = new Date(st).getSeconds()
                let start = h+':'+m+':'+s
                // console.log(y+'-'+ mon +'-'+d)
                this.tableData[i].day = y+'-'+ mon +'-'+d
                this.tableData[i].start = start
                
                let et = new Date(this.tableData[i].endTime)
                let eh =  et.getHours()
                let em = et.getMinutes()
                let es = et.getSeconds()
                let end = eh+':'+em+':'+es
                this.tableData[i].end = end
            }
            // console.log('最后的',this.tableData)
            // this.total = res.length
            //     } 
            // })
        },
        timeToBook(val){
            let now = new Date()
            if ((val.startTime > now + 60 * 15 * 1000) || (val.startTime +60*5*1000 < now))
            return false
            else return true
        },
        bookStu(val){
            bookingCheck(val.stuNum).then((res)=>{
                if(res){
                    this.$message.success('签到成功')
                }
                else{
                    this.$message.success('已超过签退时间，签到失败！')
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
                    seatId: val.seatId,
                    startTime: val.startTime,
                    endTime: val.endTime,
                    isEnd:val.isEnd,
                    isTimeout: val.isTimeout,
                    stuNum:val.stuNum,
                    name:val.name,
                    buildingNum: val.buildingNum,
                    classRoomNum: val.val.classRoomNum,
                }
                bookingDel(data).then((res) => {
                    if(res) {
                        this.query()
                        this.$message.success('退预约成功')
                    } else {
                        this.$message.success('退预约失败')
                    }
                })
            }).catch(() => {
                this.$message.info('已取消')
            })
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
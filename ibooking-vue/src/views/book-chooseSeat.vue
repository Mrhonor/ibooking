<template>
    <layout>
        <div>
            <el-steps :active="active" finish-status="success">
                <el-step title="选择自习室"></el-step>
                <el-step title="选择座位"></el-step>
                <el-step title="选择时间"></el-step>
                <el-step title="预约"></el-step>
            </el-steps>
            <el-table :data="tableData" border style="width: 100%; margin-top: 20px;">
                <el-table-column type="index" label="序号" align="center" width="55px" />
                <el-table-column prop="seatNum" label="座位编号" align="center" />
                <el-table-column prop="isVacant" label="是否空闲" align="center" />
                <el-table-column prop="hasOutlet" label="是否有插座" align="center"/>
                <el-table-column
                    fixed="right"
                    label="操作"
                    align="center"
                    width="100">
                    <template slot-scope="scope">
                        <el-button @click="chooseSeat(scope.row)" type="text" size="small" :disabled="scope.row.is_Vacant == 0 ? true:false">选择</el-button>
                        <!-- <el-button @click="chooseSeat(scope.row)" type="text" size="small" >选择</el-button> -->
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
import { getSeat, getSeatByStudyRoomIDAndSeatNum } from '@/api/request'

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
        active: 1,
        Visible: false,
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
        next() {
            if (this.active++ > 3) this.active = 0;
        },
        query() {
            let roomNum = this.$route.query.roomnum
            getSeat(roomNum).then((res) => {
                if(res) {
                    this.tableData = res
                    for (let i in res) {
                        if (res[i].isVacant) {
                            this.tableData[i].isVacant = "是"
                            this.tableData[i].is_Vacant = 1
                        }                            
                        else {
                            this.tableData[i].isVacant = "否"
                            this.tableData[i].is_Vacant = 0
                        }
                        if (res[i].hasOutlet) this.tableData[i].hasOutlet = "是"
                        else this.tableData[i].hasOutlet = "否"
                        // console.log(this.tableData[i].openStatus)
                    }
                    // this.is_Vacant = 
                    this.total = res.length
                } 
            })
        },
        chooseSeat(val){
            let roomNum = this.$route.query.roomnum
            let st =this.$route.query.st
            let h = st.split(":")[0]
            let m = st.split(":")[1]
            let start = h +':' + m
            let et = this.$route.query.et
            let eh = et.split(":")[0]
            let em = et.split(":")[0]
            let end = eh +':' + em
            let bn = this.$route.query.bn
            let crn = this.$route.query.crn
            let seatId = ''
           
            getSeatByStudyRoomIDAndSeatNum(val.seatNum, roomNum).then((res) => {
                if(res) {
                    seatId = res.id 
                    this.$router.replace('/book/seat/time?roomnum='+roomNum+'&seatnum='+val.seatNum+'&st='+start+'&et='+end+'&bn='+bn+'&crn='+crn+'&seatid='+seatId)
                } 
            }
            )
            // console.log('seatId:',seatId)
            // this.$router.replace('/book/seat/time?roomnum='+roomNum+'&seatnum='+val.seatNum+'&st='+start+'&et='+end+'&bn='+bn+'&crn='+crn+'&seatid='+seatId)
            
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
.el-pagination {
    margin-top: 15px;
    float: right;
}
.item {
  width: 32px;
  height: 32px;
}
</style>

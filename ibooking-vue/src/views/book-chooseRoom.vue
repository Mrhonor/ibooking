<template>
    <layout>
        <div>
            <el-steps :active="active" finish-status="success">
                <el-step title="选择自习室"></el-step>
                <el-step title="选择座位"></el-step>
                <el-step title="选择时间"></el-step>
                <el-step title="预约"></el-step>
            </el-steps>
            <!-- <el-button style="margin-top: 12px;" @click="next">下一步</el-button> -->
            <el-row :gutter="180" style="margin-top:20px">
                <el-col :span="6">
                    <el-button type="primary" @click="query" >所有自习室</el-button>
                </el-col>
                <el-col :span="6">
                    <el-button type="primary" @click="queryOpen" >开放中的自习室</el-button>
                </el-col>
                <el-col :span="6">
                    <el-input v-model="input" placeholder="请输入自习室编号" style="width: 200px"></el-input>
                </el-col>
                <el-col :span="6">
                    <el-button type="primary" @click="queryId">查询</el-button>
                </el-col>
                
            </el-row>
            <el-table :data="tableData" border style="width: 100%">
                <el-table-column prop="id" label="序号" align="center" width="55px" />
                <el-table-column prop="stuRoomNumber" label="编号" align="center" />
                <el-table-column prop="buildingNumber" label="所处楼栋" align="center" />
                <el-table-column prop="classRoomNumber" label="所处教室" align="center"/>
                <el-table-column prop="openStatus" label="是否开放" align="center"/>
                <el-table-column prop="startTime" type="datatime" label="开放时间" align="center"/>
                <el-table-column prop="endTime" type="datatime" label="关闭时间" align="center"/>
                <el-table-column
                    fixed="right"
                    label="操作"
                    align="center"
                    width="100">
                    <template slot-scope="scope">
                        <!-- <el-button @click="chooseRoom(scope.row)" type="text" size="small">选择</el-button> -->
                        <el-button @click="chooseRoom(scope.row)" type="text" size="small" :disabled="scope.row.open == 0 ? true:false">选择</el-button>
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
            <!-- <el-dialog title="预约" :visible.sync="Visible" width="520px">
                <svg class="seat" v-if="item.type===0" fill="green" viewBox="0 0 1024 1024"
                    xmlns="http://www.w3.org/2000/svg" width="32" height="32">
                    <path style="pointer-events: none"
                            d="M782.873176 625.631535v-125.712101c0-26.354333-21.440885-47.795218-47.795218-47.795218H288.922042c-26.354333 0-47.795218 21.440885-47.795218 47.795218v125.712101c0 26.354333 21.440885 47.795218 47.795218 47.795218h446.155916c26.354333-0.002122 47.795218-21.440885 47.795218-47.795218zM687.053516 409.675423c9.550978-98.80381 26.884943-284.088547 30.125908-359.628296 0.534855-12.477823-4.130268-24.52479-13.135779-33.922953C694.229484 5.877035 680.437872 0 666.202669 0H359.437977c-14.23308 0-28.026816 5.877035-37.840976 16.122052-9.005511 9.398163-13.668511 21.44513-13.133657 33.922952 3.238843 75.539749 20.57493 260.822363 30.125908 359.628297h348.464264zM735.077958 715.873424h-105.111701l73.011924 292.04345a21.224396 21.224396 0 0 0 41.814183-5.146916V715.342814c-3.194272 0.343835-6.430992 0.53061-9.714406 0.53061zM288.922042 715.873424c-3.281292 0-6.520135-0.186775-9.714406-0.53061v287.427144a21.224396 21.224396 0 0 0 41.814183 5.146916l73.011924-292.04345h-105.111701z"
                    ></path>
                </svg>
            </el-dialog> -->
        </div>
    </layout>
</template>
<script>
import layout from '@/components/layout-page.vue'
import { getStudyRoom, getStudyRoomOpen,getStudyRoomId,bookingDefStu } from '@/api/request'

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
        active: 0,
        input:'',
        // Visible: false,
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
            getStudyRoom().then((res) => {
                if(res) {
                    if(!res.length){
                        this.tableData = [res]
                    }else{
                        this.tableData = res
                    }
                        for (let i in res) {
                            if (res[i].openStatus) {
                                this.tableData[i].open = 1
                                this.tableData[i].openStatus = "是"
                            }
                            else {
                                this.tableData[i].openStatus = "否"
                                this.tableData[i].open = 0
                            }
                            // console.log(this.tableData[i].openStatus)
                        }
                        this.total = res.length
                } 
            })
        },
        queryOpen(){
            getStudyRoomOpen().then((res) => {
                if(res) {
                    if(!res.length){
                        this.tableData = [res]
                    }else{
                        this.tableData = res
                    }
                        for (let i in res) {
                            if (res[i].openStatus) {
                                this.tableData[i].open = 1
                                this.tableData[i].openStatus = "是"
                            }
                            else {
                                this.tableData[i].openStatus = "否"
                                this.tableData[i].open = 0
                            }
                            // console.log(this.tableData[i].openStatus)
                        }
                        this.total = res.length
                } 
            })
        },
        queryId(){
            getStudyRoomId(this.input).then((res) => {
                console.log(res)
                if(res) {
                    if(!res.length){
                        this.tableData = [res]
                    }else{
                        this.tableData = res
                    }
                    for (let i in this.tableData) {
                        if (this.tableData[i].openStatus) {
                            this.tableData[i].open = 1
                            this.tableData[i].openStatus = "是"
                        }
                        else {
                            this.tableData[i].openStatus = "否"
                            this.tableData[i].open = 0
                        }
                        // console.log(this.tableData[i].openStatus)
                    }
                    this.total = res.length
                    }
                } 
            )
        },
        chooseRoom(val){
            const stuNum = localStorage.getItem('username')
            bookingDefStu(stuNum).then((res) => {
                if(res) {
                    // 获取7天前的日期
                    const sevenDaysAgo = new Date();
                    sevenDaysAgo.setDate(sevenDaysAgo.getDate() - 7);

                    // 过滤出7天内的违约记录
                    const defaultBookingsWithin7Days = res.filter(booking => {
                        return new Date(booking.startTime) >= sevenDaysAgo;
                    });

                    // 计算数量
                    const defaultBookingsCount = defaultBookingsWithin7Days.length;
                    console.log(sevenDaysAgo)
                    console.log(defaultBookingsWithin7Days)
                    console.log(defaultBookingsCount) 
                    if(defaultBookingsCount > 3){
                        this.$message({
                            message: '您七天内有超过三条违约记录，不能预约',
                            type: 'warning'
                        })
                    }else{
                        this.$router.replace('/book/seat?roomnum='+val.stuRoomNumber+'&st='+val.startTime+'&et='+val.endTime+'&bn='+val.buildingNumber+'&crn='+val.classRoomNumber)
                    }
                } 
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
.el-pagination {
    margin-top: 15px;
    float: right;
}
.el-row {
    margin-bottom: 20px;
    &:last-child {
      margin-bottom: 0;
    }
  }
.el-col {
    border-radius: 4px;
}
.item {
  width: 32px;
  height: 32px;
}
</style>

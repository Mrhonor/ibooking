<template>
    <layout>
        <div>
            <el-button type="primary" @click="query">查询</el-button>
            <el-table :data="tableData" border style="width: 100%">
                <el-table-column type="index" label="序号" align="center" width="55px" />
                <el-table-column prop="stuRoomNumber" label="编号" align="center" />
                <el-table-column prop="buildingNumber" label="所处楼栋" align="center" />
                <el-table-column prop="classRoomNumber" label="所处教室" align="center"/>
                <el-table-column prop="openStatus" label="是否开放" align="center">
                    <!-- <template slot-scope="scope">
                        <el-checkbox :true-label="1" :false-label="0" v-model="scope.row.displayStatus" @change="changeStatus"></el-checkbox>
                    </template> -->
                </el-table-column>
                <el-table-column prop="startTime" type="datatime" label="开放时间" align="center"/>
                <el-table-column prop="endTime" type="datatime" label="关闭时间" align="center"/>
                <el-table-column
                    fixed="right"
                    label="操作"
                    align="center"
                    width="100">
                    <template slot-scope="scope">
                        <el-button @click="renewRoom(scope.row)" type="text" size="small">修改</el-button>
                        <el-button @click="deleteRoom(scope.row)" type="text" size="small">删除</el-button>
                        <el-button @click="manSeat(scope.row)" type="text" size="small">座位管理</el-button>
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
            <el-button type="primary" @click="add">添加</el-button>
            <el-dialog title="更新自习室信息" :visible.sync="Visible" width="520px">
                <el-form :model="form">
                    <el-form-item label="自习室编号" >
                        <el-input v-model="form.stuRoomNumber" style="width: 385px"/>
                    </el-form-item>
                    <el-form-item label="所处楼栋" >
                        <el-input v-model="form.buildingNumber" style="width: 400px"/>
                    </el-form-item>
                    <el-form-item label="所处教室" >
                        <el-input v-model="form.classRoomNumber" style="width: 400px"/>
                    </el-form-item>
                    <el-form-item label="是否开放" >
                        <el-select v-model="form.openStatus" style="width: 400px">
                            <el-option label="是" value="true"></el-option>
                            <el-option label="否" value="false"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="开放时间" >
                      <!-- <el-date-picker
                        v-model="form.time"
                        type="datetimerange"
                        range-separator="至"
                        start-placeholder="开始日期"
                        end-placeholder="结束日期"
                        value-format="yyyy-MM-dd HH:mm:ss">
                        :default-value="form.returnTime"
                        </el-date-picker> -->
                        <el-time-picker
                            v-model="form.time"
                            format="HH:mm:ss"
                            value-format="HH:mm:ss"
                            style="width: 380px"
                            is-range
                            range-separator="至"
                            start-placeholder="开始时间"
                            end-placeholder="结束时间"
                            placeholder="选择时间范围"
                        />
                    </el-form-item>
                </el-form>
                <span slot="footer" class="dialog-footer">
                  <el-button type="primary" @click="sure">确 定</el-button>
                  <el-button @click="Visible = false">取 消</el-button>
                </span>
            </el-dialog>

            <el-dialog title="添加自习室信息" :visible.sync="addVisible" width="520px">
                <el-form :model="form">
                    <el-form-item label="自习室编号" >
                        <el-input v-model="form.stuRoomNumber" style="width: 385px"/>
                    </el-form-item>
                    <el-form-item label="所处楼栋" >
                        <el-input v-model="form.buildingNumber" style="width: 400px"/>
                    </el-form-item>
                    <el-form-item label="所处教室" >
                        <el-input v-model="form.classRoomNumber" style="width: 400px"/>
                    </el-form-item>
                    <el-form-item label="是否开放" >
                        <el-select v-model="form.openStatus" style="width: 400px">
                            <el-option label="是" value="true"></el-option>
                            <el-option label="否" value="false"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="开放时间" >
                      <!-- <el-date-picker
                        v-model="form.time"
                        type="datetimerange"
                        range-separator="至"
                        start-placeholder="开始日期"
                        end-placeholder="结束日期"
                        value-format="yyyy-MM-dd HH:mm:ss">
                        :default-value="form.returnTime"
                        </el-date-picker> -->
                        <el-time-picker
                            v-model="form.time"
                            format="HH:mm:ss"
                            value-format="HH:mm:ss"
                            style="width: 380px"
                            is-range
                            range-separator="至"
                            start-placeholder="开始时间"
                            end-placeholder="结束时间"
                            placeholder="选择时间范围"
                        />
                    </el-form-item>
                </el-form>
                <span slot="footer" class="dialog-footer">
                  <el-button type="primary" @click="addSure">确 定</el-button>
                  <el-button @click="addVisible = false">取 消</el-button>
                </span>
            </el-dialog>

            <el-dialog title="删除自习室信息" :visible.sync="deleteVisible" width="520px">
                <el-form>
                    <el-form-item label="自习室编号" >
                        <el-input v-model="form.stuRoomNumber" style="width: 385px"/>
                    </el-form-item>
                </el-form>
                <span slot="footer" class="dialog-footer">
                  <el-button type="primary" @click="deleteSure(form.stuRoomNumber)">确 定</el-button>
                  <el-button @click="deleteVisible = false">取 消</el-button>
                </span>
            </el-dialog>
        </div>
    </layout>
</template>
<script>
import layout from '@/components/layout-page.vue'
import { getStudyRoom, renewStudyRoom, addStudyRoom, deleteStudyRoom } from '@/api/request'

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
        Visible: false,
        addVisible: false,
        deleteVisible: false,
        bookingVal: '',
        bookingInfo: [],
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
        status: '',
      }
    },
    methods: {
        handleCurrentChange(page) {
            this.currentPage = page
            this.query()
        },
        query() {
            // this.$message.info('query')
            getStudyRoom().then((res) => {
                if(res) {
                    this.tableData = res
                    for (let i in res) {
                        if (res[i].openStatus) this.tableData[i].openStatus = "是"
                        else this.tableData[i].openStatus = "否"
                        // console.log(this.tableData[i].openStatus)
                    }
                    this.total = res.length
                } 
            })
        },
        renewRoom(val) {
            // this.form.id = val.index
            this.form.stuRoomNumber = val.stuRoomNumber
            this.form.buildingNumber = val.buildingNumber
            this.form.classRoomNumber = val.classRoomNumber
            // console.log(val.openStatus)
            // if (val.openStatus=="是") this.status = "true"
            // else this.status = "false"
            // console.log(this.status)
            this.form.openStatus = val.openStatus
            // console.log('更新', this.form.openStatus)
            this.form.returnTime.push(val.startTime, val.endTime)
            // this.form.time.push(val.startTime, val.endTime)
            this.Visible = true
        },
        sure() {
            if( this.form.openStatus=="是" && !this.form.time) {
                this.$message.info('请选择自习室开放时间和结束时间！')
                return
            }
            if(!this.form.time){
                this.form.time = [null, null]//灰色
            }
            if (this.form.openStatus == "是"||this.form.openStatus=="true") this.open = true
            else this.open=false
            let data = {
                // id: this.form.index,
                stuRoomNumber: this.form.stuRoomNumber,
                buildingNumber: this.form.buildingNumber,
                classRoomNumber: this.form.classRoomNumber,
                openStatus: this.open,
                startTime: this.form.time[0],
                endTime: this.form.time[1],
            }
            renewStudyRoom(data).then((res) => {
                this.Visible = false
                // 刷新页面
                this.form = {
                        index: '',
                        stuRoomNumber: '',
                        buildingNumber: '',
                        classRoomNumber: '',
                        openStatus: '',
                        time: '',
                        returnTime:[],
                }
                this.$message.success('更新成功')
                getStudyRoom().then((res) => {
                if(res) {
                    this.tableData = res
                    for (let i in res) {
                        if (res[i].openStatus) this.tableData[i].openStatus = "是"
                        else this.tableData[i].openStatus = "否"
                    }
                    this.total = res.length
                } 
            })
            }).catch(err=>{
                console.log(err)
                this.$message.success('更新失败')
            })
        },
        add(){
            this.addVisible = true
        },
        addSure(){
            if( this.form.openStatus=="是" && !this.form.time) {
                this.$message.info('请选择自习室开放时间和结束时间！')
                return
            }
            if(!this.form.time){
                this.form.time = [null, null]//灰色
            }
            // console.log(this.form.openStatus)
            if (this.form.openStatus == "是"||this.form.openStatus=="true") this.open = true
            else this.open=false
            let data = {
                // id: this.form.index,
                stuRoomNumber: this.form.stuRoomNumber,
                buildingNumber: this.form.buildingNumber,
                classRoomNumber: this.form.classRoomNumber,
                openStatus: this.open,
                startTime: this.form.time[0],
                endTime: this.form.time[1],
            }
            console.log(data)
            addStudyRoom(data).then((res) => {
                this.addVisible = false
                // 刷新页面
                this.form = {
                        index: '',
                        stuRoomNumber: '',
                        buildingNumber: '',
                        classRoomNumber: '',
                        openStatus: '',
                        time: '',
                        returnTime:[],
                }
                this.$message.success('更新成功')
                getStudyRoom().then((res) => {
                if(res) {
                    this.tableData = res
                    for (let i in res) {
                        if (res[i].openStatus) this.tableData[i].openStatus = "是"
                        else this.tableData[i].openStatus = "否"
                    }
                    this.total = res.length
                } 
            })
            }).catch(err=>{
                console.log(err)
                this.$message.success('更新失败')
            })
        },
        deleteRoom(val){
            this.form.stuRoomNumber = val.stuRoomNumber
            this.deleteVisible = true
        },
        deleteSure(data){
            // console.log(data)
            deleteStudyRoom(data).then((res) => {
                this.deleteVisible = false
                // 刷新页面
                this.form = {
                        index: '',
                        stuRoomNumber: '',
                        buildingNumber: '',
                        classRoomNumber: '',
                        openStatus: '',
                        time: '',
                        returnTime:[],
                }
                this.$message.success('删除成功')
                getStudyRoom().then((res) => {
                if(res) {
                    this.tableData = res
                    for (let i in res) {
                        if (res[i].openStatus) this.tableData[i].openStatus = "是"
                        else this.tableData[i].openStatus = "否"
                    }
                    this.total = res.length
                } 
            })
            }).catch(err=>{
                console.log(err)
                this.$message.success('删除失败')
            })
        },
        manSeat(val){
            this.$router.replace('/seatmanage?roomnum='+val.stuRoomNumber)
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
</style>

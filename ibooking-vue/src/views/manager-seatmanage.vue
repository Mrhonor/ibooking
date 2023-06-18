<template>
    <layout>
        <div>
            <!-- <el-button type="primary" @click="add" :disabled="find()? false:true">添加</el-button> -->
            <el-button type="primary" @click="add">添加</el-button>
            <el-table :data="tableData" border style="width: 100%">
                <el-table-column type="index" label="序号" align="center" width="55px" />
                <el-table-column prop="seatNum" label="座位编号" align="center" />
                <el-table-column prop="studyRoomId" label="自习室编号" align="center" />
                <el-table-column prop="is_Vacant" label="是否空闲" align="center" />
                <el-table-column prop="has_Outlet" label="是否有插座" align="center"/>
                <el-table-column
                    fixed="right"
                    label="操作"
                    align="center"
                    width="100">
                    <template slot-scope="scope">
                        <el-button @click="delSeat(scope.row)" type="text" size="small" >删除</el-button>
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
            <el-dialog title="添加座位" :visible.sync="addVisible" width="520px">
                <el-form :model="form" :label-position="labelPosition">
                    <el-form-item label="座位编号" >
                        <el-input v-model="form.seatNum" style="width: 450px"/>
                    </el-form-item>
                    <el-form-item label="自习室编号" >
                        <el-input v-model="form.studyRoomId" style="width: 450px"/>
                    </el-form-item>
                    <el-form-item label="是否有插座" >
                        <el-select v-model="form.hasOutlet" style="width: 450px">
                            <el-option label="是" value="1"></el-option>
                            <el-option label="否" value="0"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="可用时间" >
                      <el-date-picker
                        v-model="form.time"
                        type="datetimerange"
                        range-separator="至"
                        start-placeholder="开始时间"
                        end-placeholder="结束时间"
                        value-format="yyyy-MM-dd HH:mm:ss">
                        </el-date-picker>
                    </el-form-item>
                </el-form>
                <span slot="footer" class="dialog-footer">
                  <el-button type="primary" @click="sure">确 定</el-button>
                  <el-button @click="addVisible = false">取 消</el-button>
                </span>
            </el-dialog>
        </div>
    </layout>
</template>
<script>
import layout from '@/components/layout-page.vue'
import { getSeat,deleteSeat, addSeat, getAllSeat } from '@/api/request'

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
        addVisible: false,
        form: {
            seatNum: '',
            studyRoomId: '',
            hasOutlet: '',
            isVacant: true,
            time: [],
        },
        labelPosition:"top",
        has:1
      }
    },
    methods: {
        handleCurrentChange(page) {
            this.currentPage = page
            this.query()
        },
        find(){
            let roomNum = this.$route.query.roomnum
            return !roomNum
        },
        query() {
            let roomNum = this.$route.query.roomnum
            console.log('roomNum: ', roomNum)
            if (!roomNum){
                getAllSeat().then((res) => {
                    if (res){
                        if(!res.length){
                            this.tableData = [res]
                        }else{
                            this.tableData = res
                        }
                        for (let i in res){
                            this.tableData[i] = res[i]
                            if (res[i].isVacant) this.tableData[i].is_Vacant = "是"
                            else this.tableData[i].is_Vacant = "否"
                            if (res[i].hasOutlet) this.tableData[i].has_Outlet = "是"
                            else this.tableData[i].has_Outlet = "否"
                            console.log(res[i].hasOutlet)
                        }
                        this.total = res.length
                        }
                })
            }
            else {
                getSeat(roomNum).then((res) => {
                    console.log(res)
                if(res) {
                    this.tableData = res
                    for (let i in res) {
                        if (res[i].isVacant) {
                            this.tableData[i].is_Vacant = "是"
                        }                            
                        else {
                            this.tableData[i].is_Vacant = "否"
                        }
                        if (res[i].hasOutlet) this.tableData[i].has_Outlet = "是"
                        else this.tableData[i].has_Outlet = "否"
                        // console.log(this.tableData[i].openStatus)
                    }
                    // this.is_Vacant = 
                    this.total = res.length
                } 
            })
        }},
        add(){
            this.addVisible = true
        },
        delSeat(val){
            this.$confirm('确定要删除这个座位吗?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                // console.log(typeof val.seatNum)
                let data = {
                    id:val.id,
                }
                deleteSeat(data).then((res) => {
                    this.query()
                    this.$message.success('删除成功')
                })
            }).catch(() => {
                this.$message.info('已取消')
            })
        },
        sure(){
            if( !this.form.seatNum || !this.form.time) {
                this.$message.info('请填写座位编号、开放时间和结束时间！')
                return
            }
            // console.log(this.form.hasOutlet)
            if (this.form.hasOutlet) {this.has = 1}
            else {this.has = 0}
            let data={
                seatNum: parseInt(this.form.seatNum),
                studyRoomId:parseInt(this.form.studyRoomId),
                start:this.form.time[0],
                end:this.form.time[1],
                hasOutlet: this.has,
                isVacant:1,
            }
            // console.log(data)
            addSeat(data).then((res) => {
                    this.addVisible = false
                    this.form= {
                        seatNum: '',
                        studyRoomId: '',
                        hasOutlet: '',
                        isVacant: true,
                        time: [],
                    }
                    this.query()
                    this.$message.success('更新成功')
                // console.log(res)
            }).catch(err=>{
                console.log(err)
                this.$message.success('更新失败')
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
.el-pagination {
    margin-top: 15px;
    float: right;
}
.item {
  width: 32px;
  height: 32px;
}
</style>
